package com.fuzzyDucks.fms.File;

public class File {
    String name;
    String type;
    String path;

    public File(String name, String type, String path) {
        this.name = name;
        this.type = type;
        this.path = path == null ? "" : path;
    }

    public File(String name, String type) {
        this(name, type, null);
    }

    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
