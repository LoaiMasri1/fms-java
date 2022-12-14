package com.fuzzyDucks.fms;

import com.fuzzyDucks.fms.classification.FileService;
import org.bson.Document;

public class App {

    public static void main(String[] args) {

        for (Document s :FileService.getFilesBySize("5")) {
            System.out.println(s);
        }


    }

}
