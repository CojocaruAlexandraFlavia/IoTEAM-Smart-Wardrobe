package com.example.smartwardrobe.controller;

import com.example.smartwardrobe.model.User;
import com.example.smartwardrobe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User saveUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id){
        return userService.findUserById(id);
    }

}
