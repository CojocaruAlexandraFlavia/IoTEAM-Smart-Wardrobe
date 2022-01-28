package com.example.smartwardrobe.service;


import com.example.smartwardrobe.enums.CoatCategory;
import com.example.smartwardrobe.model.Coat;

import org.json.simple.JSONArray;

import java.util.List;

public interface CoatService {
    Coat saveCoat(Coat coat);
    Coat findCoatById(Long id);
    List<Coat> findCoatByCategory(CoatCategory coatCategory);
    void deleteCoatById(Long id);
    List<Coat> findAllCoats();
    List<Coat> getCoatsByStyleName(String styleName);
    String washItem(String coatId);
    void updateCoatAfterAddingOutfit(Long coatId);
//    JSONArray createJsonArrayOfItems(List<Coat> coats);
    void readAllCoatsFromStore();
    List<Coat> readAllCoatsByCategoryFromStore(CoatCategory coatCategory);
}
