package com.fuzzyDucks.fms.User.enums;

public enum UserRole {
    ADMIN(0), STAFF(1), READER(2);

    private final int value;

    UserRole(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
