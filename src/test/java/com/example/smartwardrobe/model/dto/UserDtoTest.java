package com.example.smartwardrobe.model.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDtoTest {

    @Test
    void testConstructor(){
        UserDto userDto = new UserDto();
        userDto.setAge(10);
        userDto.setEyeColor("BLUE");
        userDto.setGender("FEMALE");
        userDto.setId(123L);
        userDto.setUsername("username");
        userDto.setHeight(170);
        userDto.setHairColor("BLONDE");
        userDto.setPassword("password");
        userDto.setWeight(70.5);

        assertEquals(10, userDto.getAge());
        assertEquals("BLUE", userDto.getEyeColor());
        assertEquals("FEMALE", userDto.getGender());
        assertEquals(123L, userDto.getId());
        assertEquals("username", userDto.getUsername());
        assertEquals(170, userDto.getHeight());
        assertEquals("BLONDE", userDto.getHairColor());
        assertEquals("password", userDto.getPassword());
        assertEquals(70.5, userDto.getWeight());
    }

}
