package com.example.smartwardrobe.controller;


import com.example.smartwardrobe.enums.CoatCategory;

import com.example.smartwardrobe.model.Coat;
import com.example.smartwardrobe.service.CoatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/coat")
public class CoatController {

    @Autowired
    private CoatService coatService;

    @GetMapping
    public List<Coat> getAllCoats(){
        return coatService.findAllCoats();
    }

    @GetMapping("/{id}")
    public Coat getCoatById(@PathVariable("id") Long id){
        return coatService.findCoatById(id);
    }

    @PostMapping
    public Coat saveCoat(@RequestBody Coat coat){
        return coatService.saveCoat(coat);
    }

    @DeleteMapping("/{id}")
    public void deleteCoatById(@PathVariable("id") Long id){
        coatService.deleteCoatById(id);
    }

    @GetMapping("/getByStyle/{styleName}")
    public ResponseEntity<?> getItemByStyle(@PathVariable("styleName") String styleName){
        List<Coat> coat = coatService.getCoatsByStyleName(styleName);
        if(coat.size() != 0){
            return ResponseEntity.ok(coat);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Style or items not found!");
        }
    }

    @PatchMapping ("/washing/{itemId}")
    public String washItem(@PathVariable("itemId") String itemId){
        return coatService.washItem(itemId);
    }
    @PostMapping("/getAllStore")
    public void readAllCoatsFromStore(){
        coatService.readAllCoatsFromStore();
    }
    @GetMapping("/getAllCoatsByCategory/{category}")
    public ResponseEntity<?>  readAllCoatsByCategoryFromStore(@PathVariable("category") String coatCategory){
        List<Coat> coats = coatService.readAllCoatsByCategoryFromStore(CoatCategory.valueOf(coatCategory));
        if(coats.size() != 0){
            return ResponseEntity.ok(coats);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No items in this category!");
        }
    }
}
