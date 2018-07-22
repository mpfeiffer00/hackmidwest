package com.hackmidwest.mongo;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.hackmidwest.adapter.BookAdapter;
import com.hackmidwest.adapter.ItemInformationAdapter;
import com.hackmidwest.adapter.PersonAdaptor;
import com.hackmiswest.common.Address;
import com.hackmiswest.common.Book;
import com.hackmiswest.common.ItemInformation;
import com.hackmiswest.common.Person;
import com.mongodb.DB;
import static java.util.Arrays.asList;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.util.JSON;

public class MongoDBClient {

	public static void main(String[] args) throws UnknownHostException {
		MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://vmordersdev2.ip.devcerner.net:27017"));
		//personTable(mongoClient);
		//bookTable(mongoClient);
		//itemInformationTable(mongoClient);
		convertDataToJSON(mongoClient);
	}

	public static void convertDataToJSON(final MongoClient mongoClient) {
		DBCollection collection = mongoClient.getDB("shareit").getCollection("iteminformation");
		DBCursor cursor = collection.find();
		JSON json = new JSON();
		String serialize = json.serialize(cursor);
		System.out.println(serialize);
	}

	@SuppressWarnings("null")
	public static void personTable(final MongoClient mongoClient) {
		DB database = mongoClient.getDB("shareit");
		DBCollection collection = database.getCollection("person");

		List<Address> addresses = Arrays.asList(new Address("123 Fake St", "LondonTown", 1234567890));
		Person bob = new Person("bob", "Bob The Amazing", addresses);
		collection.insert(PersonAdaptor.toDBObject(bob));
	}

	public static void bookTable(final MongoClient mongoClient) {
		DB database = mongoClient.getDB("shareit");
		DBCollection collection = database.getCollection("book");

		Book lor = new Book(1, "The Lord Of the ring", "J. R. R. Tolkien", "9781782808084", "ISBN 978-1-78280-808-4",
				1937, new Date());
		collection.insert(BookAdapter.toDBObject(lor));

	}

	public static void itemInformationTable(final MongoClient mongoClient) {
		DB database = mongoClient.getDB("shareit");
		DBCollection collection = database.getCollection("iteminformation");
		ItemInformation itemInfo = new ItemInformation(1, 1, "borrowed", new Date(), new Date());
		collection.insert(ItemInformationAdapter.toDBObject(itemInfo));

	}

}
