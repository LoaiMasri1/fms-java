package com.fuzzyDucks.fms.File;

import java.util.Base64;

import org.bson.Document;

import com.mongodb.client.FindIterable;

public class FileUtils {
    public static String encodeValue(String message) {
        return Base64.getEncoder().encodeToString(message.getBytes());
    }

    public static String decodeValue(String encodedMessage) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedMessage);
        String decodedString = new String(decodedBytes);
        return decodedString;
    }

    public static boolean isEmpty(FindIterable<Document> docs) {
        if (docs.first() == null) {
            throw new IllegalArgumentException("No files");
        }
        return true;
    }
    
      public static int countFoldersInDirectory(String directoryPath) {
        int count = 0;
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isDirectory()) {
                count++;
            }
        }
        return count;
    }
}
