package com.fuzzyDucks.fms.Auth.AuthService.enums;

import com.fuzzyDucks.fms.User.enums.UserFieldName;

public enum AuthConstant {
    USER_ROLE(UserFieldName.ROLE.getValue()),
    USER_TOKEN("token"),
    ;
    final String value;

    AuthConstant(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }

}
