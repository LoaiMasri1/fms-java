package com.fuzzyDucks.fms.File.fileSchema.services;

import com.fuzzyDucks.fms.File.enums.FileFieldName;
import com.fuzzyDucks.fms.File.fileSchema.models.FileSchema;
import com.fuzzyDucks.fms.File.fileSchema.models.VersionSchema;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.fuzzyDucks.fms.File.fileSchema.services.FileSchemaService.fileUtils;
import static com.fuzzyDucks.fms.File.fileSchema.services.FileSchemaService.findWithNameAndType;
import static com.fuzzyDucks.fms.File.fileSchema.services.FileSchemaService.logger;
import static com.fuzzyDucks.fms.File.fileSchema.services.FileSchemaService.files;

import java.util.ArrayList;
import java.util.Date;


public class VersionService {
    public void addNewVersion(FileSchema newVersion) {
        int length =getVersionCounter(newVersion);
        String versionPath = newVersionPath(newVersion, length);
        newVersion.setPath(versionPath);
        updateVersions(length, newVersion);
        updateFile(newVersion);
        logger.logInfo("Added New Version successfully for: " + newVersion.getName() + "." + newVersion.getType());
    }

    public void updateVersions(int length, FileSchema version) {
        files.updateOne(findWithNameAndType(version.getName(), version.getType()), new Document("$push",
                new Document(FileFieldName.VERSIONS.getValue(),
                        new VersionSchema(length, version.getPath(), version.getSize()).toDocument())));
    }

    private void updateFile(String name, String type, String path, double size) {
        files.updateOne(findWithNameAndType(name, type),
                Updates.combine(Updates.set(FileFieldName.PATH.getValue(), path),
                        Updates.set(FileFieldName.SIZE.getValue(), size),
                        Updates.set(FileFieldName.UPDATE_DATE.getValue(), new Date())));
    }

    private void updateFile(FileSchema file) {
        updateFile(file.getName(), file.getType(), file.getPath(), file.getSize());
    }

    private String newVersionPath(FileSchema newVersion, int length) {
        String type = fileUtils.decodeValue(newVersion.getType());
        String path = fileUtils.decodeValue(newVersion.getPath());
        return new StringBuilder().append(path.substring(0, path.lastIndexOf(".")))
                .append("(").append(length).append(").").append(type).toString();
    }


    public void rollback(String name, String type, int version) {
        FindIterable<Document> findIterable = files
                .find(findWithNameAndType(fileUtils.encodeValue(name), fileUtils.encodeValue(type)));
        fileUtils.isEmpty(findIterable);
        Document tmp = findIterable.first();
        assert tmp != null;
        ArrayList<Document> versions = tmp.get(FileFieldName.VERSIONS.getValue(), ArrayList.class);
        fileUtils.checkVersion(versions, version);
        Document versionDoc = versions.get(version);
        updateFile(name, type, versionDoc.getString(FileFieldName.PATH.getValue()), versionDoc.getDouble(FileFieldName.SIZE.getValue()));
        logger.logInfo("Rollback to version: " + version + " of file: " + name + "." + type);
    }
    public static int getVersionCounter(FileSchema file) throws NullPointerException {
        Bson find = Filters.and(Filters.eq("name", file.getName()), Filters.eq("type", file.getType()));
        if (find.toString() != null) {
            Document tmp = files.find(find).first();
            assert tmp != null;
            return tmp.get("versions", ArrayList.class).size();
        } else
            throw new NullPointerException("Could not find a file with name :" + fileUtils.decodeValue(file.getName()) + "and type :" + fileUtils.decodeValue(file.getType()));
    }
}
