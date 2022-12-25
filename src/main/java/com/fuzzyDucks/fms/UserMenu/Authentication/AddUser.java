package com.fuzzyDucks.fms.UserMenu.Authentication;

import com.fuzzyDucks.fms.User.models.UserSchema;
import com.fuzzyDucks.fms.UserMenu.Operation;

public class AddUser implements Operation {


    @Override
    public void execute(String info) {
        String username = info.split(" ")[0];
        String password = info.split(" ")[1];
        String email = info.split(" ")[2];
        String name = info.split(" ")[3];
        UserSchema userSchema=new UserSchema(username,password,email,name);
    }
}
