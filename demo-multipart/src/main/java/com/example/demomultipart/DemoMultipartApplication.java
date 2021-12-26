package com.example.demomultipart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@SpringBootApplication
public class DemoMultipartApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoMultipartApplication.class, args);
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(524288000);
        multipartResolver.setMaxUploadSizePerFile(52428800);
        return multipartResolver;
    }
}
