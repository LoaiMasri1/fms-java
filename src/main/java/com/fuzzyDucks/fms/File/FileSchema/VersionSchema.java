package com.fuzzyDucks.fms.File.FileSchema;

import com.fuzzyDucks.fms.File.enums.FileFieldName;
import com.fuzzyDucks.fms.Logger.ILogger;
import com.fuzzyDucks.fms.Logger.LoggingHandler;
import org.bson.Document;

class VersionSchema {
    private int version;
    private String path;
    private Double size;
    private static final ILogger logger= LoggingHandler.getInstance();
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
        logger.logInfo("Convert new file Version  to document successfully "+getClass().getName());
        return document;
    }


}