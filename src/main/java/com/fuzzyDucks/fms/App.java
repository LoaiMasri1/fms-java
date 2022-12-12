package com.fuzzyDucks.fms;

import org.bson.Document;

import com.fuzzyDucks.fms.Database.MongoConnector;
import com.fuzzyDucks.fms.Database.enums.MongoConf;
import com.mongodb.client.MongoCollection;

public class App {
    private static MongoConnector db = MongoConnector.getInstance();

    public static void main(String[] args) {
        // temporary code for testing
        MongoCollection<Document> files = db.getDatabase().getCollection(MongoConf.FILES_COLLECTION.getValue());
        System.out.println(files.countDocuments());
    }
}
