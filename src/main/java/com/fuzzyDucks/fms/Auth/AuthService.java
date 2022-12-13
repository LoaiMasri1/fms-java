package com.fuzzyDucks.fms.Auth;

import org.bson.Document;

import com.fuzzyDucks.fms.Auth.JWT.JWTService;
import com.fuzzyDucks.fms.User.UserService;
import com.fuzzyDucks.fms.User.UserUtils;

public class AuthService {

    private static JWTService jwtService = new JWTService();

    private AuthService() {
    }

    public static String getToken(Document user) {
        jwtService.signToken(user);
        return jwtService.getToken();
    }

    public static Boolean validateUser(String username, String password) {
        Document user = UserService.getUser(username);
        if (user != null && UserUtils.checkPassword(password, user.getString("password"))) {
            return true;
        }
        throw new IllegalArgumentException("Invalid username or password");
    }

    public static String login(String username, String password) {
        if (validateUser(username, password)) {
            return getToken(UserService.getUser(username));
        }
        throw new IllegalArgumentException("Invalid username or password 2");
    }

}
