package com.example.smartwardrobe.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.smartwardrobe.enums.EyeColor;
import com.example.smartwardrobe.enums.Gender;
import com.example.smartwardrobe.enums.HairColor;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

class UserTest {
    @Test
    void testConstructor() {
        User actualUser = new User();
        actualUser.setAge(1);
        actualUser.setEyeColor(EyeColor.BLUE);
        actualUser.setGender(Gender.MALE);
        actualUser.setHairColor(HairColor.BROWN);
        actualUser.setHeight(10.0);
        actualUser.setId(123L);
        actualUser.setPassword("password");
        actualUser.setUsername("username");
        actualUser.setWeight(10.0);
        assertEquals(1, actualUser.getAge());
        assertEquals(EyeColor.BLUE, actualUser.getEyeColor());
        assertEquals(Gender.MALE, actualUser.getGender());
        assertEquals(HairColor.BROWN, actualUser.getHairColor());
        assertEquals(10.0, actualUser.getHeight());
        assertEquals(123L, actualUser.getId().longValue());
        assertEquals("password", actualUser.getPassword());
        assertEquals("username", actualUser.getUsername());
        assertEquals(10.0, actualUser.getWeight());
        assertTrue(actualUser.isAccountNonExpired());
        assertTrue(actualUser.isAccountNonLocked());
        assertTrue(actualUser.isCredentialsNonExpired());
        assertTrue(actualUser.isEnabled());
        assertEquals("User{id=123, eyeColor=BLUE, weight=10.0, height=10.0, gender=MALE, age=1, hairColor=BROWN, username="
                + "'username', password='password', isAccountNonExpired=true, isAccountNonLocked=true, isCredentialsNonExpired"
                + "=true, isEnabled=true}", actualUser.toString());
    }

    @Test
    void testGetAuthorities() {
        List<? extends GrantedAuthority> actualAuthorities = (List<? extends GrantedAuthority>) (new User()).getAuthorities();
        assertEquals(1, actualAuthorities.size());
        assertEquals("ROLE_ADMIN", actualAuthorities.get(0).getAuthority());
    }

    @Test
    void testAllArgsConstructor(){
        User user = new User(1L, EyeColor.BLUE, 71.5, 170.0, Gender.FEMALE, 21, HairColor.BLONDE,
                "username", "password", true, true,
                true, true);
        assertEquals(1L, user.getId());
        assertEquals(EyeColor.BLUE, user.getEyeColor());
        assertEquals(71.5, user.getWeight());
        assertEquals(170.0, user.getHeight());
        assertEquals(Gender.FEMALE, user.getGender());
        assertEquals(21, user.getAge());
        assertEquals(HairColor.BLONDE, user.getHairColor());
        assertEquals("username", user.getUsername());
        assertEquals("password", user.getPassword());
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertTrue(user.isEnabled());
    }
}

