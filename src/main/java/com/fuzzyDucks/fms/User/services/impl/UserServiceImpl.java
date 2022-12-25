package com.fuzzyDucks.fms.User.services.impl;

import com.fuzzyDucks.fms.Database.intf.IMongoDatabase;
import com.fuzzyDucks.fms.Logger.intf.ILogger;
import com.fuzzyDucks.fms.Logger.LoggingHandler;
import com.fuzzyDucks.fms.User.models.UserSchema;
import com.fuzzyDucks.fms.User.enums.UserFieldName;
import com.fuzzyDucks.fms.User.services.intf.UserService;
import org.bson.Document;
import com.fuzzyDucks.fms.Database.MongoConnector;
import com.fuzzyDucks.fms.Database.enums.MongoConf;
import com.mongodb.client.MongoCollection;

public class UserServiceImpl implements UserService {

    final private static IMongoDatabase db = MongoConnector.getInstance();
    private static final MongoCollection<Document> users = db.getDatabase()
            .getCollection(MongoConf.USERS_COLLECTION.getValue());
    private static final ILogger logger = LoggingHandler.getInstance();

    @Override
    public void addUser(UserSchema user) {
        if (users.find(new Document(UserFieldName.USER_NAME.getValue(), user.getUsername())).first() != null) {
            logger.logWarning("User already exists");
            throw new IllegalArgumentException("User already exists");
        }
        users.insertOne(user.toDocument());
        logger.logInfo(UserServiceImpl.class.getName() + " add User successfully");
    }

    @Override
    public Document getUser(String username) {
        return users.find(new Document(UserFieldName.USER_NAME.getValue(), username)).first();
    }

}
