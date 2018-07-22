package com.hackmiswest.common;

import java.util.Date;

public class Book {
	private final long bookId;
	private final String title;
	private final String author;
	private final String barCode;
	private final String isbnCode;
	private final int year;
	private final Date dateWhenAddedToInventory;

	public Book(final long bookId, final String title, final String author, final String barCode, final String isbnCode,
			final int year, final Date dateWhenAddedToInventory) {
		this.bookId = bookId;
		this.title = title;
		this.author = author;
		this.barCode = barCode;
		this.isbnCode = isbnCode;
		this.year = year;
		this.dateWhenAddedToInventory = dateWhenAddedToInventory;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getBarCode() {
		return barCode;
	}

	public String getIsbnCode() {
		return isbnCode;
	}

	public int getYear() {
		return year;
	}

	public Date getDateWhenAddedToInventory() {
		return dateWhenAddedToInventory;
	}

	public long getBookId() {
		return bookId;
	}
}
