package com.fuzzyDucks.fms;

import com.fuzzyDucks.fms.Auth.impl.AuthServiceImpl;
import com.fuzzyDucks.fms.Auth.intf.AuthService;
import com.fuzzyDucks.fms.Factories.impl.FileFactory;
import com.fuzzyDucks.fms.Factories.impl.UserFactory;
import com.fuzzyDucks.fms.Factories.intf.IFactory;
import com.fuzzyDucks.fms.Factories.intf.IUserFactory;
import com.fuzzyDucks.fms.File.File;
import com.fuzzyDucks.fms.User.User;
import com.fuzzyDucks.fms.User.models.UserSchema;

public class App {
    public static void main(String[] args) {
        try {
            AuthService authService = new AuthServiceImpl();
            authService.login("admin","admin");
            IFactory factory = new FileFactory();
            factory.doAction("import",new File("HDFS1","docx","C:\\Users\\jehad\\OneDrive\\Desktop\\HDFS\\HDFS1.docx"));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
