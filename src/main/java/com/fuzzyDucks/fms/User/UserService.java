package com.fuzzyDucks.fms.User;

import org.bson.Document;

import com.fuzzyDucks.fms.Database.MongoConnector;
import com.fuzzyDucks.fms.Database.enums.MongoConf;
import com.mongodb.client.MongoCollection;

public class UserService {

    private static MongoCollection<Document> users = MongoConnector.getInstance().getDatabase()
            .getCollection(MongoConf.USERS_COLLECTION.getValue());

    private UserService() {
    }

    public static void addUser(UserSchema user) {
        if (users.find(new Document("username", user.getUsername())).first() != null)
            throw new IllegalArgumentException("User already exists");
        users.insertOne(user.toDocument());
    }

    public static void removeUser(UserSchema user) {
        users.deleteOne(user.toDocument());
    }

    public static void updateUser(UserSchema user, UserSchema newUser) {
        users.updateOne(user.toDocument(), newUser.toDocument());
    }

    public static Document getUser(String username) {
        Document doc = users.find(new Document("username", username)).first();
        if (doc == null)
            throw new IllegalArgumentException("User not found");
        return doc;
    }

}
