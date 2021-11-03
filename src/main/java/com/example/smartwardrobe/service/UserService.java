package com.example.smartwardrobe.service;

import com.example.smartwardrobe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserService{

    User saveUser(User user);
    User findUserById(Long id);

}
