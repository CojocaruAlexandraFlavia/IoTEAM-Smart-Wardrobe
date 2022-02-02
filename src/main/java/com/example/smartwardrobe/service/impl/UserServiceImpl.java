package com.example.smartwardrobe.service.impl;

import com.example.smartwardrobe.enums.EyeColor;
import com.example.smartwardrobe.enums.Gender;
import com.example.smartwardrobe.enums.HairColor;
import com.example.smartwardrobe.enums.Size;
import com.example.smartwardrobe.model.Item;
import com.example.smartwardrobe.model.User;
import com.example.smartwardrobe.model.dto.UserDto;
import com.example.smartwardrobe.repository.UserRepository;
import com.example.smartwardrobe.service.ItemService;
import com.example.smartwardrobe.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private ItemService itemService;

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
        JSONParser jsonParser = new JSONParser();
        ObjectMapper objectMapper = new ObjectMapper();
//        jsonObject.put("id", user.getId().toString());
//        jsonObject.put("eyeColor", user.getEyeColor().toString());
//        jsonObject.put("weight", String.valueOf(user.getWeight()));
//        jsonObject.put("height", String.valueOf(user.getHeight()));
//        jsonObject.put("gender", user.getGender().toString());
//        jsonObject.put("age", String.valueOf(user.getAge()));
//        jsonObject.put("hairColor", user.getHairColor());
//        jsonObject.put("items", itemService.createJsonArrayOfItems(user.getItems()));
//        jsonObject.put("username", user.getUsername());
//        jsonObject.put("password", user.getPassword());
        // jsonObject.put("coat", outfit.getCoat().toString());
        // jsonObject.put("items", itemService.createJsonArrayOfItems(outfit.getItems()));
       // jsonArray.add(jsonObject);
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(objectMapper.writeValueAsString(user));
            jsonArray.add(jsonObject);
            try (FileWriter file = new FileWriter("src/main/java/com/example/smartwardrobe/json/users.json")) {
                file.write(jsonArray.toJSONString());
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public JSONArray getUsersFromFile() {
        JSONParser parser = new JSONParser();
        try{
            return (JSONArray) parser.parse(new FileReader("src/main/java/com/example/smartwardrobe/json/users.json"));
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return new JSONArray();
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
    public static List<Item> convertObjectToList(Object obj) {
        List<Item> list = new ArrayList<>();
        if (obj.getClass().isArray()) {
            list = Arrays.asList((Item[])obj);
        } else if (obj instanceof Collection) {
            list = new ArrayList<>((Collection<Item>)obj);
        }
        return list;
    }

    @Override
    public User saveUserFromFile() {
        JSONArray fromFile = getUsersFromFile();
        JSONObject userFromFile = (JSONObject) fromFile.get(0);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            User user = objectMapper.readValue(userFromFile.toJSONString(), User.class);
            saveUser(user);
            return user;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new User();
    }

    @Override
    public User convertDtoToEntity(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        if(userDto.getId() != 0){
            user.setId(userDto.getId());
        }
        user.setEyeColor(EyeColor.valueOf(userDto.getEyeColor()));
        user.setAge(userDto.getAge());
        user.setGender(Gender.valueOf(userDto.getGender()));
        user.setWeight(userDto.getWeight());
        user.setHeight(user.getHeight());
        user.setHairColor(HairColor.valueOf(userDto.getHairColor()));
        user.setPassword(userDto.getPassword());
        return user;
    }


    @Override
    public Size calculateUserSize(User user){
        double weight = user.getWeight();
        double height = user.getHeight();
        if(weight <= 50){
            return Size.XS;
        }
        if(weight > 50 && weight <= 55 && height >= 155){
            return Size.XS;
        }
        if(weight > 50 && weight <= 55 && height < 155){
            return Size.S;
        }
        if(weight > 55 && weight <= 60 && height > 170){
            return Size.XS;
        }
        if(weight > 55 && weight <= 60 && height >= 155 && height < 170){
            return Size.S;
        }
        if(weight > 60 && weight <= 65 && height > 160){
            return Size.S;
        }
        if(weight > 60 && weight <= 65 && height < 160){
            return Size.M;
        }
        if(weight > 65 && weight <= 70 && height < 165){
            return Size.L;
        }
        if(weight > 65 && weight <= 70 && height >= 165){
            return Size.M;
        }
        if(weight > 70 && weight <= 75 && height < 155){
            return Size.XL;
        }
        if(weight > 70 && weight <= 75 && height >= 155 && height < 170){
            return Size.L;
        }
        if(weight > 70 && weight <= 75 && height > 170){
            return Size.M;
        }
        if(weight > 75 && weight <= 80 && height < 160){
            return Size.XL;
        }
        if(weight > 75 && weight <= 80 && height >= 160 ){
            return Size.L;
        }
        if(weight > 80 && weight <= 90){
            return Size.XL;
        }
        if(weight > 90){
            return Size.XXL;
        }

        return null;
    }

}