package com.example.smartwardrobe.service;

import com.example.smartwardrobe.model.User;

import java.util.Optional;

public interface UserService{

    User saveUser(User user);
    User findUserById(Long id);
    Optional<User> findUserByUsername(String username);

}
