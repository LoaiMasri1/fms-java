package com.fuzzyDucks.fms.Exceptions;

import java.nio.file.AccessDeniedException;

public class PermissionException extends AccessDeniedException {
    public PermissionException(String message) {
        super(message);
    }
}
