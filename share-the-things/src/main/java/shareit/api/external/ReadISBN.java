package shareit.api.external;

import java.util.List;

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

import shareit.object.Book;

public class ReadISBN
{
    private static final String BASE_URL = "https://www.goodreads.com/book/";
    private static final String ISBN_TO_SHOW_BOOK = "isbn";

    public static void main(final String[] args)
    {
        try
        {
            // callResource();
        } catch (final Exception e)
        {
            e.printStackTrace();
        }

        final String isbn = "0441172717";

        // final String isbn = "9780441172719"; // test isbn13 - works

        getBookFromISBN(isbn);
    }

    public static Book getBookFromISBN(final String isbn)
    {
        final Book book = callResource(ISBN_TO_SHOW_BOOK, isbn);
        return book;
    }

    private static Book callResource(final String resource, final String isbn)
    {
        if (isbn == null || isbn.isEmpty())
        {
            System.out.println("isbn null or empty");
            return null;
        }
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
                } catch (final DocumentException e)
                {
                    e.printStackTrace();
                    return null;
                }

                final Element root = document.getRootElement();

                final Element bookElement = root.element("book");

                final String title = bookElement.elementText("title");
                final String goodReadsId = bookElement.elementText("id");

                // get the first author cuz whatever
                final Element authorsElement = bookElement.element("authors");
                final List<Element> authorElements = authorsElement.elements("author");
                final String firstAuthorName = authorElements.get(0).elementText("name");

                final String isbnResponse = bookElement.elementText("isbn");
                final String isbn13 = bookElement.elementText("isbn13");
                final String imageUrl = bookElement.elementText("image_url");
                final String smallImageUrl = bookElement.elementText("small_image_url");
                final String publicationYear = bookElement.elementText("publication_year");
                final String publicationMonth = bookElement.elementText("publication_month");
                final String publicationDay = bookElement.elementText("publication_day");
                final String publisher = bookElement.elementText("publisher");

                final Book.Builder bookBuilder = Book.Builder.create();
                bookBuilder.withGoodReadsId(Long.parseLong(goodReadsId));
                bookBuilder.withTitle(title);
                bookBuilder.withAuthor(firstAuthorName);
                bookBuilder.withIsbn(isbnResponse);
                bookBuilder.withIsbn13(isbn13);
                bookBuilder.withImageUrl(imageUrl);
                bookBuilder.withSmallImageUrl(smallImageUrl);
                try
                {
                    bookBuilder.withPublicationYear(Long.valueOf(publicationYear));
                } catch (final NumberFormatException e)
                {
                }
                try
                {
                    bookBuilder.withPublicationMonth(Long.valueOf(publicationMonth));
                } catch (final NumberFormatException e)
                {
                }
                try
                {
                    bookBuilder.withPublicationDay(Long.valueOf(publicationDay));
                } catch (final NumberFormatException e)
                {
                }
                bookBuilder.withPublisher(publisher);

                return bookBuilder.build();
            }
        } catch (final Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
