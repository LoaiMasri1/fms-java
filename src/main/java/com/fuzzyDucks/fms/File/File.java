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

    public File(String path) {
        java.io.File file = new java.io.File(path);
        this.name = file.getName();
        this.type = path.substring(0, name.lastIndexOf("."));
        this.path = file.getPath();
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

    public void setName(String name) {
        this.name = name;
    }

}
