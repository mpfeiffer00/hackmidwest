package com.hackmidwest.adapter;

import java.util.Date;

import com.hackmiswest.common.ItemInformation;
import com.hackmiswest.common.Person;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * This Adaptor allows us to separate our domain object, ItemInformationAdapter, from our library-specific classes, in this case the MongoDB-specific
 * DBObject.
 */
public final class ItemInformationAdapter {
    public static final DBObject toDBObject(ItemInformation itemInformation) {
    	 return new BasicDBObject("id", itemInformation.getItemId())
                 .append("bookId", itemInformation.getBookId())
                 .append("status", itemInformation.getStatus())	
                 .append("toDate", itemInformation.getToDate())	
                 .append("fromDate", itemInformation.getFromDate())	;
    }

}
