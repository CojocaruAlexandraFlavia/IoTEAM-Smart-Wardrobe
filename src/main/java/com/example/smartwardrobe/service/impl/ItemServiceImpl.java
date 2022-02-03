package com.example.smartwardrobe.service.impl;

import com.example.smartwardrobe.enums.*;
import com.example.smartwardrobe.model.Coat;
import com.example.smartwardrobe.model.Item;
import com.example.smartwardrobe.model.User;
import com.example.smartwardrobe.model.dto.ItemDto;
import com.example.smartwardrobe.repository.ItemRepository;
import com.example.smartwardrobe.service.ItemService;
import com.example.smartwardrobe.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.EnumUtils;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    UserService userService;

    @Override
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Item findItemById(Long id) {
        return itemRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Item> findItemsByCategory(ItemCategory itemCategory) {
        return itemRepository.findByItemCategory(itemCategory);
    }
    @Override
    public List<Item> findItemIfDirty()
    {
        return itemRepository.findItemIfDirty();
    }

    @Override
    public void deleteItemById(Long id) {
        itemRepository.deleteById(id);
    }

    @Override
    public List<Item> findAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public List<Item> getItemsByStyleName(@NotNull String styleName) {
        if (!EnumUtils.isValidEnum(Style.class, styleName.toUpperCase()) ){
            return new ArrayList<>();
        }
        return itemRepository.findByStyle(Style.valueOf(styleName.toUpperCase()));
    }

    @Override
    public Item washItem(String itemId) {
        Item item = findItemById(Long.valueOf(itemId));
        item.setLastWashingDay(LocalDate.now());
        item.setNrOfWearsSinceLastWash(0);
        return saveItem(item);
    }

    @Override
    public Item updateItemAfterAddingOutfit(Long itemId) {
        Item item = findItemById(itemId);
        item.setNrOfWearsSinceLastWash(item.getNrOfWearsSinceLastWash() + 1);
        item.setLastWearing(LocalDate.now());
        return saveItem(item);
    }

    @Override
    public Pair<List<Item>, Set<JSONObject>> getDirtyItemsByColor(String color) {
        List<Item> specificDirtyItems = new ArrayList<>();
        List<Item> allDirtyItems = findItemIfDirty();
        Set<JSONObject> instructions  = new HashSet<>();
        JSONParser jsonParser = new JSONParser();
        JSONObject da = null;
        try(FileReader reader = new FileReader("src/main/java/com/example/smartwardrobe/json/wash_instructions.json"))
        {
            Object obj = jsonParser.parse(reader);
            JSONObject d = (JSONObject) obj;

            for (Item i: allDirtyItems) {
                if(i.getWashingZoneColor().name().equalsIgnoreCase(color))
                {
                    specificDirtyItems.add(i);
                    String specificInstr = i.getMaterial().name();
                    da = (JSONObject) d.get(specificInstr);
                    instructions.add(da);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return Pair.of(specificDirtyItems, instructions);

    }

    @Override
    public JSONArray createJsonArrayOfItems(@NotNull List<Item> items) {
        JSONArray jsonArray = new JSONArray();
        for(Item item: items){
            JSONObject object = createJsonObjectFromItem(item);
            jsonArray.add(object);
        }
        return jsonArray;
    }

    @Override
    public JSONObject createJsonObjectFromItem(Item item) {
        JSONParser jsonParser = new JSONParser();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return (JSONObject) jsonParser.parse(objectMapper.writeValueAsString(item));
        } catch (ParseException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    @Override
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Override
    public Item convertDtoToEntity(@NotNull ItemDto itemDto) {
        Item item = new Item();
        if(itemDto.getId() != 0){
            item.setId(itemDto.getId());
        }
        if(!EnumUtils.isValidEnumIgnoreCase(Size.class, itemDto.getSize())){
            item.setSize(null);
        }else{
            item.setSize(Size.valueOf(itemDto.getSize().toUpperCase()));
        }
        if(!EnumUtils.isValidEnumIgnoreCase(ItemColor.class, itemDto.getItemColor())){
            item.setItemColor(null);
        }else{
            item.setItemColor(ItemColor.valueOf(itemDto.getItemColor().toUpperCase()));
        }
        if(!EnumUtils.isValidEnumIgnoreCase(ItemCategory.class, itemDto.getItemCategory())){
            item.setItemCategory(null);
        }else{
            item.setItemCategory(ItemCategory.valueOf(itemDto.getItemCategory().toUpperCase()));
        }
        if(!EnumUtils.isValidEnumIgnoreCase(Material.class, itemDto.getMaterial())){
            item.setMaterial(null);
        }else{
            item.setMaterial(Material.valueOf(itemDto.getMaterial().toUpperCase()));
        }
        item.setCode(itemDto.getCode());
        if(!EnumUtils.isValidEnumIgnoreCase(Style.class, itemDto.getStyle())){
            item.setStyle(null);
        }else{
            item.setStyle(Style.valueOf(itemDto.getStyle().toUpperCase()));
        }
        if(!EnumUtils.isValidEnumIgnoreCase(WashingZoneColor.class, itemDto.getWashingZoneColor())){
            item.setWashingZoneColor(null);
        }else{
            item.setWashingZoneColor(WashingZoneColor.valueOf(itemDto.getWashingZoneColor().toUpperCase()));
        }
        item.setNrOfWearsSinceLastWash(itemDto.getNrOfWearsSinceLastWash());
        try{
            item.setLastWearing(LocalDate.parse(itemDto.getLastWearing()));
        }catch (DateTimeParseException e){
            throw new IllegalArgumentException();
        }
        try{
            item.setLastWashingDay(LocalDate.parse(itemDto.getLastWashingDay()));
        }catch (DateTimeParseException e){
            throw new IllegalArgumentException();
        }

        return item;
    }

    @Override
    public void readAllItemsFromStore(){
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("src/main/java/com/example/smartwardrobe/json/store.json"))
        {
            JSONObject obj = (JSONObject) jsonParser.parse(reader);

            JSONArray itemList = (JSONArray) obj.get("items");
            for(int i = 0;i<itemList.toArray().length;i++)
            {
                JSONObject jsonItem = (JSONObject) itemList.get(i);
                Item item = (Item)convertJsonObjectToItemOrCoat(jsonItem, 1);
                saveItem(item);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Item> readAllItemsByCategoryFromStore(ItemCategory itemCategory){
        List<Item> items = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("src/main/java/com/example/smartwardrobe/json/store.json"))
        {
            //Read JSON file
            JSONObject obj = (JSONObject) jsonParser.parse(reader);
            JSONArray itemList = (JSONArray) obj.get("items");
            for(int i = 0;i<itemList.toArray().length;i++)
            {
                JSONObject jsonItem = (JSONObject) itemList.get(i);
                Item item = (Item)convertJsonObjectToItemOrCoat(jsonItem, 1);
                if(item.getItemCategory() == itemCategory){
                    items.add(item);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return items;
    }

    @Override
    public List<Item> sortItemsByLastWearingDate(){
        List<Item> items = findAllItems();
        items.sort(new Item.SortByDate());
        return items;
    }

    public List<Item> updateWardrobe(String userId){
        User user = userService.findUserById(Long.valueOf(userId));
        List<Item> itemsToReturn = new ArrayList<>();
        List<Item> allItems = findAllItems();
        for (Item item : allItems) {
            if (!item.getSize().toString().equals(userService.calculateUserSize(user).name())) {
                itemsToReturn.add(item);
            }
        }
        return itemsToReturn;
    }

    @Override
    public Object convertJsonObjectToItemOrCoat(JSONObject jsonItem, int option){
        if(option == 1){
            Item item = new Item();
            item.setMaterial(Material.valueOf((String) jsonItem.get("material")));
            item.setSize(Size.valueOf((String) jsonItem.get("size")));
            item.setCode((String) jsonItem.get("code"));
            item.setItemColor(ItemColor.valueOf((String) jsonItem.get("itemColor")));
            item.setStyle(Style.valueOf((String)  jsonItem.get("style")));
            item.setItemCategory(ItemCategory.valueOf((String)  jsonItem.get("itemCategory")));
            item.setWashingZoneColor(WashingZoneColor.valueOf((String) jsonItem.get("washingZoneColor")));
            return item;
        }
        Coat coat = new Coat();
        coat.setMaterial(Material.valueOf((String) jsonItem.get("material")));
        coat.setSize(Size.valueOf((String) jsonItem.get("size")));
        coat.setCode((String) jsonItem.get("code"));
        coat.setItemColor(ItemColor.valueOf((String) jsonItem.get("itemColor")));
        coat.setStyle(Style.valueOf((String)  jsonItem.get("style")));
        coat.setCoatCategory(CoatCategory.valueOf((String)  jsonItem.get("coatCategory")));
        coat.setWashingZoneColor(WashingZoneColor.valueOf((String) jsonItem.get("washingZoneColor")));
        return coat;
    }


}
