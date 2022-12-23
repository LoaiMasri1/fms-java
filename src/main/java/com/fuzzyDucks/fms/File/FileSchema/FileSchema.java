package com.fuzzyDucks.fms.File.FileSchema;

import com.fuzzyDucks.fms.File.enums.FileFieldName;
import org.bson.Document;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import com.fuzzyDucks.fms.File.FileService;
import com.fuzzyDucks.fms.File.FileUtils;
import com.fuzzyDucks.fms.File.enums.PathInfo;

public class FileSchema {
    private static final int BYTE_TO_BITS = 8;
    private static final int BITS_TO_KILOBITS = 1024;
    private String name;
    private String path;
    private String type;
    private double size;
    private ArrayList<VersionSchema> versions;
    private Date crtDate;
    private Date updDate;

    public FileSchema(File file) throws IOException {
        String fileName = file.getName();
        this.name = encodeName(fileName);
        this.path = FileUtils.encodeValue(newPath(fileName));
        this.type = encodeType(fileName);
        this.size = (double) (file.length() * BYTE_TO_BITS) / BITS_TO_KILOBITS; // In kilobits
        this.crtDate = new Date();
        this.updDate = new Date();
        this.versions = new ArrayList<>();
        FileService.importFile(this, file);
    }

    public Document toDocument() {
        Document document = new Document();
        document.append(FileFieldName.NAME.getValue(), this.name);
        document.append(FileFieldName.PATH.getValue(), this.path);
        document.append(FileFieldName.TYPE.getValue(), this.type);
        document.append(FileFieldName.SIZE.getValue(), this.size);
        document.append("versions", this.versions);
        document.append(FileFieldName.CREATE_DATE.getValue(), this.crtDate);
        document.append(FileFieldName.UPDATE_DATE.getValue(), this.updDate);
        return document;
    }

    private String encodeName(String name) {
        return FileUtils.encodeValue(
                name.substring(0, name.lastIndexOf(".")));
    }

    private String encodeType(String type) {
        return FileUtils.encodeValue(
                type.substring(type.lastIndexOf('.') + 1));
    }

    private String newPath(String fileName) {
        return new StringBuilder()
                .append(PathInfo.LOCAL_PATH.getPath())
                .append(fileName)
                .append(PathInfo.PATH_SEPARATOR.getPath())
                .append(fileName)
                .toString();
    }

    public void setPath(String path) {
        this.path = FileUtils.encodeValue(path);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getSize() {
        return size;
    }

    public String getPath() {
        return path;
    }
}
