package com.fuzzyDucks.fms.File.FileSchema;

import com.fuzzyDucks.fms.File.FileUtils;
import org.bson.Document;

import java.time.LocalDate;

import static com.fuzzyDucks.fms.File.FileSchema.FileSchemaService.getFilePath;

class VersionSchema {
    private int version;
    private String path;
    private Double size;
    private LocalDate ubdDate;


    public VersionSchema(int length, String path, Double size) {
        this.version = length;
        this.path = path;
        this.size = size;
        this.ubdDate=LocalDate.now();
    }

    public Document toDocument() {
        Document document = new Document();
        document.append("version", this.version);
        document.append("path", this.path);
        document.append("size", this.size);
        document.append("ubdDate",this.ubdDate);
        return document;
    }

}