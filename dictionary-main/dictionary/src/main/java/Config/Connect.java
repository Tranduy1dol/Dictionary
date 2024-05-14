package Config;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.MongoClientSettings.builder;
import static com.mongodb.client.MongoClients.*;

public class Connect {

    public static MongoClient client = null;

    public static void Connect() {
        String connectionString =
                "mongodb+srv://pklinh:pklinh@dictionary.klmocrh.mongodb.net/?retryWrites=true&w=majority&appName=Dictionary";
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi).build();

        // Create a new client and connect to the server
        try (MongoClient mongoClient = create(settings)) {
            try {
                // Send a ping to confirm a successful connection
                MongoDatabase database = mongoClient.getDatabase("admin");
                database.runCommand(new Document("ping", 1));
                System.out.println("Pinged your deployment. You successfully connected to MongoDB!");

                client = create(settings);
            } catch (MongoException e) {
                e.printStackTrace();
            }
        }
    }
}
