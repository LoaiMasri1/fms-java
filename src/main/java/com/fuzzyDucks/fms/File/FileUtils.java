package com.fuzzyDucks.fms.File;

import java.util.Base64;

public class FileUtils {
    public static String encodeValue(String message) {
        return Base64.getEncoder().encodeToString(message.getBytes());
    }

    public static String decodeValue(String encodedMessage) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedMessage);
        String decodedString = new String(decodedBytes);
        return decodedString;
    }
}
