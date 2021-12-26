package com.example.sampleoauth2.domain;

import com.example.sampleoauth2.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OAuth2Entity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @Builder
    public OAuth2Entity(String username, String email){
        this.username = username;
        this.email = email;
    }

    public OAuth2Entity updateUserName(String username){
        this.username = username;

        return this;
    }
}
