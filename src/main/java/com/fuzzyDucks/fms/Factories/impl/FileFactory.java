package com.fuzzyDucks.fms.Factories.impl;

import com.fuzzyDucks.fms.Exceptions.NullDataException;
import com.fuzzyDucks.fms.Factories.intf.IFileFactory;
import com.fuzzyDucks.fms.File.File;
import com.fuzzyDucks.fms.File.fileSchema.models.FileSchema;
import com.fuzzyDucks.fms.File.impl.FileActions;
import com.fuzzyDucks.fms.File.impl.FileServiceImpl;
import com.fuzzyDucks.fms.File.intf.FileService;
import com.sun.media.sound.InvalidDataException;

import java.io.IOException;

public class FileFactory implements IFileFactory {
    private  final FileService fileService= new FileServiceImpl();
    @Override
    public void doAction(String action, Object object) throws IOException, ClassNotFoundException {
        File file = (File) object;
        action = action.toLowerCase();
        if (FileActions.IMPORT.getValue().equals(action)) {
            String path = file.getPath();
            if (path == null || path.isEmpty()) {
                throw new NullDataException("Path is null or empty");
            } else {
                new FileSchema(new java.io.File(file.getPath()));
            }
        } else {
            throw new InvalidDataException("Invalid action");
        }
    }
    public void doAction(String action, String name,String type) throws IOException, ClassNotFoundException {
        action = action.toLowerCase();
        if (FileActions.EXPORT.getValue().equals(action)) {
            fileService.exportFile(name, type);
        } else if (FileActions.DELETE.getValue().equals(action)) {
            fileService.deleteFile(name, type);
        } else {
            throw new InvalidDataException("Invalid action");
        }
    }


}
