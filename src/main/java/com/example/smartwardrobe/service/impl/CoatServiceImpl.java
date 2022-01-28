package com.example.smartwardrobe.service.impl;

import com.example.smartwardrobe.enums.*;
import com.example.smartwardrobe.model.Coat;
import com.example.smartwardrobe.repository.CoatRepository;
import com.example.smartwardrobe.service.CoatService;
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

    @Override
    public Coat saveCoat(Coat coat){
        return coatRepository.save(coat);
    }

    @Override
    public Coat findCoatById(Long id) {
        return coatRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Coat> findCoatByCategory(CoatCategory coatCategory) {
        return coatRepository.findByCoatCategory(coatCategory);
    }

    @Override
    public void deleteCoatById(Long id) {
        coatRepository.deleteById(id);
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
        Coat coat = findCoatById(Long.valueOf(coatId));
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
//                System.out.println(item);
                Coat coat = new Coat();
                coat.setMaterial(Material.valueOf((String) jsonItem.get("material")));
                coat.setSize(Size.valueOf((String) jsonItem.get("size")));
                coat.setCode((String) jsonItem.get("code"));
                coat.setItemColor(ItemColor.valueOf((String) jsonItem.get("itemColor")));
                coat.setStyle(Style.valueOf((String)  jsonItem.get("style")));
                coat.setCoatCategory(CoatCategory.valueOf((String)  jsonItem.get("itemCategory")));
                coat.setWashingZoneColor(WashingZoneColor.valueOf((String) jsonItem.get("washingZoneColor")));
                saveCoat(coat);
            }

            System.out.println(coatList);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Coat> readAllCoatsByCategoryFromStore(CoatCategory coatCategory){
        List<Coat> coats = new ArrayList<Coat>();
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("src/main/java/com/example/smartwardrobe/json/coatstore.json"))
        {
            //Read JSON file
            JSONObject obj = (JSONObject) jsonParser.parse(reader);

            JSONArray coatList = (JSONArray) obj.get("items");
            for(int i = 0;i<coatList.toArray().length;i++)
            {
                JSONObject jsonItem = (JSONObject) coatList.get(i);
//                System.out.println(item);
                Coat coat = new Coat();
                coat.setMaterial(Material.valueOf((String) jsonItem.get("material")));
                coat.setSize(Size.valueOf((String) jsonItem.get("size")));
                coat.setCode((String) jsonItem.get("code"));
                coat.setItemColor(ItemColor.valueOf((String) jsonItem.get("itemColor")));
                coat.setStyle(Style.valueOf((String)  jsonItem.get("style")));
                coat.setCoatCategory(CoatCategory.valueOf((String)  jsonItem.get("itemCategory")));
                coat.setWashingZoneColor(WashingZoneColor.valueOf((String) jsonItem.get("washingZoneColor")));
                saveCoat(coat);

                if(coat.getCoatCategory() == coatCategory){
                    coats.add(coat);
                }
            }
            System.out.println(coatList);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return coats;
    }
}
