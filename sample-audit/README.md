# Spring Boot JPA - Audting 기능

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
