package com.fuzzyDucks.fms.File.FileSchema;
import com.fuzzyDucks.fms.File.enums.FileFieldName;
import org.bson.Document;

class VersionSchema {
    private final int version;
    private final String path;
    private final Double size;
    public VersionSchema(int length, String path, Double size) {
        this.version = length;
        this.path = path;
        this.size = size;
    }

    public Document toDocument() {
        Document document = new Document();
        document.append(FileFieldName.VERSIONS.getValue(), this.version);
        document.append(FileFieldName.PATH.getValue(), this.path);
        document.append(FileFieldName.SIZE.getValue(), this.size);
        return document;
    }


}