package com.example.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ImageController {
    String filePath = "/Users/dongwoo-yang/spring-file/";

    @CrossOrigin(origins="*")
    @PostMapping("/file/upload")
    public ResponseEntity upload(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) throws IOException, URISyntaxException {

        if(multipartFile.isEmpty()){
            log.info("File is empty");
        }

        String fullFilename = multipartFile.getOriginalFilename();
        int lastIndex = fullFilename.lastIndexOf(".");
        String filename = fullFilename.substring(0, lastIndex);
        String ext = fullFilename.substring(lastIndex + 1);

        // 새로운 파일 이름 생성
        String newName = UUID.randomUUID() + "." + ext;
        String uploadPath = filePath + newName;

        // Path 객체를 이용해 저장
        multipartFile.transferTo(Paths.get(uploadPath));

        URI uri = new URI(request.getRequestURI());
        return ResponseEntity.created(uri).body(newName);
    }

    @ResponseBody
    @GetMapping("/file/upload/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        log.info("file: {}", filePath + filename);
        return new UrlResource("file:" + filePath + filename);
    }
}
