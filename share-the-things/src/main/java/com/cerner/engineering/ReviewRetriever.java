package com.cerner.engineering;

import com.cerner.engineering.auth.AuthorizationAssistant;
import com.cerner.engineering.object.Crucible;
import com.cerner.engineering.object.CrucibleInstance;
import com.cerner.engineering.object.Review;
import com.cerner.engineering.object.Reviewer;
import com.cerner.engineering.object.ReviewsResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Instant;

import com.google.common.collect.Maps;
import com.google.common.collect.SetMultimap;

/**
 * Retriever for getting reviews for a username.
 * @author Aaron McGinn (am025347)
 */
public class ReviewRetriever
{
    private final ExecutorService service = Executors.newFixedThreadPool(4);

    /**
     * Format the response for HTML display.
     * @param username
     *            The username to search for reviews.
     * @param reviews
     *            The {@link SetMultimap} of {@link Review}s keyed by their {@link Crucible} instance.
     * @return The formatted HTML to be returned to the page for displaying to the user.
     */
    public static String formatResponse(final String username, final List<Review> reviews)
    {
        final StringBuilder response = new StringBuilder();

        final List<Review> reviewsToComplete = getReviewsToComplete(username, reviews);
        final List<Review> reviewsToClose = getReviewsToClose(username, reviews);
        final List<Review> outgoingReviews = getOutgoingReviews(username, reviews);
        final List<Review> completedReviews = getCompletedReviews(username, reviews);

        if (reviewsToComplete.isEmpty() && reviewsToClose.isEmpty() && outgoingReviews.isEmpty())
        {
            response.append("You have no reviews to address! Congratulations!<br>");
        }

        if (!reviewsToComplete.isEmpty())
        {

            response.append("<h3>Reviews to Complete (" + reviewsToComplete.size() + "):</h3>");

            response.append(getReviewTableHeaderRow());

            for (final Review review : reviewsToComplete)
            {
                response.append(convertReviewToTableRow(review));
            }
            response.append("</table><br>");
        }

        if (!reviewsToClose.isEmpty())
        {

            response.append("<h3>Reviews to Close (" + reviewsToClose.size() + "):</h3>");

            response.append(getReviewTableHeaderRow());

            for (final Review review : reviewsToClose)
            {
                response.append(convertReviewToTableRow(review));
            }
            response.append("</table><br>");
        }

        if (!outgoingReviews.isEmpty())
        {
            response.append("<h3>Outgoing Reviews (" + outgoingReviews.size() + "):</h3>");
            response.append(getReviewTableHeaderRow());

            for (final Review review : outgoingReviews)
            {
                response.append(convertReviewToTableRow(review));
            }
            response.append("</table><br>");
        }

        if (!completedReviews.isEmpty())
        {
            response.append("<h3>Completed Reviews (" + completedReviews.size() + "):</h3>");
            response.append(getReviewTableHeaderRow());

            for (final Review review : completedReviews)
            {
                response.append(convertReviewToTableRow(review));
            }
            response.append("</table><br>");
        }

        return response.toString();
    }

    /**
     * Get the Crucible reviews for the username.
     * @param username
     *            The username to retrieve reviews for.
     * @param daysAgo
     *            The number of days ago to begin looking at reviews.
     * @return A non-null {@link List} of {@link Review}s.
     */
    public ReviewsResponse getReviews(final String username, final Long daysAgo)
    {
        Long fromMillis = null;

        if (daysAgo != null && daysAgo >= 0)
        {
            try
            {
                final Integer numDaysAgo = daysAgo.intValue();

                final long millisAgo = Math.min(TimeUnit.DAYS.toMillis(numDaysAgo), Instant.now().getMillis());
                fromMillis = DateTime.now().withTimeAtStartOfDay().getMillis() - millisAgo;
            }
            catch (@SuppressWarnings("unused") final NumberFormatException e)
            {
                // Just don't use the days ago
            }
        }

        final List<Review> reviews = new ArrayList<>();
        final Map<CrucibleInstance, Future<ReviewsResponse>> futures = Maps.newHashMap();
        final StringBuilder errorInformation = new StringBuilder();

        final Set<Crucible> unauthorizedInstances = AuthorizationAssistant.getUnauthorizedInstances();

        for (final Crucible unauthorizedInstance : unauthorizedInstances)
        {
            AuthorizationAssistant.retryInstanceAuthorization(unauthorizedInstance);
        }

        for (final CrucibleInstance instance : AuthorizationAssistant.getCrucibleInstances())
        {
            final ReviewRetrieverCallable callable = ReviewRetrieverCallable.create(instance, username, fromMillis);
            final Future<ReviewsResponse> future = service.submit(callable);
            futures.put(instance, future);
        }

        final long inTwoMinutes = Instant.now().plus(Duration.standardMinutes(2).getMillis()).getMillis();
        for (final Entry<CrucibleInstance, Future<ReviewsResponse>> futureEntry : futures.entrySet())
        {
            try
            {
                final long timeout = inTwoMinutes - Instant.now().getMillis();
                final ReviewsResponse reviewsResponse = futureEntry.getValue().get(timeout, TimeUnit.MILLISECONDS);

                reviews.addAll(reviewsResponse.getReviews());
                if (reviewsResponse.getErrorInformation() != null)
                {
                    errorInformation.append(reviewsResponse.getErrorInformation());
                }
            }
            catch (@SuppressWarnings("unused") InterruptedException | ExecutionException e)
            {
                errorInformation.append("Error retrieving information from Crucible instance: " + futureEntry.getKey().getBaseURL());
            }
            catch (@SuppressWarnings("unused") final TimeoutException e)
            {
                errorInformation.append("Timeout occurred retrieving information from Crucible instance: " + futureEntry.getKey().getBaseURL());
            }
        }

        final Set<Crucible> updatedUnauthorizedInstances = AuthorizationAssistant.getUnauthorizedInstances();

        for (final Crucible unauthorizedInstance : updatedUnauthorizedInstances)
        {
            errorInformation.append("\nCould not authorize for Crucible instance: " + unauthorizedInstance.getBaseUrl());
        }

        return ReviewsResponse.Builder.create().withReviews(reviews).withErrorInformation(errorInformation.toString()).build();
    }

    /**
     * Create the review table's header row.
     * @return The HTML formatted header row for the review table.
     */
    private static String getReviewTableHeaderRow()
    {
        return new StringBuilder().append("<table style=\"border: 1px solid black; border-collapse: collapse;\"><tr>")
                .append("<th style=\"border: 1px solid black; padding: 10px;\">Author</th>")
                .append("<th style=\"border: 1px solid black; padding: 10px;\">Review Id</th>")
                .append("<th style=\"border: 1px solid black; padding: 10px; min-width: 14ch; text-align: center;\">Date Created</th>")
                .append("<th style=\"border: 1px solid black; padding: 10px;\">Review Name</th></tr>").toString();
    }

    /**
     * Create an HTML link string from the review.
     * @param review
     *            The review to create a link to.
     * @return The HTML formatted URL link to the review.
     */
    private static String convertReviewToLink(final Review review)
    {
        return "<a href=\"" + review.getUrl() + "\" target=\"_blank\">" + review.getPermaId() + "</a>";
    }

    private static String convertReviewToTableRow(final Review review)
    {
        final StringBuilder tableRow = new StringBuilder("<tr>");
        tableRow.append("<td style=\"border: 1px solid black; border-collapse: collapse; padding: 10px;\">").append("<img src=\"")
                .append(review.getAuthor().getAvatarUrl()).append("\" height=\"18\" width=\"18\">&nbsp;").append(review.getAuthor().getDisplayName())
                .append("</td>");

        return tableRow.append("<td style=\"border: 1px solid black; border-collapse: collapse; padding: 10px;\">")
                .append(convertReviewToLink(review))
                .append("</td><td style=\"border: 1px solid black; border-collapse: collapse; padding: 10px; text-align: center;\">")
                .append(review.getDateCreated().toString("MM/dd/yy H:mm"))
                .append("</td><td style=\"border: 1px solid black; border-collapse: collapse; padding: 10px;\">").append(review.getName())
                .append("</tr>").toString();
    }

    /**
     * Determines which reviews are outgoing and all reviewers have marked it as complete.
     * @param username
     *            The username of the user for which reviews are being requested.
     * @param reviews
     *            All open reviews the user is on.
     * @return A SetMultimap of outgoing {@link Review}s keyed by their {@link Crucible} instance.
     */
    private static List<Review> getReviewsToClose(final String username, final List<Review> reviews)
    {
        final List<Review> reviewsToClose = new ArrayList<>();

        for (final Review review : reviews)
        {
            if (!isAuthorOrModerator(review, username) || !review.isAllReviewersComplete())
            {
                continue;
            }

            reviewsToClose.add(review);
        }

        Collections.sort(reviewsToClose, new ReviewComparator());

        return reviewsToClose;
    }

    /**
     * Determines which reviews the user is on and has not completed.
     * @param username
     *            The username of the user for which reviews are being requested.
     * @param reviews
     *            All open reviews the user is on.
     * @return A SetMultimap of outgoing {@link Review}s keyed by their {@link Crucible} instance.
     */
    private static List<Review> getReviewsToComplete(final String username, final List<Review> reviews)
    {
        final List<Review> reviewsToComplete = new ArrayList<>();

        for (final Review review : reviews)
        {
            if (isAuthorOrModerator(review, username))
            {
                continue;
            }

            for (final Reviewer reviewer : review.getReviewers())
            {
                if (username.equalsIgnoreCase(reviewer.getUser().getUsername()) && !reviewer.isCompleted())
                {
                    reviewsToComplete.add(review);
                }
            }
        }

        Collections.sort(reviewsToComplete, new ReviewComparator());

        return reviewsToComplete;
    }

    /**
     * Determines which reviews are outgoing from the user, but not all reviewers have marked it as complete.
     * @param username
     *            The username of the user for which reviews are being requested.
     * @param reviews
     *            All open reviews the user is on.
     * @return A SetMultimap of {@link Review}s needing attention keyed by their {@link Crucible} instance.
     */
    private static List<Review> getOutgoingReviews(final String username, final List<Review> reviews)
    {
        final List<Review> outgoingReviews = new ArrayList<>();

        for (final Review review : reviews)
        {
            if (!isAuthorOrModerator(review, username) || review.isAllReviewersComplete())
            {
                continue;
            }

            outgoingReviews.add(review);
        }

        Collections.sort(outgoingReviews, new ReviewComparator());

        return outgoingReviews;
    }

    /**
     * Determines which reviews the user is on and has completed.
     * @param username
     *            The username of the user for which reviews are being requested.
     * @param reviews
     *            All open reviews the user is on.
     * @return A SetMultimap of outgoing {@link Review}s keyed by their {@link Crucible} instance.
     */
    private static List<Review> getCompletedReviews(final String username, final List<Review> reviews)
    {
        final List<Review> completedReviews = new ArrayList<>();

        for (final Review review : reviews)
        {
            if (isAuthorOrModerator(review, username))
            {
                continue;
            }

            for (final Reviewer reviewer : review.getReviewers())
            {
                if (username.equalsIgnoreCase(reviewer.getUser().getUsername()) && reviewer.isCompleted())
                {
                    final DateTime completedDateTime = reviewer.getCompletedDateTime();
                    final DateTime oneMonthAgo = new DateTime().minusMonths(1);
                    if (completedDateTime.isAfter(oneMonthAgo))
                    {
                        completedReviews.add(review);
                    }
                }
            }
        }

        Collections.sort(completedReviews, new ReviewComparator());

        return completedReviews;
    }

    /**
     * Determines if the provided username is the author or moderator of the review.
     * @param review
     *            The {@link Review} being evaluated.
     * @param username
     *            The current username being requested.
     * @return True if the username provided matches the username on the author or provider on the {@link Review}.
     */
    private static boolean isAuthorOrModerator(final Review review, final String username)
    {
        if (review.getAuthor().getUsername().equalsIgnoreCase(username))
        {
            return true;
        }
        if (review.getModerator() != null && review.getModerator().getUsername().equalsIgnoreCase(username))
        {
            return true;
        }

        return false;
    }

    /**
     * A {@link Comparator} for sorting {@link Review}s.
     * @author Aaron McGinn (am025347)
     */
    private static class ReviewComparator implements Comparator<Review>
    {
        @Override
        public int compare(final Review o1, final Review o2)
        {
            return o1.getDateCreated().compareTo(o2.getDateCreated());
        }

    }
}
