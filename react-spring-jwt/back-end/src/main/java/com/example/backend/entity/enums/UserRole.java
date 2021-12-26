package com.example.backend.entity.enums;

public enum UserRole {
    USER("ROLE_USER"), MANAGER("ROLE_MANAGER"), ADMIN("ROLE_ADMIN");

    private String value;

    UserRole(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
