# JPA 프로그래밍(기본편) - 영속성 전이 Cascade

> Cascading 이란, 특정 Entity 에 작업을 수행했을 때, 같은 작업이 연관된 Entity 에도 일어나는 것을 의미한다.

|                     |                                                                                      |
| ------------------- | ------------------------------------------------------------------------------------ |
| CascadeType.ALL     | 상위 Entity 에서 발생한 모든 작업을 자식 Entity 로 모두 전파                         |
| CascadeType.PERSIST | 상위 Entity 를 **영속화** 하는 작업이 일어 날때 자식 Entity 도 같이 **영속화** 한다. |
| CascadeType.MERGE   | 상위 Entity 에서 발생한 Merge 작업을 하위 Entity 까지 Merge 작업을 전파한다.         |
| CascadeType.REMOVE  | 상위 Entity 를 삭제할 때 하위 Entity 까지 삭제한다.                                  |
| CascadeType.REFRESH | DataBase 로부터 상위 Entity 를 다시 읽어올때 하위 Entity 까지 다시 읽어온다.         |
| CascadeType.DETACH  | 상위 Entity 를 영속성 컨텍스트에서 제거할 때 하위 Entity 까지 같이 제거한다.         |


```java
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Person {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Phone> phones = new ArrayList<>();

    public void addPhone(Phone phone){
        phones.add(phone);
        phone.setPerson(this);
    }
}
```

```java
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Phone {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "PERSON_ID")
    public Person person;
}
```