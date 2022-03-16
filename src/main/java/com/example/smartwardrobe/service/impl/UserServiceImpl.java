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
    public JSONObject getUsersFromFile() {
        JSONParser parser = new JSONParser();
        try{
            return (JSONObject) parser.parse(new FileReader("src/main/java/com/example/smartwardrobe/json/users.json"));
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return new JSONObject();
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
            jsonObject.put("itemCategory", item.getItemCategory().toString());
            jsonObject.put("itemColor", item.getItemColor().toString());
            if(item.getLastWearing() == null){
                jsonObject.put("lastWearing", null);
            }else{
                jsonObject.put("lastWearing", item.getLastWearing().toString());
            }
            if(item.getLastWashingDay() == null){
                jsonObject.put("lastWashingDay", null);
            }else{
                jsonObject.put("lastWashingDay", item.getLastWearing().toString());
            }
            jsonObject.put("nrOfWearsSinceLastWash", item.getNrOfWearsSinceLastWash());
            jsonObject.put("washingZoneColor", item.getWashingZoneColor().toString());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @Override
    public User saveUserFromFile() {
        JSONObject fromFile = getUsersFromFile();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            User user = objectMapper.readValue(fromFile.toJSONString(), User.class);
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
        if(weight <= 55){
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
        if(weight > 65 && weight <= 70 && height >= 165){
            return Size.M;
        }
        if(weight > 70 && weight <= 75 && height > 170){
            return Size.M;
        }
        if(weight > 70 && weight <= 75 && height >= 155 && height < 170){
            return Size.L;
        }
        if(weight > 75 && weight <= 80 && height < 160){
            return Size.XL;
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