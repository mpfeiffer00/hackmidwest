package com.cerner.engineering.github;

import com.cerner.engineering.http.HttpClientHelper;
import com.cerner.engineering.object.Issue;
import com.cerner.engineering.object.User;
import com.cerner.system.exception.Verifier;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.joda.time.LocalDateTime;
import org.joda.time.format.ISODateTimeFormat;

/**
 * A {@link Callable} to allow concurrent issue retrieval.
 * @author Aaron McGinn (am025347)
 */
public class GithubReviewRetrieverCallable implements Callable<List<Issue>>
{
    private static final String SEARCH_URL = "//github.cerner.com/api/v3/search/issues?sort=created&order=asc&q=is:open+involves:{username}";
    private static final String FILTER_DATE_POSTFIX = "+created:>={filterDate}";
    private final String username;
    private final Long actualDaysAgo;

    private GithubReviewRetrieverCallable(final String username, final Long actualDaysAgo)
    {
        this.username = username;
        this.actualDaysAgo = actualDaysAgo;
    }

    /**
     * Create a {@link GithubReviewRetrieverCallable}.
     * @param username
     *            The username to request issues for (cannot be null, empty, or blank).
     * @param actualDaysAgo
     *            The number of days ago to limit issues from (may be null).
     * @return The instantiated {@link GithubReviewRetrieverCallable}.
     */
    public static GithubReviewRetrieverCallable create(final String username, final Long actualDaysAgo)
    {
        Verifier.verifyNotBlank(username);

        return new GithubReviewRetrieverCallable(username, actualDaysAgo);
    }

    @Override
    public List<Issue> call() throws Exception
    {
        HttpClientHelper.trustAllHosts();

        final List<Issue> issues = new ArrayList<>();
        try (final CloseableHttpClient httpClient = HttpClientHelper.getNewHttpClient())
        {
            final StringBuilder urlBuilder = new StringBuilder(SEARCH_URL.replace("{username}", username));
            if (actualDaysAgo != null)
            {
                final String filterDate = new LocalDateTime().minusDays(actualDaysAgo.intValue()).toString("yyyy-MM-dd");
                urlBuilder.append(FILTER_DATE_POSTFIX.replace("{filterDate}", filterDate));
            }

            final URI uri = new URI("https", urlBuilder.toString(), null);
            final HttpRequestBase request = new HttpGet(uri);

            final String reviewerJsonString;
            try (CloseableHttpResponse response = httpClient.execute(request))
            {
                reviewerJsonString = EntityUtils.toString(response.getEntity());
            }

            final JSONObject reviewerJsonResponse = new JSONObject(reviewerJsonString);
            if (!reviewerJsonResponse.isNull("items"))
            {
                final JSONArray items = reviewerJsonResponse.getJSONArray("items");

                for (int i = 0; i < items.length(); i++)
                {
                    final JSONObject jsonIssue = items.getJSONObject(i);
                    final Issue issue = getIssueFromJson(jsonIssue);

                    issues.add(issue);
                }
            }
        }
        catch (final Exception e)
        {
            e.printStackTrace();
            return null;
        }

        return issues;
    }

    /**
     * Parse the provided JSON and create a review from it.
     * @param jsonIssue
     *            The issue JSON to parse.
     * @return The {@link Issue}.
     * @throws JSONException
     *             If an error occurs parsing the JSON.
     */
    private Issue getIssueFromJson(final JSONObject jsonIssue) throws JSONException
    {
        final String title = jsonIssue.getString("title");
        final String repoUrl = jsonIssue.getString("repository_url");
        final String htmlUrl = jsonIssue.getString("html_url");
        final String createdDate = jsonIssue.getString("created_at");
        final long number = jsonIssue.getLong("number");

        final JSONObject author = jsonIssue.getJSONObject("user");
        final String userName = author.getString("login");
        final String displayName = getNameFromLDAP(author.getString("ldap_dn"));
        final String avatarUrl = author.getString("avatar_url");
        final String authorUrl = author.getString("html_url");

        final User authorUser = User.Builder.create().withUsername(userName).withDisplayName(displayName != null ? displayName : userName)
                .withAvatarUrl(avatarUrl).withUrl(authorUrl).build();

        final Issue issue = Issue.Builder.create().withDateCreated(LocalDateTime.parse(createdDate, ISODateTimeFormat.dateTimeParser()))
                .withNumber(number).withRepoUrl(repoUrl).withTitle(title).withUrl(htmlUrl).withUser(authorUser).build();

        return issue;
    }

    /**
     * Get the user's name from the ldap_dn string.
     * @param ldap
     *            The ldap_dn string for the user.
     * @return Possibly null user's name.
     */
    private String getNameFromLDAP(final String ldap)
    {
        try
        {
            return ldap == null ? null : ldap.substring(3, ldap.indexOf(",OU")).replace("\\", "");
        }
        catch (@SuppressWarnings("unused") final Exception e)
        {
            return null;
        }
    }
}
