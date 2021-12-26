package com.example.demomultipart.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@Slf4j
public class FileController {

    String filePath = "/Users/dongwoo-yang/spring-file/";

    @PostMapping("/file/upload")
    public ResponseEntity upload(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) throws IOException, URISyntaxException {

        if(multipartFile.isEmpty()){
            log.info("File is empty");
        }

        log.info("multipartFile.getName() : {}", multipartFile.getName());
        log.info("multipartFile.getOriginalFilename() : {}", multipartFile.getOriginalFilename());
        log.info("multipartFile.getContentType() : {}", multipartFile.getContentType());
        log.info("multipartFile.getSize() : {}", multipartFile.getSize());
        log.info("multipartFile.getResource() : {}", multipartFile.getResource());

        String fullFilename = multipartFile.getOriginalFilename();
        int lastIndex = fullFilename.lastIndexOf(".");
        String filename = fullFilename.substring(0, lastIndex);
        String ext = fullFilename.substring(lastIndex + 1);
        // 새로운 파일 이름 생성
        String newName = UUID.randomUUID() + "." + ext;
        String uploadPath = filePath + newName;

        // File 객체를 이용해 저장
        // multipartFile.transferTo(new File(uploadPath));

        // Path 객체를 이용해 저장
        multipartFile.transferTo(Paths.get(uploadPath));

        URI uri = new URI(request.getRequestURI());
        return ResponseEntity.created(uri).body("File Upload Success");
    }

    @PostMapping("/file/upload2")
    public ResponseEntity<Resource> upload2(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) throws IOException {

        if(multipartFile.isEmpty()){
            log.info("File is empty");
        }

        log.info("HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE : {}",request.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE));
        log.info("HandlerMapping.LOOKUP_PATH : {}", request.getAttribute(HandlerMapping.LOOKUP_PATH));
        log.info("HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE : {}",request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE));
        log.info("HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE : {}",request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE));
        log.info("HandlerMapping.INTROSPECT_TYPE_LEVEL_MAPPING : {}",request.getAttribute(HandlerMapping.INTROSPECT_TYPE_LEVEL_MAPPING));
        log.info("HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE : {}",request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
        log.info("HandlerMapping.MATRIX_VARIABLES_ATTRIBUTE : {}",request.getAttribute(HandlerMapping.MATRIX_VARIABLES_ATTRIBUTE));
        log.info("HandlerMapping.PRODUCIBLE_MEDIA_TYPES_ATTRIBUTE : {}",request.getAttribute(HandlerMapping.PRODUCIBLE_MEDIA_TYPES_ATTRIBUTE));


        Resource resource = multipartFile.getResource();

        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                .filename(multipartFile.getOriginalFilename())
                .build();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentDisposition(contentDisposition);

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(resource);
    }
}
