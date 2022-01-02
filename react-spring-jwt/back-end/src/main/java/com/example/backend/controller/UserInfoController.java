package com.example.backend.controller;

import com.example.backend.entity.UserInfo;
import com.example.backend.entity.dto.UserInfoDto;
import com.example.backend.entity.enums.UserRole;
import com.example.backend.security.jwt.JwtUtils;
import com.example.backend.security.servcie.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class UserInfoController {

    private final AuthenticationManager authenticationManager;
    private final UserInfoService userInfoService;
    private final JwtUtils jwtUtils;

    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }

    @PostMapping("/api/signup")
    public ResponseEntity signUp(@RequestBody UserInfoDto userInfoDto){
        UserInfo userInfo = UserInfo.builder()
                .username(userInfoDto.getUsername())
                .email(userInfoDto.getEmail())
                .password(userInfoDto.getPassword())
                .userRole(UserRole.USER)
                .build();

        UserInfo createdUser = userInfoService.saveUserInfo(userInfo);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{id}")
                .buildAndExpand(createdUser.getId())
                .toUri();

        return ResponseEntity.created(uri).body(createdUser);
    }

    @PostMapping("/api/login")
    public ResponseEntity login(@RequestBody UserInfo userInfo) throws Exception {
        String email = userInfo.getEmail();
        String password = userInfo.getPassword();


        try{
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);
            Authentication authenticate = authenticationManager.authenticate(token);
            String jwt = jwtUtils.createToken(authenticate);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Authorization", "Bearer " +jwt);

            return ResponseEntity.ok().headers(httpHeaders).body(jwt);
        }catch (Exception e){
            throw new Exception("invalid username/password");
        }
    }
}
