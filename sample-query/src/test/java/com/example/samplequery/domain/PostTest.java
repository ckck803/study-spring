package com.example.samplequery.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class PostTest {

    @PersistenceContext
    private EntityManager em;

    @Test
    @DisplayName("Post가 제대로 생성 됐는지 확인")
    void createPostTest(){
        String title = "Test Title";
        String subTitle = "Test SubTitle";
        String content = "Test Content";

        Post post = Post.builder()
                .title(title)
                .subTitle(subTitle)
                .content(content)
                .build();

        em.persist(post);
        em.flush();

        Post savedPost = em.find(Post.class, 1L);
        assertThat(savedPost.getTitle()).isEqualTo(title);
        assertThat(savedPost.getSubTitle()).isEqualTo(subTitle);
        assertThat(savedPost.getContent()).isEqualTo(content);
        assertThat(savedPost.getCreatedDate()).isNotNull();
        assertThat(savedPost.getLastModifiedDate()).isNotNull();
        assertThat(savedPost.getCreatedDate()).isEqualTo(savedPost.getLastModifiedDate());
    }
}