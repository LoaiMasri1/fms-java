package com.fuzzyDucks.fms.Factories.impl;

import com.fuzzyDucks.fms.Auth.AuthService;
import com.fuzzyDucks.fms.Exceptions.InvalidDataException;
import com.fuzzyDucks.fms.Exceptions.NullDataException;
import com.fuzzyDucks.fms.Factories.intf.IUserFactory;
import com.fuzzyDucks.fms.User.User;
import com.fuzzyDucks.fms.User.models.UserSchema;

public class UserFactory implements IUserFactory {
    private final AuthService authService = new AuthService();

    @Override
    public void doAction(String action, Object object) {
        action = action.toLowerCase();
        User user = (User) object;
        switch (action) {
            case "create":
                if (user.getEmail() == null || user.getUsername() == null) {
                    throw new NullDataException("Email or username is null");
                }
                new UserSchema(user);
                break;
            case "login":
                authService.login(user.getUsername(), user.getPassword());
                break;
            default:
                throw new InvalidDataException("Invalid action");
        }
    }

    @Override
    public void doAction(String action) {
        if ("logout".equalsIgnoreCase(action))
            authService.logout();
        else {
            throw new InvalidDataException("Invalid action");
        }
    }
}






