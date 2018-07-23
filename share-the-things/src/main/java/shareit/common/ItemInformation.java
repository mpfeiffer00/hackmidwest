package shareit.common;

import java.util.Date;

public class ItemInformation {
	private final long itemId;
	private final long itemChildId;
	private final String itemType;
	private final String status;
	private final Date toDate;
	private final Date fromDate;

	public ItemInformation(final long itemId, final long itemChildId, final String itemType, final String status, final Date toDate, final Date fromDate) {
		this.itemId = itemId;
		this.itemChildId =itemChildId;
		this.itemType = itemType;
		this.status = status;
		this.toDate = toDate;
		this.fromDate = fromDate;
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

	public long getItemChildId() {
		return itemChildId;
	}

	public String getItemType() {
		return itemType;
	}
}
