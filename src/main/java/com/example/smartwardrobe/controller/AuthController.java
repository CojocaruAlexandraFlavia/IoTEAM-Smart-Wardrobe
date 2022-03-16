package com.example.smartwardrobe.controller;

import com.example.smartwardrobe.authentication.UsernameAndPasswordAuthenticationRequest;
import com.example.smartwardrobe.model.User;
import com.example.smartwardrobe.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Optional;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@RestController
public class AuthController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Autowired
    MqttController mqttController;

    @Autowired
    public AuthController(UserService userService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signIn")
    public ResponseEntity<String> authenticateUser(@NotNull @RequestBody UsernameAndPasswordAuthenticationRequest loginRequest) throws Exception {

        Object existingAuthenticated = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(existingAuthenticated != "anonymousUser"){
            return ResponseEntity.badRequest().body("Already logged in");
        }

        Optional<User> user = userService.findUserByUsername(loginRequest.getUsername());

        if(user.isEmpty() || !passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword())){
            return ResponseEntity.badRequest().body("Username or password incorrect!");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword()));

        getContext().setAuthentication(authentication);
        mqttController.publishAllDirtyClothes();
        mqttController.publishItems();
        return ResponseEntity.ok().body("Login successful for: \n " + loginRequest.getUsername());

    }

}
