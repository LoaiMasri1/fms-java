package com.fuzzyDucks.fms;

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
            //FileSchema f = new FileSchema(new File("C:\\Users\\jehad\\OneDrive\\Desktop\\First delivery.txt"));
            //FileService.deleteFile("First delivery","txt");
            //FileService.exportFile("First delivery","txt");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
