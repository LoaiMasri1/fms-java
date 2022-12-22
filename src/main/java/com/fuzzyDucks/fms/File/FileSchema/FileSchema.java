package com.fuzzyDucks.fms.File.FileSchema;
import org.bson.Document;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import com.fuzzyDucks.fms.File.FileService;
import com.fuzzyDucks.fms.File.FileUtils;
import com.fuzzyDucks.fms.File.enums.PathInfo;

public class FileSchema {
    private static final int BYTE_TO_BITS = 8;
    private static final int BITS_TO_KILOBITS = 1024;
    private File file;
    private String name;
    private String path;
    private String type;
    private double size;
    private Date crtDate;
    private Date updDate;
    private int version;
    private ArrayList<VersionSchema> versions;

    public FileSchema(File file) throws IOException {
        this.file = file;
        String fileName = file.getName();
        this.name = encodeName(fileName);
        File newFile = new File(newPath(fileName));
        this.path = FileUtils.encodeValue(newFile.getPath());
        this.type = encodeType(fileName);
        this.versions = new ArrayList<>();
        this.size = (double) (file.length() * BYTE_TO_BITS) / BITS_TO_KILOBITS; // In kilobits
        this.crtDate = new Date();
        this.updDate = new Date();
        this.version = 0;
        FileService.importFile(this, file, newFile);
    }

    public Document toDocument() {
        Document document = new Document();
        document.append("name", this.name);
        document.append("path", this.path);
        document.append("type", this.type);
        document.append("size", this.size);
        document.append("versions", this.versions);
        document.append("version", this.version);
        document.append("crtDate", this.crtDate);
        document.append("updDate", this.updDate);
        return document;
    }

    public void defaultFile(File f) throws IOException {
        String fileName = f.getName();
        this.name = encodeName(fileName);
        this.path = FileUtils.encodeValue(f.getPath());
        FileService.importFile(this, file, f);
    }

    public Document updateDocument(int newVersion, String newPath) {
        Document document = new Document();
        document.append("version", newVersion);
        document.append("updDate", LocalDate.now());
        document.append("path", FileUtils.encodeValue(newPath));


        return document;
    }

    private String encodeName(String name) {
        return FileUtils.encodeValue(
                name.substring(0, name.lastIndexOf(".")));
    }

    private String encodeType(String type) {
        return FileUtils.encodeValue(
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

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getPath() {
        return path;
    }

    public File getFile() {
        return file;
    }

    public double getSize() {
        return size;
    }

    public Date getCrtDate() {
        return crtDate;
    }

    public Date getUpdDate() {
        return updDate;
    }

    public int getVersion() {
        return version;
    }
}




