package com.example.smartwardrobe.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ItemDtoTest {
    @Test
    void testConstructor() {
        ItemDto actualItemDto = new ItemDto();
        actualItemDto.setCode("Code");
        actualItemDto.setId(123L);
        actualItemDto.setItemCategory("Item Category");
        actualItemDto.setItemColor("Item Color");
        actualItemDto.setLastWashingDay("Last Washing Day");
        actualItemDto.setLastWearing("Last Wearing");
        actualItemDto.setMaterial("Material");
        actualItemDto.setNrOfWearsSinceLastWash(1);
        actualItemDto.setSize("Size");
        actualItemDto.setStyle("Style");
        actualItemDto.setWashingZoneColor("Washing ZOne Color");
        assertEquals("Code", actualItemDto.getCode());
        assertEquals(123L, actualItemDto.getId());
        assertEquals("Item Category", actualItemDto.getItemCategory());
        assertEquals("Item Color", actualItemDto.getItemColor());
        assertEquals("Last Washing Day", actualItemDto.getLastWashingDay());
        assertEquals("Last Wearing", actualItemDto.getLastWearing());
        assertEquals("Material", actualItemDto.getMaterial());
        assertEquals(1, actualItemDto.getNrOfWearsSinceLastWash());
        assertEquals("Size", actualItemDto.getSize());
        assertEquals("Style", actualItemDto.getStyle());
        assertEquals("Washing ZOne Color", actualItemDto.getWashingZoneColor());
    }
}

