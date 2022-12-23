package com.fuzzyDucks.fms.File.enums;

public enum PathInfo {
    LOCAL_PATH("src\\main\\resources\\"),
    PATH_SEPARATOR("\\"),
    HOME_PATH(System.getProperty("user.home")),
    DOWNLOAD_PATH("\\Documents\\File Management System"),
    FULL_DOWNLOAD_PATH(HOME_PATH.path + DOWNLOAD_PATH.path);

    private final String path;

    PathInfo(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
