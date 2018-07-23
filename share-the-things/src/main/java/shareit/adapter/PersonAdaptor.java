package shareit.adapter;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import shareit.common.Person;

/**
 * This Adaptor allows us to separate our domain object, Person, from our library-specific classes, in this case the MongoDB-specific
 * DBObject.
 */
public final class PersonAdaptor {
    public static final DBObject toDBObject(Person person) {
    	 return new BasicDBObject("person_id", person.getPersonId())
                 .append("first_name", person.getFirstName())
                 .append("second_name", person.getSecondName())
                 .append("item_id", person.getItemId())
                 .append("address", new BasicDBObject("street", person.getAddresses().get(0).getStreet())
                                              .append("city", person.getAddresses().get(0).getTown())
                                              .append("phone", person.getAddresses().get(0).getPhone()))	;
    }
}