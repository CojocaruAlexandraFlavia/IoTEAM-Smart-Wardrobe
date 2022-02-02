package com.example.smartwardrobe.controller;

import com.example.smartwardrobe.enums.ItemCategory;
import com.example.smartwardrobe.model.Item;
import com.example.smartwardrobe.model.dto.ItemDto;
import com.example.smartwardrobe.service.ItemService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public List<Item> getAllItems(){
        return itemService.findAllItems();
    }

    @GetMapping("/findDirty")
    public List<Item> getDirtyItems(){
        return itemService.findItemIfDirty();
    }

    @GetMapping("/findDirtyByColor/{color}")
    public ResponseEntity<?> getDirtyItemsByColor(@PathVariable("color") String color) throws FileNotFoundException {
        Pair<List<Item>, Set<JSONObject>> item = itemService.getDirtyItemsByColor(color);
        if(color.equalsIgnoreCase("WHITE") || color.equalsIgnoreCase("BLACK") ||
                color.equalsIgnoreCase("COLOR")){
            return ResponseEntity.ok(item);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("WASH COLOR DOESN'T EXIST!");
        }

    }

    @GetMapping("/{id}")
    public Item getItemById(@PathVariable("id") Long id){
        return itemService.findItemById(id);
    }

    @PostMapping
    public Item saveItem(@RequestBody ItemDto itemDto){
        return itemService.saveItem(itemService.convertDtoToEntity(itemDto));
    }

    @DeleteMapping("/{id}")
    public void deleteItemById(@PathVariable("id") Long id){
        itemService.deleteItemById(id);
    }

    @GetMapping("/getByStyle/{styleName}")
    public ResponseEntity<Object> getItemByStyle(@PathVariable("styleName") String styleName){
        List<Item> item = itemService.getItemsByStyleName(styleName);
        if(!item.isEmpty()){
            return ResponseEntity.ok(item);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Style or items not found!");
        }
    }

    @PatchMapping ("/washing/{itemId}")
    public Item washItem(@PathVariable("itemId") String itemId){
        return itemService.washItem(itemId);
    }

    @GetMapping("/getAllStore")
    public void readAllItemsFromStore(){
         itemService.readAllItemsFromStore();
    }

    @GetMapping("/getAllItemsByCategory/{category}")
    public ResponseEntity<?>  readAllItemsByCategoryFromStore(@PathVariable("category") String itemCategory){
        List<Item> items = itemService.readAllItemsByCategoryFromStore(ItemCategory.valueOf(itemCategory));
        if(items.size() != 0){
            return ResponseEntity.ok(items);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No items in this category!");
        }
    }

    @GetMapping("/sortByLastWearingDate")
    public ResponseEntity<?> sortItemsByLastWearingDate(){
        List<Item> items = itemService.sortItemsByLastWearingDate();
        if(items.size() != 0){
            return ResponseEntity.ok(items);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Items not found for sorting!");
        }
    }

    @PostMapping("/updateWardrobe/{userId}")
    public List<Item> updateUserDetails(@PathVariable("userId") String userId){
        return itemService.updateWardrobe(userId);
    }
}
