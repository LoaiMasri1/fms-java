package com.fuzzyDucks.fms.File.enums;

public enum ClassifySort {
    GREATEST,
    LEAST;
    public boolean isGreatest() {
        return this == GREATEST;
    }
    public boolean isLeast() {
        return this == LEAST;
    }
}
