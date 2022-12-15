package com.fuzzyDucks.fms.classification.enums;

public enum SortType {
    ASCENDING,
    DESCENDING;
    public boolean isAscending() {
        return this == ASCENDING;
    }
}
