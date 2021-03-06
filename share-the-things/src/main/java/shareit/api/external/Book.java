/*
 * ShareIt Inventory for stuff. OpenAPI spec version: 1.0.0 Contact: michael.pfieffer@cerner.com NOTE: This class is auto generated by the swagger
 * code generator program. https://github.com/swagger-api/swagger-codegen.git Do not edit the class manually.
 */

package shareit.api.external;

import java.util.Objects;

/**
 * Book
 */

public class Book
{
    private String id = null;

    private String itemId = null;

    private String title = null;

    private String author = null;

    private String isbnCode = null;

    private String goodReadsId = null;

    private String isbn = null;

    private String isbn13 = null;

    private String imageUrl = null;

    private String smallImageUrl = null;

    public Book id(final String id)
    {
        this.id = id;
        return this;
    }

    /**
     * Get id
     * @return id
     **/
    public String getId()
    {
        return id;
    }

    public void setId(final String id)
    {
        this.id = id;
    }

    public Book itemId(final String itemId)
    {
        this.itemId = itemId;
        return this;
    }

    /**
     * Get itemId
     * @return itemId
     **/
    public String getItemId()
    {
        return itemId;
    }

    public void setItemId(final String itemId)
    {
        this.itemId = itemId;
    }

    public Book title(final String title)
    {
        this.title = title;
        return this;
    }

    /**
     * Get title
     * @return title
     **/
    public String getTitle()
    {
        return title;
    }

    public void setTitle(final String title)
    {
        this.title = title;
    }

    public Book author(final String author)
    {
        this.author = author;
        return this;
    }

    /**
     * Get author
     * @return author
     **/
    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(final String author)
    {
        this.author = author;
    }

    public Book isbnCode(final String isbnCode)
    {
        this.isbnCode = isbnCode;
        return this;
    }

    /**
     * Get isbnCode
     * @return isbnCode
     **/
    public String getIsbnCode()
    {
        return isbnCode;
    }

    public void setIsbnCode(final String isbnCode)
    {
        this.isbnCode = isbnCode;
    }

    public Book goodReadsId(final String goodReadsId)
    {
        this.goodReadsId = goodReadsId;
        return this;
    }

    /**
     * Get goodReadsId
     * @return goodReadsId
     **/
    public String getGoodReadsId()
    {
        return goodReadsId;
    }

    public void setGoodReadsId(final String goodReadsId)
    {
        this.goodReadsId = goodReadsId;
    }

    public Book isbn(final String isbn)
    {
        this.isbn = isbn;
        return this;
    }

    /**
     * Get isbn
     * @return isbn
     **/
    public String getIsbn()
    {
        return isbn;
    }

    public void setIsbn(final String isbn)
    {
        this.isbn = isbn;
    }

    public Book isbn13(final String isbn13)
    {
        this.isbn13 = isbn13;
        return this;
    }

    /**
     * Get isbn13
     * @return isbn13
     **/
    public String getIsbn13()
    {
        return isbn13;
    }

    public void setIsbn13(final String isbn13)
    {
        this.isbn13 = isbn13;
    }

    public Book imageUrl(final String imageUrl)
    {
        this.imageUrl = imageUrl;
        return this;
    }

    /**
     * Get imageUrl
     * @return imageUrl
     **/
    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public Book smallImageUrl(final String smallImageUrl)
    {
        this.smallImageUrl = smallImageUrl;
        return this;
    }

    /**
     * Get smallImageUrl
     * @return smallImageUrl
     **/
    public String getSmallImageUrl()
    {
        return smallImageUrl;
    }

    public void setSmallImageUrl(final String smallImageUrl)
    {
        this.smallImageUrl = smallImageUrl;
    }

    @Override
    public boolean equals(final java.lang.Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        final Book book = (Book) o;
        return Objects.equals(this.id, book.id) && Objects.equals(this.itemId, book.itemId) && Objects.equals(this.title, book.title)
                && Objects.equals(this.author, book.author) && Objects.equals(this.isbnCode, book.isbnCode)
                && Objects.equals(this.goodReadsId, book.goodReadsId) && Objects.equals(this.isbn, book.isbn)
                && Objects.equals(this.isbn13, book.isbn13) && Objects.equals(this.imageUrl, book.imageUrl)
                && Objects.equals(this.smallImageUrl, book.smallImageUrl);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, itemId, title, author, isbnCode, goodReadsId, isbn, isbn13, imageUrl, smallImageUrl);
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append("class Book {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    itemId: ").append(toIndentedString(itemId)).append("\n");
        sb.append("    title: ").append(toIndentedString(title)).append("\n");
        sb.append("    author: ").append(toIndentedString(author)).append("\n");
        sb.append("    isbnCode: ").append(toIndentedString(isbnCode)).append("\n");
        sb.append("    goodReadsId: ").append(toIndentedString(goodReadsId)).append("\n");
        sb.append("    isbn: ").append(toIndentedString(isbn)).append("\n");
        sb.append("    isbn13: ").append(toIndentedString(isbn13)).append("\n");
        sb.append("    imageUrl: ").append(toIndentedString(imageUrl)).append("\n");
        sb.append("    smallImageUrl: ").append(toIndentedString(smallImageUrl)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces (except the first line).
     */
    private String toIndentedString(final java.lang.Object o)
    {
        if (o == null)
        {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
