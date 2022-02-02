package com.example.smartwardrobe.service.impl;

import com.example.smartwardrobe.enums.*;
import com.example.smartwardrobe.model.Item;
import com.example.smartwardrobe.model.dto.ItemDto;
import com.example.smartwardrobe.repository.ItemRepository;
import com.example.smartwardrobe.service.ItemService;
import org.springframework.data.util.Pair;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.EnumUtils;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemRepository itemRepository;

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
        item.setSize(Size.valueOf(itemDto.getSize()));
        item.setItemColor(ItemColor.valueOf(itemDto.getItemColor()));
        item.setItemCategory(ItemCategory.valueOf(itemDto.getItemCategory()));
        item.setMaterial(Material.valueOf(itemDto.getMaterial()));
        item.setCode(itemDto.getCode());
        item.setStyle(Style.valueOf(itemDto.getStyle()));
        item.setWashingZoneColor(WashingZoneColor.valueOf(itemDto.getWashingZoneColor()));
        item.setNrOfWearsSinceLastWash(itemDto.getNrOfWearsSinceLastWash());
        item.setLastWearing(LocalDate.parse(itemDto.getLastWearing()));
        item.setLastWashingDay(LocalDate.parse(itemDto.getLastWashingDay()));
        return item;
    }
    @Override
    public void readAllItemsFromStore(){
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("src/main/java/com/example/smartwardrobe/json/store.json"))
        {
            //Read JSON file
            JSONObject obj = (JSONObject) jsonParser.parse(reader);

            JSONArray itemList = (JSONArray) obj.get("items");
            for(int i = 0;i<itemList.toArray().length;i++)
            {
                JSONObject jsonItem = (JSONObject) itemList.get(i);
//                System.out.println(item);
                Item item = new Item();
                item.setMaterial(Material.valueOf((String) jsonItem.get("material")));
                item.setSize(Size.valueOf((String) jsonItem.get("size")));
                item.setCode((String) jsonItem.get("code"));
                item.setItemColor(ItemColor.valueOf((String) jsonItem.get("itemColor")));
                item.setStyle(Style.valueOf((String)  jsonItem.get("style")));
                item.setItemCategory(ItemCategory.valueOf((String)  jsonItem.get("itemCategory")));
                item.setWashingZoneColor(WashingZoneColor.valueOf((String) jsonItem.get("washingZoneColor")));
                saveItem(item);
            }

            System.out.println(itemList);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Item> readAllItemsByCategoryFromStore(ItemCategory itemCategory){
        List<Item> items = new ArrayList<Item>();
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("src/main/java/com/example/smartwardrobe/json/store.json"))
        {
            //Read JSON file
            JSONObject obj = (JSONObject) jsonParser.parse(reader);

            JSONArray itemList = (JSONArray) obj.get("items");
            for(int i = 0;i<itemList.toArray().length;i++)
            {
                JSONObject jsonItem = (JSONObject) itemList.get(i);
//                System.out.println(item);
                Item item = new Item();
                item.setMaterial(Material.valueOf((String) jsonItem.get("material")));
                item.setSize(Size.valueOf((String) jsonItem.get("size")));
                item.setCode((String) jsonItem.get("code"));
                item.setItemColor(ItemColor.valueOf((String) jsonItem.get("itemColor")));
                item.setStyle(Style.valueOf((String)  jsonItem.get("style")));
                item.setItemCategory(ItemCategory.valueOf((String)  jsonItem.get("itemCategory")));
                item.setWashingZoneColor(WashingZoneColor.valueOf((String) jsonItem.get("washingZoneColor")));

                if(item.getItemCategory() == itemCategory){
                    items.add(item);
                }
            }
            System.out.println(itemList);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return items;
    }

    @Override
    public List<Item> sortItemsByLastWearingDate(){
        List<Item> items = findAllItems();
        //sort ASC by lastWearing date
        Collections.sort(items, new Item.SortByDate() );
        System.out.println(items);

        return items;
    }

}
