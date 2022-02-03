package com.example.smartwardrobe.service.impl;

import com.example.smartwardrobe.enums.*;
import com.example.smartwardrobe.model.Coat;
import com.example.smartwardrobe.repository.CoatRepository;
import com.example.smartwardrobe.service.CoatService;
import com.example.smartwardrobe.service.ItemService;
import org.apache.commons.lang3.EnumUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoatServiceImpl implements CoatService {

    @Autowired
    CoatRepository coatRepository;

    @Autowired
    ItemService itemService;

    @Override
    public Coat saveCoat(Coat coat){
        return coatRepository.save(coat);
    }

    @Override
    public Coat findCoatById(Long id) {
        return coatRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Coat> findAllCoats() {
        return coatRepository.findAll();
    }

    @Override
    public List<Coat> getCoatsByStyleName(String styleName) {
        if (!EnumUtils.isValidEnum(Style.class, styleName.toUpperCase()) ){
            return new ArrayList<>();
        }
        return coatRepository.findByStyle(Style.valueOf(styleName.toUpperCase()));
    }

    @Override
    public String washItem(String coatId) {
        Coat coat = findCoatById(Long.valueOf(coatId));
        coat.setLastWashingDay(LocalDate.now());
        coat.setNrOfWearsSinceLastWash(0);
        coatRepository.save(coat);
        return "The item is clean!";
    }

    @Override
    public void updateCoatAfterAddingOutfit(Long coatId) {
        Coat coat = findCoatById(coatId);
        coat.setNrOfWearsSinceLastWash(coat.getNrOfWearsSinceLastWash() + 1);
        coat.setLastWearing(LocalDate.now());
        saveCoat(coat);
    }

    @Override
    public void readAllCoatsFromStore(){
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("src/main/java/com/example/smartwardrobe/json/coatstore.json"))
        {
            //Read JSON file
            JSONObject obj = (JSONObject) jsonParser.parse(reader);
            JSONArray coatList = (JSONArray) obj.get("items");
            for(int i = 0;i<coatList.toArray().length;i++)
            {
                JSONObject jsonItem = (JSONObject) coatList.get(i);
                Coat coat = (Coat)itemService.convertJsonObjectToItemOrCoat(jsonItem, 2);
                saveCoat(coat);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Coat> readAllCoatsByCategoryFromStore(CoatCategory coatCategory){
        List<Coat> coats = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("src/main/java/com/example/smartwardrobe/json/coatstore.json"))
        {
            JSONObject obj = (JSONObject) jsonParser.parse(reader);
            JSONArray coatList = (JSONArray) obj.get("items");
            for(int i = 0;i<coatList.toArray().length;i++)
            {
                JSONObject jsonItem = (JSONObject) coatList.get(i);
                Coat coat = (Coat) itemService.convertJsonObjectToItemOrCoat(jsonItem, 2);
                saveCoat(coat);
                if(coat.getCoatCategory() == coatCategory){
                    coats.add(coat);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return coats;
    }
}