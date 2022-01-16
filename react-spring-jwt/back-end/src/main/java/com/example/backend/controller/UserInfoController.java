package com.example.backend.controller;

import java.net.URI;

import com.example.backend.entity.UserInfo;
import com.example.backend.entity.dto.SignupDto;
import com.example.backend.security.jwt.JwtUtils;

import com.example.backend.security.servcie.UserInfoService;
import com.example.backend.security.servcie.UserInfoServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "**")
public class UserInfoController {

    private final UserInfoService userInfoService;
    private final JwtUtils jwtUtils;

    @GetMapping("/")
    public String hello() {
        return "Hello World";
    }

    @PostMapping("/api/signup")
    public ResponseEntity signUp(@RequestBody SignupDto signupDto) {

        UserInfo createdUser = userInfoService.saveUserInfo(signupDto);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}").buildAndExpand(createdUser.getId())
                .toUri();

        return ResponseEntity.created(uri).body(createdUser);
    }
}
