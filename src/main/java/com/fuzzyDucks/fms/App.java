package com.fuzzyDucks.fms;

import com.fuzzyDucks.fms.Auth.AuthService;
import com.fuzzyDucks.fms.Database.MongoConnector;
import com.fuzzyDucks.fms.User.UserSchema;
import com.fuzzyDucks.fms.User.enums.UserRole;

public class App {
    private static MongoConnector db = MongoConnector.getInstance();

    public static void main(String[] args) {
        try {
            String token = AuthService.login("muthana1", "12345");
            System.out.println(token);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
