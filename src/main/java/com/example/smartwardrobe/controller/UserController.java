package com.example.smartwardrobe.controller;

import com.example.smartwardrobe.model.User;
import com.example.smartwardrobe.model.dto.UserDto;
import com.example.smartwardrobe.service.UserService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUserFromFile")
    public JSONObject saveUserFromFile(){
        return userService.getUsersFromFile();
    }
}
