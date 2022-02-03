package com.example.smartwardrobe.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

class OutfitDtoTest {
    @Test
    void testConstructor() {
        OutfitDto actualOutfitDto = new OutfitDto();
        CoatDto coatDto = new CoatDto();
        coatDto.setCoatCategory("Coat Category");
        coatDto.setId("42");
        actualOutfitDto.setCoat(coatDto);
        actualOutfitDto.setDescription("The characteristics of someone or something");
        assertSame(coatDto, actualOutfitDto.getCoat());
        assertEquals("The characteristics of someone or something", actualOutfitDto.getDescription());
        assertNull(actualOutfitDto.getItems());
    }
}

