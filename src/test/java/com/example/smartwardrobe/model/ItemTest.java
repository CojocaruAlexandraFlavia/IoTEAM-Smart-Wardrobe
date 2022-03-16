package com.example.smartwardrobe.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.example.smartwardrobe.enums.ItemCategory;
import com.example.smartwardrobe.enums.ItemColor;
import com.example.smartwardrobe.enums.Material;
import com.example.smartwardrobe.enums.Size;
import com.example.smartwardrobe.enums.Style;
import com.example.smartwardrobe.enums.WashingZoneColor;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class ItemTest {
    @Test
    void testConstructor() {
        Item actualItem = new Item();
        actualItem.setCode("Code");
        actualItem.setId(123L);
        actualItem.setItemCategory(ItemCategory.JEANS);
        actualItem.setItemColor(ItemColor.MOHOGAMY);
        LocalDate ofEpochDayResult = LocalDate.ofEpochDay(1L);
        actualItem.setLastWashingDay(ofEpochDayResult);
        LocalDate ofEpochDayResult1 = LocalDate.ofEpochDay(1L);
        actualItem.setLastWearing(ofEpochDayResult1);
        actualItem.setMaterial(Material.WOOL);
        actualItem.setNrOfWearsSinceLastWash(1);
        ArrayList<Outfit> outfitList = new ArrayList<>();
        actualItem.setOutfits(outfitList);
        actualItem.setSize(Size.M);
        actualItem.setStyle(Style.CASUAL);
        actualItem.setWashingZoneColor(WashingZoneColor.COLOR);

        assertEquals("Code", actualItem.getCode());
        assertEquals(123L, actualItem.getId().longValue());
        assertEquals(ItemCategory.JEANS, actualItem.getItemCategory());
        assertEquals(ItemColor.MOHOGAMY, actualItem.getItemColor());
        assertSame(ofEpochDayResult, actualItem.getLastWashingDay());
        assertSame(ofEpochDayResult1, actualItem.getLastWearing());
        assertEquals(Material.WOOL, actualItem.getMaterial());
        assertEquals(1, actualItem.getNrOfWearsSinceLastWash());
        assertSame(outfitList, actualItem.getOutfits());
        assertEquals(Size.M, actualItem.getSize());
        assertEquals(Style.CASUAL, actualItem.getStyle());
        assertEquals(WashingZoneColor.COLOR, actualItem.getWashingZoneColor());
    }
}

