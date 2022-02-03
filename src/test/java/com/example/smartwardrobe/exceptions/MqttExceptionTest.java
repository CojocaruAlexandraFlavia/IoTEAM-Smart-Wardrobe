package com.example.smartwardrobe.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class MqttExceptionTest {
    @Test
    void testConstructor() {
        MqttException actualMqttException = new MqttException("An error occurred");
        assertNull(actualMqttException.getCause());
        assertEquals("com.example.smartwardrobe.exceptions.MqttException: An error occurred",
                actualMqttException.toString());
        assertEquals(0, actualMqttException.getSuppressed().length);
        assertEquals("An error occurred", actualMqttException.getMessage());
        assertEquals("An error occurred", actualMqttException.getLocalizedMessage());
    }
}

