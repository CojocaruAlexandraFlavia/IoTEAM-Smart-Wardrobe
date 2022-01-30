package com.example.smartwardrobe.service;

import com.example.smartwardrobe.enums.ItemCategory;
import com.example.smartwardrobe.enums.Style;
import com.example.smartwardrobe.model.Item;
import com.example.smartwardrobe.model.dto.ItemDto;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

public interface ItemService {

    Item saveItem(Item item);
    Item findItemById(Long id);
    List<Item> findItemsByCategory(ItemCategory itemCategory);
    List<Item> findItemIfDirty();
    List<Item> getDirtyItems(String color);
    void deleteItemById(Long id);
    List<Item> findAllItems();
    List<Item> getItemsByStyleName(String styleName);
    Item washItem(String itemId);
    Item updateItemAfterAddingOutfit(Long itemId);
    JSONArray createJsonArrayOfItems(List<Item> items);
    JSONObject createJsonObjectFromItem(Item item);
    List<Item> findAll();
    Item convertDtoToEntity(ItemDto itemDto);
}
