package com.fuzzyDucks.fms.User.enums;

public enum UserFieldName {
    USER_NAME("username"),
    PASSWORD("password"),
    EMAIL("email"),
    NAME("name"),
    ROLE("role"),
    CREATE_DATE("crtDate");
    final String value;

    UserFieldName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
