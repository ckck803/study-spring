package com.example.samplequery.repository;

import com.example.samplequery.domain.Post;
import com.example.samplequery.repository.dto.PostDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p")
    public Optional<List<Post>> getPosts();

    @Query("select p from Post p where p.id = :id")
    public Optional<Post> getPost(@Param("id") Long id);

    @Query("select p.title from Post p where p.id = :id")
    public Optional<String> getPostTitle(@Param("id") Long id);

    @Query("select new com.example.samplequery.repository.dto.PostDto(p.title, p.subTitle, p.content) from Post p")
    public Optional<List<PostDto>> getPostsAsDto();
}
