package shareit.google;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import shareit.object.Book;

/**
 * @author am025347
 */
public class GoogleBooks
{
    private final static String BOOKS_SEARCH = "https://www.googleapis.com/books/v1/volumes?q=";
    private final static String KEY = "&key=AIzaSyDJLMIeoy2OYUuq9kCsHi8KsvB9hgCi2aw";
    private final static String NO_IMAGE = "https://books.google.com/googlebooks/images/no_cover_thumb.gif";

    /**
     * Search Google Books for the ISBN. Returns the first result.
     * @param isbn
     *            The ISBN of the book to search.
     * @return The possibly null {@link Book};
     */
    public static List<Book> search(final String isbn)
    {
        try (final CloseableHttpClient httpClient = HttpClients.createDefault())
        {
            final HttpRequestBase request = new HttpGet(BOOKS_SEARCH + URLEncoder.encode(isbn, StandardCharsets.UTF_8.name()) + KEY);
            try (final CloseableHttpResponse response = httpClient.execute(request))
            {
                final String stringResponse = EntityUtils.toString(response.getEntity());

                final JsonParser jsonParser = new JsonParser();
                final JsonObject root = jsonParser.parse(stringResponse).getAsJsonObject();
                final int totalItems = root.get("totalItems").getAsInt();
                if (totalItems < 1)
                {
                    return null;
                }

                final List<Book> bookResults = Lists.newArrayListWithExpectedSize(totalItems);
                final Iterator<JsonElement> bookResultIterator = root.getAsJsonArray("items").iterator();
                while (bookResultIterator.hasNext())
                {
                    final JsonObject bookResult = bookResultIterator.next().getAsJsonObject();
                    final JsonObject volumeInfo = bookResult.getAsJsonObject("volumeInfo");
                    if (volumeInfo == null)
                    {
                        return null;
                    }
                    final String title = getString(volumeInfo, "title");

                    final StringBuilder allAuthors = new StringBuilder();
                    final JsonArray authorsArray = volumeInfo.getAsJsonArray("authors");
                    if (authorsArray != null)
                    {
                        final Iterator<JsonElement> authors = authorsArray.iterator();

                        while (authors.hasNext())
                        {
                            final String author = authors.next().getAsString();
                            allAuthors.append(author);
                            if (authors.hasNext())
                            {
                                allAuthors.append("; ");
                            }
                        }
                    }

                    String isbn10 = null;
                    String isbn13 = null;
                    final JsonArray jsonIdentifiers = volumeInfo.getAsJsonArray("industryIdentifiers");
                    if (jsonIdentifiers != null)
                    {
                        final Iterator<JsonElement> identifiers = jsonIdentifiers.iterator();
                        while (identifiers.hasNext())
                        {
                            final JsonObject identifierObject = identifiers.next().getAsJsonObject();
                            final String type = getString(identifierObject, "type");
                            final String identifier = identifierObject.get("identifier").getAsString();
                            if ("ISBN_10".equals(type))
                            {
                                isbn10 = identifier;
                            } else
                                if ("ISBN_13".equals(type))
                                {
                                    isbn13 = identifier;
                                }
                        }
                    }

                    final JsonObject imageLinks = volumeInfo.getAsJsonObject("imageLinks");
                    final String thumbnail = getString(imageLinks, "thumbnail");
                    final String smallThumbnail = getString(imageLinks, "smallThumbnail");

                    String publicationYear = null;
                    String publicationMonth = null;
                    String publicationDay = null;
                    final String bookPublishedDate = getString(volumeInfo, "publishedDate");
                    if (bookPublishedDate != null && !bookPublishedDate.isEmpty())
                    {
                        final String[] publishDate = bookPublishedDate.split("-");
                        publicationYear = publishDate[0];
                        if (publishDate.length > 1)
                        {
                            publicationMonth = publishDate[1];
                        }
                        if (publishDate.length > 2)
                        {
                            publicationDay = publishDate[2];
                        }
                    }

                    final String publisher = getString(volumeInfo, "publisher");

                    final Book.Builder bookBuilder = Book.Builder.create();

                    bookBuilder.withTitle(title);
                    bookBuilder.withAuthor(allAuthors.toString());
                    bookBuilder.withIsbn(isbn10);
                    bookBuilder.withIsbn13(isbn13);
                    bookBuilder.withImageUrl(thumbnail != null ? thumbnail : smallThumbnail != null ? smallThumbnail : NO_IMAGE);
                    bookBuilder.withSmallImageUrl(smallThumbnail);

                    try
                    {
                        bookBuilder.withPublicationYear(Long.valueOf(publicationYear));
                    } catch (@SuppressWarnings("unused") final NumberFormatException e)
                    {
                    }
                    try
                    {
                        bookBuilder.withPublicationMonth(Long.valueOf(publicationMonth));
                    } catch (@SuppressWarnings("unused") final NumberFormatException e)
                    {
                    }
                    try
                    {
                        bookBuilder.withPublicationDay(Long.valueOf(publicationDay));
                    } catch (@SuppressWarnings("unused") final NumberFormatException e)
                    {
                    }
                    bookBuilder.withPublisher(publisher);

                    bookResults.add(bookBuilder.build());
                }

                return bookResults;
            }
        } catch (final Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private static String getString(final JsonObject object, final String attribute)
    {
        if (object == null)
        {
            return null;
        }

        try
        {
            final JsonElement attributeElement = object.get(attribute);
            return attributeElement != null ? attributeElement.getAsString() : null;
        } catch (final Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
