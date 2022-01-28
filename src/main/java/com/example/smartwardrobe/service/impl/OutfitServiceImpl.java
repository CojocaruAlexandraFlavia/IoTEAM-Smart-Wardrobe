package com.example.smartwardrobe.service.impl;

import com.example.smartwardrobe.colorpalette.ColorGenerator;
import com.example.smartwardrobe.enums.ItemCategory;
import com.example.smartwardrobe.enums.ItemColor;
import com.example.smartwardrobe.model.History;
import com.example.smartwardrobe.model.Item;
import com.example.smartwardrobe.model.Outfit;
import com.example.smartwardrobe.repository.ItemRepository;
import com.example.smartwardrobe.repository.OutfitRepository;
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



    @Override
    public List<Outfit> recommendMonochromaticOutfit(){
        int outfitID;
        JSONParser parser = new JSONParser();
        try{
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("src/main/java/com/example/smartwardrobe/json/outfits.json")); ;
            outfitID = jsonArray.size();
        } catch (ParseException | IOException e) {
            e.printStackTrace();
            outfitID = 1;
        }
        ColorGenerator colorGenerator = new ColorGenerator();
        List<Outfit> outfitList = new ArrayList<Outfit>();
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
                    outfitID += 1;
                    List<Item> outfitItems = new ArrayList<Item>();
                    outfitItems.add(top);
                    outfitItems.add(bottom);
                    outfit.setItems(outfitItems);
                    outfitList.add(outfit);
                    System.out.println(outfit);
//                    writeOutfitToFile(outfit);
                }
            }
            for(int j = 0; j < pants.toArray().length; j++){
                Item bottom = pants.get(j);
                if(bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                    if(bottom.getStyle() == top.getStyle()){
                    Outfit outfit = new Outfit();
                    outfit.setId((long) outfitID);
                    outfitID += 1;
                    List<Item> outfitItems = new ArrayList<Item>();
                    outfitItems.add(top);
                    outfitItems.add(bottom);
                    outfit.setItems(outfitItems);
                    outfitList.add(outfit);
                    System.out.println(outfit);
//                    writeOutfitToFile(outfit);
                }
            }
            for(int j = 0; j < skirts.toArray().length; j++){
                Item bottom = skirts.get(j);
                if(bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                    if(bottom.getStyle() == top.getStyle()){
                    Outfit outfit = new Outfit();
                    outfit.setId((long) outfitID);
                    outfitID += 1;
                    List<Item> outfitItems = new ArrayList<Item>();
                    outfitItems.add(top);
                    outfitItems.add(bottom);
                    outfit.setItems(outfitItems);
                    outfitList.add(outfit);
                    System.out.println(outfit);
//                    writeOutfitToFile(outfit);
                }
            }

        }
        for(int i = 0; i < shirts.toArray().length; i++){
            Item top = shirts.get(i);
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
                    outfitID += 1;
                    List<Item> outfitItems = new ArrayList<Item>();
                    outfitItems.add(top);
                    outfitItems.add(bottom);
                    outfit.setItems(outfitItems);
                    outfitList.add(outfit);
                    System.out.println(outfit);
//                    writeOutfitToFile(outfit);
                }
            }
            for(int j = 0; j < pants.toArray().length; j++){
                Item bottom = pants.get(j);
                if(bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                    if(bottom.getStyle() == top.getStyle()){
                    Outfit outfit = new Outfit();
                    outfit.setId((long) outfitID);
                    outfitID += 1;
                    List<Item> outfitItems = new ArrayList<Item>();
                    outfitItems.add(top);
                    outfitItems.add(bottom);
                    outfit.setItems(outfitItems);
                    outfitList.add(outfit);
                    System.out.println(outfit);
//                    writeOutfitToFile(outfit);
                }
            }
            for(int j = 0; j < skirts.toArray().length; j++){
                Item bottom = skirts.get(j);
                if(bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                    if(bottom.getStyle() == top.getStyle()){
                    Outfit outfit = new Outfit();
                    outfit.setId((long) outfitID);
                    outfitID += 1;
                    List<Item> outfitItems = new ArrayList<Item>();
                    outfitItems.add(top);
                    outfitItems.add(bottom);
                    outfit.setItems(outfitItems);
                    outfitList.add(outfit);
                    System.out.println(outfit);
//                    writeOutfitToFile(outfit);
                }
            }

        }
        for(int i = 0; i < tshirts.toArray().length; i++){
            Item top = tshirts.get(i);
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
                    outfitID += 1;
                    List<Item> outfitItems = new ArrayList<Item>();
                    outfitItems.add(top);
                    outfitItems.add(bottom);
                    outfit.setItems(outfitItems);
                    outfitList.add(outfit);
                    System.out.println(outfit);
//                    writeOutfitToFile(outfit);
                }
            }
            for(int j = 0; j < pants.toArray().length; j++){
                Item bottom = pants.get(j);
                if(bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                    if(bottom.getStyle() == top.getStyle()){
                    Outfit outfit = new Outfit();
                    outfit.setId((long) outfitID);
                    outfitID += 1;
                    List<Item> outfitItems = new ArrayList<Item>();
                    outfitItems.add(top);
                    outfitItems.add(bottom);
                    outfit.setItems(outfitItems);
                    outfitList.add(outfit);
                    System.out.println(outfit);
//                    writeOutfitToFile(outfit);
                }
            }
            for(int j = 0; j < skirts.toArray().length; j++){
                Item bottom = skirts.get(j);
                if(bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                    if(bottom.getStyle() == top.getStyle()){
                    Outfit outfit = new Outfit();
                    outfit.setId((long) outfitID);
                    outfitID += 1;
                    List<Item> outfitItems = new ArrayList<Item>();
                    outfitItems.add(top);
                    outfitItems.add(bottom);
                    outfit.setItems(outfitItems);
                    outfitList.add(outfit);
                    System.out.println(outfit);
//                    writeOutfitToFile(outfit);
                }
            }

        }
        return outfitList;
    }
    @Override
    public List<Outfit> recommendAnalogousOutfit(){
        int outfitID;
        JSONParser parser = new JSONParser();
        try{
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("src/main/java/com/example/smartwardrobe/json/outfits.json")); ;
            outfitID = jsonArray.size();
        } catch (ParseException | IOException e) {
            e.printStackTrace();
            outfitID = 1;
        }
        ColorGenerator colorGenerator = new ColorGenerator();
        List<Outfit> outfitList = new ArrayList<Outfit>();
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
            ItemColor[] colors = colorGenerator.analogous(topColor);
            if(colors != null){
                ItemColor firstColor = colors[0];
                ItemColor secondColor = colors[1];
                for(int j = 0; j < jeans.toArray().length; j++){
                    Item bottom = jeans.get(j);
                    if(bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                        if(bottom.getStyle() == top.getStyle()){
                        Outfit outfit = new Outfit();
                        outfit.setId((long) outfitID);
                        outfitID += 1;
                        List<Item> outfitItems = new ArrayList<Item>();
                        outfitItems.add(top);
                        outfitItems.add(bottom);
                        outfit.setItems(outfitItems);
                        outfitList.add(outfit);
                        System.out.println(outfit);
//                        writeOutfitToFile(outfit);
                    }
                }
                for(int j = 0; j < pants.toArray().length; j++){
                    Item bottom = pants.get(j);
                    if(bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                        if(bottom.getStyle() == top.getStyle()){
                        Outfit outfit = new Outfit();
                        outfit.setId((long) outfitID);
                        outfitID += 1;
                        List<Item> outfitItems = new ArrayList<Item>();
                        outfitItems.add(top);
                        outfitItems.add(bottom);
                        outfit.setItems(outfitItems);
                        outfitList.add(outfit);
                        System.out.println(outfit);
//                    writeOutfitToFile(outfit);
                    }
                }
                for(int j = 0; j < skirts.toArray().length; j++){
                    Item bottom = skirts.get(j);
                    if(bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                        if(bottom.getStyle() == top.getStyle()){
                        Outfit outfit = new Outfit();
                        outfit.setId((long) outfitID);
                        outfitID += 1;
                        List<Item> outfitItems = new ArrayList<Item>();
                        outfitItems.add(top);
                        outfitItems.add(bottom);
                        outfit.setItems(outfitItems);
                        outfitList.add(outfit);
                        System.out.println(outfit);
//                    writeOutfitToFile(outfit);
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
                        if(bottom.getStyle() == top.getStyle()){
                        Outfit outfit = new Outfit();
                        outfit.setId((long) outfitID);
                        outfitID += 1;
                        List<Item> outfitItems = new ArrayList<Item>();
                        outfitItems.add(top);
                        outfitItems.add(bottom);
                        outfit.setItems(outfitItems);
                        outfitList.add(outfit);
                        System.out.println(outfit);
//                        writeOutfitToFile(outfit);
                    }
                }
                for (int j = 0; j < pants.toArray().length; j++) {
                    Item bottom = pants.get(j);
                    if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                        if(bottom.getStyle() == top.getStyle()){
                        Outfit outfit = new Outfit();
                        outfit.setId((long) outfitID);
                        outfitID += 1;
                        List<Item> outfitItems = new ArrayList<Item>();
                        outfitItems.add(top);
                        outfitItems.add(bottom);
                        outfit.setItems(outfitItems);
                        outfitList.add(outfit);
                        System.out.println(outfit);
//                    writeOutfitToFile(outfit);
                    }
                }
                for (int j = 0; j < skirts.toArray().length; j++) {
                    Item bottom = skirts.get(j);
                    if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                        if(bottom.getStyle() == top.getStyle()){
                        Outfit outfit = new Outfit();
                        outfit.setId((long) outfitID);
                        outfitID += 1;
                        List<Item> outfitItems = new ArrayList<Item>();
                        outfitItems.add(top);
                        outfitItems.add(bottom);
                        outfit.setItems(outfitItems);
                        outfitList.add(outfit);
                        System.out.println(outfit);
//                    writeOutfitToFile(outfit);
                    }
                }
            }

        }
        for(int i = 0; i < tshirts.toArray().length; i++){
            Item top = tshirts.get(i);
            ItemColor topColor = top.getItemColor();
            ItemColor[] colors = colorGenerator.analogous(topColor);
            if(colors != null) {
                ItemColor firstColor = colors[0];
                ItemColor secondColor = colors[1];
                for (int j = 0; j < jeans.toArray().length; j++) {
                    Item bottom = jeans.get(j);
                    if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                        if(bottom.getStyle() == top.getStyle())
                        {
                        Outfit outfit = new Outfit();
                        outfit.setId((long) outfitID);
                        outfitID += 1;
                        List<Item> outfitItems = new ArrayList<Item>();
                        outfitItems.add(top);
                        outfitItems.add(bottom);
                        outfit.setItems(outfitItems);
                        outfitList.add(outfit);
                        System.out.println(outfit);
//                        writeOutfitToFile(outfit);
                        }
                }
                for (int j = 0; j < pants.toArray().length; j++) {
                    Item bottom = pants.get(j);
                    if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                        if(bottom.getStyle() == top.getStyle()){
                        Outfit outfit = new Outfit();
                        outfit.setId((long) outfitID);
                        outfitID += 1;
                        List<Item> outfitItems = new ArrayList<Item>();
                        outfitItems.add(top);
                        outfitItems.add(bottom);
                        outfit.setItems(outfitItems);
                        outfitList.add(outfit);
                        System.out.println(outfit);
//                    writeOutfitToFile(outfit);
                    }
                }
                for (int j = 0; j < skirts.toArray().length; j++) {
                    Item bottom = skirts.get(j);
                    if (bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor || bottom.getItemColor() == topColor)
                        if(bottom.getStyle() == top.getStyle()){
                        Outfit outfit = new Outfit();
                        outfit.setId((long) outfitID);
                        outfitID += 1;
                        List<Item> outfitItems = new ArrayList<Item>();
                        outfitItems.add(top);
                        outfitItems.add(bottom);
                        outfit.setItems(outfitItems);
                        outfitList.add(outfit);
                        System.out.println(outfit);
//                    writeOutfitToFile(outfit);
                    }
                }
            }

        }
        return outfitList;
    }
    @Override
    public List<Outfit> recommendPastelOutfit(){
        int outfitID;
        JSONParser parser = new JSONParser();
        try{
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("src/main/java/com/example/smartwardrobe/json/outfits.json")); ;
            outfitID = jsonArray.size();
        } catch (ParseException | IOException e) {
            e.printStackTrace();
            outfitID = 1;
        }
        ColorGenerator colorGenerator = new ColorGenerator();
        List<Outfit> outfitList = new ArrayList<Outfit>();
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
                if(bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor){
                    Outfit outfit = new Outfit();
                    outfit.setId((long) outfitID);
                    outfitID += 1;
                    List<Item> outfitItems = new ArrayList<Item>();
                    outfitItems.add(top);
                    outfitItems.add(bottom);
                    outfit.setItems(outfitItems);
                    outfitList.add(outfit);
                    System.out.println(outfit);
//                    writeOutfitToFile(outfit);
                }
            }
            for(int j = 0; j < pants.toArray().length; j++){
                Item bottom = pants.get(j);
                if(bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor){
                    Outfit outfit = new Outfit();
                    outfit.setId((long) outfitID);
                    outfitID += 1;
                    List<Item> outfitItems = new ArrayList<Item>();
                    outfitItems.add(top);
                    outfitItems.add(bottom);
                    outfit.setItems(outfitItems);
                    outfitList.add(outfit);
                    System.out.println(outfit);
//                    writeOutfitToFile(outfit);
                }
            }
            for(int j = 0; j < skirts.toArray().length; j++){
                Item bottom = skirts.get(j);
                if(bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor){
                    Outfit outfit = new Outfit();
                    outfit.setId((long) outfitID);
                    outfitID += 1;
                    List<Item> outfitItems = new ArrayList<Item>();
                    outfitItems.add(top);
                    outfitItems.add(bottom);
                    outfit.setItems(outfitItems);
                    outfitList.add(outfit);
                    System.out.println(outfit);
//                    writeOutfitToFile(outfit);
                }
            }

        }
        for(int i = 0; i < shirts.toArray().length; i++){
            Item top = shirts.get(i);
            ItemColor topColor = top.getItemColor();
            ItemColor[] colors = colorGenerator.pastel(topColor);
            ItemColor firstColor = colors[0];
            ItemColor secondColor = colors[1];
            for(int j = 0; j < jeans.toArray().length; j++){
                Item bottom = jeans.get(j);
                if(bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor){
                    Outfit outfit = new Outfit();
                    outfit.setId((long) outfitID);
                    outfitID += 1;
                    List<Item> outfitItems = new ArrayList<Item>();
                    outfitItems.add(top);
                    outfitItems.add(bottom);
                    outfit.setItems(outfitItems);
                    outfitList.add(outfit);
                    System.out.println(outfit);
//                    writeOutfitToFile(outfit);
                }
            }
            for(int j = 0; j < pants.toArray().length; j++){
                Item bottom = pants.get(j);
                if(bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor){
                    Outfit outfit = new Outfit();
                    outfit.setId((long) outfitID);
                    outfitID += 1;
                    List<Item> outfitItems = new ArrayList<Item>();
                    outfitItems.add(top);
                    outfitItems.add(bottom);
                    outfit.setItems(outfitItems);
                    outfitList.add(outfit);
                    System.out.println(outfit);
//                    writeOutfitToFile(outfit);
                }
            }
            for(int j = 0; j < skirts.toArray().length; j++){
                Item bottom = skirts.get(j);
                if(bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor){
                    Outfit outfit = new Outfit();
                    outfit.setId((long) outfitID);
                    outfitID += 1;
                    List<Item> outfitItems = new ArrayList<Item>();
                    outfitItems.add(top);
                    outfitItems.add(bottom);
                    outfit.setItems(outfitItems);
                    outfitList.add(outfit);
                    System.out.println(outfit);
//                    writeOutfitToFile(outfit);
                }
            }

        }
        for(int i = 0; i < tshirts.toArray().length; i++){
            Item top = tshirts.get(i);
            ItemColor topColor = top.getItemColor();
            ItemColor[] colors = colorGenerator.pastel(topColor);
            ItemColor firstColor = colors[0];
            ItemColor secondColor = colors[1];
            for(int j = 0; j < jeans.toArray().length; j++){
                Item bottom = jeans.get(j);
                if(bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor){
                    Outfit outfit = new Outfit();
                    outfit.setId((long) outfitID);
                    outfitID += 1;
                    List<Item> outfitItems = new ArrayList<Item>();
                    outfitItems.add(top);
                    outfitItems.add(bottom);
                    outfit.setItems(outfitItems);
                    outfitList.add(outfit);
                    System.out.println(outfit);
//                    writeOutfitToFile(outfit);
                }
            }
            for(int j = 0; j < pants.toArray().length; j++){
                Item bottom = pants.get(j);
                if(bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor){
                    Outfit outfit = new Outfit();
                    outfit.setId((long) outfitID);
                    outfitID += 1;
                    List<Item> outfitItems = new ArrayList<Item>();
                    outfitItems.add(top);
                    outfitItems.add(bottom);
                    outfit.setItems(outfitItems);
                    outfitList.add(outfit);
                    System.out.println(outfit);
//                    writeOutfitToFile(outfit);
                }
            }
            for(int j = 0; j < skirts.toArray().length; j++){
                Item bottom = skirts.get(j);
                if(bottom.getItemColor() == firstColor || bottom.getItemColor() == secondColor){
                    Outfit outfit = new Outfit();
                    outfit.setId((long) outfitID);
                    outfitID += 1;
                    List<Item> outfitItems = new ArrayList<Item>();
                    outfitItems.add(top);
                    outfitItems.add(bottom);
                    outfit.setItems(outfitItems);
                    outfitList.add(outfit);
                    System.out.println(outfit);
//                    writeOutfitToFile(outfit);
                }
            }

        }
        return outfitList;
    }

}
