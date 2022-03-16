package com.example.smartwardrobe.controller;

import com.example.smartwardrobe.exceptions.ItemException;
import com.example.smartwardrobe.model.Outfit;
import com.example.smartwardrobe.model.dto.OutfitDto;
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

    private static final String NEED_TO_BUY_MORE = "You need to buy more ";

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

    @PostMapping("/recommendOutfit/{outfitType}")
    public ResponseEntity<Object> recommendOutfit(@PathVariable("outfitType") String outfitType) {
        try {
            return ResponseEntity.ok(outfitService.recommendOutfit(outfitType));
        } catch (ItemException itemException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NEED_TO_BUY_MORE + itemException.getCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/recommendMonochromaticOutfit")
    public ResponseEntity<Object> recommendMonochromaticOutfit() {
        try {
            return ResponseEntity.ok(outfitService.recommendMonochromaticOutfit());
        } catch (ItemException itemException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NEED_TO_BUY_MORE + itemException.getCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PatchMapping("/{outfitId}/{nrOfStars}")
    public Outfit rateOutfit(@PathVariable("outfitId") String outfitId, @PathVariable("nrOfStars") String nrOfStars){
        return outfitService.giveStarsToOutfit(outfitId, Integer.parseInt(nrOfStars));
    }

    @GetMapping("/getFavouriteOutfits/{minimRating}")
    public List<Outfit> getFavouriteOutfits(@PathVariable("minimRating") double minimRating){
        return outfitService.findOutfitsWithBestRating(minimRating);
    }

    @PostMapping("/recommendAnalogousOutfit")
    public ResponseEntity<Object>  recommendAnalogousOutfit(){
        try {
            return ResponseEntity.ok(outfitService.recommendAnalogousOutfit());
        }
        catch (ItemException itemException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NEED_TO_BUY_MORE + itemException.getCode());
        }
        catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    @PostMapping("/recommendPastelOutfit")
    public ResponseEntity<Object> recommendPastelOutfit(){
        try {
            return ResponseEntity.ok(outfitService.recommendPastelOutfit());
        }
        catch (ItemException itemException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NEED_TO_BUY_MORE + itemException.getCode());
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