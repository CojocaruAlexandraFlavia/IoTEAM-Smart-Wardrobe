package com.example.smartwardrobe.service;

import com.example.smartwardrobe.model.Outfit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

public interface OutfitService{

    Outfit saveOutfit( Outfit outfit);
    void deleteOutfitById(Long id);
    void deleteOutfit(Outfit outfit);
    Outfit findOutfitById(Long id);
    List<Outfit> findAllOutfits();
    void writeOutfitToFile(Outfit outfit);
    JSONArray getOutfitsFromFile();
}
