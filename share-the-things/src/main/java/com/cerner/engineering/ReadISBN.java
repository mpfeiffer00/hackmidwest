package com.cerner.engineering;

import com.cerner.common.collection.SetUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Set;

public class ReadISBN
{
    private static final String API_KEY = "vAk3StDKFQ1FtKXzpzo9g";
    private static final String BASE_URL = "https://www.goodreads.com/book/";
    private static final String ISBN_TO_ID = "isbn_to_id";

    private static HttpURLConnection con;

    public static void main(final String[] args)
    {
        try
        {
            // callShit();
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }

        final Set<Long> isbns = SetUtils.newSet(123L, 1233L);
        final Set<Long> ids = getIdsFromISBNs(isbns);
        System.out.println(ids);
    }

    private static Set<Long> getIdsFromISBNs(final Set<Long> isbns)
    {
        // https://www.goodreads.com/book/isbn_to_id/0590353403?key=vAk3StDKFQ1FtKXzpzo9g
        return null;
    }

    private static void getBookInfoFromIds()
    {
        // https://www.goodreads.com/book/show/29409154?format=xml&key=YOURAPIKEY
    }

    public static String callShit(final String resource, final Set<Long> ids) throws MalformedURLException, ProtocolException, IOException
    {
        final String url = "https://www.goodreads.com/book/isbn_to_id/0590353403?key=vAk3StDKFQ1FtKXzpzo9g";
        final String urlz = new StringBuilder().append(BASE_URL).append(resource).append("/").toString();

        try
        {
            final URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();
            con.setRequestMethod("GET");
            StringBuilder content;

            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream())))
            {
                String line;
                content = new StringBuilder();

                while ((line = in.readLine()) != null)
                {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }

            return content.toString();
        }
        finally
        {
            con.disconnect();
        }
    }

}
