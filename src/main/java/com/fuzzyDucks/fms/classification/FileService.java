package com.fuzzyDucks.fms.classification;

import com.fuzzyDucks.fms.Database.MongoConnector;
import com.fuzzyDucks.fms.Database.enums.MongoConf;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;

import java.util.Date;

public class FileService {
    private static MongoCollection<Document> files = MongoConnector.getInstance().getDatabase()
            .getCollection(MongoConf.FILES_COLLECTION.getValue());
    private static FindIterable<Document> docs;

    public static FindIterable<Document> getFiles() {
        docs = files.find();
        CheckFile.isEmpty(docs);
        return docs;
    }

    public static FindIterable<Document> getFilesBySize(String size) {//throws Exception
        docs = files.find(new Document("size", size))
                .filter(Filters.gte("size",size));
        CheckFile.isEmpty(docs);
        return docs;
    }

    public static FindIterable<Document> getFilesByName(String name) {
        docs = files.find(new Document("name", name));
        CheckFile.isEmpty(docs);
        return docs;
    }
    public static FindIterable<Document> getFilesByType(String type) {
        docs = files.find(new Document("type", type));
        CheckFile.isEmpty(docs);
        return docs;
    }
    public static  FindIterable<Document> getFileByPath(String path) {
        docs = files.find(new Document("path",path));
        CheckFile.isEmpty(docs);
        return docs;
    }

    public static  FindIterable<Document> getFileSortedBySize() {
        docs = FileService.getFiles();
        docs = docs.sort(Sorts.ascending("size"));
        return docs;
    }
    public static  FindIterable<Document> getFileSortedByName() {
        docs = FileService.getFiles();
        docs = docs.sort(Sorts.ascending("name"));
        CheckFile.isEmpty(docs);
        return docs;
    }
    public static  FindIterable<Document> getFileSortedByDate() {
        docs = FileService.getFiles();
        docs = docs.sort(Sorts.ascending("crtDate"));
        return docs;
    }

    public static  FindIterable<Document> getFilesBetweenTwoDate(Date startDate,Date endDate) {
        docs = files.find()
                .filter(Filters.gte("crtDate",startDate))
                .filter(Filters.lte("crtDate",endDate));
        CheckFile.isEmpty(docs);
        return docs;
    }


}
