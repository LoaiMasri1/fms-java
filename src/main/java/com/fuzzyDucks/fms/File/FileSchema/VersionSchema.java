package com.fuzzyDucks.fms.File.FileSchema;

import org.bson.Document;

class VersionSchema {
    private int version;
    private String path;
    private Double size;

    public VersionSchema(int length, String path, Double size) {
        this.version = length;
        this.path = path;
        this.size = size;
    }

    public Document toDocument() {
        Document document = new Document();
        document.append("version", this.version);
        document.append("path", this.path);
        document.append("size", this.size);
        return document;
    }


}