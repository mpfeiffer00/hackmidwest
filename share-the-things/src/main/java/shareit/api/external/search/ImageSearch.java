package shareit.api.external.search;

import java.util.List;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import shareit.google.GoogleBooks;
import shareit.object.Book;

/**
 * @author am025347
 */
public class ImageSearch
{
    private static final String URL = "http://api.ocr.space/parse/image";
    private static final String KEY = "38d8b1bdf288957";

    public static List<Book> search(final MultipartFile file)
    {
        try (final CloseableHttpClient httpClient = HttpClients.createDefault())
        {
            file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);

            final ContentBody contentBody = new ByteArrayBody(file.getBytes(), file.getOriginalFilename());
            final HttpEntity multipartEntity = MultipartEntityBuilder.create().addPart("image", contentBody).build();

            final HttpPost imgurRequest = new HttpPost("https://api.imgur.com/3/image");
            imgurRequest.setEntity(multipartEntity);
            imgurRequest.addHeader("Authorization", "Client-ID 2ea5f0bfa9c4b62");

            String imageLink;
            try (final CloseableHttpResponse response = httpClient.execute(imgurRequest))
            {
                if (response.getStatusLine().getStatusCode() != 200)
                {
                    return null;
                }
                final String stringResponse = EntityUtils.toString(response.getEntity());
                imageLink = new JSONObject(stringResponse).getJSONObject("data").getString("link");
            }

            if (imageLink == null)
            {
                return null;
            }
            final HttpPost request = new HttpPost(URL);
            request.addHeader("apikey", KEY);
            request.addHeader("content-type", "application/x-www-form-urlencoded;charset=UTF-8");

            request.setEntity(new StringEntity("url=" + imageLink));

            try (final CloseableHttpResponse response = httpClient.execute(request))
            {
                if (response.getStatusLine().getStatusCode() != 200)
                {
                    return null;
                }

                final String stringResponse = EntityUtils.toString(response.getEntity());

                final Set<Book> books = Sets.newHashSet();
                final String parsedText = new JSONObject(stringResponse).getJSONArray("ParsedResults").getJSONObject(0).getString("ParsedText");

                if (parsedText == null || parsedText.isEmpty())
                {
                    return null;
                }

                final String[] linesOfText = parsedText.split("\\r\\n");
                for (final String line : linesOfText)
                {
                    final List<Book> searchResult = GoogleBooks.search(line);
                    if (searchResult != null)
                    {
                        books.addAll(searchResult);
                    }
                }

                return Lists.newArrayList(books);
            }

        } catch (final Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
