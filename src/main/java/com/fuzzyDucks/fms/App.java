package com.fuzzyDucks.fms;

import com.fuzzyDucks.fms.File.enums.ClassifySort;
import com.fuzzyDucks.fms.File.enums.SortType;
import org.bson.Document;

import com.fuzzyDucks.fms.File.FileSchema.FileSchemaService;

public class App {

    public static void main(String[] args) throws Exception {
        try {
        for (Document s : FileSchemaService.getSizeBy(0.0859375, ClassifySort.GREATEST)) {
            System.out.println(s);
        }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
