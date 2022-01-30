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

//    @Test
//    void testGetUserById() throws Exception {
//        User user = new User();
//        user.setAge(1);
//        user.setEyeColor(EyeColor.BLUE);
//        user.setGender(Gender.MALE);
//        user.setHairColor(HairColor.BROWN);
//        user.setHeight(10.0);
//        user.setId(123L);
//        //user.setItems(new ArrayList<>());
//        user.setPassword("iloveyou");
//        user.setUsername("janedoe");
//        user.setWeight(10.0);
//        when(this.userService.findUserById(any())).thenReturn(user);
//        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/{id}", 123L);
//        MockMvcBuilders.standaloneSetup(this.userController)
//                .build()
//                .perform(requestBuilder)
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//                .andExpect(MockMvcResultMatchers.content()
//                        .string(
//                                "{\"id\":123,\"eyeColor\":\"BLUE\",\"weight\":10.0,\"height\":10.0,\"gender\":\"MALE\",\"age\":1," +
//                                        "\"hairColor\":\"BROWN\",\"username\":\"janedoe\",\"password\":\"iloveyou\",\"enabled\":true," +
//                                        "\"accountNonLocked\":true,\"authorities\":[{\"authority\":\"ROLE_ADMIN}]\"," +
//                                        "\"credentialsNonExpired\":true,\"accountNonExpired\":true}"));
//
//    }

    @Test
    void testSaveUser() throws Exception {
        User user = new User();
        user.setAge(1);
        user.setEyeColor(EyeColor.BLUE);
        user.setGender(Gender.MALE);
        user.setHairColor(HairColor.BROWN);
        user.setHeight(10.0);
        user.setId(123L);
        //user.setItems(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setUsername("janedoe");
        user.setWeight(10.0);
        String content = (new ObjectMapper()).writeValueAsString(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(405));
    }
}

