package com.fuzzyDucks.fms;

import com.fuzzyDucks.fms.Factories.impl.UserFactory;
import com.fuzzyDucks.fms.Factories.intf.IUserFactory;
import com.fuzzyDucks.fms.User.User;

public class App {
    public static void main(String[] args) {
        try {
            User user = new User("muthana", "muthana","muthana","muthana");
            IUserFactory userFactory = new UserFactory();
            //userFactory.doAction("create",user);
            userFactory.doAction("login", user);
            userFactory.doAction("logout");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
