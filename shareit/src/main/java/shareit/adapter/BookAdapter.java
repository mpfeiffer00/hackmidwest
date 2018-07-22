package shareit.adapter;

import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import shareit.common.Book;

/**
 * This Adaptor allows us to separate our domain object, Book, from our
 * library-specific classes, in this case the MongoDB-specific DBObject.
 */

public final class BookAdapter {
	public static final DBObject toDBObject(Book book) {
		return new BasicDBObject("book_id", book.getBookId()).append("title", book.getTitle())
				.append("item_id", book.getItemId()).append("author", book.getAuthor())
				.append("isbn", book.getIsbnCode())
				.append("date_when_added_to_inventory", book.getDateWhenAddedToInventory())
				.append("good_resds_id", book.getGoodReadsId())
				.append("image_url", book.getImageUrl())
				.append("isbn13", book.getIsbn13())
				.append("small_image_url", book.getSmallImageUrl());
	}
}
