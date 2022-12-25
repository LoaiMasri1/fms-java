package com.fuzzyDucks.fms.File.fileSchema.services;

import com.fuzzyDucks.fms.Database.MongoConnector;
import com.fuzzyDucks.fms.Database.enums.MongoConf;
import com.fuzzyDucks.fms.Database.intf.IMongoDatabase;
import com.fuzzyDucks.fms.Exceptions.AccessibleParameterException;
import com.fuzzyDucks.fms.Exceptions.PermissionException;
import com.fuzzyDucks.fms.File.fileSchema.models.FileSchema;
import com.fuzzyDucks.fms.File.utils.FileUtils;
import com.fuzzyDucks.fms.File.enums.*;
import com.fuzzyDucks.fms.Logger.*;
import com.fuzzyDucks.fms.Logger.intf.ILogger;
import com.fuzzyDucks.fms.User.enums.UserRole;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;


import org.bson.Document;
import org.bson.conversions.Bson;

import static com.fuzzyDucks.fms.File.FileService.permissionsHandler;
import static com.fuzzyDucks.fms.File.FileService.role;

public class FileSchemaService {

    final private static IMongoDatabase database = MongoConnector.getInstance();
    final static MongoCollection<Document> files = database.getDatabase()
            .getCollection(MongoConf.FILES_COLLECTION.getValue());
    final static ILogger logger = LoggingHandler.getInstance();
    final static FileUtils fileUtils = new FileUtils();
    final private static VersionService versionService = new VersionService();

    public void addFile(FileSchema file) throws PermissionException {
        if (files.find(findWithNameAndType(file.getName(), file.getType())).first() != null) {
            if (permissionsHandler.hasPermission(UserRole.fromValue(role), "overwrite")) {
                logger.logInfo("Updating File Version: " + file.getName() + "." + file.getType());
                versionService.addNewVersion(file);
            } else {
                throw new PermissionException("User does not have permission to overwrite file");
            }
        } else {
            files.insertOne(file.toDocument());
            logger.logInfo("Adding file: " + file.getName() + "." + file.getType());
            versionService.updateVersions(0, file);
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
}
