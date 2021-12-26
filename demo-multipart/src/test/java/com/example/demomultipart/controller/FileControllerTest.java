package com.example.demomultipart.controller;


import jdk.jfr.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FileControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    int randomServerPort;

    @Test
    public void getFile() throws IOException {
        ClassPathResource resource = new ClassPathResource("testFile.txt");
        File file = resource.getFile();
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line = "";
        while((line = bufferedReader.readLine()) != null){
            System.out.println(line);
        }
    }

    @Test
    public void uploadTest() throws IOException {
        ClassPathResource resource = new ClassPathResource("testFile.txt");
        File file = resource.getFile();

        MultiValueMap multiValueMap = new LinkedMultiValueMap();
        multiValueMap.add("file", resource);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity(multiValueMap, headers);

        String requestURL = "http://localhost:" + randomServerPort + "/file/upload";

        ResponseEntity<String> responseEntity = this.testRestTemplate.exchange(requestURL,
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<String>() {
                });

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isEqualTo("File Upload Success");
    }
}