package com.fuzzyDucks.fms.User.services.intf;

import com.fuzzyDucks.fms.User.models.UserSchema;
import org.bson.Document;

public interface UserService {
    void addUser(UserSchema user);
    Document getUser(String username);
}
