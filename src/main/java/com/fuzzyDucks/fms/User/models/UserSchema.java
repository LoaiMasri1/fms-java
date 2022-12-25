package com.fuzzyDucks.fms.User.models;
import java.util.Date;

import com.fuzzyDucks.fms.User.User;
import com.fuzzyDucks.fms.User.enums.UserFieldName;
import com.fuzzyDucks.fms.User.services.impl.UserServiceImpl;
import org.bson.Document;
import com.fuzzyDucks.fms.User.utils.UserUtils;
import com.fuzzyDucks.fms.User.enums.UserRole;


public class UserSchema {
    final private User user;
    private final Date crtDate;
    private final UserRole role;

    private final static UserServiceImpl userService = new UserServiceImpl();
    public UserSchema(User user) {
        this.user = user;
        this.crtDate = new Date();
        this.role = UserRole.READER;
        userService.addUser(this);
    }

    public Document toDocument() {
        Document doc = new Document();
        doc.append(UserFieldName.USER_NAME.getValue(), user.getUsername());
        doc.append(UserFieldName.PASSWORD.getValue(), UserUtils.hashPassword(user.getPassword()));
        doc.append(UserFieldName.EMAIL.getValue(), user.getEmail());
        doc.append(UserFieldName.NAME.getValue(), user.getName());
        doc.append(UserFieldName.ROLE.getValue(), role.getValue());
        doc.append(UserFieldName.CREATE_DATE.getValue(), crtDate);
        return doc;
    }

    public String getUsername() {
        return user.getUsername();
    }
}
