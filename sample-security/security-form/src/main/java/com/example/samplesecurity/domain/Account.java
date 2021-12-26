package com.example.samplesecurity.domain;

import com.example.samplesecurity.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Account {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String getRoleValue(){
        return this.role.getValue();
    }

    @Builder
    public Account(String username, String email, String password, Role role){
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
