package com.example.smartwardrobe.service;

import com.example.smartwardrobe.enums.ItemCategory;
import com.example.smartwardrobe.enums.Style;
import com.example.smartwardrobe.model.Item;
import org.json.simple.JSONArray;

import java.util.List;

public interface ItemService {

    Item saveItem(Item item);
    Item findItemById(Long id);
    List<Item> findItemsByCategory(ItemCategory itemCategory);
    void deleteItemById(Long id);
    List<Item> findAllItems();
    List<Item> getItemsByStyleName(String styleName);
    String washItem(String itemId);
    void updateItemAfterAddingOutfit(Long itemId);
    JSONArray createJsonArrayOfItems(List<Item> items);

}
