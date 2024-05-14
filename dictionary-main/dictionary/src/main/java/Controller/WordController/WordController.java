package Controller.WordController;

import Config.Connect;
import Controller.Controller;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.List;

public abstract class WordController<T> extends Controller {

    protected MongoCollection collection;


    public abstract List<Document> readOperation(T filter, int limit);
    public abstract void writeOperation(T data);
    public abstract void updateOperation(T filter, T data);
    public abstract void deleteOperation(T filter);

    public MongoCollection getCollection() {
        return collection;
    }
}
