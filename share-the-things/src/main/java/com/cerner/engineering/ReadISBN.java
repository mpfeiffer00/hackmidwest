package com.cerner.engineering;

import com.cerner.common.collection.SetUtils;
import com.cerner.engineering.object.Book;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class ReadISBN
{
    private static final String API_KEY = "vAk3StDKFQ1FtKXzpzo9g";
    private static final String BASE_URL = "https://www.goodreads.com/book/";
    private static final String ISBN_TO_ID = "isbn_to_id";
    private static final String SHOW_BOOK = "show";
    private static final String SEPARATOR = ",";
    private static final String ISBN_TO_ID_RESPONSE_SEPARATOR = "\n";

    private static HttpURLConnection con;

    public static void main(final String[] args) throws MalformedURLException, ProtocolException, IOException
    {
        try
        {
            // callResource();
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }

        final Set<String> isbns = SetUtils.newSet("0590353403");
        // final Set<String> isbns = SetUtils.newSet("0441172717", "0590353403", "0739467352"); // need to figure out multiple isbns
        final Set<Long> ids = getIdsFromISBNs(isbns);
        final Set<Book> books = getBookInfoFromIds(ids);
        System.out.println(ids);
    }

    private static Set<Long> getIdsFromISBNs(final Set<String> isbns) throws MalformedURLException, ProtocolException, IOException
    {
        // https://www.goodreads.com/book/isbn_to_id/0590353403?key=vAk3StDKFQ1FtKXzpzo9g

        final StringBuilder concatenatedIsbnsBuilder = new StringBuilder();
        for (final String isbn : isbns)
        {
            concatenatedIsbnsBuilder.append(isbn);
            concatenatedIsbnsBuilder.append(SEPARATOR);
        }

        String concatenatedIsbns = concatenatedIsbnsBuilder.toString();
        concatenatedIsbns = concatenatedIsbns.substring(0, concatenatedIsbns.length() - SEPARATOR.length());

        System.out.println(concatenatedIsbns);

        final String response = callResource(ISBN_TO_ID, concatenatedIsbns);

        final Set<Long> goodReadsIds = new HashSet();
        final StringTokenizer st = new StringTokenizer(response, ISBN_TO_ID_RESPONSE_SEPARATOR);
        while (st.hasMoreTokens())
        {
            goodReadsIds.add(Long.valueOf(st.nextToken()));
        }

        return goodReadsIds;
    }

    private static Set<Book> getBookInfoFromIds(final Set<Long> goodReadsBookIds) throws MalformedURLException, ProtocolException, IOException
    {
        // https://www.goodreads.com/book/show/29409154?format=xml&key=YOURAPIKEY

        final StringBuilder concatenatedIdsBuilder = new StringBuilder();
        for (final Long goodReadsBookId : goodReadsBookIds)
        {
            concatenatedIdsBuilder.append(goodReadsBookId.toString());
            concatenatedIdsBuilder.append(SEPARATOR);
        }

        String concatenatedIds = concatenatedIdsBuilder.toString();
        concatenatedIds = concatenatedIds.substring(0, concatenatedIds.length() - SEPARATOR.length());

        System.out.println(concatenatedIds);

        callResource(SHOW_BOOK, concatenatedIds);
        return null;
    }

    public static String callResource(final String resource, final String idParameters) throws MalformedURLException, ProtocolException, IOException
    {
        // final String url = "https://www.goodreads.com/book/isbn_to_id/0590353403,0441172717?key=vAk3StDKFQ1FtKXzpzo9g";

        final String url = new StringBuilder().append(BASE_URL).append(resource).append("/").append(idParameters).append("?key=vAk3StDKFQ1FtKXzpzo9g")
                .toString();

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

                System.out.println(in.toString());

                while ((line = in.readLine()) != null)
                {
                    content.append(line); // eh eh?
                    content.append(System.lineSeparator());
                }
            }

            System.out.println(content.toString());
            return content.toString();
        }
        finally
        {
            con.disconnect();
        }
    }
}
