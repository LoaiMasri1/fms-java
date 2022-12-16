package com.fuzzyDucks.fms.fileRepository;

import org.bson.Document;
import java.io.File;
import java.time.LocalDateTime;

public class FileSchema {
    private String name;
    private String path;
    private String type;
    private double size;
    private LocalDateTime crtDate;
    private LocalDateTime updDate;

    public FileSchema(File file){
        String fileName = file.getName();
        this.name = FileRepositoryUtils.encodeToBase64(fileName.substring(0,fileName.lastIndexOf(".")));
        File newFile = new File("src\\main\\resources\\"+ FileRepositoryUtils.decodeFromBase64(name) + "\\" + fileName);
        this.path = FileRepositoryUtils.encodeToBase64(newFile.getPath());
        this.type = fileName.substring(fileName.lastIndexOf('.') + 1);
        this.size = (double) (file.length() * 8) / 1024; // In kilobits
        this.crtDate = LocalDateTime.now();
        this.updDate = LocalDateTime.now();
        FileServices.importFile(this,file, newFile);
    }

    public Document toDocument() {
        Document document = new Document();
        document.append("name", this.name);
        document.append("path", this.path);
        document.append("type", this.type);
        document.append("size", this.size);
        document.append("crtDate", this.crtDate);
        document.append("updDate", this.updDate);
        return document;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public String getType() {
        return type;
    }

    public double getSize() {
        return size;
    }

}
