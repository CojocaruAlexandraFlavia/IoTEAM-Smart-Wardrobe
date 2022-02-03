package com.example.smartwardrobe.mqtt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class MqttPublishModelTest {
    @Test
    void testConstructor() {
        MqttPublishModel actualMqttPublishModel = new MqttPublishModel();
        actualMqttPublishModel.setMessage("Not all who wander are lost");
        actualMqttPublishModel.setQos(1);
        actualMqttPublishModel.setRetained(true);
        actualMqttPublishModel.setTopic("Topic");
        assertEquals("Not all who wander are lost", actualMqttPublishModel.getMessage());
        assertEquals(1, actualMqttPublishModel.getQos().intValue());
        assertTrue(actualMqttPublishModel.getRetained());
        assertEquals("Topic", actualMqttPublishModel.getTopic());
    }
}

