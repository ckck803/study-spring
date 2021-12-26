package com.example.samplepaging.repository;

import com.example.samplepaging.domain.Post;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback(value = false)
@Transactional
//@Slf4j
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

//    @BeforeEach
    void init(){
        IntStream.rangeClosed(1, 200).forEach((index) -> {
            String title = "test title " + String.valueOf(index);
            String subTitle = "test Sub title "+ String.valueOf(index);
            String content = "test content "+ String.valueOf(index);
            String author = "tester";

            Post post = new Post();
            post.setTitle(title);
            post.setSubtitle(subTitle);
            post.setContent(content);
            post.setAuthor(author);

            Post savedPost = postRepository.save(post);
        });
    }

    @Test
    void postsAsPagingTest(){
        int pageIndex = 0;
        int pageSize = 20;
        String author = "tester";

        PageRequest pageRequest = PageRequest.of(pageIndex, pageSize);
        Page<Post> posts = postRepository.findPostsAsPageByAuthor(author, pageRequest);

        assertThat(posts.getNumber()).isEqualTo(pageIndex);    // 현재 Page Index 를 가져온다.
        assertThat(posts.getSize()).isEqualTo(pageSize);       // Paging 사이즈를 가져온다.
        assertThat(posts.isFirst()).isTrue();                  // 현재 Page 가 첫번째 인지 확인한다.
        assertThat(posts.hasNext()).isTrue();                  // 다음 Page 가 있는지 확인한다.
        assertThat(posts.getTotalPages()).isEqualTo(10);       // 전체 페이지 수를 확인한다.
        assertThat(posts.getTotalElements()).isEqualTo(200);   // 저장된 전체 객체의 수를 가져온다.
        assertThat(posts.getNumberOfElements()).isEqualTo(20); // 현재 Page 내 원소의 개수를 가져온다.
    }

    @Test
    void postsAsSliceTest(){
        int pageIndex = 0;
        int pageSize = 20;
        String author = "tester";

        PageRequest pageRequest = PageRequest.of(pageIndex, pageSize);
        Slice<Post> posts = postRepository.findPostsAsSliceByAuthor(author, pageRequest);

        assertThat(posts.getNumber()).isEqualTo(pageIndex);    // 현재 Page Index 를 가져온다.
        assertThat(posts.getSize()).isEqualTo(pageSize);       // Paging 사이즈를 가져온다.
        assertThat(posts.isFirst()).isTrue();                  // 현재 Page 가 첫번째 인지 확인한다.
        assertThat(posts.hasNext()).isTrue();                  // 다음 Page 가 있는지 확인한다.
        // Slice 로 데이터를 반환하게 되면 Count 쿼리를 별도로 날리지 않기 때문에 전체 크기를 알 수 없다.
        // assertThat(posts.getTotalPages()).isEqualTo(10);       // 전체 페이지 수를 확인한다.
        // assertThat(posts.getTotalElements()).isEqualTo(200);   // 저장된 전체 객체의 수를 가져온다.
        assertThat(posts.getNumberOfElements()).isEqualTo(20); // 현재 Page 내 원소의 개수를 가져온다.
    }

//    @Test
    void postsAsListTest(){
        int pageIndex = 0;
        int pageSize = 20;
        String author = "tester";

        PageRequest pageRequest = PageRequest.of(pageIndex, pageSize);
        List<Post> posts = postRepository.findPostsAsListByAuthor(author, pageRequest);
        assertThat(posts.size()).isEqualTo(20);
    }
}