package com.example.smartwardrobe.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.smartwardrobe.enums.*;
import com.example.smartwardrobe.model.Item;
import com.example.smartwardrobe.model.User;
import com.example.smartwardrobe.model.dto.UserDto;
import com.example.smartwardrobe.repository.UserRepository;
import com.example.smartwardrobe.service.ItemService;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.JSONException;
import org.json.simple.JSONArray;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class UserServiceImplTest {
    @MockBean
    private ItemService itemService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Test
    void testSaveUser() {
        User user = new User();
        user.setAge(1);
        user.setEyeColor(EyeColor.BLUE);
        user.setGender(Gender.MALE);
        user.setHairColor(HairColor.BROWN);
        user.setHeight(10.0);
        user.setId(123L);
        user.setPassword("iloveyou");
        user.setUsername("janedoe");
        user.setWeight(10.0);
        when(this.userRepository.save(any())).thenReturn(user);
        when(this.passwordEncoder.encode(any())).thenReturn("secret");

        User user1 = new User();
        user1.setAge(1);
        user1.setEyeColor(EyeColor.BLUE);
        user1.setGender(Gender.MALE);
        user1.setHairColor(HairColor.BROWN);
        user1.setHeight(10.0);
        user1.setId(123L);
        user1.setPassword("iloveyou");
        user1.setUsername("janedoe");
        user1.setWeight(10.0);
        assertSame(user, this.userServiceImpl.saveUser(user1));
        verify(this.userRepository).save(any());
        verify(this.passwordEncoder).encode(any());
        assertEquals("secret", user1.getPassword());
    }

    @Test
    void testFindUserById() {
        User user = new User();
        user.setAge(1);
        user.setEyeColor(EyeColor.BLUE);
        user.setGender(Gender.MALE);
        user.setHairColor(HairColor.BROWN);
        user.setHeight(10.0);
        user.setId(123L);
        user.setPassword("iloveyou");
        user.setUsername("janedoe");
        user.setWeight(10.0);
        Optional<User> ofResult = Optional.of(user);
        when(this.userRepository.findById(any())).thenReturn(ofResult);
        assertSame(user, this.userServiceImpl.findUserById(123L));
        verify(this.userRepository).findById(any());
    }

    @Test
    void testFindUserByUsername() {
        User user = new User();
        user.setAge(1);
        user.setEyeColor(EyeColor.BLUE);
        user.setGender(Gender.MALE);
        user.setHairColor(HairColor.BROWN);
        user.setHeight(10.0);
        user.setId(123L);
        user.setPassword("iloveyou");
        user.setUsername("janedoe");
        user.setWeight(10.0);
        Optional<User> ofResult = Optional.of(user);
        when(this.userRepository.findByUsername(any())).thenReturn(ofResult);
        Optional<User> actualFindUserByUsernameResult = this.userServiceImpl.findUserByUsername("janedoe");
        assertSame(ofResult, actualFindUserByUsernameResult);
        assertTrue(actualFindUserByUsernameResult.isPresent());
        verify(this.userRepository).findByUsername(any());
    }

    @Test
    void testGetUserFromFile() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 1);
        jsonObject.put("eyeColor", "BLUE");
        UserServiceImpl mock = Mockito.mock(UserServiceImpl.class);
        when(mock.getUsersFromFile()).thenReturn(jsonObject);

        JSONObject result = userServiceImpl.getUsersFromFile();

        JSONAssert.assertEquals(jsonObject.toJSONString(), result.toJSONString(), false);
    }

    @Test
    void testCreateJsonArrayOfItemsWhenEmpty(){
        List<Item> items = new ArrayList<>();
        JSONArray result = userServiceImpl.createJsonArrayOfItems(items);
        assertEquals(0, result.size());
    }

    @Test
    void testCreateJsonArrayOfItemsWhenNotEmpty() throws JsonProcessingException {
        List<Item> items = new ArrayList<>();
        Item item = new Item();
        item.setWashingZoneColor(WashingZoneColor.WHITE);
        item.setItemColor(ItemColor.RED);
        item.setItemCategory(ItemCategory.DRESS);
        item.setStyle(Style.CASUAL);
        item.setMaterial(Material.CASHMERE);
        item.setSize(Size.S);
        item.setCode("123");
        item.setId(1L);
        items.add(item);
        JSONArray result = userServiceImpl.createJsonArrayOfItems(items);
        assertEquals(1, result.size());
    }

    @Test
    void testConvertDtoToEntity(){

        UserDto userDto = new UserDto();
        userDto.setWeight(70);
        userDto.setUsername("username");
        userDto.setPassword("password");
        userDto.setHeight(170);
        userDto.setId(1L);
        userDto.setGender("MALE");
        userDto.setAge(20);
        userDto.setEyeColor("BLUE");
        userDto.setHairColor("BROWN");

        User user = new User();
        user.setUsername(userDto.getUsername());
        if(userDto.getId() != 0){
            user.setId(userDto.getId());
        }
        user.setEyeColor(EyeColor.valueOf(userDto.getEyeColor()));
        user.setAge(userDto.getAge());
        user.setGender(Gender.valueOf(userDto.getGender()));
        user.setWeight(userDto.getWeight());
        user.setHeight(user.getHeight());
        user.setHairColor(HairColor.valueOf(userDto.getHairColor()));
        user.setPassword(userDto.getPassword());

        User result = userServiceImpl.convertDtoToEntity(userDto);

        assertThat(result).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    void testSaveUserFromFile() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("age", 1);
        jsonObject.put("eyeColor", EyeColor.BLUE);
        jsonObject.put("gender", Gender.MALE);
        jsonObject.put("hairColor", HairColor.BROWN);
        jsonObject.put("height", 10.0);
        jsonObject.put("id", 123L);
        jsonObject.put("password", "password");
        jsonObject.put("username", "username");
        jsonObject.put("weight", 10.0);

        User user = new User();
        user.setAge(1);
        user.setEyeColor(EyeColor.BLUE);
        user.setGender(Gender.MALE);
        user.setHairColor(HairColor.BROWN);
        user.setHeight(10.0);
        user.setId(123L);
        user.setPassword("password");
        user.setUsername("username");
        user.setWeight(10.0);

        when(this.userRepository.save(any())).thenReturn(user);
        when(this.passwordEncoder.encode(any())).thenReturn("secret");
        when(Mockito.mock(UserServiceImpl.class).getUsersFromFile()).thenReturn(jsonObject);

        User actualSaveUserFromFileResult = this.userServiceImpl.saveUserFromFile();

        assertEquals(21, actualSaveUserFromFileResult.getAge());
        assertTrue(actualSaveUserFromFileResult.isEnabled());
        assertTrue(actualSaveUserFromFileResult.isCredentialsNonExpired());
        assertTrue(actualSaveUserFromFileResult.isAccountNonLocked());
        assertTrue(actualSaveUserFromFileResult.isAccountNonExpired());
        assertEquals(70.0, actualSaveUserFromFileResult.getWeight());
        assertEquals("username1", actualSaveUserFromFileResult.getUsername());
        assertEquals("secret", actualSaveUserFromFileResult.getPassword());
        assertEquals(1L, actualSaveUserFromFileResult.getId().longValue());
        assertEquals(165.0, actualSaveUserFromFileResult.getHeight());
        assertEquals(HairColor.BLONDE, actualSaveUserFromFileResult.getHairColor());
        assertEquals(Gender.FEMALE, actualSaveUserFromFileResult.getGender());
        assertEquals(EyeColor.BLUE, actualSaveUserFromFileResult.getEyeColor());

        verify(this.userRepository).save(any());
        verify(this.passwordEncoder).encode(any());
    }

    @ParameterizedTest
    @MethodSource("provideUserInput")
    void testCalculateUserSize(User user){

        double weight = user.getWeight();
        double height = user.getHeight();

        Size size;

        if(weight <= 55){
            size =  Size.XS;
        }
        else if(weight > 55 && weight <= 60 && height >= 155 && height < 170){
            size =  Size.S;
        }
        else if(weight > 60 && weight <= 65 && height > 160){
            size =  Size.S;
        }
        else if(weight > 60 && weight <= 65 && height < 160){
            size =  Size.M;
        }
        else if(weight > 65 && weight <= 70 && height >= 165){
            size =  Size.M;
        }
        else if(weight > 70 && weight <= 75 && height > 170){
            size =  Size.M;
        }
        else if(weight > 70 && weight <= 75 && height >= 155 && height < 170){
            size =  Size.L;
        }
        else if(weight > 75 && weight <= 80 && height < 160){
            size =  Size.XL;
        }
        else if(weight > 80 && weight <= 90){
            size =  Size.XL;
        }
        else if(weight > 90){
            size =  Size.XXL;
        }else{
            size = null;
        }

        Size result = userServiceImpl.calculateUserSize(user);

        assertEquals(size, result);

    }

    private static Stream<Arguments> provideUserInput(){
        User user1 = new User();
        user1.setWeight(50);
        User user2 = new User();
        user2.setWeight(57);
        user2.setHeight(160);
        User user3 = new User();
        user3.setWeight(62);
        user3.setHeight(165);
        User user4 = new User();
        user4.setHeight(150);
        user4.setWeight(62);
        User user5 = new User();
        user5.setWeight(67);
        user5.setHeight(167);
        User user6 = new User();
        user6.setHeight(175);
        user6.setWeight(72);
        User user7 = new User();
        user7.setWeight(72);
        user7.setHeight(160);
        User user8 = new User();
        user8.setHeight(155);
        user8.setWeight(77);
        User user9 = new User();
        user9.setWeight(85);
        User user10 = new User();
        user10.setWeight(100);
        return Stream.of(Arguments.of(user1), Arguments.of(user2), Arguments.of(user3), Arguments.of(user4), Arguments.of(user5), Arguments.of(user6),
                Arguments.of(user7), Arguments.of(user8), Arguments.of(user9), Arguments.of(user10));
    }

}

