package com.example.smartwardrobe.service.impl;

import com.example.smartwardrobe.enums.EyeColor;
import com.example.smartwardrobe.enums.Gender;
import com.example.smartwardrobe.enums.HairColor;
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
        return null;
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


}
