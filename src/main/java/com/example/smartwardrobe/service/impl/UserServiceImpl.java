package com.example.smartwardrobe.service.impl;

import com.example.smartwardrobe.enums.EyeColor;
import com.example.smartwardrobe.enums.Gender;
import com.example.smartwardrobe.enums.HairColor;
import com.example.smartwardrobe.model.Item;
import com.example.smartwardrobe.model.Outfit;
import com.example.smartwardrobe.model.User;
import com.example.smartwardrobe.repository.UserRepository;
import com.example.smartwardrobe.service.ItemService;
import com.example.smartwardrobe.service.UserService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ItemService userService;

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void writeUserToFile(User user) {
        JSONArray jsonArray = getUsersFromFile();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", user.getId().toString());
        jsonObject.put("eyeColor", user.getEyeColor().toString());
        jsonObject.put("weight", String.valueOf(user.getWeight()));
        jsonObject.put("height", String.valueOf(user.getHeight()));
        jsonObject.put("gender", user.getGender().toString());
        jsonObject.put("age", String.valueOf(user.getAge()));
        jsonObject.put("hairColor", user.getHairColor());
        jsonObject.put("items", userService.createJsonArrayOfItems(user.getItems()));
        jsonObject.put("username", user.getUsername());
        jsonObject.put("password", user.getPassword());
        // jsonObject.put("coat", outfit.getCoat().toString());
        // jsonObject.put("items", itemService.createJsonArrayOfItems(outfit.getItems()));
        jsonArray.add(jsonObject);
        try {
            FileWriter file = new FileWriter("src/main/java/com/example/smartwardrobe/json/users.json");
            file.write(jsonArray.toJSONString());
            file.close();
            //Files.write(Paths.get("src/main/java/com/example/smartwardrobe/json/outfits.json"),jsonObject.toJSONString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public JSONArray getUsersFromFile() {
        JSONParser parser = new JSONParser();
        try{
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("src/main/java/com/example/smartwardrobe/json/users.json")); ;
            return jsonArray;
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Item> convertObjectToList(Object obj) {
        List<Item> list = new ArrayList<>();
        if (obj.getClass().isArray()) {
            list = Arrays.asList((Item[])obj);
        } else if (obj instanceof Collection) {
            list = new ArrayList<Item>((Collection<Item>)obj);
        }
        return list;
    }

    @Override
    public User saveUserFromFile(User user) {


        JSONParser parser = new JSONParser();
        try{
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("src/main/java/com/example/smartwardrobe/json/users.json")); ;
            user.setId(Long.parseLong(jsonObject.get("id").toString()));
            user.setEyeColor(EyeColor.valueOf((jsonObject.get("eyeColor").toString())));
            user.setWeight(Double.parseDouble(jsonObject.get("weight").toString()));
            user.setHeight(Double.parseDouble(jsonObject.get("height").toString()));
            user.setGender(Gender.valueOf((jsonObject.get("gender").toString())));
            user.setAge(Integer.parseInt(jsonObject.get("age").toString()));
            user.setHairColor(HairColor.valueOf((jsonObject.get("hairColor").toString())));
            List<Item> listOfItems = convertObjectToList(jsonObject.get("items"));
            user.setItems(listOfItems);
            user.setUsername(jsonObject.get("username").toString());
//            user.setPassword(jsonObject.get("password").toString());
            user.setPassword(passwordEncoder.encode(jsonObject.get("password").toString()));
            saveUser(user);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public JSONArray createJsonArrayOfItems(List<Item> items) {
        JSONArray jsonArray = new JSONArray();
        for(Item item: items){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", item.getId().toString());
            jsonObject.put("material", item.getMaterial().toString());
            jsonObject.put("size", item.getSize().toString());
            jsonObject.put("code", item.getCode());
            jsonObject.put("color", item.getItemColor().toString());
            jsonObject.put("style", item.getStyle().toString());
            jsonObject.put("category", item.getItemCategory().toString());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @Override
    public String calculateUserSize(User user){
        double weight = user.getWeight();
        double height = user.getHeight();
        if(weight <= 50){
            return "XS";
        }
        if(weight > 50 && weight <= 55 && height >= 155){
            return "XS";
        }
        if(weight > 50 && weight <= 55 && height < 155){
            return "S";
        }
        if(weight > 55 && weight <= 60 && height > 170){
            return "XS";
        }
        if(weight > 55 && weight <= 60 && height >= 155 && height < 170){
            return "S";
        }
        if(weight > 60 && weight <= 65 && height > 160){
            return "S";
        }
        if(weight > 60 && weight <= 65 && height < 160){
            return "M";
        }
        if(weight > 65 && weight <= 70 && height < 165){
            return "L";
        }
        if(weight > 65 && weight <= 70 && height >= 165){
            return "M";
        }
        if(weight > 70 && weight <= 75 && height < 155){
            return "XL";
        }
        if(weight > 70 && weight <= 75 && height >= 155 && height < 170){
            return "L";
        }
        if(weight > 70 && weight <= 75 && height > 170){
            return "M";
        }
        if(weight > 75 && weight <= 80 && height < 160){
            return "XL";
        }
        if(weight > 75 && weight <= 80 && height >= 160 ){
            return "L";
        }
        if(weight > 80 && weight <= 90){
            return "XL";
        }
        if(weight > 90){
            return "XXL";
        }

        return null;
    }

}