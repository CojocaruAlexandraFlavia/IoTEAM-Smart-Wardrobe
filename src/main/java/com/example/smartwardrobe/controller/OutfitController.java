package com.example.smartwardrobe.controller;

import com.example.smartwardrobe.exceptions.ItemException;
import com.example.smartwardrobe.model.Outfit;
import com.example.smartwardrobe.service.OutfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/outfit")
public class OutfitController {

    @Autowired
    private OutfitService outfitService;

    @GetMapping
    public List<Outfit> getAllOutfits(){
        return outfitService.findAllOutfits();
    }

    @GetMapping("/{id}")
    public Outfit getOutfitById(@PathVariable("id") Long id){
        return outfitService.findOutfitById(id);
    }

    @PostMapping
    public Outfit saveOutfit(@RequestBody Outfit outfit){
        return outfitService.saveOutfit(outfit);
    }

    @DeleteMapping("/{id}")
    public void deleteOutfitById(@PathVariable("id") Long id){
        outfitService.deleteOutfitById(id);
    }
    @PostMapping("/recommendMonochromaticOutfit")
    public ResponseEntity<?> recommendMonochromaticOutfit(){
        try {
            return ResponseEntity.ok(outfitService.recommendMonochromaticOutfit());
        }
        catch (ItemException itemException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("You need to buy more " + itemException.getCode());
        }
        catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }
    @PostMapping("/recommendAnalogousOutfit")
    public ResponseEntity<?>  recommendAnalogousOutfit(){
        try {
            return ResponseEntity.ok(outfitService.recommendAnalogousOutfit());
        }
        catch (ItemException itemException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("You need to buy more " + itemException.getCode());
        }
        catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }
    @PostMapping("/recommendPastelOutfit")
    public ResponseEntity<?> recommendPastelOutfit(){
        try {
            return ResponseEntity.ok(outfitService.recommendPastelOutfit());
        }
        catch (ItemException itemException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("You need to buy more " + itemException.getCode());
        }
        catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }
    @PostMapping("/selectRecommendedOutfit/{id}")
    public void selectRecommendedOutfit(@PathVariable("id") Integer id){
        outfitService.selectRecommendedOutfit(id);
    }

}