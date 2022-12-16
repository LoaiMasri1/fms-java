package com.fuzzyDucks.fms.File.IO;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class IOService {

    public static void copyFile(File selectedFile, File destinationFile) throws IOException {
        try {
            FileUtils.copyFile(selectedFile, destinationFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyFileTo(File selectedFile, File destinationFile) throws IOException {
        try {
            FileUtils.copyFileToDirectory(selectedFile, destinationFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFile(File selectedFile) throws IOException {
        try {
            FileUtils.forceDelete(selectedFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
