package com.hackmiswest.common;

import java.util.Date;

public class ItemInformation {
	private final long itemId;
	private final long bookId;
	private final String status;
	private final Date toDate;
	private final Date fromDate;

	public ItemInformation(final long itemId, final long bookId, final String status, final Date toDate, final Date fromDate) {
		this.bookId = bookId;
		this.itemId =itemId;
		this.status = status;
		this.toDate = toDate;
		this.fromDate = fromDate;
	}

	public long getBookId() {
		return bookId;
	}

	public String getStatus() {
		return status;
	}

	public Date getToDate() {
		return toDate;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public long getItemId() {
		return itemId;
	}
}
