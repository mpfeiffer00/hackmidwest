/*
 * ShareIt
 * Inventory for stuff.
 *
 * OpenAPI spec version: 1.0.0
 * Contact: michael.pfieffer@cerner.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package shareit.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;

/**
 * Book
 */

public class Book   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("itemId")
  private String itemId = null;

  @JsonProperty("title")
  private String title = null;

  @JsonProperty("author")
  private String author = null;

  @JsonProperty("isbnCode")
  private String isbnCode = null;

  @JsonProperty("goodReadsId")
  private String goodReadsId = null;

  @JsonProperty("isbn")
  private String isbn = null;

  @JsonProperty("isbn13")
  private String isbn13 = null;

  @JsonProperty("imageUrl")
  private String imageUrl = null;

  @JsonProperty("smallImageUrl")
  private String smallImageUrl = null;

  public Book id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   **/
  @JsonProperty("id")
  @ApiModelProperty(value = "")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Book itemId(String itemId) {
    this.itemId = itemId;
    return this;
  }

  /**
   * Get itemId
   * @return itemId
   **/
  @JsonProperty("itemId")
  @ApiModelProperty(value = "")
  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  public Book title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Get title
   * @return title
   **/
  @JsonProperty("title")
  @ApiModelProperty(value = "")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Book author(String author) {
    this.author = author;
    return this;
  }

  /**
   * Get author
   * @return author
   **/
  @JsonProperty("author")
  @ApiModelProperty(value = "")
  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public Book isbnCode(String isbnCode) {
    this.isbnCode = isbnCode;
    return this;
  }

  /**
   * Get isbnCode
   * @return isbnCode
   **/
  @JsonProperty("isbnCode")
  @ApiModelProperty(value = "")
  public String getIsbnCode() {
    return isbnCode;
  }

  public void setIsbnCode(String isbnCode) {
    this.isbnCode = isbnCode;
  }

  public Book goodReadsId(String goodReadsId) {
    this.goodReadsId = goodReadsId;
    return this;
  }

  /**
   * Get goodReadsId
   * @return goodReadsId
   **/
  @JsonProperty("goodReadsId")
  @ApiModelProperty(value = "")
  public String getGoodReadsId() {
    return goodReadsId;
  }

  public void setGoodReadsId(String goodReadsId) {
    this.goodReadsId = goodReadsId;
  }

  public Book isbn(String isbn) {
    this.isbn = isbn;
    return this;
  }

  /**
   * Get isbn
   * @return isbn
   **/
  @JsonProperty("isbn")
  @ApiModelProperty(value = "")
  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public Book isbn13(String isbn13) {
    this.isbn13 = isbn13;
    return this;
  }

  /**
   * Get isbn13
   * @return isbn13
   **/
  @JsonProperty("isbn13")
  @ApiModelProperty(value = "")
  public String getIsbn13() {
    return isbn13;
  }

  public void setIsbn13(String isbn13) {
    this.isbn13 = isbn13;
  }

  public Book imageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
    return this;
  }

  /**
   * Get imageUrl
   * @return imageUrl
   **/
  @JsonProperty("imageUrl")
  @ApiModelProperty(value = "")
  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public Book smallImageUrl(String smallImageUrl) {
    this.smallImageUrl = smallImageUrl;
    return this;
  }

  /**
   * Get smallImageUrl
   * @return smallImageUrl
   **/
  @JsonProperty("smallImageUrl")
  @ApiModelProperty(value = "")
  public String getSmallImageUrl() {
    return smallImageUrl;
  }

  public void setSmallImageUrl(String smallImageUrl) {
    this.smallImageUrl = smallImageUrl;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Book book = (Book) o;
    return Objects.equals(this.id, book.id) &&
        Objects.equals(this.itemId, book.itemId) &&
        Objects.equals(this.title, book.title) &&
        Objects.equals(this.author, book.author) &&
        Objects.equals(this.isbnCode, book.isbnCode) &&
        Objects.equals(this.goodReadsId, book.goodReadsId) &&
        Objects.equals(this.isbn, book.isbn) &&
        Objects.equals(this.isbn13, book.isbn13) &&
        Objects.equals(this.imageUrl, book.imageUrl) &&
        Objects.equals(this.smallImageUrl, book.smallImageUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, itemId, title, author, isbnCode, goodReadsId, isbn, isbn13, imageUrl, smallImageUrl);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
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
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
