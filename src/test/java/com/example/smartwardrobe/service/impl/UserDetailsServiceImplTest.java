package com.example.smartwardrobe.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.smartwardrobe.enums.EyeColor;
import com.example.smartwardrobe.enums.Gender;
import com.example.smartwardrobe.enums.HairColor;
import com.example.smartwardrobe.model.User;
import com.example.smartwardrobe.repository.UserRepository;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserDetailsServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserDetailsServiceImplTest {
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testLoadUserByUsername() throws UsernameNotFoundException {
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
        when(this.userRepository.findByUsername(any())).thenReturn(ofResult);
        UserDetails actualLoadUserByUsernameResult = this.userDetailsServiceImpl.loadUserByUsername("foo");
        assertEquals(1, actualLoadUserByUsernameResult.getAuthorities().size());
        assertEquals(
                "org.springframework.security.core.userdetails.User [Username=janedoe, Password=[PROTECTED], Enabled=true,"
                        + " AccountNonExpired=true, credentialsNonExpired=true, AccountNonLocked=true, Granted Authorities=[ROLE"
                        + "_ADMIN]]",
                actualLoadUserByUsernameResult.toString());
        assertTrue(actualLoadUserByUsernameResult.isEnabled());
        assertTrue(actualLoadUserByUsernameResult.isCredentialsNonExpired());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonLocked());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonExpired());
        assertEquals("janedoe", actualLoadUserByUsernameResult.getUsername());
        assertEquals("iloveyou", actualLoadUserByUsernameResult.getPassword());
        verify(this.userRepository).findByUsername(any());
    }

    @Test
    void testLoadUserByUsername2() throws UsernameNotFoundException {
        when(this.userRepository.findByUsername(any())).thenThrow(new UsernameNotFoundException("Msg"));
        assertThrows(UsernameNotFoundException.class, () -> this.userDetailsServiceImpl.loadUserByUsername("foo"));
        verify(this.userRepository).findByUsername(any());
    }
}

