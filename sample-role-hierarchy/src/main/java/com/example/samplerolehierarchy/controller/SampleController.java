package com.example.samplerolehierarchy.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class SampleController {
    @GetMapping("/")
    public String home(){
        return "Home";
    }

    @GetMapping("/user")
    public String user(){
        return "user";
    }

    @GetMapping("/manager")
    public String manager(Principal principal){
        return "manager";
    }

    @GetMapping("/admin")
    public String admin(Principal principal){
        return "admin";
    }
}
