package com.example.smartwardrobe.service.impl;

import com.example.smartwardrobe.model.History;
import com.example.smartwardrobe.model.Item;
import com.example.smartwardrobe.model.Outfit;
import com.example.smartwardrobe.repository.ItemRepository;
import com.example.smartwardrobe.repository.OutfitRepository;
import com.example.smartwardrobe.service.HistoryService;
import com.example.smartwardrobe.service.ItemService;
import com.example.smartwardrobe.service.OutfitService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OutfitServiceImpl implements OutfitService {

    @Autowired
    private OutfitRepository outfitRepository;

    @Autowired
    private ItemService itemService;

    @Autowired
    private HistoryService historyService;

    @Override
    public Outfit saveOutfit(Outfit outfit) {
        List<Item> outfitItems = outfit.getItems();
        for(Item item: outfitItems){
            itemService.updateItemAfterAddingOutfit(item.getId());
        }

        Outfit savedOutfit = outfitRepository.save(outfit);

        History history = new History();
        history.setDateTime(LocalDateTime.now());
        history.setOutfit(savedOutfit);
        historyService.saveHistory(history);

        writeOutfitToFile(savedOutfit);

        return savedOutfit;
    }

    @Override
    public void deleteOutfitById(Long id) {
        outfitRepository.deleteById(id);
    }

    @Override
    public void deleteOutfit(Outfit outfit) {
        outfitRepository.delete(outfit);
    }

    @Override
    public Outfit findOutfitById(Long id) {
        return outfitRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Outfit> findAllOutfits() {
        return outfitRepository.findAll();
    }

    @Override
    public void writeOutfitToFile(Outfit outfit) {
        JSONArray jsonArray = getOutfitsFromFile();
        if(jsonArray.size() == 7){
            jsonArray.remove(0);
            for(int i = 0; i < jsonArray.size() - 1; i++){
                jsonArray.set(i, jsonArray.get(i + 1));
            }
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", outfit.getId().toString());
        jsonObject.put("description", outfit.getDescription());
        jsonObject.put("coat", outfit.getCoat().toString());
        jsonObject.put("items", itemService.createJsonArrayOfItems(outfit.getItems()));
        jsonArray.add(jsonObject);
        try {
            FileWriter file = new FileWriter("src/main/java/com/example/smartwardrobe/json/outfits.json");
            file.write(jsonArray.toJSONString());
            file.close();
            //Files.write(Paths.get("src/main/java/com/example/smartwardrobe/json/outfits.json"),jsonObject.toJSONString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public JSONArray getOutfitsFromFile() {
        JSONParser parser = new JSONParser();
        try{
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("src/main/java/com/example/smartwardrobe/json/outfits.json")); ;
            return jsonArray;
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
