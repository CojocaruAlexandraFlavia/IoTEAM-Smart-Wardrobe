package com.example.smartwardrobe.authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class UsernameAndPasswordAuthenticationRequestTest {
    @Test
    void testConstructor() {
        UsernameAndPasswordAuthenticationRequest actualUsernameAndPasswordAuthenticationRequest = new UsernameAndPasswordAuthenticationRequest();
        actualUsernameAndPasswordAuthenticationRequest.setPassword("iloveyou");
        actualUsernameAndPasswordAuthenticationRequest.setUsername("janedoe");
        assertEquals("iloveyou", actualUsernameAndPasswordAuthenticationRequest.getPassword());
        assertEquals("janedoe", actualUsernameAndPasswordAuthenticationRequest.getUsername());
    }
}

