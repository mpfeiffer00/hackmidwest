package shareit.common;

import java.util.Date;

public class Book {
	private final long bookId;
	private final long itemId;
	private final String title;
	private final String author;
	private final String isbnCode;
	private final Date dateWhenAddedToInventory;
	private final long goodReadsId;
	private final String isbn13;
	private final String imageUrl;
	private final String smallImageUrl;

	public Book(final long bookId, final long itemId, final String title, final String author, final String isbnCode,
			final Date dateWhenAddedToInventory, final long goodReadsId, final String isbn13, final String imageUrl, final String smallImageUrl) {
		this.itemId = itemId;
		this.bookId = bookId;
		this.title = title;
		this.author = author;
		this.isbnCode = isbnCode;
		this.dateWhenAddedToInventory = dateWhenAddedToInventory;
		this.goodReadsId = goodReadsId;
		this.isbn13 = isbn13;
		this.imageUrl =imageUrl;
		this.smallImageUrl = smallImageUrl;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getIsbnCode() {
		return isbnCode;
	}

	public Date getDateWhenAddedToInventory() {
		return dateWhenAddedToInventory;
	}

	public long getItemId() {
		return itemId;
	}

	public long getBookId() {
		return bookId;
	}

	public long getGoodReadsId() {
		return goodReadsId;
	}

	public String getIsbn13() {
		return isbn13;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getSmallImageUrl() {
		return smallImageUrl;
	}
}
