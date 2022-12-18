package com.fuzzyDucks.fms.File.FileSchema;

import com.fuzzyDucks.fms.Database.MongoConnector;
import com.fuzzyDucks.fms.Database.enums.MongoConf;
import com.fuzzyDucks.fms.File.FileUtils;
import com.fuzzyDucks.fms.File.enums.SortType;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

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
        return FileUtils.decodeValue(findIterable.first().getString("path"));
    }

    public Bson findWithNameAndType(String name, String type) {
        return Filters.and(Filters.eq("name", name), Filters.eq("type", type));
    }

    public static FindIterable<Document> getFiles() {
        FindIterable<Document> tmp = files.find();
        FileUtils.isEmpty(tmp);
        return tmp;
    }

    public static FindIterable<Document> getBySizeGte(double size) {
        return files.find(new Document("size", size)).filter(Filters.gte("size", size));
    }

    public static FindIterable<Document> getBySizeLte(double size) {
        return files.find(new Document("size", size)).filter(Filters.lte("size", size));
    }

    public static FindIterable<Document> getEqualValue(String nameField, String value) {
        return files.find(new Document(nameField, value));

    }

    public static FindIterable<Document> getSortedBy(String nameField, SortType type) {
        if (type.isAscending()) {
            return getFiles().sort(Sorts.ascending(nameField));
        }
        return getFiles().sort(Sorts.descending(nameField));
    }

    public static FindIterable<Document> getBetweenTwoDate(Date startDate, Date endDate) {
        return files.find().filter(Filters.gte("crtDate", startDate))
                .filter(Filters.lte("crtDate", endDate));
    }

}
