package com.example.smartwardrobe.service;

import com.example.smartwardrobe.model.User;
import com.example.smartwardrobe.model.dto.UserDto;
import org.json.simple.JSONArray;

import java.util.Optional;

public interface UserService{

    User saveUser(User user);
    User findUserById(Long id);
    Optional<User> findUserByUsername(String username);
    void writeUserToFile(User user);
    JSONArray getUsersFromFile();
    User saveUserFromFile();
    //JSONArray createJsonArrayOfItems(List<Item> items);
    User convertDtoToEntity(UserDto userDto);

}
