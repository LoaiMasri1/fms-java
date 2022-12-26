package com.fuzzyDucks.fms.File.fileSchema.services;

import com.fuzzyDucks.fms.Database.MongoConnector;
import com.fuzzyDucks.fms.Database.enums.MongoConf;
import com.fuzzyDucks.fms.Database.intf.IMongoDatabase;
import com.fuzzyDucks.fms.Exceptions.AccessibleParameterException;
import com.fuzzyDucks.fms.Exceptions.InvalidDataException;
import com.fuzzyDucks.fms.Exceptions.PermissionException;
import com.fuzzyDucks.fms.File.fileSchema.models.FileSchema;

import com.fuzzyDucks.fms.File.impl.FileActions;
import com.fuzzyDucks.fms.File.impl.FileServiceImpl;
import com.fuzzyDucks.fms.File.intf.FileService;
import com.fuzzyDucks.fms.File.utils.FileUtils;
import com.fuzzyDucks.fms.File.enums.*;
import com.fuzzyDucks.fms.Logger.*;
import com.fuzzyDucks.fms.Logger.intf.ILogger;
import com.fuzzyDucks.fms.User.enums.UserRole;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

import org.bson.Document;
import org.bson.conversions.Bson;

import static com.fuzzyDucks.fms.File.fileSchema.services.VersionService.getVersionCounter;
import static com.fuzzyDucks.fms.File.impl.FileServiceImpl.permissionsHandler;
import static com.fuzzyDucks.fms.File.impl.FileServiceImpl.role;

public class FileSchemaService {

    final private static IMongoDatabase database = MongoConnector.getInstance();
    final static MongoCollection<Document> files = database.getDatabase()
            .getCollection(MongoConf.FILES_COLLECTION.getValue());
    final static ILogger logger = LoggingHandler.getInstance();
    final static FileUtils fileUtils = new FileUtils();
    final private static VersionService versionService = new VersionService();
    final private static int FIRST_VERSION_NUMBER = 0;

    public void addFile(FileSchema file, File selectedFile) throws IOException, ClassNotFoundException {
        if (files.find(findWithNameAndType(file.getName(), file.getType())).first() != null) {
            if (permissionsHandler.hasPermission(UserRole.fromValue(role), FileActions.OVERWRITE.getValue())) {
                updateFileNameIfExist(file, selectedFile);
                logger.logInfo("Updating File Version: " + file.getName() + "." + file.getType());
            } else {
                throw new PermissionException("User does not have permission to overwrite file");
            }
        } else {
            files.insertOne(file.toDocument());
            logger.logInfo("Adding file: " + file.getName() + "." + file.getType());
            versionService.updateVersions(FIRST_VERSION_NUMBER, file);
        }
    }

    public void removeFile(String name, String type) {
        logger.logInfo("Removing file Successfully: " + name + "." + type);
        files.deleteOne(findWithNameAndType(fileUtils.encodeValue(name), fileUtils.encodeValue(type)));
    }

    public String getFilePath(String name, String type) {
        FindIterable<Document> findIterable = files
                .find(findWithNameAndType(fileUtils.encodeValue(name), fileUtils.encodeValue(type)));
        if (findIterable.first() == null) {
            logger.logWarning("file not found: " + name + "." + type);
            throw new IllegalArgumentException("file not found");
        }
        logger.logInfo("Getting file path: " + name + "." + type);
        return fileUtils
                .decodeValue(Objects.requireNonNull(findIterable.first()).getString(FileFieldName.PATH.getValue()));
    }

    static Bson findWithNameAndType(String name, String type) {
        return Filters.and(Filters.eq(FileFieldName.NAME.getValue(), name),
                Filters.eq(FileFieldName.TYPE.getValue(), type));
    }

    public FindIterable<Document> getFiles() {
        FindIterable<Document> tmp = files.find();
        fileUtils.isEmpty(tmp);
        logger.logInfo("Getting all files");
        return tmp;
    }

    public ArrayList<Document> getSizeBy(double size, ClassifySort type) {
        if (type.isLeast()) {
            logger.logInfo("Getting files with size less than: " + size);
            return fileUtils.decodeData(files.find(new Document(FileFieldName.SIZE.getValue(), size))
                    .filter(Filters.lte(FileFieldName.SIZE.getValue(), size)));
        }
        logger.logInfo("Getting files with size more than: " + size);
        return fileUtils.decodeData(files.find(new Document(FileFieldName.SIZE.getValue(), size))
                .filter(Filters.gte(FileFieldName.SIZE.getValue(), size)));

    }

    public ArrayList<Document> getEqualValue(String fieldName, String value) {
        if (fieldName.equals(FileFieldName.PATH.getValue())) {
            logger.logWarning("Can't search by path");
            throw new AccessibleParameterException("you can't access using Path Field");
        }
        if (fieldName.equals(FileFieldName.NAME.getValue()) || fieldName.equals(FileFieldName.TYPE.getValue())) {
            value = fileUtils.encodeValue(value);
            logger.logInfo("Getting files with " + fieldName + " equal to: " + value);
        }
        return fileUtils.decodeData(files.find(new Document(fieldName, value)));
    }

    public ArrayList<Document> getSortedBy(String fieldName, SortType type) {
        if (fieldName.equals(FileFieldName.PATH.getValue())) {
            logger.logWarning("Can't search by path");
            throw new AccessibleParameterException("you can't access using Path Field");
        }
        if (type.isAscending()) {
            logger.logInfo("Getting files sorted by " + fieldName + " in ascending order");
            return fileUtils.decodeData(getFiles().sort(Sorts.ascending(fieldName)));
        }
        logger.logInfo("Getting files sorted by " + fieldName + " in descending order");
        return fileUtils.decodeData(getFiles().sort(Sorts.descending(fieldName)));

    }

    public ArrayList<Document> getBetweenTwoDate(Date startDate, Date endDate) {
        logger.logInfo("Getting files Between Two Date " + startDate + " to " + endDate);
        return fileUtils.decodeData(files.find().filter(Filters.gte(FileFieldName.CREATE_DATE.getValue(), startDate))
                .filter(Filters.lte(FileFieldName.CREATE_DATE.getValue(), endDate)));
    }

    public static void addNewFile(FileSchema file, String newName, File selectedFile) throws IOException, ClassNotFoundException {
        try {
            FileService fileService = new FileServiceImpl();
            file.setName(fileUtils.encodeValue(newName));
            String newPath = file.newPath(newName) + "." + fileUtils.decodeValue(file.getType());
            file.setPath(newPath);
            fileService.importFile(file, selectedFile);
        } catch (Exception e) {
            System.out.println("Error occurred while renaming the file " + e.getMessage());
        }
    }

    public static void updateFileNameIfExist(FileSchema file, File selectedFile) throws IOException, ClassNotFoundException {
        int versionCounter = getVersionCounter(file);
        System.out.println("Do you want to save the new file as  " + fileUtils.decodeValue(file.getName()) + "V." + (versionCounter) + "?(1) or type a new name(2)");
        Scanner myObj = new Scanner(System.in);
        System.out.println("1/2 ?");
        String choice = myObj.nextLine();
        if (Objects.equals(choice, "1")) {
            versionService.addNewVersion(file);
        } else if (Objects.equals(choice, "2")) {
            System.out.println("Type the new name");
            String newName = myObj.nextLine();
            if (!Objects.equals(newName, "")) {
                addNewFile(file, newName, selectedFile);
            } else
                throw new InvalidDataException("the name cant be empty");
        } else throw new IOException("This input is unsupported");
    }
}
