package com.example.smartwardrobe.controller;


import com.example.smartwardrobe.enums.CoatCategory;
import com.example.smartwardrobe.model.Coat;
import com.example.smartwardrobe.model.dto.CoatDto;
import com.example.smartwardrobe.service.CoatService;
import com.example.smartwardrobe.service.OutfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/coat")
public class CoatController {

    @Autowired
    private CoatService coatService;

    @Autowired
    private OutfitService outfitService;

    @PatchMapping("/washing/{itemId}")
    public String washItem(@PathVariable("itemId") String itemId) {
        return coatService.washItem(itemId);
    }

    @PostMapping("/getAllStore")
    public void readAllCoatsFromStore() {
        coatService.readAllCoatsFromStore();
    }

    @GetMapping("/getAllCoatsByCategory/{category}")
    public ResponseEntity<Object> readAllCoatsByCategoryFromStore(@PathVariable("category") String coatCategory) {
        List<Coat> coats = coatService.readAllCoatsByCategoryFromStore(CoatCategory.valueOf(coatCategory));
        if (!coats.isEmpty()) {
            return ResponseEntity.ok(coats);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No items in this category!");
        }
    }
}