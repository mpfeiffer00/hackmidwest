package com.hackmidwest.adapter;

import java.util.Date;

import com.hackmiswest.common.Book;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * This Adaptor allows us to separate our domain object, Book, from our
 * library-specific classes, in this case the MongoDB-specific DBObject.
 */

public final class BookAdapter {
	public static final DBObject toDBObject(Book book) {
		return new BasicDBObject("id", book.getBookId()).append("title", book.getTitle())
				.append("author", book.getAuthor()).append("barCode", book.getBarCode())
				.append("isbnCode", book.getIsbnCode()).append("year", book.getYear())
				.append("dateWhenAddedToInventory", book.getDateWhenAddedToInventory());
	}
}
