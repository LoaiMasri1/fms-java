package com.fuzzyDucks.fms.User.enums;

public enum UserRole {
    ADMIN(0, new String[]{"export", "import", "delete", "overwrite"}),
    STAFF(1, new String[]{"export", "import"}),
    READER(2, new String[]{"export"});
    private final int value;
    private final String[] permissions;

    UserRole(int value, String[] permissions) {
        this.value = value;
        this.permissions = permissions;
    }

    public int getValue() {
        return value;
    }

    public String[] getPermissions() {
        return permissions;
    }

    public static UserRole fromValue(int value) {
        for (UserRole role : UserRole.values()) {
            if (role.getValue() == value) {
                return role;
            }
        }
        throw new IllegalArgumentException("No role with value " + value);
    }

}
