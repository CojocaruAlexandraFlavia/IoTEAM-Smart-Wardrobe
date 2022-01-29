package com.example.smartwardrobe.service;

import com.example.smartwardrobe.enums.ItemCategory;
import com.example.smartwardrobe.enums.Style;
import com.example.smartwardrobe.model.Item;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.data.util.Pair;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;

public interface ItemService {

    Item saveItem(Item item);
    Item findItemById(Long id);
    List<Item> findItemsByCategory(ItemCategory itemCategory);
    List<Item> findItemIfDirty();
    Pair<List<Item>, Set<JSONObject>> getDirtyItemsByColor(String color) throws FileNotFoundException;
    void deleteItemById(Long id);
    List<Item> findAllItems();
    List<Item> getItemsByStyleName(String styleName);
    String washItem(String itemId);
    void updateItemAfterAddingOutfit(Long itemId);
    JSONArray createJsonArrayOfItems(List<Item> items);

}
