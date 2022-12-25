package com.fuzzyDucks.fms.File.fileSchema.models;
import com.fuzzyDucks.fms.File.enums.FileFieldName;
import com.fuzzyDucks.fms.File.File;
import org.bson.Document;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import com.fuzzyDucks.fms.File.FileService;
import com.fuzzyDucks.fms.File.utils.FileUtils;
import com.fuzzyDucks.fms.File.enums.PathInfo;

public class FileSchema {
    private static final int BYTE_TO_BITS = 8;
    private static final int BITS_TO_KILOBITS = 1024;

    final static private FileUtils fileUtils = new FileUtils();
    final private File file;
    private final double size;
    private final ArrayList<VersionSchema> versions;
    private final Date crtDate;
    private final Date updDate;

    public FileSchema(java.io.File file) throws IOException, ClassNotFoundException {
        String fileName = file.getName();
        this.file = new File(encodeName(fileName), fileUtils.encodeValue(newPath(fileName)), encodeType(fileName));
        this.size = (double) (file.length() * BYTE_TO_BITS) / BITS_TO_KILOBITS; // In kilobits
        this.crtDate = new Date();
        this.updDate = new Date();
        this.versions = new ArrayList<>();
        FileService.importFile(this, file);
    }

    public Document toDocument() {
        Document document = new Document();
        document.append(FileFieldName.NAME.getValue(), this.file.getName());
        document.append(FileFieldName.PATH.getValue(), this.file.getPath());
        document.append(FileFieldName.TYPE.getValue(), this.file.getType());
        document.append(FileFieldName.SIZE.getValue(), this.size);
        document.append(FileFieldName.VERSIONS.getValue(), this.versions);
        document.append(FileFieldName.CREATE_DATE.getValue(), this.crtDate);
        document.append(FileFieldName.UPDATE_DATE.getValue(), this.updDate);
        return document;
    }

    private String encodeName(String name) {
        return fileUtils.encodeValue(
                name.substring(0, name.lastIndexOf(".")));
    }

    private String encodeType(String type) {
        return fileUtils.encodeValue(
                type.substring(type.lastIndexOf('.') + 1));
    }

    private String newPath(String fileName) {
        return new StringBuilder()
                .append(PathInfo.LOCAL_PATH.getPath())
                .append(fileName)
                .append(PathInfo.PATH_SEPARATOR.getPath())
                .append(fileName)
                .toString();
    }

    public void setPath(String path) {
        this.file.setPath(fileUtils.encodeValue(path));
    }

    public String getName() {
        return file.getName();
    }

    public String getType() {
        return file.getType();
    }

    public double getSize() {
        return size;
    }

    public String getPath() {
        return file.getPath();
    }
}
