package com.fuzzyDucks.fms.UserMenu.Authentication;

import com.fuzzyDucks.fms.Auth.AuthService;
import com.fuzzyDucks.fms.UserMenu.Operation;

public class Logout implements Operation {
    @Override
    public void execute(String info) {
        try {
            AuthService.logout();
        }catch (Exception e){
            e.getMessage();
        }
    }
}
