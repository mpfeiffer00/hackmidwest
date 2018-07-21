package com.cerner.engineering.object;

/**
 * The instance of Crucible that also contains the most recent authorization token.
 * @author Aaron McGinn (am025347)
 */
public class CrucibleInstance
{
    private final String baseURL;
    private final String authToken;

    /**
     * Constructs a {@link CrucibleInstance}.
     * @param baseURL
     *            The {@link Crucible} associated with this instance.
     * @param authToken
     *            The authorization token for this instance.
     */
    public CrucibleInstance(final String baseURL, final String authToken)
    {
        this.baseURL = baseURL;
        this.authToken = authToken;
    }

    /**
     * @return the Crucible base URL.
     */
    public String getBaseURL()
    {
        return baseURL;
    }

    /**
     * @return the authToken.
     */
    public String getAuthToken()
    {
        return authToken;
    }
}
