package com.example.smartwardrobe.service;

import com.example.smartwardrobe.enums.ItemCategory;
import com.example.smartwardrobe.enums.Style;
import com.example.smartwardrobe.model.Item;

import java.util.List;

public interface ItemService {

    Item saveItem(Item item);
    Item findItemById(Long id);
    Item findItemByCategory(ItemCategory itemCategory);
    Item findItemByStyle(Style style);
    void deleteItemById(Long id);
    List<Item> findAllItems();

}
