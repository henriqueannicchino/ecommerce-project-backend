package com.ann.ecommerce.entity;

public enum UserRole {
    ADMIN("admin"),
    CUSTOMER("customer"),
    USER("user");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
