package com.example.sampleaudit.controller;

import com.example.sampleaudit.controller.dto.PostDto;
import com.example.sampleaudit.domain.Post;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@RestController
public class PostController {

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/hello")
    public String hello() {
        return "hello world!";
    }

//    @GetMapping("/post")
//    public void getPost(){
//        entityManager.
//    }

    @PostMapping("/post")
    @Transactional
    public void createPost(@RequestBody PostDto postDto) {
        Post post = Post.builder()
                .title(postDto.getTitle())
                .subTitle(postDto.getSubtitle())
                .build();

        entityManager.persist(post);
        entityManager.flush();
    }

}
