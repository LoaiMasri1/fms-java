package com.fuzzyDucks.fms.File.fileSchema.services;

import com.fuzzyDucks.fms.File.fileSchema.models.FileSchema;
import com.fuzzyDucks.fms.File.fileSchema.models.VersionSchema;
import com.fuzzyDucks.fms.File.enums.FileFieldName;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Date;


public class VersionService {
    public  synchronized void addNewVersion(FileSchema newVersion) {
        Bson find = FileSchemaService.findWithNameAndType(newVersion.getName(), newVersion.getType());
        Document tmp = FileSchemaService.files.find(find).first();
        assert tmp != null;
        int length = tmp.get(FileFieldName.VERSIONS.getValue(), ArrayList.class).size();
        String versionPath = newVersionPath(newVersion, length);
        newVersion.setPath(versionPath);
        updateVersions(length, newVersion);
        updateFile(newVersion);
        FileSchemaService.logger.logInfo("Added New Version successfully for: " + newVersion.getName() + "." + newVersion.getType());
    }

    public synchronized void updateVersions(int length, FileSchema version) {
        FileSchemaService.files.updateOne(FileSchemaService.findWithNameAndType(version.getName(), version.getType()), new Document("$push",
                new Document(FileFieldName.VERSIONS.getValue(),
                        new VersionSchema(length, version.getPath(), version.getSize()).toDocument())));
    }

    private synchronized void updateFile(String name, String type, String path, double size) {
        FileSchemaService.files.updateOne(FileSchemaService.findWithNameAndType(name, type),
                Updates.combine(Updates.set(FileFieldName.PATH.getValue(), path),
                        Updates.set(FileFieldName.SIZE.getValue(), size),
                        Updates.set(FileFieldName.UPDATE_DATE.getValue(), new Date())));
    }

    private synchronized void updateFile(FileSchema file) {
        updateFile(file.getName(), file.getType(), file.getPath(), file.getSize());
    }

    private String newVersionPath(FileSchema newVersion, int length) {
        String type = FileSchemaService.fileUtils.decodeValue(newVersion.getType());
        String path = FileSchemaService.fileUtils.decodeValue(newVersion.getPath());
        return new StringBuilder().append(path.substring(0, path.lastIndexOf(".")))
                .append("(").append(length).append(").").append(type).toString();
    }


    public synchronized void  rollback(String name, String type, int version) {
        FindIterable<Document> findIterable = FileSchemaService.files
                .find(FileSchemaService.findWithNameAndType(FileSchemaService.fileUtils.encodeValue(name), FileSchemaService.fileUtils.encodeValue(type)));
        FileSchemaService.fileUtils.isEmpty(findIterable);
        Document tmp = findIterable.first();
        assert tmp != null;
        ArrayList<Document> versions = tmp.get(FileFieldName.VERSIONS.getValue(), ArrayList.class);
        FileSchemaService.fileUtils.checkVersion(versions, version);
        Document versionDoc = versions.get(version);
        updateFile(name, type, versionDoc.getString(FileFieldName.PATH.getValue()), versionDoc.getDouble(FileFieldName.SIZE.getValue()));
        FileSchemaService.logger.logInfo("Rollback to version: " + version + " of file: " + name + "." + type);
    }
}
