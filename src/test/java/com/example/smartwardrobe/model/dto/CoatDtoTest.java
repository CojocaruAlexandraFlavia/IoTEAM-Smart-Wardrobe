package com.example.smartwardrobe.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CoatDtoTest {
    @Test
    void testConstructor() {
        CoatDto actualCoatDto = new CoatDto();
        actualCoatDto.setCoatCategory("Coat Category");
        actualCoatDto.setId("42");
        assertEquals("Coat Category", actualCoatDto.getCoatCategory());
        assertEquals("42", actualCoatDto.getId());
    }
}

