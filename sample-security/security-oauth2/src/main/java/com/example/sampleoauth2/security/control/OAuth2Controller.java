package com.example.sampleoauth2.security.control;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuth2Controller {
    @GetMapping("/")
    public String Hello(){
        return "Hello World";
    }

    @GetMapping("/restricted")
    public String restricted(){
        return "restricted";
    }
}
