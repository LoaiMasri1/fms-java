package com.fuzzyDucks.fms.Database.intf;

import com.mongodb.client.MongoDatabase;

public interface IMongoDatabase extends IDatabase {
    MongoDatabase getDatabase();
}

