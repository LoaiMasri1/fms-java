package com.fuzzyDucks.fms.File;

import com.fuzzyDucks.fms.File.FileSchema.FileSchema;
import com.fuzzyDucks.fms.File.FileSchema.FileSchemaService;
import com.fuzzyDucks.fms.File.IO.IOService;
import com.fuzzyDucks.fms.File.enums.PathInfo;
import java.io.File;
import java.io.IOException;

public class FileService {

    private static FileSchemaService fileSchemaService;
    private static IOService ioService;

    private FileService() {
        fileSchemaService = new FileSchemaService();
        ioService = new IOService();
    }

    public static void importFile(FileSchema file, File selectedFile, File newFile) throws IOException {
        fileSchemaService.addFile(file);
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
    
    public static void updateFileNameIfExist(@NotNull FileSchema file, String name, String newName) {

        int versionCounter = (files.find(new Document("name", name)).first().getInteger("version"))+1;
        if (files.find(new Document("name", name)).first() != null){
            System.out.println("Do you want to save the new file as"+ name+"."+versionCounter+1+"?"+"y/n");
        
            Scanner myObj = new Scanner(System.in);
            System.out.println("Y/N ?");
            String choise = myObj.nextLine();
            if (Objects.equals(choise, "y") || Objects.equals(choise, "Y")) {
                String oldPath = getFilePath(name);
                String newPath = oldPath.split("[.]")[0];
                newPath = newPath + "V" + versionCounter + "." + file.getType();
                files.findOneAndUpdate(eq("name", name), file.UpdateDocument(versionCounter, newPath, LocalDateTime.now()));

        }   else if (Objects.equals(choise, "n") || Objects.equals(choise, "N")) {
            String oldPath = getFilePath(name);
            String newPath = oldPath.split(name)[0];
            newPath = newPath + "Default"  + "." + file.getType();
            versionCounter=0;
            files.findOneAndUpdate(eq("name", name), file.UpdateDocument(versionCounter, newPath, LocalDateTime.now()));}
        } else {
            throw new IllegalArgumentException("File is not exist");
        }

    }

}
