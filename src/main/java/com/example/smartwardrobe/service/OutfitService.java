package com.example.smartwardrobe.service;

import com.example.smartwardrobe.model.Item;
import com.example.smartwardrobe.model.Outfit;

import java.util.List;
import java.util.Optional;

public interface OutfitService{

    Outfit saveOutfit( Outfit outfit);
    void deleteOutfitById(Long id);
    void deleteOutfit(Outfit outfit);
    Outfit findOutfitById(Long id);
    Outfit findOutfitByItems(List<Item> items);
    List<Outfit> findAllOutfits();

}
