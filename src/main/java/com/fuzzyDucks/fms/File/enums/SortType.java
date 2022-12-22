package com.fuzzyDucks.fms.File.enums;

public enum SortType {
    ASCENDING,
    DESCENDING;

    public boolean isAscending() {
        return this == ASCENDING;
    }
    public boolean isDescending() {
        return this == DESCENDING;
    }
}
