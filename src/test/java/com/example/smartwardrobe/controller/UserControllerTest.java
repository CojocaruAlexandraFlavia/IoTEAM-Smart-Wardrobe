package com.example.smartwardrobe.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.example.smartwardrobe.enums.EyeColor;
import com.example.smartwardrobe.enums.Gender;
import com.example.smartwardrobe.enums.HairColor;
import com.example.smartwardrobe.model.User;
import com.example.smartwardrobe.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    @Test
    void testSaveUserFromFile() throws Exception {
        when(this.userService.getUsersFromFile()).thenReturn(new JSONObject());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/getUserFromFile");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{}"));
    }

    @Test
    void testSaveUserFromFile2() throws Exception {
        when(this.userService.getUsersFromFile()).thenReturn(new JSONObject());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/user/getUserFromFile");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{}"));
    }
}

