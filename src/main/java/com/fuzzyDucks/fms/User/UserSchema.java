package com.fuzzyDucks.fms.User;
import java.util.Date;
import com.fuzzyDucks.fms.User.enums.UserFieldName;
import org.bson.Document;

import com.fuzzyDucks.fms.User.enums.UserRole;

public class UserSchema {
    private final String username;
    private final String password;
    private final String email;
    private final String name;
    private final Date crtDate;
    private final UserRole role;
    public UserSchema(String username, String password, String email, String name, UserRole role) {
        this.username = username;
        this.password = UserUtils.hashPassword(password);
        this.email = email;
        this.name = name;
        this.crtDate = new Date();
        this.role = role == null ? UserRole.READER : role;
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
