package com.example.samplesecurity.domain.enums;

public enum Role {
    USER("ROLE_USER"), MANAGER("ROLE_MANAGER"), ADMIN("ROLE_ADMIN");

    private String value;
    Role(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
