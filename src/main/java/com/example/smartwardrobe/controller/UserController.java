package com.example.smartwardrobe.controller;

import com.example.smartwardrobe.model.User;
import com.example.smartwardrobe.model.dto.UserDto;
import com.example.smartwardrobe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User saveUser(@RequestBody UserDto userDto){
        return userService.saveUser(userService.convertDtoToEntity(userDto));
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id){
        return userService.findUserById(id);
    }

//    @PostMapping("/saveUser")
//    public User saveUserFromFile(User user){
//        return userService.saveUserFromFile(user);
//    }
}
