# Spring Boot JPA 공부

## @Query 사용하기

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

## Audting 기능

> JPA 에서 제공하는 Audting 기능을 사용해 데이터 생성, 변경을 감지(추적) 하도록 한다.

- @EnableJpaAuditing
  - Spring Application 에서 Auditing 기능을 확성화 새준다.
- @EntityListeners(AuditingEntityListener.class)
  - 해당 Entity 에 Auditing 기능을 포함 시켜 데이터 변경을 감시할 수 있도록 한다.

```java
@SpringBootApplication
@EnableJpaAuditing
public class SampleAuditApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleAuditApplication.class, args);
    }

}
```

### 생성일, 수정일 남기기

| 어노테이션        | 설명                                          |
| ----------------- | --------------------------------------------- |
| @CreatedDate      | Entity 가 생성될 때 시간을 자동으로 저장한다. |
| @LastModifiedDate | Entity가 수정될 때 시간을 자동으로 저장한다.  |

```java
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String subTitle;

    private String content;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
```

### 생성자, 수정자 남기기

- 생성자와 수정자를 남기기 위해서는 **AuditorAware** 를 구현해 데이터를 변경한 사람 정보를 가져올 수 있도록 해야 한다.
- Spring Security 와 함께 사용하게 되면 SeuciryContext 에 저장된 Authentication 객체로 부터 사용자 정보를 가져올 수 있다.

| 어노테이션      | 설명                              |
| --------------- | --------------------------------- |
| @CreatedBy      | Entity 를 생성한 주체를 저장한다. |
| @LastModifiedBy | Entity 를 수정한 주체를 저장한다. |

```java
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(UUID.randomUUID().toString());
    }
}
```

```java
@Configuration
public class AuditingConfig {

    @Bean
    public AuditorAware<String> auditorProvider(){
        return new AuditorAwareImpl();
    }
}
```

```java
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String subTitle;

    private String content;

    @CreatedDate
    private LocalDateTime createdDate;

    @CreatedBy
    private String creator;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @LastModifiedBy
    private String modifier;
}
```
