package shareit.db;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.mongodb.DB;
import static java.util.Arrays.asList;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.util.JSON;

import shareit.adapter.BookAdapter;
import shareit.adapter.ItemInformationAdapter;
import shareit.adapter.PersonAdaptor;
import shareit.common.Address;
import shareit.common.Book;
import shareit.common.ItemInformation;
import shareit.common.Person;

public class MongoDBClient {

	public static void main(String[] args) throws UnknownHostException {
		MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://vmordersdev2.ip.devcerner.net:27017"));
		personTable(mongoClient);
		bookTable(mongoClient);
		itemInformationTable(mongoClient);
		convertDataToJSON(mongoClient);
	}

	public static void convertDataToJSON(final MongoClient mongoClient) {
		DBCollection collection = mongoClient.getDB("shareit").getCollection("person");
		//DBCursor cursor = collection.find();
		//JSON json = new JSON();
		//String serialize = json.serialize(cursor);
		System.out.println(collection.getCount());
	}

	@SuppressWarnings("null")
	public static void personTable(final MongoClient mongoClient) {
		DB database = mongoClient.getDB("shareit");
		DBCollection collection = database.getCollection("person");

		List<Address> addresses = Arrays.asList(new Address("123 Fake St", "LondonTown", 1234567890));
		Person bob = new Person(1, "bob", "Bob the builder", 2, addresses);
		collection.insert(PersonAdaptor.toDBObject(bob));
	}

	public static void bookTable(final MongoClient mongoClient) {
		DB database = mongoClient.getDB("shareit");
		DBCollection collection = database.getCollection("book");

		Book lor = new Book(3, 2, "Dune", "Frank Herbert", "0441172717", new Date(), 53732, "9780441172719", "https://images.gr-assets.com/books/1426192671m/53732.jpg",
				"https://images.gr-assets.com/books/1426192671s/53732.jpg");
		collection.insert(BookAdapter.toDBObject(lor));

	}

	public static void itemInformationTable(final MongoClient mongoClient) {
		DB database = mongoClient.getDB("shareit");
		DBCollection collection = database.getCollection("iteminformation");
		
		ItemInformation itemInfo = new ItemInformation(2, 3, "book", "borrowed", new Date(), new Date());
		collection.insert(ItemInformationAdapter.toDBObject(itemInfo));

	}

}
