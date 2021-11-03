package com.example.smartwardrobe.service.impl;

import com.example.smartwardrobe.model.User;
import com.example.smartwardrobe.repository.UserRepository;
import com.example.smartwardrobe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }
}
