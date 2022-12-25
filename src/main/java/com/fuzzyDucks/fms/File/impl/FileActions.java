package com.fuzzyDucks.fms.File.impl;

public enum FileActions {
    DELETE("delete"),
    IMPORT("import"),
    EXPORT("export"),
    OVERWRITE("overwrite");
    private final String value;

    FileActions(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
