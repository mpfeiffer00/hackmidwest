package com.hackmidwest.adapter;

import com.hackmiswest.common.Person;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * This Adaptor allows us to separate our domain object, Person, from our library-specific classes, in this case the MongoDB-specific
 * DBObject.
 */
public final class PersonAdaptor {
    public static final DBObject toDBObject(Person person) {
    	 return new BasicDBObject("id", person.getId())
                 .append("name", person.getName())
                 .append("address", new BasicDBObject("street", person.getAddresses().get(0).getStreet())
                                              .append("city", person.getAddresses().get(0).getTown())
                                              .append("phone", person.getAddresses().get(0).getPhone()))	;
    }
}