package com.example.smartwardrobe.service.impl;

import com.example.smartwardrobe.model.User;
import com.example.smartwardrobe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUsername(s);
        if(user.isPresent()){
            User foundUser = user.get();
            return new org.springframework.security.core.userdetails.User(
                    foundUser.getUsername(),
                    foundUser.getPassword(), foundUser.isEnabled(), true,
                    true, true, foundUser.getAuthorities()
            );
        }
        return new org.springframework.security.core.userdetails.User("", "", true,
                true, true, true,
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }
}
