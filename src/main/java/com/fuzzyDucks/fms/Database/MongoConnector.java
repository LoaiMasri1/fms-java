package com.fuzzyDucks.fms.Database;

import com.fuzzyDucks.fms.Database.enums.MongoConf;
import com.fuzzyDucks.fms.Database.intf.IMongoDatabase;
import com.fuzzyDucks.fms.Logger.intf.ILogger;
import com.fuzzyDucks.fms.Logger.LoggingHandler;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class MongoConnector implements IMongoDatabase {
    private static MongoConnector instance = null;
    private static final MongoClientURI uri = new MongoClientURI(MongoConf.URI.getValue());
    private static MongoClient client = null;
    private static final ILogger logger = LoggingHandler.getInstance();
    public static MongoDatabase db;
    private MongoConnector() {
        connect();
    }


    public static MongoConnector getInstance() {
        if (instance == null) {
            instance = new MongoConnector();
        }
        logger.logInfo("MongoConnector instance created");
        return instance;
    }

    @Override
    public void connect() {
        try  {
            client =  new MongoClient(uri);
            db = client.getDatabase(MongoConf.DB.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        if (db == null) {
            throw new IllegalStateException("Database is not connected");
        }
    }


    @Override
    public MongoDatabase getDatabase() {
        return db;
    }
}
