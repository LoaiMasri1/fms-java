package com.fuzzyDucks.fms.fileRepository;

import com.fuzzyDucks.fms.Database.MongoConnector;
import com.fuzzyDucks.fms.Database.enums.MongoConf;
import com.fuzzyDucks.fms.versionControl.appendOperation;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.Objects;
import java.util.Scanner;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.time.LocalDateTime;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;

public class FileServices  {
    private static final MongoConnector mongoConnector = MongoConnector.getInstance();
    private static final MongoCollection<Document> files = mongoConnector.getDatabase().getCollection(MongoConf.FILES_COLLECTION.getValue());

    public static void importFile(File selectedFile, File newFile) {
        FileUtils.copyFile(selectedFile, newFile);
    }

    public static void addFileInfoToDB(@NotNull FileSchema file){
        if (files.find(new Document("name", file.getName())).first() != null)
            throw new IllegalArgumentException("File already exists");
        files.insertOne(file.toDocument());
        System.out.println("Document inserted successfully");
    }

    public static void deleteFile(String name) {
        String path = getFilePath(name);
        File file =new File(path);
        try {
            if(file.delete()) {
                removeFileInfoFromDB(name);
                System.out.println(file.getName() + " deleted");
            }
            else {
                System.out.println("failed");
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateFileNameIfExist(@NotNull FileSchema file, String name, String newName) {

        int versionCounter = (files.find(new Document("name", name)).first().getInteger("version"))+1;
        if (files.find(new Document("name", name)).first() != null){
            System.out.println("Do you want to save the new file as"+ name+"."+versionCounter+1+"?"+"y/n");
        
            Scanner myObj = new Scanner(System.in);
            System.out.println("Y/N ?");
            String choise = myObj.nextLine();
            if (Objects.equals(choise, "y") || Objects.equals(choise, "Y")) {
                String oldPath = getFilePath(name);
                String newPath = oldPath.split("[.]")[0];
                newPath = newPath + "V" + versionCounter + "." + file.getType();
                files.findOneAndUpdate(eq("name", name), file.UpdateDocument(versionCounter, newPath, LocalDateTime.now()));

        }   else if (Objects.equals(choise, "n") || Objects.equals(choise, "N")) {
            String oldPath = getFilePath(name);
            String newPath = oldPath.split(name)[0];
            newPath = newPath + "Default"  + "." + file.getType();
            versionCounter=0;
            files.findOneAndUpdate(eq("name", name), file.UpdateDocument(versionCounter, newPath, LocalDateTime.now()));}
        } else {
            throw new IllegalArgumentException("File is not exist");
        }

    }
    public static void removeFileInfoFromDB(String name){
        files.deleteOne(eq("name", name));
    }

    public static void exportFile(String name,String downloadPath){
        File selectedFile = new File(getFilePath(name));
        File downloadFile = new File(downloadPath+"\\"+selectedFile.getName());
        FileUtils.copyFile(selectedFile, downloadFile);
    }

    public static String getFilePath(String name) {
        FindIterable<Document> findIterable = files.find(eq("name", name))
                .projection(fields(include("path"), excludeId()));
        if (findIterable == null)
            throw new IllegalArgumentException("file not found");
        String path = findIterable.first().getString("path");
        return path;
    }


}
