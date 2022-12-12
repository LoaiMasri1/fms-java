package com.fuzzyDucks.fms.Database;

import com.fuzzyDucks.fms.Database.enums.MongoConf;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class MongoConnector {
    private static MongoConnector instance = null;
    private static MongoClientURI uri = new MongoClientURI(MongoConf.URI.getValue());
    private static MongoClient client = new MongoClient(uri);
    private static MongoDatabase db;

    private MongoConnector() {
        db = client.getDatabase(MongoConf.DB.getValue());
    }

    public static MongoConnector getInstance() {
        if (instance == null) {
            instance = new MongoConnector();
        }
        return instance;
    }

    public MongoDatabase getDatabase() {
        return db;
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            client.close();
        } finally {
            super.finalize();
        }
    }

}
