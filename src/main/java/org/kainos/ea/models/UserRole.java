package org.kainos.ea.models;

import java.util.Map;

public class UserRole {
    final static public String ADMIN = "ADMIN";
    final static public String USER = "USER";
    private static final Map<Integer, String> rolesMap =
            Map.of(1, ADMIN, 2, USER);
    int roleId;

    public UserRole(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return rolesMap.get(getRoleId());
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
