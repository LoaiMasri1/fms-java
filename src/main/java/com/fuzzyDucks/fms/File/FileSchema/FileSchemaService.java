package com.fuzzyDucks.fms.File.FileSchema;

import com.fuzzyDucks.fms.Database.MongoConnector;
import com.fuzzyDucks.fms.Database.enums.MongoConf;
import com.fuzzyDucks.fms.File.FileUtils;
import com.fuzzyDucks.fms.File.enums.ClassifySort;
import com.fuzzyDucks.fms.File.enums.FileFieldName;
import com.fuzzyDucks.fms.File.enums.SortType;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

import java.util.ArrayList;
import java.util.Date;

import org.bson.Document;
import org.bson.conversions.Bson;

public class FileSchemaService {

    final private static MongoCollection<Document> files = MongoConnector.getInstance().getDatabase()
            .getCollection(MongoConf.FILES_COLLECTION.getValue());

    public void addFile(FileSchema file) {
        if (files.find(findWithNameAndType(file.getName(), file.getType())).first() != null)
            throw new IllegalArgumentException("File already exists");
        files.insertOne(file.toDocument());
    }

    public void removeFile(String name, String type) {
        files.deleteOne(findWithNameAndType(FileUtils.encodeValue(name), FileUtils.encodeValue(type)));
    }

    public String getFilePath(String name, String type) {
        FindIterable<Document> findIterable = files
                .find(findWithNameAndType(FileUtils.encodeValue(name), FileUtils.encodeValue(type)));
        if (findIterable == null)
            throw new IllegalArgumentException("file not found");
        return FileUtils.decodeValue(findIterable.first().getString(FileFieldName.PATH.getValue()));
    }

    public Bson findWithNameAndType(String name, String type) {
        return Filters.and(Filters.eq(FileFieldName.NAME.getValue(), name), Filters.eq(FileFieldName.TYPE.getValue(), type));
    }

    public static FindIterable<Document> getFiles() {
        FindIterable<Document> tmp = files.find();
        FileUtils.isEmpty(tmp);
        return tmp;
    }



    public static ArrayList<Document> getSizeBy(double size, ClassifySort type) {
        if (type.isLeast())
            return FileUtils.decodeData(files.find(new Document(FileFieldName.SIZE.getValue(), size))
                    .filter(Filters.lte(FileFieldName.SIZE.getValue(), size)));
        return FileUtils.decodeData(files.find(new Document(FileFieldName.SIZE.getValue(), size))
                    .filter(Filters.gte(FileFieldName.SIZE.getValue(), size)));

    }

    public static ArrayList<Document> getEqualValue(String fieldName, String value) {
        if (fieldName.equals(FileFieldName.PATH.getValue()))
            throw new IllegalArgumentException("you can't access using Path Field");
        if (fieldName.equals(FileFieldName.NAME.getValue()) || fieldName.equals(FileFieldName.TYPE.getValue()))
            value = FileUtils.encodeValue(value);
        return FileUtils.decodeData(files.find(new Document(fieldName, value)));
    }

    public static ArrayList<Document> getSortedBy(String fieldName, SortType type) {
        if (fieldName.equals(FileFieldName.PATH.getValue()))
            throw new IllegalArgumentException("you can't access using Path Field");
        if (type.isAscending())
            return FileUtils.decodeData(getFiles().sort(Sorts.ascending(fieldName)));
        return FileUtils.decodeData(getFiles().sort(Sorts.descending(fieldName)));


    }

    public static ArrayList<Document> getBetweenTwoDate(Date startDate, Date endDate) {
        return FileUtils.decodeData(files.find().filter(Filters.gte(FileFieldName.CREATE_DATE.getValue(), startDate))
                .filter(Filters.lte(FileFieldName.CREATE_DATE.getValue(), endDate)));
    }

}
