package com.example.smartwardrobe.service;


import com.example.smartwardrobe.enums.CoatCategory;
import com.example.smartwardrobe.model.Coat;

import java.util.List;

public interface CoatService {
    Coat saveCoat(Coat coat);
    Coat findCoatById(Long id);
    List<Coat> findAllCoats();
    List<Coat> getCoatsByStyleName(String styleName);
    String washItem(String coatId);
    void updateCoatAfterAddingOutfit(Long coatId);
    void readAllCoatsFromStore();
    List<Coat> readAllCoatsByCategoryFromStore(CoatCategory coatCategory);
}