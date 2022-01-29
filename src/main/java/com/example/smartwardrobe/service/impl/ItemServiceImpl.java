package com.example.smartwardrobe.service.impl;

import com.example.smartwardrobe.enums.*;
import com.example.smartwardrobe.model.Item;
import com.example.smartwardrobe.repository.ItemRepository;
import com.example.smartwardrobe.service.ItemService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.EnumUtils;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    public String washItem(String itemId) {
        Item item = findItemById(Long.valueOf(itemId));
        item.setLastWashingDay(LocalDate.now());
        item.setNrOfWearsSinceLastWash(0);
        itemRepository.save(item);
        return "The item is clean!";
    }

    @Override
    public void updateItemAfterAddingOutfit(Long itemId) {
        Item item = findItemById(itemId);
        item.setNrOfWearsSinceLastWash(item.getNrOfWearsSinceLastWash() + 1);
        item.setLastWearing(LocalDate.now());
        saveItem(item);
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
        // daca se actualizeaaza statusul in baza de date de la purtat la spalat,
        // cum arata aceste arraylists?

//        if(whiteDirtyItems.size() >= 3)
//        {
//            System.out.println("U have more than 3 white items to wash");
//        }
//        if(blackDirtyItems.size() >= 3)
//        {
//            System.out.println("U have more than 3 black items to wash");
//        }
//        if(colorDirtyItems.size() >= 3)
//        {
//            System.out.println("U have more than 3 colored items to wash");
//        }


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
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", item.getId().toString());
            jsonObject.put("material", item.getMaterial().toString());
            jsonObject.put("size", item.getSize().toString());
            jsonObject.put("code", item.getCode());
            jsonObject.put("color", item.getItemColor().toString());
            jsonObject.put("style", item.getStyle().toString());
            jsonObject.put("category", item.getItemCategory().toString());
            if(item.getLastWearing() == null){
                jsonObject.put("lastWearingDate", "null");
            }else{
                jsonObject.put("lastWearingDate", item.getLastWearing().toString());
            }
            if(item.getLastWashingDay() == null){
                jsonObject.put("lastWashingDay", "null");
            }else{
                jsonObject.put("lastWashingDay", item.getLastWashingDay().toString());
            }
            jsonObject.put("wearsSinceLastWashing", item.getNrOfWearsSinceLastWash());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
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

}
