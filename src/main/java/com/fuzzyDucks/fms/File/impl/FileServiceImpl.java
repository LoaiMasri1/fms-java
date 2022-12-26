package com.fuzzyDucks.fms.File.impl;

import com.fuzzyDucks.fms.Auth.AuthService.enums.AuthConstant;
import com.fuzzyDucks.fms.Cache.Cache;
import com.fuzzyDucks.fms.Exceptions.PermissionException;
import com.fuzzyDucks.fms.File.fileSchema.models.FileSchema;
import com.fuzzyDucks.fms.File.fileSchema.services.FileSchemaService;
import com.fuzzyDucks.fms.File.intf.FileService;
import com.fuzzyDucks.fms.File.fileSchema.services.IOService;
import com.fuzzyDucks.fms.File.enums.PathInfo;
import com.fuzzyDucks.fms.File.utils.FileUtils;
import com.fuzzyDucks.fms.Logger.LoggingHandler;
import com.fuzzyDucks.fms.Logger.intf.ILogger;
import com.fuzzyDucks.fms.Permissions.PermissionsHandler;
import com.fuzzyDucks.fms.User.enums.UserRole;

import java.io.File;
import java.io.IOException;

public class FileServiceImpl implements FileService {
    private static final FileSchemaService fileSchemaService = new FileSchemaService();
    private static final IOService ioService = new IOService();
    private static final Cache cache = Cache.getInstance();
    private static final FileUtils fileUtils = new FileUtils();
    public static final PermissionsHandler permissionsHandler = new PermissionsHandler();
    private static final ILogger logger = LoggingHandler.getInstance();
    private static final Object token = cache.get(AuthConstant.USER_TOKEN.getValue());
    public static final int role = cache.get(AuthConstant.USER_ROLE.getValue()) == null ? UserRole.READER.getValue() : (int) cache.get(AuthConstant.USER_ROLE.getValue());

    private static void checkIfLoggedIn() throws ClassNotFoundException {
        if (token == null) {
            logger.logWarning("User is not logged in");
            throw new ClassNotFoundException("Please login first");
        }
    }

    @Override
    public void importFile(FileSchema file, File selectedFile) throws IOException, ClassNotFoundException {
        checkIfLoggedIn();
        if (permissionsHandler.hasPermission(UserRole.fromValue(role), FileActions.IMPORT.getValue())) {
            fileSchemaService.addFile(file, selectedFile);
            ioService.copyFile(selectedFile, new File(fileUtils.decodeValue(file.getPath())));
            logger.logInfo("Importing file: " + file.getName() + "." + file.getType());
        } else {
            logger.logWarning("User does not have permission to import");
            throw new PermissionException("You do not have permission to import files");
        }
    }

    @Override
    public void deleteFile(String name, String type) throws IOException, ClassNotFoundException {
        checkIfLoggedIn();
        if (permissionsHandler.hasPermission(UserRole.fromValue(role), FileActions.DELETE.getValue())) {
            String path = fileSchemaService.getFilePath(name, type);
            String folderPath = path.substring(0, path.lastIndexOf(PathInfo.PATH_SEPARATOR.getPath()));
            ioService.deleteFile(new File(folderPath));
            fileSchemaService.removeFile(name, type);
            logger.logInfo("Deleting file: " + name + "." + type);
        } else {
            logger.logWarning("User does not have permission to delete");
            throw new PermissionException("You do not have permission to delete files");
        }
    }

    @Override
    public void exportFile(String name, String type) throws IOException, ClassNotFoundException {
        checkIfLoggedIn();
        if (permissionsHandler.hasPermission(UserRole.fromValue(role), FileActions.EXPORT.getValue())) {
            ioService.copyFileTo(new File(fileSchemaService.getFilePath(name, type)), new File(PathInfo.FULL_DOWNLOAD_PATH.getPath()));
        } else {
            logger.logWarning("User does not have permission to export");
            throw new PermissionException("You do not have permission to export files");
        }
    }

}
