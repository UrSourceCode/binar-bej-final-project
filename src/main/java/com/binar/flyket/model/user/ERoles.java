package com.binar.flyket.model.user;

public enum ERoles {
    ROLE_ADMIN, ROLE_BUYER;

    public static ERoles getRole(String role) {
        if (role != null && "admin".equalsIgnoreCase(role.trim())) {
            return ERoles.ROLE_ADMIN;
        }
        return ERoles.ROLE_BUYER;
    }
}
