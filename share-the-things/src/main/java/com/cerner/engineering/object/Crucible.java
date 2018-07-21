package com.cerner.engineering.object;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * A Crucible instance containing URL information.
 * @author Aaron McGinn (am025347)
 */
public enum Crucible
{
    /** The Crucible 1 instance. */
    CRUCIBLE01("http://crucible01.cerner.com/"),

    /** The Crucible 2 instance. */
    CRUCIBLE02("http://crucible02.cerner.com/viewer/"),

    /** The Crucible 3 instance. */
    CRUCIBLE03("http://crucible03.cerner.com/viewer/"),

    /** The Crucible 4 instance. */
    CRUCIBLE04("http://crucible04.cerner.com/viewer/"),

    /** The Crucible 5 instance. */
    CRUCIBLE05("http://crucible05.cerner.com/viewer/"),

    /** The Crucible 6 instance. */
    CRUCIBLE06("http://crucible06.cerner.com/viewer/"),

    /** The Crucible training instance. */
    CRUCIBLE_TRAINING("https://crucible.training.cerner.com/");

    /**
     * The API endpoint to get the reviews with their details according to the specified filter information.
     */
    public final static String FILTER_DETAILS = "rest-service/reviews-v1/filter/details.json";

    /**
     * The query parameters necessary for searching where the user is the author, moderator, or reviewer of a review. Will only return reviews where
     * the state of the review is 'Review'.<br>
     * Note: The username needs to be added with a {@link String#replace} using '{username}' as the string to be replaced.
     */
    public final static String SEARCH_QUERY_PARAMS = "?author={username}&moderator={username}&reviewer={username}&orRoles=true&states=Review";

    /**
     * The query parameters for limiting results to after the specified date. Must be included after another search query params string.<br>
     * Note: The fromDate needs to be added with a {@link String#replace} using '{fromMillis}' as the string to be replaced.
     */
    public final static String FROM_DATE_QUERY_PARAMS = "&fromDate={fromMillis}";

    /**
     * The base URL of the Crucible instance.
     */
    private String baseUrl;
    private static Map<String, Crucible> crucibleByBaseUrl = Maps.newConcurrentMap();

    static
    {
        for (final Crucible crucible : Crucible.values())
        {
            crucibleByBaseUrl.put(crucible.getBaseUrl(), crucible);
        }
    }

    private Crucible(final String baseUrl)
    {
        this.baseUrl = baseUrl;
    }

    /**
     * Get the base URL of the Crucible instance.
     * @return The base URL.
     */
    public String getBaseUrl()
    {
        return baseUrl;
    }

    /**
     * Get the {@link Crucible} for the provided base URL.
     * @param baseUrl
     *            The base URL to retrieve an instance of.
     * @return The possibly null {@link Crucible} with a base URL matching the provided URL.
     */
    public static Crucible getForBaseUrl(final String baseUrl)
    {
        return crucibleByBaseUrl.get(baseUrl);
    }
}
