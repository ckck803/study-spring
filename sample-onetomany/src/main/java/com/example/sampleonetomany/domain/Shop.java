package com.example.sampleonetomany.domain;

import com.example.sampleonetomany.domain.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class Shop extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List<ShopItem> shopItems = new ArrayList<>();

    public void addShopItem(ShopItem shopItem) {
        shopItem.setShop(this);
        this.shopItems.add(shopItem);
    }

    public void addShopItems(List<ShopItem> shopItems){
        shopItems.forEach((item) -> {
            item.setShop(this);
            this.shopItems.add(item);
        });
    }
}
