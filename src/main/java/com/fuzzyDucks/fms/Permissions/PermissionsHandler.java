package com.fuzzyDucks.fms.Permissions;

import com.fuzzyDucks.fms.User.enums.UserRole;

public class PermissionsHandler {
    public boolean hasPermission(UserRole userType, String action) {
        for (String permission : userType.getPermissions()) {
            if (permission.equals(action)) return true;
        }
        return false;
    }
}
