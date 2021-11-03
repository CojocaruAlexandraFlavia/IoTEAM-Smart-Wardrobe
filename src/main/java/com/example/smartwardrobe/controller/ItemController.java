package com.example.smartwardrobe.controller;

import com.example.smartwardrobe.model.Item;
import com.example.smartwardrobe.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/{id}")
    public Item getItemById(@PathVariable("id") Long id){
        return itemService.findItemById(id);
    }

    @PostMapping
    public Item saveItem(@RequestBody Item item){
        return itemService.saveItem(item);
    }

    @DeleteMapping("{id}")
    public void deleteItemById(@PathVariable("id") Long id){
        itemService.deleteItemById(id);
    }

}
