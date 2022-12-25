package com.fuzzyDucks.fms.UserMenu.Authentication;

import com.fuzzyDucks.fms.Auth.AuthService;
import com.fuzzyDucks.fms.UserMenu.Operation;

public class Login implements Operation {
    @Override
    public void execute(String info) {
        try {
            String userName = info.split(" ")[0];
            String password = info.split(" ")[1];
            AuthService.login(userName,password);
        }catch (Exception e){
            e.getMessage();
        }

    }
}
