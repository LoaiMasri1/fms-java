package com.fuzzyDucks.fms.classification;

import com.fuzzyDucks.fms.Database.MongoConnector;
import com.fuzzyDucks.fms.Database.enums.MongoConf;
import com.fuzzyDucks.fms.classification.enums.SortType;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;

import java.util.Date;

public class FileService {
    final private static MongoCollection<Document> files = MongoConnector.getInstance().getDatabase()
            .getCollection(MongoConf.FILES_COLLECTION.getValue());
    //private static FindIterable<Document> docs;

    public static FindIterable<Document> getFiles() {
        FindIterable<Document> tmp=files.find();
        FileUtils.isEmpty(tmp);
        return tmp;
    }

    public static FindIterable<Document> getBySizeGte(double size) {
        return files.find(new Document("size", size)).filter(Filters.gte("size",size));
    }

    public static FindIterable<Document> getBySizeLte(double size) {//throws Exception
        return files.find(new Document("size", size)).filter(Filters.lte("size",size));
    }

    public static FindIterable<Document> getEqualValue(String nameField, String value) {
        return files.find(new Document(nameField, value));

    }
    public static  FindIterable<Document> getSortedBy(String nameField, SortType type) {
        if(type.isAscending()){
            return getFiles().sort(Sorts.ascending(nameField));
        }
        return getFiles().sort(Sorts.descending(nameField));
    }
    public static  FindIterable<Document> getBetweenTwoDate(Date startDate,Date endDate) {
        return files.find().filter(Filters.gte("crtDate",startDate))
                .filter(Filters.lte("crtDate",endDate));
    }


}
