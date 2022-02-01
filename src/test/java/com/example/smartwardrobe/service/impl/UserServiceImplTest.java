package com.example.smartwardrobe.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.smartwardrobe.enums.EyeColor;
import com.example.smartwardrobe.enums.Gender;
import com.example.smartwardrobe.enums.HairColor;
import com.example.smartwardrobe.model.User;
import com.example.smartwardrobe.repository.UserRepository;
import com.example.smartwardrobe.service.ItemService;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONArray;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
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
        //user.setItems(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setUsername("janedoe");
        user.setWeight(10.0);
        when(this.userRepository.save((User) any())).thenReturn(user);
        when(this.passwordEncoder.encode((CharSequence) any())).thenReturn("secret");

        User user1 = new User();
        user1.setAge(1);
        user1.setEyeColor(EyeColor.BLUE);
        user1.setGender(Gender.MALE);
        user1.setHairColor(HairColor.BROWN);
        user1.setHeight(10.0);
        user1.setId(123L);
        //user1.setItems(new ArrayList<>());
        user1.setPassword("iloveyou");
        user1.setUsername("janedoe");
        user1.setWeight(10.0);
        assertSame(user, this.userServiceImpl.saveUser(user1));
        verify(this.userRepository).save((User) any());
        verify(this.passwordEncoder).encode((CharSequence) any());
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
        //user.setItems(new ArrayList<>());
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
        //user.setItems(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setUsername("janedoe");
        user.setWeight(10.0);
        Optional<User> ofResult = Optional.of(user);
        when(this.userRepository.findByUsername((String) any())).thenReturn(ofResult);
        Optional<User> actualFindUserByUsernameResult = this.userServiceImpl.findUserByUsername("janedoe");
        assertSame(ofResult, actualFindUserByUsernameResult);
        assertTrue(actualFindUserByUsernameResult.isPresent());
        verify(this.userRepository).findByUsername((String) any());
    }

    //@Test
//    void testGetUsersFromFile() {
//        JSONArray actualUsersFromFile = this.userServiceImpl.getUsersFromFile();
//        assertEquals(1, actualUsersFromFile.size());
//        assertEquals(10, ((Map<Object, Object>) actualUsersFromFile.get(0)).size());
//    }

    @Test
    void testSaveUserFromFile() {
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

        when(this.userRepository.save((User) any())).thenReturn(user);
        when(this.passwordEncoder.encode((CharSequence) any())).thenReturn("secret");

        User actualSaveUserFromFileResult = this.userServiceImpl.saveUserFromFile();

        assertEquals(20, actualSaveUserFromFileResult.getAge());
        assertTrue(actualSaveUserFromFileResult.isEnabled());
        assertTrue(actualSaveUserFromFileResult.isCredentialsNonExpired());
        assertTrue(actualSaveUserFromFileResult.isAccountNonLocked());
        assertTrue(actualSaveUserFromFileResult.isAccountNonExpired());
        assertEquals(70.5, actualSaveUserFromFileResult.getWeight());
        assertEquals("username", actualSaveUserFromFileResult.getUsername());
        assertEquals("secret", actualSaveUserFromFileResult.getPassword());
        assertEquals(1L, actualSaveUserFromFileResult.getId().longValue());
        assertEquals(170.0, actualSaveUserFromFileResult.getHeight());
        assertEquals(HairColor.BLONDE, actualSaveUserFromFileResult.getHairColor());
        assertEquals(Gender.FEMALE, actualSaveUserFromFileResult.getGender());
        assertEquals(EyeColor.BLUE, actualSaveUserFromFileResult.getEyeColor());
        verify(this.userRepository).save((User) any());
        verify(this.passwordEncoder).encode((CharSequence) any());
    }
}

