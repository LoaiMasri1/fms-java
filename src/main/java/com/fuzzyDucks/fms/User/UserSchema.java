package com.fuzzyDucks.fms.User;

import java.util.Date;

import org.bson.Document;

import com.fuzzyDucks.fms.User.enums.UserRole;

public class UserSchema {
    private String username;
    private String password;
    private String email;
    private String name;
    private Date crtDate;
    private UserRole role;

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
        doc.append("username", username);
        doc.append("password", password);
        doc.append("email", email);
        doc.append("name", name);
        doc.append("role", role.getValue());
        doc.append("crtDate", crtDate);
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
