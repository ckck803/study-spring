# JPA - 연관관계 OneToMany


```java
@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseEntity {

    @CreatedDate
    @Column(name = "CREATEDDATE", updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "LASTMODIFIEDDATE")
    private LocalDateTime lastModifiedDate;
}
```

```java
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;

    private int price;
}
```

```java
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Shop extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "shop")
    private List<ShopItem> shopItems;
}
```

```java
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ShopItem extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "SHOP_ID")
    private Shop shop;
}
```