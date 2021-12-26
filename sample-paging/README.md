# Spring Boot JPA - Paging

> 조회 쿼리에 **Pageable** 객체를 넣어 줌으로써 JPA 에서 제공하는 Paging 기능을 사용할 수 있다.

Paging 결과로 **Page**, **Slice**, **List** 세가지 종류의 객체가 반환된다.

| 종류  | 설명                              |
| ----- | --------------------------------- |
| Page  | **Count 쿼리** 를 포함하는 페이징 |
| Slice | 내부적으로 **Limit + 1** 조회     |
| List  | 별도의 작업 없이 결과만 반환      |

## 의존성 추가

JPA 에서 생성하는 쿼리의 상세 파라미터를 알고 싶을 경우 추가하는 라이브러리

```gradle
implementation group: 'com.github.gavlyukovskiy', name: 'p6spy-spring-boot-starter', version: '1.7.1'
```


## 사전 데이터 생성

```java
@BeforeEach
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
```

## Page 객체로 반환 받기

```java
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
```

- Paging 쿼리를 날릴 때 **Count 쿼리** 도 같이 날리는 것을 확인할 수 있다.

```java
select post0_.id as id1_0_, post0_.author as author2_0_, post0_.content as content3_0_, post0_.subtitle as subtitle4_0_, post0_.title as title5_0_ from post post0_ where post0_.author='tester' limit 20;
//--- count 쿼리가 날라간 것을 확인할 수 있다. ---//
select count(post0_.id) as col_0_0_ from post post0_ where post0_.author='tester';
```

## Slice 객체로 반환 받기

```java
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
```

- Paging 쿼리를 보낼 때 **Limit + 1** 로 쿼리를 날리는 것을 확인할 수 있다.

```java
select post0_.id as id1_0_, post0_.author as author2_0_, post0_.content as content3_0_, post0_.subtitle as subtitle4_0_, post0_.title as title5_0_ from post post0_ where post0_.author='tester' limit 21;
```

## List 객체로 반환 받기

```java
@Test
void postsAsListTest(){
    int pageIndex = 0;
    int pageSize = 20;
    String author = "tester";

    PageRequest pageRequest = PageRequest.of(pageIndex, pageSize);
    List<Post> posts = postRepository.findPostsAsListByAuthor(author, pageRequest);
    assertThat(posts.size()).isEqualTo(20);
}
```

```java
select post0_.id as id1_0_, post0_.author as author2_0_, post0_.content as content3_0_, post0_.subtitle as subtitle4_0_, post0_.title as title5_0_ from post post0_ where post0_.author='tester' limit 20;
```