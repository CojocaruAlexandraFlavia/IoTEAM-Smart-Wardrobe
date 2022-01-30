package com.example.smartwardrobe.service.impl;

import com.example.smartwardrobe.colorpalette.ColorGenerator;
import com.example.smartwardrobe.controller.WeatherController;
import com.example.smartwardrobe.enums.*;
import com.example.smartwardrobe.model.Coat;
import com.example.smartwardrobe.model.History;
import com.example.smartwardrobe.model.Item;
import com.example.smartwardrobe.model.Outfit;
import com.example.smartwardrobe.repository.ItemRepository;
import com.example.smartwardrobe.repository.OutfitRepository;
import com.example.smartwardrobe.service.CoatService;
import com.example.smartwardrobe.service.HistoryService;
import com.example.smartwardrobe.service.ItemService;
import com.example.smartwardrobe.service.OutfitService;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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

//        writeOutfitCategoryToFile(savedOutfit);


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
        if( outfit.getCoat()!=null){
            jsonObject.put("coat", outfit.getCoat().getCoatCategory().toString());
        }else{
            jsonObject.put("coat", null);
        }

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



    @Override
    public List<Outfit> recommendMonochromaticOutfit() throws Exception {
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

        ColorGenerator colorGenerator = new ColorGenerator();

        List<Outfit> outfitList = new ArrayList<Outfit>();
        List<Coat> coats = coatService.findAllCoats();
        List<Item> blouses = itemService.findItemsByCategory(ItemCategory.BLOUSE);
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
        for(int i = 0; i < blouses.toArray().length; i++){
            Item top = blouses.get(i);
            ItemColor topColor = top.getItemColor();
            ItemColor[] colors = colorGenerator.monoChromatic(topColor);
            ItemColor firstColor = colors[0];
            ItemColor secondColor = colors[1];
            for(int j = 0; j < jeans.toArray().length; j++){
                Item bottom = jeans.get(j);
                if(bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                    if(bottom.getStyle() == top.getStyle()){
                        Outfit outfit = new Outfit();
                        outfit.setId((long) outfitID);
                        outfit.setDescription("OUTFIT"+outfitID);
                        outfitID += 1;
                        List<Item> outfitItems = new ArrayList<Item>();
                        outfitItems.add(top);
                        outfitItems.add(bottom);
                        outfit.setItems(outfitItems);
                        if(temperature < 18.0){
                            Coat coat = new Coat();
                            for(int k = 0; k < coats.toArray().length; k++){
                                coat = coats.get(k);
                                if(coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                                    if(coat.getStyle() == top.getStyle()){
                                        outfit.setCoat(coat);
                                    }
                            }
                            if(coat.getCode() == null){
                                //recommend buy some

                            }
                        }
                        outfitList.add(outfit);
                        System.out.println(outfit);
                }
            }
            for(int j = 0; j < pants.toArray().length; j++){
                Item bottom = pants.get(j);
                if(bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                    if(bottom.getStyle() == top.getStyle()){
                        Outfit outfit = new Outfit();
                        outfit.setId((long) outfitID);
                        outfit.setDescription("OUTFIT"+outfitID);
                        outfitID += 1;
                        List<Item> outfitItems = new ArrayList<Item>();
                        outfitItems.add(top);
                        outfitItems.add(bottom);
                        outfit.setItems(outfitItems);
                        if(temperature < 18.0){
                            Coat coat = new Coat();
                            for(int k = 0; k < coats.toArray().length; k++){
                                coat = coats.get(k);
                                if(coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                                    if(coat.getStyle() == top.getStyle()){
                                        outfit.setCoat(coat);
                                    }
                            }
                            if(coat.getCode() == null){
                                //recommend buy some
                            }
                        }
                        outfitList.add(outfit);
                        System.out.println(outfit);

                }
            }
            if(temperature > 5.0){
                for(int j = 0; j < skirts.toArray().length; j++){
                    Item bottom = skirts.get(j);
                    if(bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                        if(bottom.getStyle() == top.getStyle()){
                            Outfit outfit = new Outfit();
                            outfit.setId((long) outfitID);
                            outfit.setDescription("OUTFIT"+outfitID);
                            outfitID += 1;
                            List<Item> outfitItems = new ArrayList<Item>();
                            outfitItems.add(top);
                            outfitItems.add(bottom);
                            outfit.setItems(outfitItems);
                            if(temperature < 18.0){
                                Coat coat = new Coat();
                                for(int k = 0; k < coats.toArray().length; k++){
                                    coat = coats.get(k);
                                    if(coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                                        if(coat.getStyle() == top.getStyle()){
                                            outfit.setCoat(coat);
                                        }
                                }
                                if(coat.getCode() == null){
                                    //recommend buy some
                                }
                            }
                            outfitList.add(outfit);
                            System.out.println(outfit);

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
                    if (bottom.getStyle() == top.getStyle()) {
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
                                if(coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                                    if(coat.getStyle() == top.getStyle()){
                                        outfit.setCoat(coat);
                                    }
                            }
                            if(coat.getCode() == null){
                                //recommend buy some
                            }
                        }
                        outfitList.add(outfit);
                        System.out.println(outfit);
//                    writeOutfitToFile(outfit);
                    }
            }
            for (int j = 0; j < pants.toArray().length; j++) {
                Item bottom = pants.get(j);
                if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                    if (bottom.getStyle() == top.getStyle()) {
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
                                if(coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                                    if(coat.getStyle() == top.getStyle()){
                                        outfit.setCoat(coat);
                                    }
                            }
                            if(coat.getCode() == null){
                                //recommend buy some
                            }
                        }
                        outfitList.add(outfit);
                        System.out.println(outfit);
//                    writeOutfitToFile(outfit);
                    }
            }
            if (temperature > 5.0) {
                for (int j = 0; j < skirts.toArray().length; j++) {
                    Item bottom = skirts.get(j);
                    if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                        if (bottom.getStyle() == top.getStyle()) {
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
                                    if(coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                                        if(coat.getStyle() == top.getStyle()){
                                            outfit.setCoat(coat);
                                        }
                                }
                                if(coat.getCode() == null){
                                    //recommend buy some
                                }
                            }
                            outfitList.add(outfit);
                            System.out.println(outfit);
//                    writeOutfitToFile(outfit);
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
                        if (bottom.getStyle() == top.getStyle()) {
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
                                    if(coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                                        if(coat.getStyle() == top.getStyle()){
                                            outfit.setCoat(coat);
                                        }
                                }
                                if(coat.getCode() == null){
                                    //recommend buy some
                                }
                            }
                            outfitList.add(outfit);
                            System.out.println(outfit);
//                    writeOutfitToFile(outfit);
                        }
                }
                for (int j = 0; j < pants.toArray().length; j++) {
                    Item bottom = pants.get(j);
                    if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                        if (bottom.getStyle() == top.getStyle()) {
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
                                    if(coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                                        if(coat.getStyle() == top.getStyle()){
                                            outfit.setCoat(coat);
                                        }
                                }
                                if(coat.getCode() == null){
                                    //recommend buy some
                                }
                            }
                            outfitList.add(outfit);
                            System.out.println(outfit);
//                    writeOutfitToFile(outfit);
                        }
                }
                for (int j = 0; j < skirts.toArray().length; j++) {
                    Item bottom = skirts.get(j);
                    if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                        if (bottom.getStyle() == top.getStyle()) {
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
                                    if(coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                                        if(coat.getStyle() == top.getStyle()){
                                            outfit.setCoat(coat);
                                        }
                                }
                                if(coat.getCode() == null){
                                    //recommend buy some
                                }
                            }
                            outfitList.add(outfit);
                            System.out.println(outfit);
//                    writeOutfitToFile(outfit);
                        }
                }

            }
        }
        if(temperature > 5.0) {
            List<Item> dresses = itemService.findItemsByCategory(ItemCategory.valueOf("DRESS"));
            for (int i = 0; i < dresses.toArray().length; i++) {
                Item dress = dresses.get(i);
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
                if(temperature < 18.0){
                    Coat coat = new Coat();
                    for(int k = 0; k < coats.toArray().length; k++){
                        coat = coats.get(k);
                        if(coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                            if(coat.getStyle() == dress.getStyle()){
                                outfit.setCoat(coat);
                            }
                    }
                    if(coat.getCode() == null){
                        //recommend buy some
                    }
                }
                outfitList.add(outfit);
            }
        }
        FileWriter file = new FileWriter("src/main/java/com/example/smartwardrobe/json/generatedoutfits.json");
        String jsonStr = JSONArray.toJSONString(outfitList);
        file.write(jsonStr);
        file.close();
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

        Double temperature = WeatherController.getTemperature();

        ColorGenerator colorGenerator = new ColorGenerator();
        List<Outfit> outfitList = new ArrayList<>();

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
                        if (bottom.getStyle() == top.getStyle()) {
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
                                    if (coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                                        if (coat.getStyle() == top.getStyle()) {
                                            outfit.setCoat(coat);
                                        }
                                }
                                if (coat.getCode() == null) {
                                    //recommend buy some
                                }
                            }
                            outfitList.add(outfit);
                            System.out.println(outfit);
                        }
                }
                for (int j = 0; j < pants.toArray().length; j++) {
                    Item bottom = pants.get(j);
                    if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                        if (bottom.getStyle() == top.getStyle()) {
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
                                    if (coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                                        if (coat.getStyle() == top.getStyle()) {
                                            outfit.setCoat(coat);
                                        }
                                }
                                if (coat.getCode() == null) {
                                    //recommend buy some
                                }
                            }
                            outfitList.add(outfit);
                            System.out.println(outfit);

                        }
                }
                if (temperature > 5.0) {
                    for (int j = 0; j < skirts.toArray().length; j++) {
                        Item bottom = skirts.get(j);
                        if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                            if (bottom.getStyle() == top.getStyle()) {
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
                                        if (coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                                            if (coat.getStyle() == top.getStyle()) {
                                                outfit.setCoat(coat);
                                            }
                                    }
                                    if (coat.getCode() == null) {
                                        //recommend buy some
                                    }
                                }
                                outfitList.add(outfit);
                                System.out.println(outfit);

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
                        if (bottom.getStyle() == top.getStyle()) {
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
                                    if (coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                                        if (coat.getStyle() == top.getStyle()) {
                                            outfit.setCoat(coat);
                                        }
                                }
                                if (coat.getCode() == null) {
                                    //recommend buy some
                                }
                            }
                            outfitList.add(outfit);
                            System.out.println(outfit);
//                    writeOutfitToFile(outfit);
                        }
                }
                for (int j = 0; j < pants.toArray().length; j++) {
                    Item bottom = pants.get(j);
                    if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                        if (bottom.getStyle() == top.getStyle()) {
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
                                    if (coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                                        if (coat.getStyle() == top.getStyle()) {
                                            outfit.setCoat(coat);
                                        }
                                }
                                if (coat.getCode() == null) {
                                    //recommend buy some
                                }
                            }
                            outfitList.add(outfit);
                            System.out.println(outfit);
//                    writeOutfitToFile(outfit);
                        }
                }
                if(temperature > 5.0) {
                    for (int j = 0; j < skirts.toArray().length; j++) {
                        Item bottom = skirts.get(j);
                        if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                            if (bottom.getStyle() == top.getStyle()) {
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
                                        if (coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                                            if (coat.getStyle() == top.getStyle()) {
                                                outfit.setCoat(coat);
                                            }
                                    }
                                    if (coat.getCode() == null) {
                                        //recommend buy some
                                    }
                                }
                                outfitList.add(outfit);
                                System.out.println(outfit);
//                    writeOutfitToFile(outfit);
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
                            if (bottom.getStyle() == top.getStyle()) {
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
                                        if (coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                                            if (coat.getStyle() == top.getStyle()) {
                                                outfit.setCoat(coat);
                                            }
                                    }
                                    if (coat.getCode() == null) {
                                        //recommend buy some
                                    }
                                }
                                outfitList.add(outfit);
                                System.out.println(outfit);
//                    writeOutfitToFile(outfit);
                            }
                    }
                    for (int j = 0; j < pants.toArray().length; j++) {
                        Item bottom = pants.get(j);
                        if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                            if (bottom.getStyle() == top.getStyle()) {
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
                                        if (coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                                            if (coat.getStyle() == top.getStyle()) {
                                                outfit.setCoat(coat);
                                            }
                                    }
                                    if (coat.getCode() == null) {
                                        //recommend buy some
                                    }
                                }
                                outfitList.add(outfit);
                                System.out.println(outfit);
//                    writeOutfitToFile(outfit);
                            }
                    }
                    for (int j = 0; j < skirts.toArray().length; j++) {
                        Item bottom = skirts.get(j);
                        if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                            if (bottom.getStyle() == top.getStyle()) {
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
                                        if (coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                                            if (coat.getStyle() == top.getStyle()) {
                                                outfit.setCoat(coat);
                                            }
                                    }
                                    if (coat.getCode() == null) {
                                        //recommend buy some
                                    }
                                }
                                outfitList.add(outfit);
                                System.out.println(outfit);
//                    writeOutfitToFile(outfit);
                            }

                    }
                }
            }
        }



        if(temperature > 5.0) {
            List<Item> dresses = itemService.findItemsByCategory(ItemCategory.valueOf("DRESS"));
            for (int i = 0; i < dresses.toArray().length; i++) {
                Item dress = dresses.get(i);
                ItemColor topColor = dress.getItemColor();
                ItemColor[] colors = colorGenerator.analogous(topColor);
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
                        if (coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                            if (coat.getStyle() == dress.getStyle()) {
                                outfit.setCoat(coat);
                            }
                    }
                    if (coat.getCode() == null) {
                        //recommend buy some
                    }
                }
                outfitList.add(outfit);
            }
        }
        FileWriter file = new FileWriter("src/main/java/com/example/smartwardrobe/json/generatedoutfits.json");
        String jsonStr = JSONArray.toJSONString(outfitList);
        file.write(jsonStr);
        file.close();
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

        ColorGenerator colorGenerator = new ColorGenerator();
        List<Outfit> outfitList = new ArrayList<Outfit>();
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
        for(int i = 0; i < blouses.toArray().length; i++){
            Item top = blouses.get(i);
            ItemColor topColor = top.getItemColor();
            ItemColor[] colors = colorGenerator.pastel(topColor);
            ItemColor firstColor = colors[0];
            ItemColor secondColor = colors[1];
            for(int j = 0; j < jeans.toArray().length; j++){
                Item bottom = jeans.get(j);
                if(bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                    if(bottom.getStyle() == top.getStyle()){
                        Outfit outfit = new Outfit();
                        outfit.setId((long) outfitID);
                        outfit.setDescription("OUTFIT"+outfitID);
                        outfitID += 1;
                        List<Item> outfitItems = new ArrayList<Item>();
                        outfitItems.add(top);
                        outfitItems.add(bottom);
                        outfit.setItems(outfitItems);
                        if(temperature < 18.0){
                            Coat coat = new Coat();
                            for(int k = 0; k < coats.toArray().length; k++){
                                coat = coats.get(k);
                                if(coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                                    if(coat.getStyle() == top.getStyle()){
                                        outfit.setCoat(coat);
                                    }
                            }
                            if(coat.getCode() == null){
                                //recommend buy some
                            }
                        }
                        outfitList.add(outfit);
                        System.out.println(outfit);
                    }
            }
            for(int j = 0; j < pants.toArray().length; j++){
                Item bottom = pants.get(j);
                if(bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                    if(bottom.getStyle() == top.getStyle()){
                        Outfit outfit = new Outfit();
                        outfit.setId((long) outfitID);
                        outfit.setDescription("OUTFIT"+outfitID);
                        outfitID += 1;
                        List<Item> outfitItems = new ArrayList<Item>();
                        outfitItems.add(top);
                        outfitItems.add(bottom);
                        outfit.setItems(outfitItems);
                        if(temperature < 18.0){
                            Coat coat = new Coat();
                            for(int k = 0; k < coats.toArray().length; k++){
                                coat = coats.get(k);
                                if(coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                                    if(coat.getStyle() == top.getStyle()){
                                        outfit.setCoat(coat);
                                    }
                            }
                            if(coat.getCode() == null){
                                //recommend buy some
                            }
                        }
                        outfitList.add(outfit);
                        System.out.println(outfit);

                    }
            }
            if(temperature > 5.0){
                for(int j = 0; j < skirts.toArray().length; j++){
                    Item bottom = skirts.get(j);
                    if(bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                        if(bottom.getStyle() == top.getStyle()){
                            Outfit outfit = new Outfit();
                            outfit.setId((long) outfitID);
                            outfit.setDescription("OUTFIT"+outfitID);
                            outfitID += 1;
                            List<Item> outfitItems = new ArrayList<Item>();
                            outfitItems.add(top);
                            outfitItems.add(bottom);
                            outfit.setItems(outfitItems);
                            if(temperature < 18.0){
                                Coat coat = new Coat();
                                for(int k = 0; k < coats.toArray().length; k++){
                                    coat = coats.get(k);
                                    if(coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                                        if(coat.getStyle() == top.getStyle()){
                                            outfit.setCoat(coat);
                                        }
                                }
                                if(coat.getCode() == null){
                                    //recommend buy some
                                }
                            }
                            outfitList.add(outfit);
                            System.out.println(outfit);

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
                    if (bottom.getStyle() == top.getStyle()) {
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
                                if(coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                                    if(coat.getStyle() == top.getStyle()){
                                        outfit.setCoat(coat);
                                    }
                            }
                            if(coat.getCode() == null){
                                //recommend buy some
                            }
                        }
                        outfitList.add(outfit);
                        System.out.println(outfit);
//                    writeOutfitToFile(outfit);
                    }
            }
            for (int j = 0; j < pants.toArray().length; j++) {
                Item bottom = pants.get(j);
                if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                    if (bottom.getStyle() == top.getStyle()) {
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
                                if(coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                                    if(coat.getStyle() == top.getStyle()){
                                        outfit.setCoat(coat);
                                    }
                            }
                            if(coat.getCode() == null){
                                //recommend buy some
                            }
                        }
                        outfitList.add(outfit);
                        System.out.println(outfit);
//                    writeOutfitToFile(outfit);
                    }
            }
            if (temperature > 5.0) {
                for (int j = 0; j < skirts.toArray().length; j++) {
                    Item bottom = skirts.get(j);
                    if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                        if (bottom.getStyle() == top.getStyle()) {
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
                                    if(coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                                        if(coat.getStyle() == top.getStyle()){
                                            outfit.setCoat(coat);
                                        }
                                }
                                if(coat.getCode() == null){
                                    //recommend buy some
                                }
                            }
                            outfitList.add(outfit);
                            System.out.println(outfit);
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
                        if (bottom.getStyle() == top.getStyle()) {
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
                                    if(coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                                        if(coat.getStyle() == top.getStyle()){
                                            outfit.setCoat(coat);
                                        }
                                }
                                if(coat.getCode() == null){
                                    //recommend buy some
                                }
                            }
                            outfitList.add(outfit);
                            System.out.println(outfit);
//                    writeOutfitToFile(outfit);
                        }
                }
                for (int j = 0; j < pants.toArray().length; j++) {
                    Item bottom = pants.get(j);
                    if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                        if (bottom.getStyle() == top.getStyle()) {
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
                                    if(coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                                        if(coat.getStyle() == top.getStyle()){
                                            outfit.setCoat(coat);
                                        }
                                }
                                if(coat.getCode() == null){
                                    //recommend buy some
                                }
                            }
                            outfitList.add(outfit);
                            System.out.println(outfit);
//                    writeOutfitToFile(outfit);
                        }
                }
                for (int j = 0; j < skirts.toArray().length; j++) {
                    Item bottom = skirts.get(j);
                    if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                        if (bottom.getStyle() == top.getStyle()) {
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
                                    if(coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                                        if(coat.getStyle() == top.getStyle()){
                                            outfit.setCoat(coat);
                                        }
                                }
                                if(coat.getCode() == null){
                                    //recommend buy some
                                }
                            }
                            outfitList.add(outfit);
                            System.out.println(outfit);
//                    writeOutfitToFile(outfit);
                        }
                }

            }
        }
        if(temperature > 5.0) {
            List<Item> dresses = itemService.findItemsByCategory(ItemCategory.valueOf("DRESS"));
            for (int i = 0; i < dresses.toArray().length; i++) {
                Item dress = dresses.get(i);
                ItemColor topColor = dress.getItemColor();
                ItemColor[] colors = colorGenerator.pastel(topColor);
                ItemColor firstColor = colors[0];
                ItemColor secondColor = colors[1];
                Outfit outfit = new Outfit();
                outfit.setId((long) outfitID);
                outfit.setDescription("OUTFIT" + outfitID);
                outfitID += 1;
                List<Item> outfitItems = new ArrayList<Item>();
                outfitItems.add(dress);
                outfit.setItems(outfitItems);
                if(temperature < 18.0){
                    Coat coat = new Coat();
                    for(int k = 0; k < coats.toArray().length; k++){
                        coat = coats.get(k);
                        if(coat.getItemColor() == firstColor || coat.getItemColor() == secondColor || coat.getItemColor() == topColor)
                            if(coat.getStyle() == dress.getStyle()){
                                outfit.setCoat(coat);
                            }
                    }
                    if(coat.getCode() == null){
                        //recommend buy some
                    }
                }
                outfitList.add(outfit);
            }
        }

        FileWriter file = new FileWriter("src/main/java/com/example/smartwardrobe/json/generatedoutfits.json");
        String jsonStr = JSONArray.toJSONString(outfitList);
        file.write(jsonStr);
        file.close();
        return outfitList;

    }
    @Override
    public void selectRecommendedOutfit(Integer id){
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("src/main/java/com/example/smartwardrobe/json/generatedoutfits.json"))
        {
            //Read JSON file
            JSONArray obj = (JSONArray) jsonParser.parse(reader);
            System.out.println("citit json");
            for(int i = 0;i<obj.toArray().length;i++)
            {
                JSONObject jsonItem = (JSONObject) obj.get(i);
//                System.out.println(obj.get(i));
//                System.out.println();
                if(id.equals(Integer.valueOf(jsonItem.get("id").toString()))){
                    System.out.println("ok");
                    Outfit outfit = new Outfit();
                    JSONObject coatObj = (JSONObject) jsonItem.get("coat");
                    if(coatObj!=null){
                        Coat coat = new Coat();
//                        coat.setId((Long) coatObj.get("id"));
                        coat = coatService.findCoatById((Long) coatObj.get("id"));
//                        coat.setCode((String)coatObj.get("code"));
//                        coat.setMaterial(Material.valueOf((String) jsonItem.get("material")));
//                        coat.setSize(Size.valueOf((String) jsonItem.get("size")));
//                        coat.setCode((String) jsonItem.get("code"));
//                        coat.setItemColor(ItemColor.valueOf((String) jsonItem.get("itemColor")));
//                        coat.setStyle(Style.valueOf((String)  jsonItem.get("style")));
//                        coat.setCoatCategory(CoatCategory.valueOf((String)  jsonItem.get("itemCategory")));
//                        coat.setLastWashingDay(LocalDate.parse((String) coatObj.get("lastWashingDay")));
//                        coat.setLastWearing(LocalDate.parse((String) coatObj.get("lastWearing")));
//                        coat.setNrOfWearsSinceLastWash(Integer.parseInt((String) coatObj.get("nrOfWearsSinceLastWash")));
//                        coat.setWashingZoneColor(WashingZoneColor.valueOf((String) jsonItem.get("washingZoneColor")));
                        outfit.setCoat(coat);
                    }


                    JSONArray itemArray = (JSONArray) jsonItem.get("items");
                    List<Item> itemList = new ArrayList<>();
                    for(int j = 0;j<itemArray.toArray().length;j++)
                    {
                        JSONObject itemObj = (JSONObject) itemArray.get(j);
                        Item item = new Item();
                        item = itemService.findItemById((Long) itemObj.get("id"));
//                        item.setId((Long) itemObj.get("id"));
//                        item.setMaterial(Material.valueOf((String) itemObj.get("material")));
//                        item.setSize(Size.valueOf((String) itemObj.get("size")));
//                        item.setCode((String) itemObj.get("code"));
//                        item.setItemColor(ItemColor.valueOf((String) itemObj.get("itemColor")));
//                        item.setStyle(Style.valueOf((String)  itemObj.get("style")));
//                        item.setItemCategory(ItemCategory.valueOf((String)  itemObj.get("itemCategory")));
//                        item.setLastWashingDay(LocalDate.parse((String) itemObj.get("lastWashingDay")));
//                        item.setLastWearing(LocalDate.parse((String) itemObj.get("lastWearing")));
//                        item.setNrOfWearsSinceLastWash(Integer.parseInt((String) itemObj.get("nrOfWearsSinceLastWash")));
//                        item.setWashingZoneColor(WashingZoneColor.valueOf((String) itemObj.get("washingZoneColor")));
                        itemList.add(item);
                    }
                    outfit.setId(Long.valueOf(id));
                    outfit.setDescription((String) jsonItem.get("description"));

                    outfit.setItems(itemList);
                    saveOutfit(outfit);
                    System.out.println("outfitul este");
                    System.out.println(outfit);
                }

            }


        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
