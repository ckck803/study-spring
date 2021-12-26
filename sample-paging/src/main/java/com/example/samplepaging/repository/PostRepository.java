package com.example.samplepaging.repository;

import com.example.samplepaging.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findPostsAsPageByAuthor(String author, Pageable pageable);
    Slice<Post> findPostsAsSliceByAuthor(String author, Pageable pageable);
    List<Post> findPostsAsListByAuthor(String author, Pageable pageable);
}
