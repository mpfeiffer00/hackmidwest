package com.cerner.engineering;

import com.cerner.engineering.object.Book;

import java.net.HttpURLConnection;
import java.util.Set;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class ReadISBN
{
    private static final String API_KEY = "vAk3StDKFQ1FtKXzpzo9g";
    private static final String BASE_URL = "https://www.goodreads.com/book/";
    private static final String ISBN_TO_ID = "isbn_to_id";
    private static final String SHOW_BOOK = "show";
    private static final String ISBN_TO_SHOW_BOOK = "isbn";
    private static final String SEPARATOR = ",";
    private static final String ISBN_TO_ID_RESPONSE_SEPARATOR = "\n";

    private static HttpURLConnection con;

    public static void main(final String[] args)
    {
        try
        {
            // callResource();
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }

        // final Set<String> isbns = SetUtils.newSet("0590353403");
        // final String isbn = "0590353403";
        final String isbn = "0441172717";
        // final Set<String> isbns = SetUtils.newSet("0441172717", "0590353403");
        // final Set<String> isbns = SetUtils.newSet("0441172717", "0590353403", "0739467352"); // I suspect the API doesn't support multiple isbns
        // final Set<Long> ids = getIdsFromISBNs(isbns);
        // final Set<Book> books = getBookInfoFromIds(ids);
        final Book book = getBooksFromISBNs(isbn);
    }

    private static Book getBooksFromISBNs(final String isbn)
    {
        final Book book = callResource(ISBN_TO_SHOW_BOOK, isbn);
        return book;
    }

    private static Set<Book> getBookInfoFromIds(final Set<Long> goodReadsBookIds)
    {
        // https://www.goodreads.com/book/show/29409154?format=xml?key=YOURAPIKEY

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

    public static Book callResource(final String resource, final String isbn)
    {
        // final String url = "https://www.goodreads.com/book/isbn_to_id/0590353403,0441172717?key=vAk3StDKFQ1FtKXzpzo9g";

        // final String url = new
        // StringBuilder().append(BASE_URL).append(resource).append("/").append(idParameters).append("?key=vAk3StDKFQ1FtKXzpzo9g")
        // .toString();

        final String url = new StringBuilder().append(BASE_URL).append(resource).append("/").append(isbn)
                .append("?format=xml&key=vAk3StDKFQ1FtKXzpzo9g").toString();

        try (final CloseableHttpClient httpClient = new DefaultHttpClient())
        {
            final HttpRequestBase request = new HttpGet(url);
            try (final CloseableHttpResponse response = httpClient.execute(request))
            {
                final String stringResponse = EntityUtils.toString(response.getEntity());

                Document document = null;
                try
                {
                    document = DocumentHelper.parseText(stringResponse);
                }
                catch (final DocumentException e)
                {
                    e.printStackTrace();
                }

                final Element root = document.getRootElement();

                final Element bookElement = root.element("book");

                final String title = bookElement.elementText("title");
                // Element authorElement = bookElement.element("author");
                // final String author = bookElement.elementText("author");
                final String isbnResponse = bookElement.elementText("isbn");
                final String isbn13 = bookElement.elementText("isbn13");
                final String imageUrl = bookElement.elementText("image_url");
                final String smallImageUrl = bookElement.elementText("small_image_url");
                final String publicationYear = bookElement.elementText("publication_year");
                final String publicationMonth = bookElement.elementText("publication_month");
                final String publicationDay = bookElement.elementText("publication_day");
                final String publisher = bookElement.elementText("publisher");

                final Book.Builder bookBuilder = Book.Builder.create();
                bookBuilder.withTitle(title);
                // bookBuilder.withAuthor(author);
                bookBuilder.withIsbn(isbnResponse);
                bookBuilder.withIsbn13(isbn13);
                bookBuilder.withImageUrl(imageUrl);
                bookBuilder.withSmallImageUrl(smallImageUrl);
                bookBuilder.withPublicationYear(Long.valueOf(publicationYear));
                bookBuilder.withPublicationMonth(Long.valueOf(publicationMonth));
                bookBuilder.withPublicationDay(Long.valueOf(publicationDay));
                bookBuilder.withPublisher(publisher);

                return bookBuilder.build();
            }
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}