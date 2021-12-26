package com.example.backend.security.servcie;

import com.example.backend.entity.UserInfo;
import com.example.backend.entity.dto.SignupDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserInfoService extends UserDetailsService {
    public UserInfo saveUserInfo(SignupDto signupDto);
}
