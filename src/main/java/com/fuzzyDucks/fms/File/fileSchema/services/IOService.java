package com.fuzzyDucks.fms.File.fileSchema.services;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class IOService {
    public void copyFile(File selectedFile, File destinationFile) throws IOException {
        FileUtils.copyFile(selectedFile, destinationFile);
    }

    public void copyFileTo(File selectedFile, File destinationFile) throws IOException {
        FileUtils.copyFileToDirectory(selectedFile, destinationFile);
    }

    public void deleteFile(File selectedFile) throws IOException {
        FileUtils.forceDelete(selectedFile);
    }
}