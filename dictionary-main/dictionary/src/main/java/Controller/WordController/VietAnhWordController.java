package Controller.WordController;

import Config.Connect;
import Model.VietAnhWord;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class VietAnhWordController extends WordController<VietAnhWord> {

    public VietAnhWordController() {
        this.collection = Connect.client
                .getDatabase("Dictionary")
                .getCollection("vietAnhWord");
    }

    @Override
    public List<Document> readOperation(VietAnhWord filter, int limit) {
        Bson query = Filters.or(
                Filters.regex("engWord", "^" + filter.getEngWord() + ".*i"),
                Filters.regex("vietWord", "^" + filter.getVietWord() + ".*i"));
        FindIterable results = collection.find(query).limit(limit);
        List<Document> documents = new ArrayList<>();
        for (Object document : results) {
            documents.add((Document) document);
        }
        return documents;
    }

    @Override
    public void writeOperation(VietAnhWord data) {
        try {
            Document doc = new Document()
                    .append("_id", new ObjectId())
                    .append("engWord", data.getEngWord())
                    .append("vietWord", data.getVietWord());
            collection.insertOne(doc);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void updateOperation(VietAnhWord filter, VietAnhWord data) {
        Bson query = Filters.or(
                Filters.eq("engWord", filter.getEngWord()),
                Filters.eq("vietWord", filter.getVietWord())
        );
        Document update = new Document()
                .append("engWord", data.getEngWord())
                .append("vietWord", data.getVietWord());
        try {
            UpdateResult result = collection.replaceOne(query, update);
            System.out.println(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteOperation(VietAnhWord filter) {
        Bson query = Filters.or(
                Filters.eq("engWord", filter.getEngWord()),
                Filters.eq("vietWord", filter.getVietWord())
        );
        try {
            collection.deleteOne(query);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
