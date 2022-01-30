package com.example.smartwardrobe.controller;

import com.example.smartwardrobe.model.Outfit;
import com.example.smartwardrobe.model.dto.OutfitDto;
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
    public Outfit saveOutfit(@RequestBody OutfitDto outfitDto){
        return outfitService.saveOutfit(outfitService.convertDtoToEntity(outfitDto));
    }

    @DeleteMapping("/{id}")
    public void deleteOutfitById(@PathVariable("id") Long id){
        outfitService.deleteOutfitById(id);
    }

    @PatchMapping("/{outfitId}/{nrOfStars}")
    public Outfit rateOutfit(@PathVariable("outfitId") String outfitId, @PathVariable("nrOfStars") String nrOfStars){
        return outfitService.giveStarsToOutfit(outfitId, Integer.parseInt(nrOfStars));
    }

}
