package com.example.smartwardrobe.mqtt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MqttSubscribeModelTest {
    @Test
    void testConstructor() {
        MqttSubscribeModel actualMqttSubscribeModel = new MqttSubscribeModel();
        actualMqttSubscribeModel.setId(1);
        actualMqttSubscribeModel.setMessage("Not all who wander are lost");
        actualMqttSubscribeModel.setQos(1);
        assertEquals(1, actualMqttSubscribeModel.getId().intValue());
        assertEquals("Not all who wander are lost", actualMqttSubscribeModel.getMessage());
        assertEquals(1, actualMqttSubscribeModel.getQos().intValue());
    }
}

