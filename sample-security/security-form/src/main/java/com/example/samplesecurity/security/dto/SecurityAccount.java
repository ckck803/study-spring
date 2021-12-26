package com.example.samplesecurity.security.dto;

import com.example.samplesecurity.domain.Account;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class SecurityAccount extends User {
    public SecurityAccount(Account account) {
        super(account.getEmail()
                , account.getPassword()
                , AuthorityUtils.createAuthorityList(account.getRole().getValue()));
    }
}
