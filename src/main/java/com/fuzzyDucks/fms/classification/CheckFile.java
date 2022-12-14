package com.fuzzyDucks.fms.classification;

import com.mongodb.client.FindIterable;
import org.bson.Document;

public class CheckFile {

    public static void isEmpty(FindIterable<Document> docs){
        if (docs.first() == null) {
            throw new IllegalArgumentException("No files");
        }
    }
}
