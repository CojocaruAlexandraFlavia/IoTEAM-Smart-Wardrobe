package com.example.smartwardrobe.controller;

import com.example.smartwardrobe.model.Item;
import com.example.smartwardrobe.model.dto.ItemDto;
import com.example.smartwardrobe.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<Item> getDirtyItemsByColor(@PathVariable("color") String color){
        return itemService.getDirtyItems(color);
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

}
