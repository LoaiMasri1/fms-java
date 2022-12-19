package com.fuzzyDucks.fms.File.enums;

public enum FileFieldName {
    ID("_id"),
    NAME("name"),
    TYPE("type"),
    SIZE("size"),
    PATH("path"),
    CREATE_DATE("crtDate"),
    UPDATE_DATE("updDate");
    final String value;
    FileFieldName(String value) {
        this.value=value;
    }
    public  String getValue(){
        return value;
    }
}
