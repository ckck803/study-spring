# Spring Boot JPA @Query 사용하기

```java
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p")
    public Optional<List<Post>> getPosts();
}
```

```java
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
```

### 파라미터 바인딩

```java
@Query("select p from Post p where p.id = :id")
public Optional<String> getPost(@Param("id") Long id);
```

```java
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
````

### Entity 조회 결과에서 특정 값만 가져오기

```java
@Query("select p.title from Post p where p.id = :id")
public Optional<String> getPostTitle(@Param("id") Long id);
```

```java
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
```

### DTO 를 이용해 값 반환하기

> Entity 로 조회한 데이터를 이용해 Dto 객체를 생성하기 때문에 파라미터 생성자가 반드시 필요하다.

```java
@Data
// 파라미터 생성자가 반드시 필요
// 모든 파라미터를 받는 생성자가 아니면 해당 파라미터로 만들 수 있는 생성자가 반드시 필요
@AllArgsConstructor 
public class PostDto {

    private String title;
    private String subTitle;
    private String content;
}
```

> DTO 를 사용하기 위해서는 Package 명을 전부 명시해줄 필요가 있다.

```java
@Query("select new com.example.samplequery.repository.dto.PostDto(p.title, p.subTitle, p.content) from Post p")
public Optional<List<PostDto>> getPostsAsDto();
```
