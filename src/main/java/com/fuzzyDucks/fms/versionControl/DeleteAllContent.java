package com.fuzzyDucks.fms.versionControl;

import com.fuzzyDucks.fms.fileRepository.FileSchema;
import com.fuzzyDucks.fms.fileRepository.FileService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DeleteAllContent {
    public static void clearFile(String filePath) {
        Path path2 = Paths.get(filePath);
        Path fileName = path2.getFileName();
        String name = fileName.toString();
        try {
            FileWriter fw = new FileWriter(name, false);
            PrintWriter pw = new PrintWriter(fw, false);
            pw.flush();
            pw.close();
            fw.close();
            FileService.updateFileNameIfExist(new FileSchema(new File(filePath)), name);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
