package Controller.WordController;

import Config.Connect;
import Model.EnglishWord;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class NormalWordController extends WordController<EnglishWord> {

    public NormalWordController() {
        this.collection = Connect.client
                .getDatabase("Dictionary")
                .getCollection("normalWord");
    }

    @Override
    public List<Document> readOperation(EnglishWord filter, int limit) {
        Bson query = Filters.regex("word", filter.getWord()); // Case-insensitive search
        FindIterable<Document> results = collection.find(query).limit(limit);
        List<Document> englishWords = new ArrayList<>();
        for (Document document : results) {
            englishWords.add(document);
        }
        return englishWords;
    }

    @Override
    public void writeOperation(EnglishWord data) {
        try {
            Document doc = new Document()
                    .append("_id", new ObjectId())
                    .append("word", data.getWord())
                    .append("pronunciation", data.getPronunciation())
                    .append("definition", data.getDefinition())
                    .append("type", data.getType());
            String response = collection.insertOne(doc).toString();
            System.out.println(response);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void updateOperation(EnglishWord filter, EnglishWord data) {
        Bson query = Filters.or(
                Filters.eq("word", filter.getWord()),
                Filters.in("type", filter.getType().getFirst()),
                Filters.in("definition", filter.getDefinition().getFirst())
        );
        Document update = new Document()
                .append("word", filter.getWord())
                .append("pronunciation", filter.getPronunciation())
                .append("definition", data.getDefinition())
                .append("type", data.getType());
        try {
            UpdateResult result = collection.replaceOne(query, update);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void deleteOperation(EnglishWord filter) {
        Bson query = Filters.or(
                Filters.eq("word", filter.getWord()),
                Filters.in("type", filter.getType().getFirst()),
                Filters.in("definition", filter.getDefinition().getFirst())
        );
        try {
            collection.deleteOne(query);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
