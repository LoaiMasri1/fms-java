package com.fuzzyDucks.fms.User;

import java.util.Date;

import com.fuzzyDucks.fms.Logger.ILogger;
import com.fuzzyDucks.fms.Logger.LoggingHandler;
import com.fuzzyDucks.fms.User.enums.UserFieldName;
import org.bson.Document;

import com.fuzzyDucks.fms.User.enums.UserRole;

public class UserSchema {
    private String username;
    private String password;
    private String email;
    private String name;
    private Date crtDate;
    private UserRole role;
    private static final ILogger logger= LoggingHandler.getInstance();
    public UserSchema(String username, String password, String email, String name, UserRole role) {
        this.username = username;
        this.password = UserUtils.hashPassword(password);
        this.email = email;
        this.name = name;
        this.crtDate = new Date();
        this.role = role == null ? UserRole.READER : role;
        logger.logInfo("Creating user successfully "+getClass().getName());
        UserService.addUser(this);
    }

    public Document toDocument() {
        Document doc = new Document();
        doc.append(UserFieldName.USER_NAME.getValue(), username);
        doc.append(UserFieldName.PASSWORD.getValue(), password);
        doc.append(UserFieldName.EMAIL.getValue(), email);
        doc.append(UserFieldName.NAME.getValue(), name);
        doc.append(UserFieldName.ROLE.getValue(), role.getValue());
        doc.append(UserFieldName.CREATE_DATE.getValue(), crtDate);
        logger.logInfo("Convert UserSchema to document successfully "+getClass().getName());
        return doc;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public UserRole getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

}
