package com.fuzzyDucks.fms.User.models;
import java.util.Date;
import com.fuzzyDucks.fms.User.enums.UserFieldName;
import com.fuzzyDucks.fms.User.services.UserService;
import com.fuzzyDucks.fms.User.utils.UserUtils;
import org.bson.Document;

import com.fuzzyDucks.fms.User.enums.UserRole;


public class UserSchema {
    private final String username;
    private final String password;
    private final String email;
    private final String name;
    private final Date crtDate;
    private final UserRole role;

    private final static UserService userService = new UserService();
    public UserSchema(String username, String password, String email, String name) {
        this.username = username;
        this.password = UserUtils.hashPassword(password);
        this.email = email;
        this.name = name;
        this.crtDate = new Date();
        this.role = UserRole.READER;
        userService.addUser(this);
    }

    public Document toDocument() {
        Document doc = new Document();
        doc.append(UserFieldName.USER_NAME.getValue(), username);
        doc.append(UserFieldName.PASSWORD.getValue(), password);
        doc.append(UserFieldName.EMAIL.getValue(), email);
        doc.append(UserFieldName.NAME.getValue(), name);
        doc.append(UserFieldName.ROLE.getValue(), role.getValue());
        doc.append(UserFieldName.CREATE_DATE.getValue(), crtDate);
        return doc;
    }

    public String getUsername() {
        return username;
    }
}
