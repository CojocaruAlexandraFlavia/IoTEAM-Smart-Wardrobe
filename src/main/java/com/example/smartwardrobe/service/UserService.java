package com.example.smartwardrobe.service;

import com.example.smartwardrobe.model.User;

public interface UserService{

    User saveUser(User user);
    User findUserById(Long id);

}
