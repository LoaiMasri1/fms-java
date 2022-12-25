package com.fuzzyDucks.fms.File.utils;

import java.util.ArrayList;
import java.util.Base64;

import com.fuzzyDucks.fms.Exceptions.FileNotFoundException;
import com.fuzzyDucks.fms.File.enums.FileFieldName;
import com.fuzzyDucks.fms.Logger.intf.ILogger;
import com.fuzzyDucks.fms.Logger.LoggingHandler;
import org.bson.Document;
import com.mongodb.client.FindIterable;

public class FileUtils {
    private static final ILogger logger = LoggingHandler.getInstance();

    public String encodeValue(String message) {
        return Base64.getEncoder().encodeToString(message.getBytes());
    }

    public String decodeValue(String encodedMessage) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedMessage);
        return new String(decodedBytes);
    }

    public void isEmpty(FindIterable<Document> docs) {
        if (docs.first() == null) {
            logger.logInfo("No files found");
            throw new FileNotFoundException("No files found");
        }
    }

    public ArrayList<Document> decodeData(FindIterable<Document> docs) {
        ArrayList<Document> decodedDocs = new ArrayList<>();
        for (Document doc : docs) {
            decodedDocs
                    .add(new Document(FileFieldName.ID.getValue(), doc.getObjectId(FileFieldName.ID.getValue()))
                            .append(FileFieldName.NAME.getValue(), decodeValue(doc.getString(FileFieldName.NAME.getValue())))
                            .append(FileFieldName.PATH.getValue(), decodeValue(doc.getString(FileFieldName.PATH.getValue())))
                            .append(FileFieldName.TYPE.getValue(), decodeValue(doc.getString(FileFieldName.TYPE.getValue())))
                            .append(FileFieldName.SIZE.getValue(), doc.getDouble(FileFieldName.SIZE.getValue())));
        }
        logger.logInfo("decode Data " + FileUtils.class.getName());
        return decodedDocs;
    }

    public void checkVersion(ArrayList<Document> docs, int version) {
        if (version > docs.size() || version < 0) {
            logger.logInfo("version not found: " + version);
            throw new IllegalArgumentException("version not found");
        }
    }

}
