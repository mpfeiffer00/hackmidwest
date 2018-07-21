package com.cerner.engineering.auth;

import com.cerner.engineering.http.HttpClientHelper;
import com.cerner.engineering.object.Crucible;
import com.cerner.engineering.object.CrucibleInstance;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jettison.json.JSONObject;

import com.google.common.base.Verify;
import com.google.common.collect.Sets;

/**
 * Assistant for authorizing against all Crucible instances.
 * @author Aaron McGinn (am025347)
 */
public class AuthorizationAssistant
{
    private static String AUTH_PATH = "rest-service/auth-v1/login.json?userName={username}&password={password}";
    private static final Set<CrucibleInstance> INSTANCES = Sets.newHashSet();
    private static final byte[] UB = {115, 118, 99, 65, 101, 111, 110, 79, 114, 100, 101, 114, 115, 65, 99, 99, 116};
    private static final byte[] PB = {65, 101, 111, 110, 42, 50, 48, 49, 52};
    private static final Set<Crucible> unauthorizedInstances = Sets.newHashSet();

    /**
     * Setup all Crucible instances with authorization.
     */
    public static void setupCrucibleInstances()
    {
        try (final CloseableHttpClient httpClient = HttpClientHelper.getNewHttpClient())
        {
            HttpClientHelper.trustAllHosts();

            for (final Crucible crucible : Crucible.values())
            {
                final String url = crucible.getBaseUrl().concat(AUTH_PATH).replace("{username}", new String(UB)).replace("{password}",
                        new String(PB));

                final HttpRequestBase request = new HttpGet(url);
                final RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(10000).build();
                request.setConfig(requestConfig);

                try (CloseableHttpResponse response = httpClient.execute(request))
                {
                    Verify.verify(response.getStatusLine().getStatusCode() == 200);

                    final JSONObject jsonResponse = new JSONObject(EntityUtils.toString(response.getEntity()));
                    INSTANCES.add(new CrucibleInstance(crucible.getBaseUrl(), "&FEAUTH=" + jsonResponse.getString("token")));
                }
                catch (final Exception e)
                {
                    e.printStackTrace();
                    unauthorizedInstances.add(crucible);
                    System.out.println("Cannot get authorization for " + crucible);
                }
            }
        }
        catch (@SuppressWarnings("unused") final IOException e)
        {
            throw new IllegalStateException("Cannot attempt authorization.");
        }
    }

    /**
     * Retry authorization if any Crucible instances have failed previously.
     * @param instance
     *            The {@link Crucible} to reauthorize.
     * @return True if authorization was successful, otherwise false.
     */
    public static boolean retryInstanceAuthorization(final Crucible instance)
    {
        final String url = instance.getBaseUrl().concat(AUTH_PATH).replace("{username}", new String(UB)).replace("{password}", new String(PB));

        try (final CloseableHttpClient httpClient = HttpClientHelper.getNewHttpClient())
        {
            final HttpRequestBase request = new HttpGet(url);
            final RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(10000).build();
            request.setConfig(requestConfig);

            try (CloseableHttpResponse response = httpClient.execute(request))
            {
                Verify.verify(response.getStatusLine().getStatusCode() == 200);

                final JSONObject jsonResponse = new JSONObject(EntityUtils.toString(response.getEntity()));
                INSTANCES.add(new CrucibleInstance(instance.getBaseUrl(), "&FEAUTH=" + jsonResponse.getString("token")));
                unauthorizedInstances.remove(instance);
                return true;
            }
            catch (final Exception e)
            {
                e.printStackTrace();
            }
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Retry authorization if any Crucible instances have failed previously.
     * @param instance
     *            The {@link CrucibleInstance} to reauthorize.
     * @return True if authorization was successful, otherwise false.
     */
    public static boolean retryInstanceAuthorization(final CrucibleInstance instance)
    {
        final Crucible crucible = Crucible.getForBaseUrl(instance.getBaseURL());
        final boolean successful = retryInstanceAuthorization(crucible);

        if (successful)
        {
            INSTANCES.remove(instance);
            unauthorizedInstances.remove(crucible);
        }

        return successful;
    }

    /**
     * Remove a {@link CrucibleInstance} from being authorized to retry authorization again.
     * @param instance
     *            The {@link CrucibleInstance} to unauthorize.
     */
    public static void unauthorizeInstance(final CrucibleInstance instance)
    {
        INSTANCES.remove(instance);
        unauthorizedInstances.add(Crucible.getForBaseUrl(instance.getBaseURL()));
    }

    /**
     * Get unauthorized Crucible instances for possible retry.
     * @return A non-null Set of {@link Crucible}s to reauthorize.
     */
    public static Set<Crucible> getUnauthorizedInstances()
    {
        return unauthorizedInstances;
    }

    /**
     * Get all Crucible instances with authorization.
     * @return The non-null Set of {@link CrucibleInstance}s.
     */
    public static Set<CrucibleInstance> getCrucibleInstances()
    {
        return new HashSet<>(INSTANCES);
    }
}
