package com.fuzzyDucks.fms.Auth.AuthService.enums;

public enum JWTConfig {
    SECRET("secret"),
    ISSUER("auth0"),
    SUBJECT("1234567890"),
    EXPIRATION_TIME(1000 * 60 * 60 * 24 * 7);

    private final String value;

    JWTConfig(String value) {
        this.value = value;
    }

    JWTConfig(int value) {
        this.value = Integer.toString(value);
    }

    public String getValue() {
        return value;
    }

}
