package shareit.adapter;

import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import shareit.common.ItemInformation;

/**
 * This Adaptor allows us to separate our domain object, ItemInformationAdapter, from our library-specific classes, in this case the MongoDB-specific
 * DBObject.
 */
public final class ItemInformationAdapter {
    public static final DBObject toDBObject(ItemInformation itemInformation)
    {
    	 return new BasicDBObject("item_id", itemInformation.getItemId())
    			 .append("item_child_id", itemInformation.getItemChildId())
                 .append("item_type", itemInformation.getItemType())
                 .append("status", itemInformation.getStatus())	
                 .append("toDate", itemInformation.getToDate())	
                 .append("fromDate", itemInformation.getFromDate())	;
    }

}
