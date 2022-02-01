package com.example.smartwardrobe.service.impl;

import com.example.smartwardrobe.enums.*;
import com.example.smartwardrobe.model.Item;
import com.example.smartwardrobe.model.dto.ItemDto;
import com.example.smartwardrobe.repository.ItemRepository;
import com.example.smartwardrobe.service.ItemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.EnumUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    public List<Item> getItemsByStyleName(String styleName) {
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
    public List<Item> getDirtyItems(String color)
    {
        List<Item> whiteDirtyItems = new ArrayList<>();
        List<Item> blackDirtyItems = new ArrayList<>();
        List<Item> colorDirtyItems = new ArrayList<>();
        List<Item> dirtyItems = findItemIfDirty();
        for (Item i: dirtyItems) {
            if(i.getWashingZoneColor().name().equals("WHITE"))
            {
                whiteDirtyItems.add(i);
            }
            if(i.getWashingZoneColor().name().equals("BLACK"))
            {
                blackDirtyItems.add(i);
            }
            if(i.getWashingZoneColor().name().equals("COLOR")){
                colorDirtyItems.add(i);
            }
        }

        if(color.equalsIgnoreCase("white"))
        {
            return whiteDirtyItems;
        }
        if (color.equalsIgnoreCase("black"))
        {
            return blackDirtyItems;
        }

        else
            return colorDirtyItems;

    }

    @Override
    public JSONArray createJsonArrayOfItems(List<Item> items) {
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
    public Item convertDtoToEntity(ItemDto itemDto) {
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
}
