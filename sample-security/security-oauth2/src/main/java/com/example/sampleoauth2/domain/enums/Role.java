package com.example.sampleoauth2.domain.enums;

import lombok.Getter;

@Getter
public enum Role {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    String value;
    Role(String value){
        this.value = value;
    }
}
