package com.fuzzyDucks.fms.fileRepository;

import com.fuzzyDucks.fms.Database.MongoConnector;
import com.fuzzyDucks.fms.Database.enums.MongoConf;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.apache.commons.io.FileUtils;
import org.bson.Document;
import java.io.File;
import java.io.IOException;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;

public class FileServices {
    private static MongoConnector mongoConnector = MongoConnector.getInstance();
    private static MongoCollection files = mongoConnector.getDatabase().getCollection(MongoConf.FILES_COLLECTION.getValue());

    public static void copyFile(File selectedFile, File newFile) {
        try {
            FileUtils.copyFile(selectedFile, newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void importFile(FileSchema file, File selectedFile,File newFile) {
        if (files.find(new Document("name", file.getName())).first() != null)
            throw new IllegalArgumentException("File already exists");
        files.insertOne(file.toDocument());
        copyFile(selectedFile, newFile);
        System.out.println("Document inserted successfully");
    }

    public static void deleteFile(String name) {
        String path = getFilePath(name);
        String folderPath = path.substring(0,path.lastIndexOf("\\"));
        File file =new File(folderPath);
        try {
            FileUtils.deleteDirectory(file);
            removeFileInfoFromDB(name);
            System.out.println(file.getName() + " deleted");

        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeFileInfoFromDB(String name){
        files.deleteOne(eq("name", FileRepositoryUtils.encodeToBase64(name)));
    }

    public static void exportFile(String name){
        File selectedFile = new File(getFilePath(name));
        String home = System.getProperty("user.home");
        File downloadFile = new File(home + "\\Documents\\File Management System");
        try {
            FileUtils.copyFileToDirectory(selectedFile, downloadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getFilePath(String name) {
        FindIterable<Document> findIterable = files.find(eq("name", FileRepositoryUtils.encodeToBase64(name)))
                .projection(fields(include("path"), excludeId()));
        if (findIterable == null)
            throw new IllegalArgumentException("file not found");
        String path = FileRepositoryUtils.decodeFromBase64(findIterable.first().getString("path"));
        return path;
    }
}

