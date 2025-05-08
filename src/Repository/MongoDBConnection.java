package Repository;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
/**
 *
 * @author Shari
 */
public class MongoDBConnection {
        private static final String URI = "mongodb+srv://sharirobi565:Shinchan565.*lu@ecocart.x9mmeu8.mongodb.net/";

    public static MongoDatabase getDatabase() {
        MongoClient mongoClient = MongoClients.create(URI);
        return mongoClient.getDatabase("eco_cart");
    }
}
