package com.fuzzyDucks.fms.Factories.impl;

import com.fuzzyDucks.fms.Exceptions.NullDataException;
import com.fuzzyDucks.fms.Factories.intf.IFileFactory;
import com.fuzzyDucks.fms.File.File;
import com.fuzzyDucks.fms.File.fileSchema.models.FileSchema;
import com.fuzzyDucks.fms.File.impl.FileServiceImpl;
import com.fuzzyDucks.fms.File.intf.FileService;

import java.io.IOException;

public class FileFactory implements IFileFactory {
    private  final FileService fileService= new FileServiceImpl();
    @Override
    public void doAction(String action, Object object) throws IOException, ClassNotFoundException {
        File file = (File) object;
        action = action.toLowerCase();
        switch (action) {
            case "import":
                String path = file.getPath();
                if (path == null || path.isEmpty()) {
                    throw new NullDataException("Path is null or empty");
                } else {
                    new FileSchema(new java.io.File(file.getPath()));
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid action");
        }
    }
    public void doAction(String action, String name,String type) throws IOException, ClassNotFoundException {
        action = action.toLowerCase();
        switch (action) {
            case "export":
                fileService.exportFile(name,type);
                break;
            case "delete":
                fileService.deleteFile(name, type);
                break;
            default:
                throw new IllegalArgumentException("Invalid action");
        }
    }


}
