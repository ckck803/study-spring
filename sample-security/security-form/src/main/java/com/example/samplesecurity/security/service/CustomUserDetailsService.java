package com.example.samplesecurity.security.service;

import com.example.samplesecurity.domain.Account;
import com.example.samplesecurity.repository.AccountRepository;
import com.example.samplesecurity.security.dto.SecurityAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        return new User("user", passwordEncoder.encode( "1234"), new ArrayList<>());
        Optional<Account> optional = accountRepository.findByEmail(email);

        if(optional.isPresent()){
            Account account = optional.get();
            return new SecurityAccount(account);
        }else{
            throw new UsernameNotFoundException(email + "사용자 없음");
        }
    }

    public void saveAccount(Account account){
        accountRepository.save(account);
    }
}
