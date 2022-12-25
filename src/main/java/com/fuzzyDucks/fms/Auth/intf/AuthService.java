package com.fuzzyDucks.fms.Auth.intf;

import org.bson.Document;

public interface AuthService {
    void login(String username, String password);
    void logout();
    boolean validateUser(String username, String password);
    String getToken(Document user);
}
