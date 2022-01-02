package com.example.backend.security.servcie;

import com.example.backend.entity.UserInfo;
import com.example.backend.repository.UserInfoRepository;
import com.example.backend.security.dto.SecurityUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserInfoService implements UserDetailsService {

    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserInfo> userInfoOptional =  userInfoRepository.findByEmail(email);

        if(userInfoOptional.isPresent()){
            UserInfo userInfo = userInfoOptional.get();
            List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userInfo.getUserRole().getValue());

            return new SecurityUserInfo(userInfo.getEmail(), passwordEncoder.encode(userInfo.getPassword()), authorities);
        }else{
            return null;
        }
    }

    public UserInfo saveUserInfo(UserInfo userInfo){
        return userInfoRepository.save(userInfo);
    }
}
