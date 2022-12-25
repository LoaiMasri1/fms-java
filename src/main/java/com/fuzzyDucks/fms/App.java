package com.fuzzyDucks.fms;

import com.fuzzyDucks.fms.Factories.impl.FileFactory;
import com.fuzzyDucks.fms.Factories.impl.UserFactory;
import com.fuzzyDucks.fms.Factories.intf.IFileFactory;
import com.fuzzyDucks.fms.Factories.intf.IUserFactory;
import com.fuzzyDucks.fms.File.File;
import com.fuzzyDucks.fms.User.User;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        IUserFactory s = new UserFactory();
        s.doAction("login",new User("admin","admin"));
        IFileFactory d = new FileFactory();
        d.doAction("import",new File("C:\\Users\\hp\\Downloads\\Hadoop Distributed File System (HDFS) (1).docx"));
        s.doAction("logout");
    }
}
