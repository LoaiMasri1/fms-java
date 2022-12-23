package com.fuzzyDucks.fms.File.FileSchema;
import com.fuzzyDucks.fms.Database.MongoConnector;
import com.fuzzyDucks.fms.Database.enums.MongoConf;
import com.fuzzyDucks.fms.File.FileUtils;
import com.fuzzyDucks.fms.File.IO.IOService;
import com.fuzzyDucks.fms.File.enums.ClassifySort;
import com.fuzzyDucks.fms.File.enums.FileFieldName;
import com.fuzzyDucks.fms.File.enums.SortType;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.jetbrains.annotations.NotNull;
public class FileSchemaService {
    private static IOService ioService=new IOService();

    final private static MongoCollection<Document> files = MongoConnector.getInstance().getDatabase()
            .getCollection(MongoConf.FILES_COLLECTION.getValue());

    public static String newVersionPath(String oldPath, FileSchema file, int version){
        oldPath=FileUtils.decodeValue(oldPath);
        String newPath = oldPath.substring(0, oldPath.lastIndexOf("."));
        newPath = newPath + "V" + version + "." + FileUtils.decodeValue(file.getType());
        return newPath;
    }


    public void addFile(FileSchema file) throws IllegalArgumentException, IOException {
        if (files.find(findWithNameAndType(file.getName(), file.getType())).first() != null){
            updateFileNameIfExist(file);}
        else {
            files.insertOne(file.toDocument());}
    }

    public void removeFile(String name, String type) {
        files.deleteOne(findWithNameAndType(FileUtils.encodeValue(name), FileUtils.encodeValue(type)));
    }

    public static String getFilePath(String name , String type) {
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

    public static int getVersionCounter(FileSchema file){
        Bson find = Filters.and(Filters.eq("name", file.getName()), Filters.eq("type", file.getType()));
        Document tmp = files.find(find).first();
        int length = tmp.get("versions", ArrayList.class).size();
        return length;
    }


    public static void newVersion(FileSchema file) throws IOException {
        Bson find = Filters.and(Filters.eq("name", file.getName()), Filters.eq("type", file.getType()));
        Document tmp = files.find(find).first();
        if (tmp != null ) {
            int length = tmp.get("versions", ArrayList.class).size();
            String newPath=newVersionPath(file.getPath(),file,length);
            files.updateOne(find, new Document("$push", new Document("versions", new VersionSchema(length, FileUtils.encodeValue(newPath), file.getSize()).toDocument())));
            ioService.copyFile(new File(file.getFile().getPath()), new File(newPath));
        }
    }

    public static void newDefaultFile( @NotNull FileSchema file,String type) throws IOException {
        String directoryPath ="src/main/resources/DefaultFiles";
        int foldersCount = FileUtils.countFoldersInDirectory(directoryPath)+1;
        String newPath = directoryPath+"/default("+foldersCount+")"+"/default("+foldersCount+")"+"."+type;
        File f =new File(newPath);
        file.defaultFile(f);
    }
    public static void updateFileNameIfExist(@NotNull FileSchema file) throws IOException {
        int versionCounter = getVersionCounter(file);
        System.out.println("Do you want to save the new file as  " + FileUtils.decodeValue(file.getName()) + "V." + (versionCounter + 1) + "?" + "y/n");
        Scanner myObj = new Scanner(System.in);
        System.out.println("Y/N ?");
        String choise = myObj.nextLine();
        if (Objects.equals(choise, "y") || Objects.equals(choise, "Y")) {
            newVersion(file);
        } else if (Objects.equals(choise, "n") || Objects.equals(choise, "N")) {
            newDefaultFile(file, FileUtils.decodeValue(file.getType()));}
        else throw new UnsupportedEncodingException("This input is unsupported");

    }
}
