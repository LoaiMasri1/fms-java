package com.fuzzyDucks.fms.User;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class UserUtils {

    private static final int SALT = 12;

    public static String hashPassword(String password) {
        return BCrypt.withDefaults().hashToString(SALT, password.toCharArray());
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);
        return result.verified;
    }

}
