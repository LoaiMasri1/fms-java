package com.fuzzyDucks.fms.File.FileSchema;

import com.fuzzyDucks.fms.Database.MongoConnector;
import com.fuzzyDucks.fms.Database.enums.MongoConf;
import com.fuzzyDucks.fms.File.FileUtils;
import com.fuzzyDucks.fms.File.enums.ClassifySort;
import com.fuzzyDucks.fms.File.enums.FileFieldName;
import com.fuzzyDucks.fms.File.enums.SortType;
import com.fuzzyDucks.fms.Logger.ILogger;
import com.fuzzyDucks.fms.Logger.LoggingHandler;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

public class FileSchemaService {

    final private static MongoCollection<Document> files = MongoConnector.getInstance().getDatabase()
            .getCollection(MongoConf.FILES_COLLECTION.getValue());
    final private static ILogger logger = LoggingHandler.getInstance();
    public void addFile(FileSchema file) {
        if (files.find(findWithNameAndType(file.getName(),file.getType())).first() != null) {
            addNewVersion(file);
            logger.logInfo("Adding file: " + file.getName() + "." + file.getType());
        } else {
            files.insertOne(file.toDocument());
            updateVersions(0,file);
            logger.logInfo("Updating file: " + file.getName() + "." + file.getType());
        }
    }

    public void addNewVersion(FileSchema newVersion) {
        Bson find = findWithNameAndType(newVersion.getName(),newVersion.getType());
        Document tmp = files.find(find).first();
        int length = tmp.get(FileFieldName.VERSIONS.getValue(), ArrayList.class).size();
        String versionPath = newVersionPath(newVersion,length);
        newVersion.setPath(versionPath);
        updateVersions(length,newVersion);
        update(newVersion);
        logger.logInfo("Adding New Version: " + newVersion.getName() + "." + newVersion.getType());
    }

    public void updateVersions(int length,FileSchema version){
        files.updateOne(findWithNameAndType(version.getName(),version.getType()), new Document("$push",
                new Document(FileFieldName.VERSIONS.getValue(), new VersionSchema(length, version.getPath(), version.getSize()).toDocument())));
        logger.logInfo("Updating New Version: " + version.getName() + "." + version.getType());
    }

    public void update(FileSchema file){
        files.updateOne(findWithNameAndType(file.getName(),file.getType()),
                Updates.combine(Updates.set(FileFieldName.PATH.getValue(), file.getPath()),
                Updates.set(FileFieldName.SIZE.getValue(), file.getSize()),
                Updates.set(FileFieldName.UPDATE_DATE.getValue(), new Date())));
        logger.logInfo("Updating FILE: " + file.getName() + "." + file.getType());
    }

    public String newVersionPath(FileSchema newVersion, int length){
        String type = FileUtils.decodeValue(newVersion.getType());
        String path = FileUtils.decodeValue(newVersion.getPath());
        return path.substring(0, path.lastIndexOf("."))
                + "(" + length + ")." + type;
    }

    public void removeFile(String name, String type) {
        logger.logInfo("Removing file: " + name + "." + type);
        files.deleteOne(findWithNameAndType(FileUtils.encodeValue(name), FileUtils.encodeValue(type)));
    }

    public String getFilePath(String name, String type) {
        FindIterable<Document> findIterable = files
                .find(findWithNameAndType(FileUtils.encodeValue(name), FileUtils.encodeValue(type)));
        if (findIterable.first() == null) {
            logger.logInfo("file not found: " + name + "." + type);
            throw new IllegalArgumentException("file not found");
        }
        logger.logInfo("Getting file path: " + name + "." + type);
        return FileUtils.decodeValue(Objects.requireNonNull(findIterable.first()).getString(FileFieldName.PATH.getValue()));
    }

    public Bson findWithNameAndType(String name, String type) {
        return Filters.and(Filters.eq(FileFieldName.NAME.getValue(), name),
                Filters.eq(FileFieldName.TYPE.getValue(), type));
    }

    public static FindIterable<Document> getFiles() {
        FindIterable<Document> tmp = files.find();
        FileUtils.isEmpty(tmp);
        logger.logInfo("Getting all files");
        return tmp;
    }

    public static ArrayList<Document> getSizeBy(double size, ClassifySort type) {
        if (type.isLeast()) {
            logger.logInfo("Getting files with size less than: " + size);
            return FileUtils.decodeData(files.find(new Document(FileFieldName.SIZE.getValue(), size))
                    .filter(Filters.lte(FileFieldName.SIZE.getValue(), size)));
        }
        logger.logInfo("Getting files with size more than: " + size);
        return FileUtils.decodeData(files.find(new Document(FileFieldName.SIZE.getValue(), size))
                .filter(Filters.gte(FileFieldName.SIZE.getValue(), size)));

    }

    public static ArrayList<Document> getEqualValue(String fieldName, String value) {
        if (fieldName.equals(FileFieldName.PATH.getValue())) {
            logger.logWarning("Can't search by path");
            throw new IllegalArgumentException("you can't access using Path Field");
        }
        if (fieldName.equals(FileFieldName.NAME.getValue()) || fieldName.equals(FileFieldName.TYPE.getValue())){
            value = FileUtils.encodeValue(value);
        logger.logInfo("Getting files with " + fieldName + " equal to: " + value);
    }
        return FileUtils.decodeData(files.find(new Document(fieldName, value)));
    }

    public static ArrayList<Document> getSortedBy(String fieldName, SortType type) {
        if (fieldName.equals(FileFieldName.PATH.getValue())){
            logger.logWarning("Can't search by path");
            throw new IllegalArgumentException("you can't access using Path Field");
        }
        if (type.isAscending()){
            logger.logInfo("Getting files sorted by " + fieldName + " in ascending order");
            return FileUtils.decodeData(getFiles().sort(Sorts.ascending(fieldName)));}
        logger.logInfo("Getting files sorted by " + fieldName + " in descending order");
        return FileUtils.decodeData(getFiles().sort(Sorts.descending(fieldName)));

    }

    public static ArrayList<Document> getBetweenTwoDate(Date startDate, Date endDate) {
        logger.logInfo("Getting files Between Two Date " + startDate + " to "+ endDate);
        return FileUtils.decodeData(files.find().filter(Filters.gte(FileFieldName.CREATE_DATE.getValue(), startDate))
                .filter(Filters.lte(FileFieldName.CREATE_DATE.getValue(), endDate)));
    }

}
