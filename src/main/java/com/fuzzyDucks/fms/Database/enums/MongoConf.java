package com.fuzzyDucks.fms.Database.enums;

public enum MongoConf {
    URI("mongodb+srv://root:Store1234@store.9wmdkug.mongodb.net/?retryWrites=true&w=majority"),
    DB("fms"),
    USERS_COLLECTION("users"),
    FILES_COLLECTION("files");

    private String value;

    private MongoConf(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
