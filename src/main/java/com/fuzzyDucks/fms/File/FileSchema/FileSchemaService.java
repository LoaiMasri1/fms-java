package com.fuzzyDucks.fms.File.FileSchema;

import com.fuzzyDucks.fms.Database.MongoConnector;
import com.fuzzyDucks.fms.Database.enums.MongoConf;
import com.fuzzyDucks.fms.File.FileUtils;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.IOException;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;

public class FileSchemaService {

    final private static MongoCollection<Document> files = MongoConnector.getInstance().getDatabase()
            .getCollection(MongoConf.FILES_COLLECTION.getValue());

    public static void addFile(FileSchema file) {
        if (files.find(findWithNameAndType(file.getName(),file.getType())).first() != null)
            throw new IllegalArgumentException("File already exists");
        files.insertOne(file.toDocument());
    }

    public static void removeFile(String name, String type) {
        files.deleteOne(findWithNameAndType(FileUtils.encodeValue(name),FileUtils.encodeValue(type)));
    }

    public static String getFilePath(String name, String type) {
        FindIterable<Document> findIterable = files.find(findWithNameAndType(FileUtils.encodeValue(name),FileUtils.encodeValue(type)));
        if (findIterable == null)
            throw new IllegalArgumentException("file not found");
        return FileUtils.decodeValue(findIterable.first().getString("path"));
    }

    public static Bson findWithNameAndType(String name, String type){
        return Filters.and(Filters.eq("name", name), Filters.eq("type",type));
    }

}
