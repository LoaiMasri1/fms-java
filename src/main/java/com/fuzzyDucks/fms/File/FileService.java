package com.fuzzyDucks.fms.File;

import com.fuzzyDucks.fms.File.FileSchema.FileSchema;
import com.fuzzyDucks.fms.File.FileSchema.FileSchemaService;
import com.fuzzyDucks.fms.File.IO.IOService;
import com.fuzzyDucks.fms.File.enums.PathInfo;
import java.io.File;
import java.io.IOException;

public class FileService {
    public static void importFile(FileSchema file, File selectedFile, File newFile) throws IOException {
        FileSchemaService.addFile(file);
        IOService.copyFile(selectedFile, newFile);
    }

    public static void deleteFile(String name, String type) throws IOException {
        String path = FileSchemaService.getFilePath(name, type);
        String folderPath = path.substring(0, path.lastIndexOf(PathInfo.PATH_SEPARATOR.getPath()));
        IOService.deleteFile(new File(folderPath));
        FileSchemaService.removeFile(name, type);
    }


    public static void exportFile(String name, String type) throws IOException {
        File selectedFile = new File(FileSchemaService.getFilePath(name, type));
        File downloadFile = new File(PathInfo.FULL_DOWNLOAD_PATH.getPath());
        IOService.copyFileTo(selectedFile, downloadFile);
    }


}
