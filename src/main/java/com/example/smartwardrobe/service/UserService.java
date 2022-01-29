package com.example.smartwardrobe.service;

import com.example.smartwardrobe.model.Item;
import com.example.smartwardrobe.model.Outfit;
import com.example.smartwardrobe.model.User;
import org.json.simple.JSONArray;

import java.util.List;
import java.util.Optional;

public interface UserService{

    User saveUser(User user);
    User findUserById(Long id);
    Optional<User> findUserByUsername(String username);
    void writeUserToFile(User user);
    JSONArray getUsersFromFile();
    User saveUserFromFile(User user);
    JSONArray createJsonArrayOfItems(List<Item> items);
    String calculateUserSize(User user);

}
