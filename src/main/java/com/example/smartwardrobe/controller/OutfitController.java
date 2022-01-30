package com.example.smartwardrobe.controller;

import com.example.smartwardrobe.model.Outfit;
import com.example.smartwardrobe.service.OutfitService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Outfit> recommendMonochromaticOutfit(){
        try {
            return outfitService.recommendMonochromaticOutfit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @PostMapping("/recommendAnalogousOutfit")
    public List<Outfit> recommendAnalogousOutfit(){
        try {
            return outfitService.recommendAnalogousOutfit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @PostMapping("/recommendPastelOutfit")
    public List<Outfit> recommendPastelOutfit(){
        try {
            return outfitService.recommendPastelOutfit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @PostMapping("/selectRecommendedOutfit/{id}")
    public void selectRecommendedOutfit(@PathVariable("id") Integer id){
        outfitService.selectRecommendedOutfit(id);
    }

}
