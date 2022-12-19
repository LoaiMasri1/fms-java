package com.fuzzyDucks.fms;

import org.bson.Document;

import com.fuzzyDucks.fms.Auth.AuthService;
import com.fuzzyDucks.fms.Auth.JWT.JWTService;
import com.fuzzyDucks.fms.Cache.Cache;
import com.fuzzyDucks.fms.User.UserSchema;
import com.fuzzyDucks.fms.User.UserService;
import com.fuzzyDucks.fms.User.enums.UserRole;
import com.mongodb.client.FindIterable;
import com.fuzzyDucks.fms.File.FileSchema.FileSchemaService;

public class App {

    public static void main(String[] args) throws Exception {
        try {
            // AuthService.login("admin", "admin");

            // Cache cache = Cache.getInstance();
            // System.out.println(JWTService.decodeObject(cache.get("token").toString(),"username"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
