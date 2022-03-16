package com.example.smartwardrobe.service;

import com.example.smartwardrobe.enums.Size;
import com.example.smartwardrobe.model.Item;
import com.example.smartwardrobe.model.Outfit;
import com.example.smartwardrobe.model.User;
import com.example.smartwardrobe.model.dto.UserDto;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Optional;

public interface UserService{

    User saveUser(User user);
    User findUserById(Long id);
    Optional<User> findUserByUsername(String username);
    JSONObject getUsersFromFile();
    JSONArray createJsonArrayOfItems(List<Item> items);
    Size calculateUserSize(User user);
    User saveUserFromFile();
    User convertDtoToEntity(UserDto userDto);

}