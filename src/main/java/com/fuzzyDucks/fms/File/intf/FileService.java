package com.fuzzyDucks.fms.File.intf;

import com.fuzzyDucks.fms.File.fileSchema.models.FileSchema;

import java.io.File;
import java.io.IOException;

public interface FileService {
    void importFile(FileSchema file, File selectedFile) throws IOException, ClassNotFoundException;
    void deleteFile(String name, String type) throws IOException, ClassNotFoundException;
    void exportFile(String name, String type) throws IOException, ClassNotFoundException;
}
