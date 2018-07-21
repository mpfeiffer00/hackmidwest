package com.cerner.engineering;

import com.cerner.engineering.auth.AuthorizationAssistant;
import com.cerner.engineering.http.HttpClientHelper;
import com.cerner.engineering.object.Crucible;
import com.cerner.engineering.object.CrucibleInstance;
import com.cerner.engineering.object.Review;
import com.cerner.engineering.object.Reviewer;
import com.cerner.engineering.object.ReviewsResponse;
import com.cerner.engineering.object.User;
import com.cerner.system.exception.Verifier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.http.auth.InvalidCredentialsException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.ISODateTimeFormat;

/**
 * A {@link Callable} to allow concurrent review retrieval.
 * @author Aaron McGinn (am025347)
 */
public class ReviewRetrieverCallable implements Callable<ReviewsResponse>
{
    private final CrucibleInstance instance;
    private final String username;
    private final Long fromMillis;

    private ReviewRetrieverCallable(final CrucibleInstance instance, final String username, final Long fromMillis)
    {
        this.instance = instance;
        this.username = username;
        this.fromMillis = fromMillis;
    }

    /**
     * Create a {@link ReviewRetrieverCallable}.
     * @param instance
     *            The {@link Crucible} instance to call (cannot be null).
     * @param username
     *            The username to request reviews for (cannot be null, empty, or blank).
     * @param fromMillis
     *            The milliseconds to use as the start time to retrieve reviews (may be null).
     * @return The instantiated {@link ReviewRetrieverCallable}.
     */
    public static ReviewRetrieverCallable create(final CrucibleInstance instance, final String username, final Long fromMillis)
    {
        Verifier.verifyNotNull(instance);
        Verifier.verifyNotBlank(username);

        return new ReviewRetrieverCallable(instance, username, fromMillis);
    }

    @Override
    public ReviewsResponse call() throws Exception
    {
        final List<Review> reviews = new ArrayList<>();
        final StringBuilder errorInformation = new StringBuilder();

        try (final CloseableHttpClient httpClient = HttpClientHelper.getNewHttpClient())
        {
            HttpClientHelper.trustAllHosts();

            final StringBuilder authorURL = new StringBuilder(instance.getBaseURL()).append(Crucible.FILTER_DETAILS)
                    .append(Crucible.SEARCH_QUERY_PARAMS.replace("{username}", username)).append(instance.getAuthToken());
            if (fromMillis != null)
            {
                authorURL.append(Crucible.FROM_DATE_QUERY_PARAMS.replace("{fromMillis}", fromMillis.toString()));
            }

            final HttpRequestBase authorRequest = new HttpGet(authorURL.toString());
            try (CloseableHttpResponse reviewResponse = httpClient.execute(authorRequest))
            {
                if (reviewResponse.getStatusLine().getStatusCode() == 401)
                {
                    final boolean authorizationSuccessful = AuthorizationAssistant.retryInstanceAuthorization(instance);

                    if (!authorizationSuccessful)
                    {
                        throw new InvalidCredentialsException("Unauthorized for instance: " + instance);
                    }
                }

                final JSONObject authorJsonResponse = new JSONObject(EntityUtils.toString(reviewResponse.getEntity()));

                if (!authorJsonResponse.isNull("detailedReviewData"))
                {
                    final JSONArray authorReviewData = authorJsonResponse.getJSONArray("detailedReviewData");

                    for (int i = 0; i < authorReviewData.length(); i++)
                    {
                        final JSONObject jsonReview = authorReviewData.getJSONObject(i);
                        final Review.Builder reviewBuilder = getReviewFromJson(jsonReview);
                        final Review review = reviewBuilder.withCrucibleInstance(instance).build();

                        reviews.add(review);
                    }
                }
            }
        }
        catch (final Exception e)
        {
            errorInformation.append("Error retrieving reviews from " + instance.getBaseURL() + "<br>");
            e.printStackTrace();
        }

        return ReviewsResponse.Builder.create().withInstance(instance).withReviews(reviews).withErrorInformation(errorInformation.toString()).build();
    }

    /**
     * Parse the provided JSON and create a review from it.
     * @param jsonReview
     *            The review JSON to parse.
     * @return The {@link Review}.
     * @throws JSONException
     *             If an error occurs parsing the JSON.
     */
    private Review.Builder getReviewFromJson(final JSONObject jsonReview) throws JSONException
    {
        final String name = jsonReview.getString("name");
        final String permaId = jsonReview.getJSONObject("permaId").getString("id");

        final JSONObject author = jsonReview.getJSONObject("author");
        final String userName = author.getString("userName");
        final String displayName = author.getString("displayName");
        final String avatarUrl = author.getString("avatarUrl");
        final String authorUrl = author.getString("url");

        final String createDate = jsonReview.getString("createDate");
        final String formattedCreateDate = createDate.substring(0, createDate.length() - 2).concat(":")
                .concat(createDate.substring(createDate.length() - 2));

        final List<Reviewer> reviewers = getReviewers(jsonReview.getJSONObject("reviewers"));
        boolean allReviewersComplete = true;

        for (final Reviewer reviewer : reviewers)
        {
            if (allReviewersComplete)
            {
                allReviewersComplete = reviewer.isCompleted();
            }
        }

        final User authorUser = User.Builder.create().withUsername(userName).withDisplayName(displayName).withAvatarUrl(avatarUrl).withUrl(authorUrl)
                .build();

        final Review.Builder review = Review.Builder.create().withPermaId(permaId).withAuthor(authorUser).withName(name).withReviewers(reviewers)
                .withAllReviewersComplete(allReviewersComplete)
                .withDateCreated(LocalDateTime.parse(formattedCreateDate, ISODateTimeFormat.dateTimeParser()));

        if (!jsonReview.isNull("moderator"))
        {
            final JSONObject jsonModerator = jsonReview.getJSONObject("moderator");
            final String moderatorUserName = jsonModerator.getString("userName");
            final String moderatorDisplayName = jsonModerator.getString("displayName");
            final String moderatorAvatarUrl = jsonModerator.getString("avatarUrl");
            final String moderatorAuthorUrl = jsonModerator.getString("url");

            final User moderator = User.Builder.create().withUsername(moderatorUserName).withDisplayName(moderatorDisplayName)
                    .withAvatarUrl(moderatorAvatarUrl).withUrl(moderatorAuthorUrl).build();
            review.withModerator(moderator);
        }

        return review;
    }

    /**
     * Get the {@link Reviewer}s from the JSON reviewers object.
     * @param jsonReviewers
     *            The JSON object with key "reviewers" in the review response.
     * @return A non-null List of {@link Reviewer}s.
     * @throws JSONException
     *             If an error occurs reading the JSON information.
     */
    private List<Reviewer> getReviewers(final JSONObject jsonReviewers) throws JSONException
    {
        if (jsonReviewers == null)
        {
            return new ArrayList<>();
        }

        final List<Reviewer> reviewers = new ArrayList<>();
        final JSONArray jsonReviewerList = jsonReviewers.getJSONArray("reviewer");

        for (int i = 0; i < jsonReviewerList.length(); i++)
        {
            final JSONObject jsonReviewer = jsonReviewerList.getJSONObject(i);
            final String username = jsonReviewer.getString("userName");
            final String displayName = jsonReviewer.getString("displayName");
            final String avatarUrl = jsonReviewer.getString("avatarUrl");

            final User user = User.Builder.create().withUsername(username).withDisplayName(displayName).withAvatarUrl(avatarUrl).build();

            final boolean completed = jsonReviewer.getBoolean("completed");
            Long dateCompletedMillis = null;
            if (completed)
            {
                dateCompletedMillis = jsonReviewer.getLong("completionStatusChangeDate");
            }

            final Reviewer.Builder reviewerBuilder = Reviewer.Builder.create().withUser(user).withCompleted(completed);
            if (dateCompletedMillis != null)
            {
                reviewerBuilder.withCompletedDateTime(new DateTime(dateCompletedMillis));
            }

            reviewers.add(reviewerBuilder.build());
        }

        return reviewers;
    }
}
