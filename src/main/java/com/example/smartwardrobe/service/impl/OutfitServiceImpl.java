package com.example.smartwardrobe.service.impl;

import com.example.smartwardrobe.enums.CoatCategory;
import com.example.smartwardrobe.model.Coat;
import com.example.smartwardrobe.model.History;
import com.example.smartwardrobe.model.Item;
import com.example.smartwardrobe.model.Outfit;
import com.example.smartwardrobe.model.dto.CoatDto;
import com.example.smartwardrobe.model.dto.OutfitDto;
import com.example.smartwardrobe.repository.OutfitRepository;
import com.example.smartwardrobe.service.HistoryService;
import com.example.smartwardrobe.service.ItemService;
import com.example.smartwardrobe.service.OutfitService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
        history.setDateTime(LocalDate.now());
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
        JSONObject jsonObject = transformOutfitToJsonObject(outfit);
        jsonArray.add(jsonObject);
        try {
            try (FileWriter file = new FileWriter("src/main/java/com/example/smartwardrobe/json/outfits.json")) {
                file.write(jsonArray.toJSONString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONArray getOutfitsFromFile() {
        JSONParser parser = new JSONParser();
        try{
            return (JSONArray) parser.parse(new FileReader("src/main/java/com/example/smartwardrobe/json/outfits.json"));
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return new JSONArray();
    }

    @Override
    public JSONObject transformOutfitToJsonObject(@NotNull Outfit outfit) {
        JSONParser jsonParser = new JSONParser();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return (JSONObject) jsonParser.parse(objectMapper.writeValueAsString(outfit));
        } catch (ParseException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    @Override
    public JSONObject transformCoatToJsonObject(Coat coat) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", coat.getId());
        jsonObject.put("coatCategory", coat.getCoatCategory());
        return jsonObject;
    }

    @Override
    public Outfit giveStarsToOutfit(String outfitId, int nrOfStars) {
        Outfit outfit = findOutfitById(Long.valueOf(outfitId));
        outfit.setNrOfStars(outfit.getNrOfStars() + nrOfStars);
        outfit.setNrOfReviews(outfit.getNrOfReviews() + 1);
        outfit.setRating((double) outfit.getNrOfStars() / (double) outfit.getNrOfReviews());
        saveOutfit(outfit);
        return outfit;
    }

    @Override
    public List<Outfit> findOutfitsWithBestRating(double minimRating) {
        return outfitRepository.findByRatingGreaterThanEqual(minimRating, Sort.by(Sort.Direction.DESC, "rating"));
    }

    @Override
    public Outfit convertDtoToEntity(OutfitDto outfitDto) {
        Outfit outfit = new Outfit();
        outfit.setCoat(convertCoatDtoToEntity(outfitDto.getCoatDto()));
        outfit.setItems(outfitDto.getItemDtoList().stream().map(itemDto -> itemService.convertDtoToEntity(itemDto)).collect(Collectors.toList()));
        outfit.setDescription(outfitDto.getDescription());
        return outfit;
    }

    @Override
    public Coat convertCoatDtoToEntity(CoatDto coatDto) {
        Coat coat = new Coat();
        coat.setCoatCategory(CoatCategory.valueOf(coatDto.getCoatCategory()));
        coat.setId(Long.valueOf(coatDto.getId()));
        return coat;
    }


}
