package com.example.smartwardrobe.service.impl;

import com.example.smartwardrobe.colorpalette.ColorGenerator;
import com.example.smartwardrobe.controller.WeatherController;
import com.example.smartwardrobe.enums.CoatCategory;
import com.example.smartwardrobe.enums.ItemCategory;
import com.example.smartwardrobe.enums.ItemColor;
import com.example.smartwardrobe.enums.Size;
import com.example.smartwardrobe.exceptions.ItemException;
import com.example.smartwardrobe.model.*;
import com.example.smartwardrobe.model.dto.CoatDto;
import com.example.smartwardrobe.model.dto.ItemDto;
import com.example.smartwardrobe.model.dto.OutfitDto;
import com.example.smartwardrobe.repository.OutfitRepository;
import com.example.smartwardrobe.service.*;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OutfitServiceImpl implements OutfitService {

    @Autowired
    private OutfitRepository outfitRepository;

    @Autowired
    private ItemService itemService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private CoatService coatService;

    @Autowired
    private UserService userService;

    @Override
    public Outfit saveOutfit(Outfit outfit) {
        List<Item> outfitItems = outfit.getItems();
        for (Item item : outfitItems) {
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

    private static final String FILE_NAME_OUTFITS = "src/main/java/com/example/smartwardrobe/json/outfits.json";

    @Override
    public void writeOutfitToFile(Outfit outfit) {
        JSONArray jsonArray = getOutfitsFromFile();
        if (jsonArray.size() == 7) {
            jsonArray.remove(0);
            for (int i = 0; i < jsonArray.size() - 1; i++) {
                jsonArray.set(i, jsonArray.get(i + 1));
            }
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", outfit.getId().toString());
        jsonObject.put("description", outfit.getDescription());
        if (outfit.getCoat() != null) {
            jsonObject.put("coat", outfit.getCoat().getCoatCategory().toString());
        } else {
            jsonObject.put("coat", null);
        }

        jsonObject.put("items", itemService.createJsonArrayOfItems(outfit.getItems()));
        jsonArray.add(jsonObject);
        try {
            try (FileWriter file = new FileWriter(FILE_NAME_OUTFITS)) {
                file.write(jsonArray.toJSONString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public JSONArray getOutfitsFromFile() {
        JSONParser parser = new JSONParser();
        try {
            return (JSONArray) parser.parse(new FileReader(FILE_NAME_OUTFITS));
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
        outfit.setCoat(convertCoatDtoToEntity(outfitDto.getCoat()));
        List<Item> items = new ArrayList<>();
        for(ItemDto itemDto: outfitDto.getItems()){
            items.add(itemService.convertDtoToEntity(itemDto));
        }
        outfit.setRating(outfitDto.getRating());
        outfit.setNrOfReviews(outfitDto.getNrOfReviews());
        outfit.setNrOfStars(outfitDto.getNrOfStars());
        outfit.setItems(items);
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

    @Override
    public List<Outfit> recommendOutfit(String type) throws Exception{
        int outfitID;
        List<Outfit> currentOutfits = findAllOutfits();
        outfitID = currentOutfits.size()+1;
        Double temperature = WeatherController.getTemperature();

        User user = userService.findUserById(1L);
        Size userSize = userService.calculateUserSize(user);

        ColorGenerator colorGenerator = new ColorGenerator();

        List<Outfit> outfitList = new ArrayList<>();
        List<Outfit> incompleteList = new ArrayList<>();
        Set<String> missingPieces = new HashSet<>();
        List<Coat> coats = new ArrayList<>();
        List<Item> blouses = itemService.findItemsByCategory(ItemCategory.BLOUSE);
        List<Item> shirts = itemService.findItemsByCategory(ItemCategory.valueOf("SHIRT"));
        List<Item> tshirts = itemService.findItemsByCategory(ItemCategory.valueOf("TSHIRT"));
        List<Item> jeans = itemService.findItemsByCategory(ItemCategory.valueOf("JEANS"));
        List<Item> pants = itemService.findItemsByCategory(ItemCategory.valueOf("PANTS"));
        List<Item> skirts = itemService.findItemsByCategory(ItemCategory.valueOf("SKIRT"));

        List<Item> tops = new ArrayList<>();
        tops.addAll(blouses);
        tops.addAll(shirts);

        List<Item> bottoms = new ArrayList<>();
        bottoms.addAll(jeans);
        bottoms.addAll(pants);

        if(temperature > 5.0){
            tops.addAll(tshirts);
            bottoms.addAll(skirts);
        }
        if(temperature < 18.0){
            coats = coatService.findAllCoats();
        }

        for(int i = 0; i < tops.size(); i++ ){
            Item top = tops.get(i);
            ItemColor topColor = top.getItemColor();
            ItemColor[] colors = {};
            ItemColor firstColor;
            ItemColor secondColor;
            switch(type){
                case "monochromatic":
                    colors = colorGenerator.monoChromatic(topColor);
                    break;
                case "analogous":
                    colors = colorGenerator.analogous(topColor);
                    break;
                case "pastel":
                    colors = colorGenerator.pastel(topColor);
                    break;
            }
            if(colors.length != 0){
                firstColor = colors[0];
                secondColor = colors[1];
                for(int j = 0; j < bottoms.toArray().length; j++){
                    Item bottom = bottoms.get(j);
                    if(bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor) {
                        if (bottom.getStyle() == top.getStyle() && top.getSize() == bottom.getSize() && top.getSize() == userSize) {
                            Outfit outfit = new Outfit();
                            outfit.setId((long) outfitID);
                            outfit.setDescription("OUTFIT" + outfitID);
                            outfitID += 1;
                            List<Item> outfitItems = new ArrayList<>();
                            outfitItems.add(top);
                            outfitItems.add(bottom);
                            outfit.setItems(outfitItems);
                            if (coats.size() > 0) {
                                Coat coat = new Coat();
                                for (int k = 0; k < coats.toArray().length; k++) {
                                    coat = coats.get(k);
                                    if (coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor || coat.getItemColor() == ItemColor.BLACK)
                                        if (coat.getStyle() == top.getStyle() && top.getSize() == coat.getSize() && top.getSize() == userSize) {
                                            outfit.setCoat(coat);
                                        }
                                }
                                if (outfit.getCoat() == null) {
                                    incompleteList.add(outfit);
                                    missingPieces.add("COATS");
                                }
                            }
                            if (temperature < 18.0 && outfit.getCoat() == null) {
                                incompleteList.add(outfit);
                            } else {
                                outfitList.add(outfit);
                            }

                        }
                    }
                }
            }
        }
        if(temperature > 5.0) {
            List<Item> dresses = itemService.findItemsByCategory(ItemCategory.valueOf("DRESS"));
            for (int i = 0; i < dresses.toArray().length; i++) {
                Item dress = dresses.get(i);
                if (dress.getSize() == userSize) {
                    ItemColor topColor = dress.getItemColor();
                    ItemColor[] colors = {};
                    ItemColor firstColor;
                    ItemColor secondColor;
                    switch (type) {
                        case "monochromatic":
                            colors = colorGenerator.monoChromatic(topColor);
                            break;
                        case "analogous":
                            colors = colorGenerator.analogous(topColor);
                            break;
                        case "pastel":
                            colors = colorGenerator.pastel(topColor);
                            break;
                    }
                    if (colors.length != 0) {
                        firstColor = colors[0];
                        secondColor = colors[1];
                        {
                            Outfit outfit = new Outfit();
                            outfit.setId((long) outfitID);
                            outfit.setDescription("OUTFIT" + outfitID);
                            outfitID += 1;
                            List<Item> outfitItems = new ArrayList<Item>();
                            outfitItems.add(dress);
                            outfit.setItems(outfitItems);
                            if (coats.size() > 0) {
                                Coat coat;
                                for (int k = 0; k < coats.toArray().length; k++) {
                                    coat = coats.get(k);
                                    if (coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor || coat.getItemColor() == ItemColor.BLACK)
                                        if (coat.getStyle() == dress.getStyle() && dress.getSize() == coat.getSize() && dress.getSize() == userSize) {
                                            outfit.setCoat(coat);
                                        }
                                }
                                if (outfit.getCoat() == null) {
                                    incompleteList.add(outfit);
                                    missingPieces.add("COATS");
                                }
                            }
                            if (temperature < 18.0 && outfit.getCoat() == null) {
                                incompleteList.add(outfit);
                            } else {
                                outfitList.add(outfit);
                            }
                        }
                    }
                }
            }
        }
        if (incompleteList.size() > outfitList.size()){
            String shop = "";
            for(String item: missingPieces){
                shop = shop.concat(item);
                shop = shop.concat(" ");
            }
            throw new ItemException(shop);
        }
        if(outfitList.isEmpty()){
            throw new ItemException("TOPS AND BOTTOMS");
        }
        try (FileWriter file = new FileWriter("src/main/java/com/example/smartwardrobe/json/generatedoutfits.json")) {
            String jsonStr = JSONArray.toJSONString(outfitList);
            file.write(jsonStr);
        }
        return outfitList;
    }
    @Override
    public List<Outfit> recommendMonochromaticOutfit() throws Exception {
        int outfitID;
        JSONParser parser = new JSONParser();
        try{
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(FILE_NAME_OUTFITS));
            outfitID = jsonArray.size();
        } catch (ParseException | IOException e) {
            e.printStackTrace();
            outfitID = 1;
        }

        Double temperature = WeatherController.getTemperature();

        User user = userService.findUserById(1L);
        Size userSize = userService.calculateUserSize(user);

        ColorGenerator colorGenerator = new ColorGenerator();

        List<Outfit> outfitList = new ArrayList<>();
        List<Outfit> incompleteList = new ArrayList<>();
        Set<String> missingPieces = new HashSet<>();
        List<Coat> coats = new ArrayList<>();
        List<Item> blouses = itemService.findItemsByCategory(ItemCategory.BLOUSE);
        List<Item> shirts = itemService.findItemsByCategory(ItemCategory.valueOf("SHIRT"));
        List<Item> tshirts = itemService.findItemsByCategory(ItemCategory.valueOf("TSHIRT"));
        List<Item> jeans = itemService.findItemsByCategory(ItemCategory.valueOf("JEANS"));
        List<Item> pants = itemService.findItemsByCategory(ItemCategory.valueOf("PANTS"));
        List<Item> skirts = itemService.findItemsByCategory(ItemCategory.valueOf("SKIRT"));

        List<Item> tops = new ArrayList<>();
        tops.addAll(blouses);
        tops.addAll(shirts);

        List<Item> bottoms = new ArrayList<>();
        bottoms.addAll(jeans);
        bottoms.addAll(pants);

        if(temperature > 5.0){
            tops.addAll(tshirts);
            bottoms.addAll(skirts);
        }
        if(temperature < 18.0){
            coats = coatService.findAllCoats();
        }



        for(int i = 0; i < blouses.toArray().length; i++){
            Item top = blouses.get(i);
            ItemColor topColor = top.getItemColor();
            ItemColor[] colors = colorGenerator.monoChromatic(topColor);
            ItemColor firstColor = colors[0];
            ItemColor secondColor = colors[1];
            for(int j = 0; j < jeans.toArray().length; j++){
                Item bottom = jeans.get(j);
                if(bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                    if(bottom.getStyle() == top.getStyle() && top.getSize() == bottom.getSize() && top.getSize() == userSize){
                        Outfit outfit = new Outfit();
                        outfit.setId((long) outfitID);
                        outfit.setDescription("OUTFIT"+outfitID);
                        outfitID += 1;
                        List<Item> outfitItems = new ArrayList<>();
                        outfitItems.add(top);
                        outfitItems.add(bottom);
                        outfit.setItems(outfitItems);
                        if(temperature < 18.0){
                            Coat coat = new Coat();
                            for(int k = 0; k < coats.toArray().length; k++){
                                coat = coats.get(k);
                                if(coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor || coat.getItemColor() == ItemColor.BLACK )
                                    if(coat.getStyle() == top.getStyle() && top.getSize() == coat.getSize() && top.getSize() == userSize){
                                        outfit.setCoat(coat);
                                    }
                            }
                            if(outfit.getCoat() == null){
                                incompleteList.add(outfit);
                                missingPieces.add("COATS");
                            }
                        }
                        if(temperature < 18.0 && outfit.getCoat() == null){
                            incompleteList.add(outfit);
                        }else{
                            outfitList.add(outfit);
                        }

                    }
            }
            for(int j = 0; j < pants.toArray().length; j++){
                Item bottom = pants.get(j);
                if(bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                    if(bottom.getStyle() == top.getStyle() && top.getSize() == bottom.getSize() && top.getSize() == userSize){
                        Outfit outfit = new Outfit();
                        outfit.setId((long) outfitID);
                        outfit.setDescription("OUTFIT"+outfitID);
                        outfitID += 1;
                        List<Item> outfitItems = new ArrayList<>();
                        outfitItems.add(top);
                        outfitItems.add(bottom);
                        outfit.setItems(outfitItems);
                        if(temperature < 18.0){
                            Coat coat = new Coat();
                            for(int k = 0; k < coats.toArray().length; k++){
                                coat = coats.get(k);
                                if(coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor || coat.getItemColor() == ItemColor.BLACK )
                                    if(coat.getStyle() == top.getStyle() && top.getSize() == coat.getSize() && top.getSize() == userSize){
                                        outfit.setCoat(coat);
                                    }
                            }
                            if(outfit.getCoat() == null){
                                incompleteList.add(outfit);
                                missingPieces.add("COATS");
                            }
                        }
                        if(temperature < 18.0 && outfit.getCoat() == null){
                            incompleteList.add(outfit);
                        }else{
                            outfitList.add(outfit);
                        }

                    }
            }
            if(temperature > 5.0){
                for(int j = 0; j < skirts.toArray().length; j++){
                    Item bottom = skirts.get(j);
                    if(bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                        if(bottom.getStyle() == top.getStyle() && top.getSize() == bottom.getSize() && top.getSize() == userSize){
                            Outfit outfit = new Outfit();
                            outfit.setId((long) outfitID);
                            outfit.setDescription("OUTFIT"+outfitID);
                            outfitID += 1;
                            List<Item> outfitItems = new ArrayList<>();
                            outfitItems.add(top);
                            outfitItems.add(bottom);
                            outfit.setItems(outfitItems);
                            if(temperature < 18.0){
                                Coat coat = new Coat();
                                for(int k = 0; k < coats.toArray().length; k++){
                                    coat = coats.get(k);
                                    if(coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor || coat.getItemColor() == ItemColor.BLACK )
                                        if(coat.getStyle() == top.getStyle() && top.getSize() == coat.getSize() && top.getSize() == userSize){
                                            outfit.setCoat(coat);
                                        }
                                }
                                if(outfit.getCoat() == null){
                                    incompleteList.add(outfit);
                                    missingPieces.add("COATS");
                                }
                            }
                            if(temperature < 18.0 && outfit.getCoat() == null){
                                incompleteList.add(outfit);
                            }else{
                                outfitList.add(outfit);
                                System.out.println(outfit);
                            }

                        }
                }
            }
        }
        for(int i = 0; i < shirts.toArray().length; i++) {
            Item top = shirts.get(i);
            ItemColor topColor = top.getItemColor();
            ItemColor[] colors = colorGenerator.monoChromatic(topColor);
            ItemColor firstColor = colors[0];
            ItemColor secondColor = colors[1];
            for (int j = 0; j < jeans.toArray().length; j++) {
                Item bottom = jeans.get(j);
                if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                    if (bottom.getStyle() == top.getStyle() && top.getSize() == bottom.getSize() && top.getSize() == userSize) {
                        Outfit outfit = new Outfit();
                        outfit.setId((long) outfitID);
                        outfit.setDescription("OUTFIT" + outfitID);
                        outfitID += 1;
                        List<Item> outfitItems = new ArrayList<Item>();
                        outfitItems.add(top);
                        outfitItems.add(bottom);
                        outfit.setItems(outfitItems);
                        if(temperature < 18.0){
                            Coat coat = new Coat();
                            for(int k = 0; k < coats.toArray().length; k++){
                                coat = coats.get(k);
                                if(coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor || coat.getItemColor() == ItemColor.BLACK )
                                    if(coat.getStyle() == top.getStyle() && top.getSize() == coat.getSize() && top.getSize() == userSize){
                                        outfit.setCoat(coat);
                                    }
                            }
                            if(outfit.getCoat() == null){
                                incompleteList.add(outfit);
                                missingPieces.add("COATS");
                            }
                        }
                        if(temperature < 18.0 && outfit.getCoat() == null){
                            incompleteList.add(outfit);
                        }else{
                            outfitList.add(outfit);
                        }
                    }
            }
            for (int j = 0; j < pants.toArray().length; j++) {
                Item bottom = pants.get(j);
                if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                    if (bottom.getStyle() == top.getStyle() && top.getSize() == bottom.getSize() && top.getSize() == userSize) {
                        Outfit outfit = new Outfit();
                        outfit.setId((long) outfitID);
                        outfit.setDescription("OUTFIT" + outfitID);
                        outfitID += 1;
                        List<Item> outfitItems = new ArrayList<>();
                        outfitItems.add(top);
                        outfitItems.add(bottom);
                        outfit.setItems(outfitItems);
                        if(temperature < 18.0){
                            Coat coat;
                            for(int k = 0; k < coats.toArray().length; k++){
                                coat = coats.get(k);
                                if(coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor || coat.getItemColor() == ItemColor.BLACK )
                                    if(coat.getStyle() == top.getStyle() && top.getSize() == coat.getSize() && top.getSize() == userSize){
                                        outfit.setCoat(coat);
                                    }
                            }
                            if(outfit.getCoat() == null){
                                incompleteList.add(outfit);
                                missingPieces.add("COATS");
                            }
                        }
                        if(temperature < 18.0 && outfit.getCoat() == null){
                            incompleteList.add(outfit);
                        }else{
                            outfitList.add(outfit);
                        }
//                    writeOutfitToFile(outfit);
                    }
            }
            if (temperature > 5.0) {
                for (int j = 0; j < skirts.toArray().length; j++) {
                    Item bottom = skirts.get(j);
                    if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                        if (bottom.getStyle() == top.getStyle() && top.getSize() == bottom.getSize() && top.getSize() == userSize) {
                            Outfit outfit = new Outfit();
                            outfit.setId((long) outfitID);
                            outfit.setDescription("OUTFIT" + outfitID);
                            outfitID += 1;
                            List<Item> outfitItems = new ArrayList<>();
                            outfitItems.add(top);
                            outfitItems.add(bottom);
                            outfit.setItems(outfitItems);
                            if(temperature < 18.0){
                                Coat coat;
                                for(int k = 0; k < coats.toArray().length; k++){
                                    coat = coats.get(k);
                                    if(coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor || coat.getItemColor() == ItemColor.BLACK )
                                        if(coat.getStyle() == top.getStyle() && top.getSize() == coat.getSize() && top.getSize() == userSize){
                                            outfit.setCoat(coat);
                                        }
                                }
                                if(outfit.getCoat() == null){
                                    incompleteList.add(outfit);
                                    missingPieces.add("COATS");
                                }
                            }
                            if(temperature < 18.0 && outfit.getCoat() == null){
                                incompleteList.add(outfit);
                            }else{
                                outfitList.add(outfit);
                            }
                        }
                }

            }
        }
        if(temperature > 5.0) {
            for (int i = 0; i < tshirts.toArray().length; i++) {
                Item top = tshirts.get(i);
                ItemColor topColor = top.getItemColor();
                ItemColor[] colors = colorGenerator.monoChromatic(topColor);
                ItemColor firstColor = colors[0];
                ItemColor secondColor = colors[1];
                for (int j = 0; j < jeans.toArray().length; j++) {
                    Item bottom = jeans.get(j);
                    if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                        if (bottom.getStyle() == top.getStyle() && top.getSize() == bottom.getSize() && top.getSize() == userSize) {
                            Outfit outfit = new Outfit();
                            outfit.setId((long) outfitID);
                            outfit.setDescription("OUTFIT" + outfitID);
                            outfitID += 1;
                            List<Item> outfitItems = new ArrayList<>();
                            outfitItems.add(top);
                            outfitItems.add(bottom);
                            outfit.setItems(outfitItems);
                            if(temperature < 18.0){
                                Coat coat;
                                for(int k = 0; k < coats.toArray().length; k++){
                                    coat = coats.get(k);
                                    if(coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor || coat.getItemColor() == ItemColor.BLACK )
                                        if(coat.getStyle() == top.getStyle() && top.getSize() == coat.getSize() && top.getSize() == userSize){
                                            outfit.setCoat(coat);
                                        }
                                }
                                if(outfit.getCoat() == null){
                                    incompleteList.add(outfit);
                                    missingPieces.add("COATS");
                                }
                            }
                            if(temperature < 18.0 && outfit.getCoat() == null){
                                incompleteList.add(outfit);
                            }else{
                                outfitList.add(outfit);
                            }
//                    writeOutfitToFile(outfit);
                        }
                }
                for (int j = 0; j < pants.toArray().length; j++) {
                    Item bottom = pants.get(j);
                    if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                        if (bottom.getStyle() == top.getStyle() && top.getSize() == bottom.getSize() && top.getSize() == userSize) {
                            Outfit outfit = new Outfit();
                            outfit.setId((long) outfitID);
                            outfit.setDescription("OUTFIT" + outfitID);
                            outfitID += 1;
                            List<Item> outfitItems = new ArrayList<>();
                            outfitItems.add(top);
                            outfitItems.add(bottom);
                            outfit.setItems(outfitItems);
                            if(temperature < 18.0){
                                Coat coat;
                                for(int k = 0; k < coats.toArray().length; k++){
                                    coat = coats.get(k);
                                    if(coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor || coat.getItemColor() == ItemColor.BLACK )
                                        if(coat.getStyle() == top.getStyle() && top.getSize() == coat.getSize() && top.getSize() == userSize){
                                            outfit.setCoat(coat);
                                        }
                                }
                                if(outfit.getCoat() == null){
                                    incompleteList.add(outfit);
                                    missingPieces.add("COATS");
                                }
                            }
                            if(temperature < 18.0 && outfit.getCoat() == null){
                                incompleteList.add(outfit);
                            }else{
                                outfitList.add(outfit);
                            }
                        }
                }
                for (int j = 0; j < skirts.toArray().length; j++) {
                    Item bottom = skirts.get(j);
                    if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                        if (bottom.getStyle() == top.getStyle() && top.getSize() == bottom.getSize() && top.getSize() == userSize) {
                            Outfit outfit = new Outfit();
                            outfit.setId((long) outfitID);
                            outfit.setDescription("OUTFIT" + outfitID);
                            outfitID += 1;
                            List<Item> outfitItems = new ArrayList<>();
                            outfitItems.add(top);
                            outfitItems.add(bottom);
                            outfit.setItems(outfitItems);
                            if(temperature < 18.0){
                                Coat coat;
                                for(int k = 0; k < coats.toArray().length; k++){
                                    coat = coats.get(k);
                                    if(coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor || coat.getItemColor() == ItemColor.BLACK )
                                        if(coat.getStyle() == top.getStyle() && top.getSize() == coat.getSize() && top.getSize() == userSize){
                                            outfit.setCoat(coat);
                                        }
                                }
                                if(outfit.getCoat() == null){
                                    incompleteList.add(outfit);
                                    missingPieces.add("COATS");
                                }
                            }
                            if(temperature < 18.0 && outfit.getCoat() == null){
                                incompleteList.add(outfit);
                            }else{
                                outfitList.add(outfit);
                            }
                        }
                }

            }
        }
        if(temperature > 5.0) {
            List<Item> dresses = itemService.findItemsByCategory(ItemCategory.valueOf("DRESS"));
            for (int i = 0; i < dresses.toArray().length; i++) {
                Item dress = dresses.get(i);
                if(dress.getSize() == userSize) {
                    ItemColor topColor = dress.getItemColor();
                    ItemColor[] colors = colorGenerator.monoChromatic(topColor);
                    ItemColor firstColor = colors[0];
                    ItemColor secondColor = colors[1];
                    Outfit outfit = new Outfit();
                    outfit.setId((long) outfitID);
                    outfit.setDescription("OUTFIT" + outfitID);
                    outfitID += 1;
                    List<Item> outfitItems = new ArrayList<Item>();
                    outfitItems.add(dress);
                    outfit.setItems(outfitItems);
                    if (temperature < 18.0) {
                        Coat coat;
                        for (int k = 0; k < coats.toArray().length; k++) {
                            coat = coats.get(k);
                            if (coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor || coat.getItemColor() == ItemColor.BLACK)
                                if (coat.getStyle() == dress.getStyle() && dress.getSize() == coat.getSize() && dress.getSize() == userSize) {
                                    outfit.setCoat(coat);
                                }
                        }
                        if (outfit.getCoat() == null) {
                            incompleteList.add(outfit);
                            missingPieces.add("COATS");
                        }
                    }
                    if (temperature < 18.0 && outfit.getCoat() == null) {
                        incompleteList.add(outfit);
                    } else {
                        outfitList.add(outfit);
                    }
                }
            }
        }
        if (incompleteList.size() > outfitList.size()){
            String shop = "";
            for(String item: missingPieces){
                shop = shop.concat(item);
                shop = shop.concat(" ");
            }
            throw new ItemException(shop);
        }
        if(outfitList.isEmpty()){
            throw new ItemException("TOPS AND BOTTOMS");
        }
        try (FileWriter file = new FileWriter("src/main/java/com/example/smartwardrobe/json/generatedoutfits.json")) {
            String jsonStr = JSONArray.toJSONString(outfitList);
            file.write(jsonStr);
        }
        return outfitList;
    }
    @Override
    public List<Outfit> recommendAnalogousOutfit() throws Exception {
        int outfitID;
        JSONParser parser = new JSONParser();
        try{
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("src/main/java/com/example/smartwardrobe/json/outfits.json")); ;
            outfitID = jsonArray.size();
        } catch (ParseException | IOException e) {
            e.printStackTrace();
            outfitID = 1;
        }
        User user = userService.findUserById(1L);
        Size userSize = userService.calculateUserSize(user);

        Double temperature = WeatherController.getTemperature();

        ColorGenerator colorGenerator = new ColorGenerator();
        List<Outfit> outfitList = new ArrayList<>();
        List<Outfit> incompleteList = new ArrayList<Outfit>();
        Set<String> missingPieces = new HashSet<>();
        List<Coat> coats = coatService.findAllCoats();
        List<Item> blouses = itemService.findItemsByCategory(ItemCategory.valueOf("BLOUSE"));
        System.out.println(blouses);
        List<Item> shirts = itemService.findItemsByCategory(ItemCategory.valueOf("SHIRT"));
        System.out.println(shirts);
        List<Item> tshirts = itemService.findItemsByCategory(ItemCategory.valueOf("TSHIRT"));
        System.out.println(tshirts);
        List<Item> jeans = itemService.findItemsByCategory(ItemCategory.valueOf("JEANS"));
        System.out.println(jeans);
        List<Item> pants = itemService.findItemsByCategory(ItemCategory.valueOf("PANTS"));
        System.out.println(pants);
        List<Item> skirts = itemService.findItemsByCategory(ItemCategory.valueOf("SKIRT"));
        System.out.println(skirts);
        for(int i = 0; i < blouses.toArray().length; i++) {
            Item top = blouses.get(i);
            ItemColor topColor = top.getItemColor();
            ItemColor[] colors = colorGenerator.analogous(topColor);
            if (colors != null) {
                ItemColor firstColor = colors[0];
                ItemColor secondColor = colors[1];
                for (int j = 0; j < jeans.toArray().length; j++) {
                    Item bottom = jeans.get(j);
                    if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                        if (bottom.getStyle() == top.getStyle() && top.getSize() == bottom.getSize() && top.getSize() == userSize) {
                            Outfit outfit = new Outfit();
                            outfit.setId((long) outfitID);
                            outfit.setDescription("OUTFIT" + outfitID);
                            outfitID += 1;
                            List<Item> outfitItems = new ArrayList<Item>();
                            outfitItems.add(top);
                            outfitItems.add(bottom);
                            outfit.setItems(outfitItems);
                            if (temperature < 18.0) {
                                Coat coat = new Coat();
                                for (int k = 0; k < coats.toArray().length; k++) {
                                    coat = coats.get(k);
                                    if (coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor || coat.getItemColor() == ItemColor.BLACK )
                                        if (coat.getStyle() == top.getStyle() && top.getSize() == coat.getSize() && top.getSize() == userSize) {
                                            outfit.setCoat(coat);
                                        }
                                }
                                if(outfit.getCoat() == null){
                                    incompleteList.add(outfit);
                                    missingPieces.add("COATS");
                                }
                            }
                            if(temperature < 18.0 && outfit.getCoat() == null){
                                incompleteList.add(outfit);
                            }else{
                                outfitList.add(outfit);
                                System.out.println(outfit);
                            }
                        }
                }
                for (int j = 0; j < pants.toArray().length; j++) {
                    Item bottom = pants.get(j);
                    if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                        if (bottom.getStyle() == top.getStyle() && top.getSize() == bottom.getSize() && top.getSize() == userSize) {
                            Outfit outfit = new Outfit();
                            outfit.setId((long) outfitID);
                            outfit.setDescription("OUTFIT" + outfitID);
                            outfitID += 1;
                            List<Item> outfitItems = new ArrayList<Item>();
                            outfitItems.add(top);
                            outfitItems.add(bottom);
                            outfit.setItems(outfitItems);
                            if (temperature < 18.0) {
                                Coat coat = new Coat();
                                for (int k = 0; k < coats.toArray().length; k++) {
                                    coat = coats.get(k);
                                    if (coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor || coat.getItemColor() == ItemColor.BLACK )
                                        if (coat.getStyle() == top.getStyle() && top.getSize() == coat.getSize() && top.getSize() == userSize) {
                                            outfit.setCoat(coat);
                                        }
                                }
                                if(outfit.getCoat() == null){
                                    incompleteList.add(outfit);
                                    missingPieces.add("COATS");
                                }
                            }
                            if(temperature < 18.0 && outfit.getCoat() == null){
                                incompleteList.add(outfit);
                            }else{
                                outfitList.add(outfit);
                                System.out.println(outfit);
                            }

                        }
                }
                if (temperature > 5.0) {
                    for (int j = 0; j < skirts.toArray().length; j++) {
                        Item bottom = skirts.get(j);
                        if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                            if (bottom.getStyle() == top.getStyle() && top.getSize() == bottom.getSize() && top.getSize() == userSize) {
                                Outfit outfit = new Outfit();
                                outfit.setId((long) outfitID);
                                outfit.setDescription("OUTFIT" + outfitID);
                                outfitID += 1;
                                List<Item> outfitItems = new ArrayList<Item>();
                                outfitItems.add(top);
                                outfitItems.add(bottom);
                                outfit.setItems(outfitItems);
                                if (temperature < 18.0) {
                                    Coat coat = new Coat();
                                    for (int k = 0; k < coats.toArray().length; k++) {
                                        coat = coats.get(k);
                                        if (coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor || coat.getItemColor() == ItemColor.BLACK )
                                            if (coat.getStyle() == top.getStyle() && top.getSize() == coat.getSize() && top.getSize() == userSize) {
                                                outfit.setCoat(coat);
                                            }
                                    }
                                    if(outfit.getCoat() == null){
                                        incompleteList.add(outfit);
                                        missingPieces.add("COATS");
                                    }
                                }
                                if(temperature < 18.0 && outfit.getCoat() == null){
                                    incompleteList.add(outfit);
                                }else{
                                    outfitList.add(outfit);
                                    System.out.println(outfit);
                                }

                            }
                    }
                }
            }
        }
        for(int i = 0; i < shirts.toArray().length; i++){
            Item top = shirts.get(i);
            ItemColor topColor = top.getItemColor();
            ItemColor[] colors = colorGenerator.analogous(topColor);
            if(colors != null) {
                ItemColor firstColor = colors[0];
                ItemColor secondColor = colors[1];
                for (int j = 0; j < jeans.toArray().length; j++) {
                    Item bottom = jeans.get(j);
                    if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                        if (bottom.getStyle() == top.getStyle() && top.getSize() == bottom.getSize() && top.getSize() == userSize) {
                            Outfit outfit = new Outfit();
                            outfit.setId((long) outfitID);
                            outfit.setDescription("OUTFIT" + outfitID);
                            outfitID += 1;
                            List<Item> outfitItems = new ArrayList<Item>();
                            outfitItems.add(top);
                            outfitItems.add(bottom);
                            outfit.setItems(outfitItems);
                            if (temperature < 18.0) {
                                Coat coat = new Coat();
                                for (int k = 0; k < coats.toArray().length; k++) {
                                    coat = coats.get(k);
                                    if (coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor || coat.getItemColor() == ItemColor.BLACK )
                                        if (coat.getStyle() == top.getStyle() && top.getSize() == coat.getSize() && top.getSize() == userSize) {
                                            outfit.setCoat(coat);
                                        }
                                }
                                if(outfit.getCoat() == null){
                                    incompleteList.add(outfit);
                                    missingPieces.add("COATS");
                                }
                            }
                            if(temperature < 18.0 && outfit.getCoat() == null){
                                incompleteList.add(outfit);
                            }else{
                                outfitList.add(outfit);
                                System.out.println(outfit);
                            }
//                    writeOutfitToFile(outfit);
                        }
                }
                for (int j = 0; j < pants.toArray().length; j++) {
                    Item bottom = pants.get(j);
                    if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                        if (bottom.getStyle() == top.getStyle() && top.getSize() == bottom.getSize() && top.getSize() == userSize) {
                            Outfit outfit = new Outfit();
                            outfit.setId((long) outfitID);
                            outfit.setDescription("OUTFIT" + outfitID);
                            outfitID += 1;
                            List<Item> outfitItems = new ArrayList<Item>();
                            outfitItems.add(top);
                            outfitItems.add(bottom);
                            outfit.setItems(outfitItems);
                            if (temperature < 18.0) {
                                Coat coat = new Coat();
                                for (int k = 0; k < coats.toArray().length; k++) {
                                    coat = coats.get(k);
                                    if (coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor || coat.getItemColor() == ItemColor.BLACK )
                                        if (coat.getStyle() == top.getStyle() && top.getSize() == coat.getSize() && top.getSize() == userSize) {
                                            outfit.setCoat(coat);
                                        }
                                }
                                if(outfit.getCoat() == null){
                                    incompleteList.add(outfit);
                                    missingPieces.add("COATS");
                                }
                            }
                            if(temperature < 18.0 && outfit.getCoat() == null){
                                incompleteList.add(outfit);
                            }else{
                                outfitList.add(outfit);
                                System.out.println(outfit);
                            }
//                    writeOutfitToFile(outfit);
                        }
                }
                if(temperature > 5.0) {
                    for (int j = 0; j < skirts.toArray().length; j++) {
                        Item bottom = skirts.get(j);
                        if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                            if (bottom.getStyle() == top.getStyle() && top.getSize() == bottom.getSize() && top.getSize() == userSize) {
                                Outfit outfit = new Outfit();
                                outfit.setId((long) outfitID);
                                outfit.setDescription("OUTFIT" + outfitID);
                                outfitID += 1;
                                List<Item> outfitItems = new ArrayList<Item>();
                                outfitItems.add(top);
                                outfitItems.add(bottom);
                                outfit.setItems(outfitItems);
                                if (temperature < 18.0) {
                                    Coat coat = new Coat();
                                    for (int k = 0; k < coats.toArray().length; k++) {
                                        coat = coats.get(k);
                                        if (coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor || coat.getItemColor() == ItemColor.BLACK )
                                            if (coat.getStyle() == top.getStyle() && top.getSize() == coat.getSize() && top.getSize() == userSize) {
                                                outfit.setCoat(coat);
                                            }
                                    }
                                    if(outfit.getCoat() == null){
                                        incompleteList.add(outfit);
                                        missingPieces.add("COATS");
                                    }
                                }
                                if(temperature < 18.0 && outfit.getCoat() == null){
                                    incompleteList.add(outfit);
                                }else{
                                    outfitList.add(outfit);
                                    System.out.println(outfit);
                                }
                            }
                    }
                }
            }
        }

        if(temperature > 5.0) {
            for (int i = 0; i < tshirts.toArray().length; i++) {
                Item top = tshirts.get(i);
                ItemColor topColor = top.getItemColor();
                ItemColor[] colors = colorGenerator.analogous(topColor);
                if (colors != null) {
                    ItemColor firstColor = colors[0];
                    ItemColor secondColor = colors[1];
                    for (int j = 0; j < jeans.toArray().length; j++) {
                        Item bottom = jeans.get(j);
                        if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                            if (bottom.getStyle() == top.getStyle() && top.getSize() == bottom.getSize() && top.getSize() == userSize) {
                                Outfit outfit = new Outfit();
                                outfit.setId((long) outfitID);
                                outfit.setDescription("OUTFIT" + outfitID);
                                outfitID += 1;
                                List<Item> outfitItems = new ArrayList<Item>();
                                outfitItems.add(top);
                                outfitItems.add(bottom);
                                outfit.setItems(outfitItems);
                                if (temperature < 18.0) {
                                    Coat coat = new Coat();
                                    for (int k = 0; k < coats.toArray().length; k++) {
                                        coat = coats.get(k);
                                        if (coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor || coat.getItemColor() == ItemColor.BLACK )
                                            if (coat.getStyle() == top.getStyle() && top.getSize() == coat.getSize() && top.getSize() == userSize) {
                                                outfit.setCoat(coat);
                                            }
                                    }
                                    if(outfit.getCoat() == null){
                                        incompleteList.add(outfit);
                                        missingPieces.add("COATS");
                                    }
                                }
                                if(temperature < 18.0 && outfit.getCoat() == null){
                                    incompleteList.add(outfit);
                                }else{
                                    outfitList.add(outfit);
                                    System.out.println(outfit);
                                }
                            }
                    }
                    for (int j = 0; j < pants.toArray().length; j++) {
                        Item bottom = pants.get(j);
                        if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                            if (bottom.getStyle() == top.getStyle() && top.getSize() == bottom.getSize() && top.getSize() == userSize) {
                                Outfit outfit = new Outfit();
                                outfit.setId((long) outfitID);
                                outfit.setDescription("OUTFIT" + outfitID);
                                outfitID += 1;
                                List<Item> outfitItems = new ArrayList<Item>();
                                outfitItems.add(top);
                                outfitItems.add(bottom);
                                outfit.setItems(outfitItems);
                                if (temperature < 18.0) {
                                    Coat coat = new Coat();
                                    for (int k = 0; k < coats.toArray().length; k++) {
                                        coat = coats.get(k);
                                        if (coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor || coat.getItemColor() == ItemColor.BLACK )
                                            if (coat.getStyle() == top.getStyle() && top.getSize() == coat.getSize() && top.getSize() == userSize) {
                                                outfit.setCoat(coat);
                                            }
                                    }
                                    if(outfit.getCoat() == null){
                                        incompleteList.add(outfit);
                                        missingPieces.add("COATS");
                                    }
                                }
                                if(temperature < 18.0 && outfit.getCoat() == null){
                                    incompleteList.add(outfit);
                                }else{
                                    outfitList.add(outfit);
                                    System.out.println(outfit);
                                }
                            }
                    }
                    for (int j = 0; j < skirts.toArray().length; j++) {
                        Item bottom = skirts.get(j);
                        if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                            if (bottom.getStyle() == top.getStyle() && top.getSize() == bottom.getSize() && top.getSize() == userSize) {
                                Outfit outfit = new Outfit();
                                outfit.setId((long) outfitID);
                                outfit.setDescription("OUTFIT" + outfitID);
                                outfitID += 1;
                                List<Item> outfitItems = new ArrayList<Item>();
                                outfitItems.add(top);
                                outfitItems.add(bottom);
                                outfit.setItems(outfitItems);
                                if (temperature < 18.0) {
                                    Coat coat = new Coat();
                                    for (int k = 0; k < coats.toArray().length; k++) {
                                        coat = coats.get(k);
                                        if (coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor || coat.getItemColor() == ItemColor.BLACK )
                                            if (coat.getStyle() == top.getStyle() && top.getSize() == coat.getSize() && top.getSize() == userSize) {
                                                outfit.setCoat(coat);
                                            }
                                    }
                                    if(outfit.getCoat() == null){
                                        incompleteList.add(outfit);
                                        missingPieces.add("COATS");
                                    }
                                }
                                if(temperature < 18.0 && outfit.getCoat() == null){
                                    incompleteList.add(outfit);
                                }else{
                                    outfitList.add(outfit);
                                    System.out.println(outfit);
                                }
                            }

                    }
                }
            }
        }



        if(temperature > 5.0) {
            List<Item> dresses = itemService.findItemsByCategory(ItemCategory.valueOf("DRESS"));
            for (int i = 0; i < dresses.toArray().length; i++) {
                Item dress = dresses.get(i);
                if(dress.getSize() == userSize) {
                    ItemColor topColor = dress.getItemColor();
                    ItemColor[] colors = colorGenerator.analogous(topColor);
                    if (colors != null) {
                        ItemColor firstColor = colors[0];
                        ItemColor secondColor = colors[1];
                        Outfit outfit = new Outfit();
                        outfit.setId((long) outfitID);
                        outfit.setDescription("OUTFIT" + outfitID);
                        outfitID += 1;
                        List<Item> outfitItems = new ArrayList<Item>();
                        outfitItems.add(dress);
                        outfit.setItems(outfitItems);
                        if (temperature < 18.0) {
                            Coat coat = new Coat();
                            for (int k = 0; k < coats.toArray().length; k++) {
                                coat = coats.get(k);
                                if (coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor || coat.getItemColor() == ItemColor.BLACK)
                                    if (coat.getStyle() == dress.getStyle() && dress.getSize() == coat.getSize() && dress.getSize() == userSize) {
                                        outfit.setCoat(coat);
                                    }
                            }
                            if (outfit.getCoat() == null) {
                                incompleteList.add(outfit);
                                missingPieces.add("COATS");
                            }
                        }
                        if (temperature < 18.0 && outfit.getCoat() == null) {
                            incompleteList.add(outfit);
                        } else {
                            outfitList.add(outfit);
                            System.out.println(outfit);
                        }
                    }
                }
            }
        }
        if (incompleteList.size() > outfitList.size()){
            String shop = new String();
            System.out.println(missingPieces);
            for(String item: missingPieces){
                shop = shop.concat(item);
                shop = shop.concat(" ");
            }
            throw new ItemException(shop);
        }
        if(outfitList.size() == 0){
            throw new ItemException("TOPS AND BOTTOMS");
        }

        try (FileWriter file = new FileWriter("src/main/java/com/example/smartwardrobe/json/generatedoutfits.json")) {
            String jsonStr = JSONArray.toJSONString(outfitList);
            file.write(jsonStr);
        }
        return outfitList;
    }
    @Override
    public List<Outfit> recommendPastelOutfit() throws Exception {
        int outfitID;
        JSONParser parser = new JSONParser();
        try{
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("src/main/java/com/example/smartwardrobe/json/outfits.json")); ;
            outfitID = jsonArray.size();
        } catch (ParseException | IOException e) {
            e.printStackTrace();
            outfitID = 1;
        }
        Double temperature = WeatherController.getTemperature();

        User user = userService.findUserById(1L);
        Size userSize = userService.calculateUserSize(user);

        ColorGenerator colorGenerator = new ColorGenerator();
        List<Outfit> outfitList = new ArrayList<>();
        List<Coat> coats = coatService.findAllCoats();
        List<Item> blouses = itemService.findItemsByCategory(ItemCategory.valueOf("BLOUSE"));
        List<Item> shirts = itemService.findItemsByCategory(ItemCategory.valueOf("SHIRT"));
        List<Item> tshirts = itemService.findItemsByCategory(ItemCategory.valueOf("TSHIRT"));
        List<Item> jeans = itemService.findItemsByCategory(ItemCategory.valueOf("JEANS"));
        List<Item> pants = itemService.findItemsByCategory(ItemCategory.valueOf("PANTS"));
        List<Item> skirts = itemService.findItemsByCategory(ItemCategory.valueOf("SKIRT"));
        for(int i = 0; i < blouses.toArray().length; i++){
            Item top = blouses.get(i);
            ItemColor topColor = top.getItemColor();
            ItemColor[] colors = colorGenerator.pastel(topColor);
            ItemColor firstColor = colors[0];
            ItemColor secondColor = colors[1];
            for(int j = 0; j < jeans.toArray().length; j++){
                Item bottom = jeans.get(j);
                if(bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                    if(bottom.getStyle() == top.getStyle() && top.getSize() == bottom.getSize() && top.getSize() == userSize){
                        Outfit outfit = new Outfit();
                        outfit.setId((long) outfitID);
                        outfit.setDescription("OUTFIT"+outfitID);
                        outfitID += 1;
                        List<Item> outfitItems = new ArrayList<>();
                        outfitItems.add(top);
                        outfitItems.add(bottom);
                        outfit.setItems(outfitItems);
                        if(temperature < 18.0){
                            Coat coat;
                            for(int k = 0; k < coats.toArray().length; k++){
                                coat = coats.get(k);
                                if(coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                                    if(coat.getStyle() == top.getStyle() && top.getSize() == coat.getSize() && top.getSize() == userSize){
                                        outfit.setCoat(coat);
                                    }
                            }
                            if(outfit.getCoat() == null){
                                throw new ItemException("COATS");
                            }
                        }
                        outfitList.add(outfit);
                    }
            }
            for(int j = 0; j < pants.toArray().length; j++){
                Item bottom = pants.get(j);
                if(bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                    if(bottom.getStyle() == top.getStyle() && top.getSize() == bottom.getSize() && top.getSize() == userSize){
                        Outfit outfit = new Outfit();
                        outfit.setId((long) outfitID);
                        outfit.setDescription("OUTFIT"+outfitID);
                        outfitID += 1;
                        List<Item> outfitItems = new ArrayList<>();
                        outfitItems.add(top);
                        outfitItems.add(bottom);
                        outfit.setItems(outfitItems);
                        if(temperature < 18.0){
                            Coat coat;
                            for(int k = 0; k < coats.toArray().length; k++){
                                coat = coats.get(k);
                                if(coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                                    if(coat.getStyle() == top.getStyle() && top.getSize() == coat.getSize() && top.getSize() == userSize){
                                        outfit.setCoat(coat);
                                    }
                            }
                            if(outfit.getCoat() == null){
                                throw new ItemException("COATS");
                            }
                        }
                        outfitList.add(outfit);
                    }
            }
            if(temperature > 5.0){
                for(int j = 0; j < skirts.toArray().length; j++){
                    Item bottom = skirts.get(j);
                    if(bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                        if(bottom.getStyle() == top.getStyle() && top.getSize() == bottom.getSize() && top.getSize() == userSize){
                            Outfit outfit = new Outfit();
                            outfit.setId((long) outfitID);
                            outfit.setDescription("OUTFIT"+outfitID);
                            outfitID += 1;
                            List<Item> outfitItems = new ArrayList<>();
                            outfitItems.add(top);
                            outfitItems.add(bottom);
                            outfit.setItems(outfitItems);
                            if(temperature < 18.0){
                                Coat coat;
                                for(int k = 0; k < coats.toArray().length; k++){
                                    coat = coats.get(k);
                                    if(coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                                        if(coat.getStyle() == top.getStyle() && top.getSize() == coat.getSize() && top.getSize() == userSize){
                                            outfit.setCoat(coat);
                                        }
                                }
                                if(outfit.getCoat() == null){
                                    throw new ItemException("COATS");
                                }
                            }
                            outfitList.add(outfit);

                        }
                }
            }
        }
        for(int i = 0; i < shirts.toArray().length; i++) {
            Item top = shirts.get(i);
            ItemColor topColor = top.getItemColor();
            ItemColor[] colors = colorGenerator.pastel(topColor);
            ItemColor firstColor = colors[0];
            ItemColor secondColor = colors[1];
            for (int j = 0; j < jeans.toArray().length; j++) {
                Item bottom = jeans.get(j);
                if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                    if (bottom.getStyle() == top.getStyle() && top.getSize() == bottom.getSize() && top.getSize() == userSize) {
                        Outfit outfit = new Outfit();
                        outfit.setId((long) outfitID);
                        outfit.setDescription("OUTFIT" + outfitID);
                        outfitID += 1;
                        List<Item> outfitItems = new ArrayList<>();
                        outfitItems.add(top);
                        outfitItems.add(bottom);
                        outfit.setItems(outfitItems);
                        if(temperature < 18.0){
                            Coat coat;
                            for(int k = 0; k < coats.toArray().length; k++){
                                coat = coats.get(k);
                                if(coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                                    if(coat.getStyle() == top.getStyle() && top.getSize() == coat.getSize() && top.getSize() == userSize){
                                        outfit.setCoat(coat);
                                    }
                            }
                            if(outfit.getCoat() == null){
                                throw new ItemException("COATS");
                            }
                        }
                        outfitList.add(outfit);
//                    writeOutfitToFile(outfit);
                    }
            }
            for (int j = 0; j < pants.toArray().length; j++) {
                Item bottom = pants.get(j);
                if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                    if (bottom.getStyle() == top.getStyle() && top.getSize() == bottom.getSize() && top.getSize() == userSize) {
                        Outfit outfit = new Outfit();
                        outfit.setId((long) outfitID);
                        outfit.setDescription("OUTFIT" + outfitID);
                        outfitID += 1;
                        List<Item> outfitItems = new ArrayList<Item>();
                        outfitItems.add(top);
                        outfitItems.add(bottom);
                        outfit.setItems(outfitItems);
                        if(temperature < 18.0){
                            Coat coat;
                            for(int k = 0; k < coats.toArray().length; k++){
                                coat = coats.get(k);
                                if(coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                                    if(coat.getStyle() == top.getStyle() && top.getSize() == coat.getSize() && top.getSize() == userSize){
                                        outfit.setCoat(coat);
                                    }
                            }
                            if(outfit.getCoat() == null){
                                throw new ItemException("COATS");
                            }
                        }
                        outfitList.add(outfit);
//                    writeOutfitToFile(outfit);
                    }
            }
            if (temperature > 5.0) {
                for (int j = 0; j < skirts.toArray().length; j++) {
                    Item bottom = skirts.get(j);
                    if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                        if (bottom.getStyle() == top.getStyle() && top.getSize() == bottom.getSize() && top.getSize() == userSize) {
                            Outfit outfit = new Outfit();
                            outfit.setId((long) outfitID);
                            outfit.setDescription("OUTFIT" + outfitID);
                            outfitID += 1;
                            List<Item> outfitItems = new ArrayList<Item>();
                            outfitItems.add(top);
                            outfitItems.add(bottom);
                            outfit.setItems(outfitItems);
                            if(temperature < 18.0){
                                Coat coat;
                                for(int k = 0; k < coats.toArray().length; k++){
                                    coat = coats.get(k);
                                    if(coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                                        if(coat.getStyle() == top.getStyle() && top.getSize() == coat.getSize() && top.getSize() == userSize){
                                            outfit.setCoat(coat);
                                        }
                                }
                                if(outfit.getCoat() == null){
                                    throw new ItemException("COATS");
                                }
                            }
                            outfitList.add(outfit);
//                    writeOutfitToFile(outfit);
                        }
                }

            }
        }
        if(temperature > 5.0) {
            for (int i = 0; i < tshirts.toArray().length; i++) {
                Item top = tshirts.get(i);
                ItemColor topColor = top.getItemColor();
                ItemColor[] colors = colorGenerator.pastel(topColor);
                ItemColor firstColor = colors[0];
                ItemColor secondColor = colors[1];
                for (int j = 0; j < jeans.toArray().length; j++) {
                    Item bottom = jeans.get(j);
                    if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                        if (bottom.getStyle() == top.getStyle() && top.getSize() == bottom.getSize() && top.getSize() == userSize) {
                            Outfit outfit = new Outfit();
                            outfit.setId((long) outfitID);
                            outfit.setDescription("OUTFIT" + outfitID);
                            outfitID += 1;
                            List<Item> outfitItems = new ArrayList<>();
                            outfitItems.add(top);
                            outfitItems.add(bottom);
                            outfit.setItems(outfitItems);
                            if(temperature < 18.0){
                                Coat coat;
                                for(int k = 0; k < coats.toArray().length; k++){
                                    coat = coats.get(k);
                                    if(coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                                        if(coat.getStyle() == top.getStyle() && top.getSize() == coat.getSize() && top.getSize() == userSize){
                                            outfit.setCoat(coat);
                                        }
                                }
                                if(outfit.getCoat() == null){
                                    throw new ItemException("COATS");
                                }
                            }
                            outfitList.add(outfit);
//                    writeOutfitToFile(outfit);
                        }
                }
                for (int j = 0; j < pants.toArray().length; j++) {
                    Item bottom = pants.get(j);
                    if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                        if (bottom.getStyle() == top.getStyle() && top.getSize() == bottom.getSize() && top.getSize() == userSize) {
                            Outfit outfit = new Outfit();
                            outfit.setId((long) outfitID);
                            outfit.setDescription("OUTFIT" + outfitID);
                            outfitID += 1;
                            List<Item> outfitItems = new ArrayList<>();
                            outfitItems.add(top);
                            outfitItems.add(bottom);
                            outfit.setItems(outfitItems);
                            if(temperature < 18.0){
                                Coat coat;
                                for(int k = 0; k < coats.toArray().length; k++){
                                    coat = coats.get(k);
                                    if(coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                                        if(coat.getStyle() == top.getStyle() && top.getSize() == coat.getSize() && top.getSize() == userSize){
                                            outfit.setCoat(coat);
                                        }
                                }
                                if(outfit.getCoat() == null){
                                    throw new ItemException("COATS");
                                }
                            }
                            outfitList.add(outfit);
//                    writeOutfitToFile(outfit);
                        }
                }
                for (int j = 0; j < skirts.toArray().length; j++) {
                    Item bottom = skirts.get(j);
                    if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                        if (bottom.getStyle() == top.getStyle() && top.getSize() == bottom.getSize() && top.getSize() == userSize) {
                            Outfit outfit = new Outfit();
                            outfit.setId((long) outfitID);
                            outfit.setDescription("OUTFIT" + outfitID);
                            outfitID += 1;
                            List<Item> outfitItems = new ArrayList<>();
                            outfitItems.add(top);
                            outfitItems.add(bottom);
                            outfit.setItems(outfitItems);
                            if(temperature < 18.0){
                                Coat coat;
                                for(int k = 0; k < coats.toArray().length; k++){
                                    coat = coats.get(k);
                                    if(coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                                        if(coat.getStyle() == top.getStyle() && top.getSize() == coat.getSize() && top.getSize() == userSize){
                                            outfit.setCoat(coat);
                                        }
                                }
                                if(outfit.getCoat() == null){
                                    throw new ItemException("COATS");
                                }
                            }
                            outfitList.add(outfit);
//                    writeOutfitToFile(outfit);
                        }
                }

            }
        }
        if(temperature > 5.0) {
            List<Item> dresses = itemService.findItemsByCategory(ItemCategory.valueOf("DRESS"));
            for (int i = 0; i < dresses.toArray().length; i++) {
                Item dress = dresses.get(i);
                if(dress.getSize() == userSize) {
                    ItemColor topColor = dress.getItemColor();
                    ItemColor[] colors = colorGenerator.pastel(topColor);
                    ItemColor firstColor = colors[0];
                    ItemColor secondColor = colors[1];
                    Outfit outfit = new Outfit();
                    outfit.setId((long) outfitID);
                    outfit.setDescription("OUTFIT" + outfitID);
                    outfitID += 1;
                    List<Item> outfitItems = new ArrayList<>();
                    outfitItems.add(dress);
                    outfit.setItems(outfitItems);
                    if (temperature < 18.0) {
                        Coat coat;
                        for (int k = 0; k < coats.toArray().length; k++) {
                            coat = coats.get(k);
                            if (coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                                if (coat.getStyle() == dress.getStyle() && dress.getSize() == coat.getSize() && dress.getSize() == userSize) {
                                    outfit.setCoat(coat);
                                }
                        }
                        if (outfit.getCoat() == null) {
                            throw new ItemException("COATS");
                        }
                    }
                    outfitList.add(outfit);
                }
            }
        }
        if(outfitList.isEmpty()){
            throw new ItemException("TOPS AND BOTTOMS");
        }
        try (FileWriter file = new FileWriter("src/main/java/com/example/smartwardrobe/json/generatedoutfits.json")) {
            String jsonStr = JSONArray.toJSONString(outfitList);
            file.write(jsonStr);
        }
        return outfitList;

    }
    @Override
    public void selectRecommendedOutfit(Integer id){
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("src/main/java/com/example/smartwardrobe/json/generatedoutfits.json"))
        {
            JSONArray obj = (JSONArray) jsonParser.parse(reader);
            for(int i = 0;i<obj.toArray().length;i++)
            {
                JSONObject jsonItem = (JSONObject) obj.get(i);
                if(id.equals(Integer.valueOf(jsonItem.get("id").toString()))){
                    Outfit outfit = new Outfit();
                    JSONObject coatObj = (JSONObject) jsonItem.get("coat");
                    if(coatObj!=null){
                        Coat coat = coatService.findCoatById((Long) coatObj.get("id"));
                        outfit.setCoat(coat);
                    }


                    JSONArray itemArray = (JSONArray) jsonItem.get("items");
                    List<Item> itemList = new ArrayList<>();
                    for(int j = 0;j<itemArray.toArray().length;j++)
                    {
                        JSONObject itemObj = (JSONObject) itemArray.get(j);
                        Item item = itemService.findItemById((Long) itemObj.get("id"));
                        itemList.add(item);
                    }
                    outfit.setId(Long.valueOf(id));
                    outfit.setDescription((String) jsonItem.get("description"));

                    outfit.setItems(itemList);
                    saveOutfit(outfit);
                }

            }


        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

}

