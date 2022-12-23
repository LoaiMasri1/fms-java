package com.fuzzyDucks.fms.User;

import com.fuzzyDucks.fms.Logger.ILogger;
import com.fuzzyDucks.fms.Logger.LoggingHandler;
import com.fuzzyDucks.fms.User.enums.UserFieldName;
import org.bson.Document;
import com.fuzzyDucks.fms.Database.MongoConnector;
import com.fuzzyDucks.fms.Database.enums.MongoConf;
import com.mongodb.client.MongoCollection;

public class UserService {

    private static final MongoCollection<Document> users = MongoConnector.getInstance().getDatabase()
            .getCollection(MongoConf.USERS_COLLECTION.getValue());
    private static final ILogger logger = LoggingHandler.getInstance();

    private UserService() {
    }

    public static void addUser(UserSchema user) {
        if (users.find(new Document(UserFieldName.USER_NAME.getValue(), user.getUsername())).first() != null) {
            throw new IllegalArgumentException("User already exists");
        }
        users.insertOne(user.toDocument());
        logger.logInfo("add User successfully" + UserService.class.getName());
    }

    public static void removeUser(UserSchema user) {
        users.deleteOne(user.toDocument());
        logger.logInfo("remove User successfully" + UserService.class.getName());
    }

    public static void updateUser(UserSchema user, UserSchema newUser) {
        users.updateOne(user.toDocument(), newUser.toDocument());
        logger.logInfo("update User successfully" + UserService.class.getName());
    }

    public static Document getUser(String username) {
        Document doc = users.find(new Document(UserFieldName.USER_NAME.getValue(), username)).first();
        if (doc == null)
            throw new IllegalArgumentException("User not found");
        return doc;
    }

}
