package com.example.smartwardrobe.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.example.smartwardrobe.enums.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class CoatTest {

    @Test
    void testNoArgsConstructor(){
        Coat coat = new Coat();
        coat.setId(123L);
        coat.setCoatCategory(CoatCategory.JACKET);
        List<Outfit> outfits = new ArrayList<>();
        coat.setOutfits(outfits);
        coat.setCode("123");
        coat.setItemColor(ItemColor.RED);
        coat.setNrOfWearsSinceLastWash(0);
        coat.setLastWashingDay(LocalDate.now());
        coat.setLastWearing(LocalDate.now());
        coat.setMaterial(Material.CASHMERE);
        coat.setSize(Size.S);
        coat.setStyle(Style.OFFICE);
        coat.setWashingZoneColor(WashingZoneColor.COLOR);

        assertEquals(CoatCategory.JACKET, coat.getCoatCategory());
        assertEquals(123L, coat.getId().longValue());
        assertSame(outfits, coat.getOutfits());
        assertEquals("123", coat.getCode());
        assertEquals(ItemColor.RED, coat.getItemColor());
        assertEquals(0, coat.getNrOfWearsSinceLastWash());
        assertEquals(LocalDate.now(), coat.getLastWashingDay());
        assertEquals(LocalDate.now(), coat.getLastWearing());
        assertEquals(Material.CASHMERE, coat.getMaterial());
        assertEquals(Size.S, coat.getSize());
        assertEquals(Style.OFFICE, coat.getStyle());
        assertEquals(WashingZoneColor.COLOR, coat.getWashingZoneColor());
        assertEquals("{" +
                "\"id\":" + coat.getId() +
                ",\"material\":\"" + coat.getMaterial().toString()+"\"" +
                ",\"size\":\"" + coat.getSize().toString() + "\""+
                ",\"code\":\"" + coat.getCode() + "\"" +
                ",\"itemColor\":\"" + coat.getItemColor().toString() +"\""+
                ",\"style\":\"" + coat.getStyle().toString() +"\""+
                ",\"coatCategory\":\"" + coat.getCoatCategory().toString() +"\""+
                ",\"washingZoneColor\":\"" + coat.getWashingZoneColor().toString() +"\""+
                '}', coat.toString());
    }

    @Test
    void testAllArgsConstructor(){
        List<Outfit> outfitList = new ArrayList<>();
        Coat coat = new Coat(1L,Material.CASHMERE, Size.S, "123", ItemColor.RED,
                Style.OFFICE, CoatCategory.JACKET, LocalDate.now(), LocalDate.now(),
                0, WashingZoneColor.COLOR, outfitList);

        assertEquals(CoatCategory.JACKET, coat.getCoatCategory());
        assertEquals(1L, coat.getId().longValue());
        assertSame(outfitList, coat.getOutfits());
        assertEquals("123", coat.getCode());
        assertEquals(ItemColor.RED, coat.getItemColor());
        assertEquals(0, coat.getNrOfWearsSinceLastWash());
        assertEquals(LocalDate.now(), coat.getLastWashingDay());
        assertEquals(LocalDate.now(), coat.getLastWearing());
        assertEquals(Material.CASHMERE, coat.getMaterial());
        assertEquals(Size.S, coat.getSize());
        assertEquals(Style.OFFICE, coat.getStyle());
        assertEquals(WashingZoneColor.COLOR, coat.getWashingZoneColor());
    }
}

