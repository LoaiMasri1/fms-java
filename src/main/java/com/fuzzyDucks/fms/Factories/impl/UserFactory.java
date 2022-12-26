package com.fuzzyDucks.fms.Factories.impl;

import com.fuzzyDucks.fms.Auth.AuthService.impl.AuthServiceImpl;
import com.fuzzyDucks.fms.Auth.AuthService.intf.AuthService;
import com.fuzzyDucks.fms.Exceptions.InvalidDataException;
import com.fuzzyDucks.fms.Exceptions.NullDataException;
import com.fuzzyDucks.fms.Factories.intf.IUserFactory;
import com.fuzzyDucks.fms.User.User;
import com.fuzzyDucks.fms.User.models.UserSchema;

import java.io.IOException;

public class UserFactory implements IUserFactory {
    private final AuthService authService = new AuthServiceImpl();

    @Override
    public void doAction(String action, Object object) {
        action = action.toLowerCase();
        User user = (User) object;
        if ("create".equals(action)) {
            new UserSchema(user);
        } else {
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
    @Override
    public void doAction(String action, String userName, String password) {
        if ("login".equalsIgnoreCase(action))
            authService.login(userName, password);
        else {
            throw new InvalidDataException("Invalid action");
        }
    }

}






