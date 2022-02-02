
package com.example.smartwardrobe.service;

import com.example.smartwardrobe.model.Coat;
import com.example.smartwardrobe.model.Outfit;
import com.example.smartwardrobe.model.dto.CoatDto;
import com.example.smartwardrobe.model.dto.OutfitDto;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

public interface OutfitService{

    Outfit saveOutfit( Outfit outfit);
    void deleteOutfitById(Long id);
    Outfit findOutfitById(Long id);
    List<Outfit> findAllOutfits();
    void writeOutfitToFile(Outfit outfit);
    JSONArray getOutfitsFromFile();
    JSONObject transformOutfitToJsonObject(Outfit outfit);
    JSONObject transformCoatToJsonObject(Coat coat);
    Outfit giveStarsToOutfit(String outfitId, int nrOfStars);
    List<Outfit> findOutfitsWithBestRating(double minimRating);
    Outfit convertDtoToEntity(OutfitDto outfitDto);
    Coat convertCoatDtoToEntity(CoatDto coatDto);

    List<Outfit> recommendMonochromaticOutfit() throws Exception;
    List<Outfit> recommendAnalogousOutfit() throws Exception;
    List<Outfit> recommendPastelOutfit() throws Exception;
    void selectRecommendedOutfit(Integer id);
}