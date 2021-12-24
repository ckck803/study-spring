package com.example.samplequery.repository;

import com.example.samplequery.domain.Post;
import com.example.samplequery.repository.dto.PostDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("Save Test")
    void saveTest(){
        String title = "Test Title";
        String subTitle = "Test SubTitle";
        String content = "Test Content";

        Post post = Post.builder()
                .title(title)
                .subTitle(subTitle)
                .content(content)
                .build();

        Post savedPost = postRepository.save(post);
        assertThat(savedPost.getTitle()).isEqualTo(title);
        assertThat(savedPost.getSubTitle()).isEqualTo(subTitle);
        assertThat(savedPost.getContent()).isEqualTo(content);
        assertThat(savedPost.getCreatedDate()).isEqualTo(savedPost.getLastModifiedDate());
    }

    @Test
    @DisplayName("@Query Test")
    void getPostsTest(){
        String title = "Test Title";
        String subTitle = "Test SubTitle";
        String content = "Test Content";

        Post post = Post.builder()
                .title(title)
                .subTitle(subTitle)
                .content(content)
                .build();

        postRepository.save(post);

        Optional<List<Post>> posts = postRepository.getPosts();
        assertThat(posts.getClass()).isEqualTo(Optional.class);
        assertThat(posts.get()).isNotNull();
        assertThat(posts.get().getClass()).isEqualTo(ArrayList.class);

        Post savedPost = posts.get().get(0);
        assertThat(savedPost.getTitle()).isEqualTo(title);
        assertThat(savedPost.getSubTitle()).isEqualTo(subTitle);
        assertThat(savedPost.getContent()).isEqualTo(content);
        assertThat(savedPost.getCreatedDate()).isEqualTo(posts.get().get(0).getLastModifiedDate());
    }

    @Test
    @DisplayName("Test Parameter test")
    void parameterBindingTest(){
        String title = "Test Title";
        String subTitle = "Test SubTitle";
        String content = "Test Content";

        Post post = Post.builder()
                .title(title)
                .subTitle(subTitle)
                .content(content)
                .build();

        postRepository.save(post);

        Optional<Post> optional = postRepository.getPost(1L);
        assertThat(optional.get()).isNotNull();

        Post savedPost = optional.get();
        assertThat(savedPost.getTitle()).isEqualTo(title);
        assertThat(savedPost.getSubTitle()).isEqualTo(subTitle);
        assertThat(savedPost.getContent()).isEqualTo(content);
        assertThat(savedPost.getCreatedDate()).isEqualTo(savedPost.getLastModifiedDate());
    }


    @Test
    @DisplayName("get Post Title Test")
    void getPostTitleTest(){
        String title = "Test Title";
        String subTitle = "Test SubTitle";
        String content = "Test Content";

        Post post = Post.builder()
                .title(title)
                .subTitle(subTitle)
                .content(content)
                .build();

        postRepository.save(post);

        Optional<String> postTitle = postRepository.getPostTitle(1L);
        assertThat(postTitle.get()).isNotNull();
        assertThat(postTitle.get()).isEqualTo(title);
    }

    @Test
    @DisplayName("Post 데이터를 Dto 로 받아오기")
    void getPostAsDtoTest(){
        String title = "Test Title";
        String subTitle = "Test SubTitle";
        String content = "Test Content";

        Post post = Post.builder()
                .title(title)
                .subTitle(subTitle)
                .content(content)
                .build();

        postRepository.save(post);

        Optional<List<PostDto>> optionalList = postRepository.getPostsAsDto();
        assertThat(optionalList.get().getClass()).isEqualTo(ArrayList.class);

        List<PostDto> postDtoList = optionalList.get();
        assertThat(postDtoList.get(0).getClass()).isEqualTo(PostDto.class);
        assertThat(postDtoList.get(0).getTitle()).isEqualTo(title);
        assertThat(postDtoList.get(0).getSubTitle()).isEqualTo(subTitle);
        assertThat(postDtoList.get(0).getContent()).isEqualTo(content);
    }
}