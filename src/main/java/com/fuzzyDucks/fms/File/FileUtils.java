package com.fuzzyDucks.fms.File;

import java.util.ArrayList;
import java.util.Base64;

import com.fuzzyDucks.fms.File.enums.FileFieldName;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Projections;
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

    public static ArrayList<Document> decodeData(FindIterable<Document> docs) {
        ArrayList<Document> decodedDocs = new ArrayList<Document>();
        for (Document doc : docs) {
            decodedDocs
                    .add(new Document(FileFieldName.ID.getValue(), doc.getObjectId(FileFieldName.ID.getValue()))
                            .append(FileFieldName.NAME.getValue(), decodeValue(doc.getString(FileFieldName.NAME.getValue())))
                            .append(FileFieldName.PATH.getValue(), decodeValue(doc.getString(FileFieldName.PATH.getValue())))
                            .append(FileFieldName.TYPE.getValue(), decodeValue(doc.getString(FileFieldName.TYPE.getValue())))
                            .append(FileFieldName.SIZE.getValue(), doc.getDouble(FileFieldName.SIZE.getValue())));
        }
        return decodedDocs;

    }
}
