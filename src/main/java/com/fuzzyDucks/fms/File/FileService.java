package com.fuzzyDucks.fms.File;

import com.fuzzyDucks.fms.File.FileSchema.FileSchema;
import com.fuzzyDucks.fms.File.FileSchema.FileSchemaService;
import com.fuzzyDucks.fms.File.IO.IOService;
import com.fuzzyDucks.fms.File.enums.PathInfo;
import java.io.File;
import java.io.IOException;

public class FileService {

    private static FileSchemaService fileSchemaService = new FileSchemaService();
    private static IOService ioService = new IOService();
    private FileService() {
        fileSchemaService = new FileSchemaService();
        ioService = new IOService();
    }

    public static void importFile(FileSchema file, File selectedFile) throws IOException {
        fileSchemaService.addFile(file);
        File newFile = new File(FileUtils.decodeValue(file.getPath()));
        ioService.copyFile(selectedFile, newFile);
    }

    public static void deleteFile(String name, String type) throws IOException {
        String path = fileSchemaService.getFilePath(name, type);
        String folderPath = path.substring(0, path.lastIndexOf(PathInfo.PATH_SEPARATOR.getPath()));
        ioService.deleteFile(new File(folderPath));
        fileSchemaService.removeFile(name, type);
    }

    public static void exportFile(String name, String type) throws IOException {
        File selectedFile = new File(fileSchemaService.getFilePath(name, type));
        File downloadFile = new File(PathInfo.FULL_DOWNLOAD_PATH.getPath());
        ioService.copyFileTo(selectedFile, downloadFile);
    }

}
