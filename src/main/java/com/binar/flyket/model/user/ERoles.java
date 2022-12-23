package com.binar.flyket.model.user;

public enum ERoles {
    ROLE_ADMIN, ROLE_BUYER;

    public static ERoles getRole(String role) {
        if ("admin".equalsIgnoreCase(role.trim())) {
            return ERoles.ROLE_ADMIN;
        }
        return ERoles.ROLE_BUYER;
    }

    public static boolean checkRole(String role) {
        return "admin".equalsIgnoreCase(role.trim()) || "buyer".equalsIgnoreCase(role);
    }
}
