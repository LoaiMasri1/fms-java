package com.fuzzyDucks.fms;

import com.fuzzyDucks.fms.Auth.AuthService;
import com.fuzzyDucks.fms.Cache.Cache;
import com.fuzzyDucks.fms.File.FileSchema.FileSchema;
import com.fuzzyDucks.fms.File.FileService;
import com.fuzzyDucks.fms.File.FileUtils;
import com.fuzzyDucks.fms.File.enums.ClassifySort;
import com.fuzzyDucks.fms.File.enums.SortType;
import org.bson.Document;

import com.fuzzyDucks.fms.File.FileSchema.FileSchemaService;

import java.io.File;

public class App {

    public static void main(String[] args) throws Exception {
        try {
            AuthService.login("admin", "admin");
            Cache cache = Cache.getInstance();
            System.out.println(cache.get("token") + "No token found");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            FileSchema f = new FileSchema(new File("C:\\Users\\hp\\Downloads\\Final-project-software-2-.doc"));
            //FileService.deleteFile("Final-project-software-2-","doc");
            //FileService.exportFile("Final-project-software-2-","doc";
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}
