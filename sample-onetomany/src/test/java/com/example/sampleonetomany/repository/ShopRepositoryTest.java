package com.example.sampleonetomany.repository;

import com.example.sampleonetomany.domain.Item;
import com.example.sampleonetomany.domain.Shop;
import com.example.sampleonetomany.domain.ShopItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@DataJpaTest
@SpringBootTest
@Rollback(value = false)
@Transactional
class ShopRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ShopRepository shopRepository;

    @BeforeEach void initData(){
        Item pencil = new Item("연필", 1000);
        Item pen = new Item("볼펜", 2000);
        Item note = new Item("노트", 1500);
        Item eraser = new Item("지우개", 500);
        Item book = new Item("책", 10000);
        List<Item> items = Arrays.asList(pencil, pen, note, eraser, book);

        itemRepository.saveAll(items);
    }

    @Test
    @Rollback(value = false)
    void saveShopTest() throws InterruptedException {
        List<Item> items = itemRepository.findAll();
        Shop shop = new Shop();
        List<ShopItem> shopItems = new ArrayList<>();

        items.forEach((item) -> {
            ShopItem shopItem = ShopItem.builder()
                    .item(item)
                    .shop(shop)
                    .build();
            shopItems.add(shopItem);
        });

        shop.addShopItems(shopItems);
        shopRepository.save(shop);
    }

}