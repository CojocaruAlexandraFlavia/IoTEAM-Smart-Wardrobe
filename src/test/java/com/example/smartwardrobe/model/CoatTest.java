package com.example.smartwardrobe.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.example.smartwardrobe.enums.CoatCategory;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class CoatTest {
    @Test
    void testConstructor() {
        Coat actualCoat = new Coat();
        actualCoat.setCoatCategory(CoatCategory.JACKET);
        actualCoat.setId(123L);
        ArrayList<Outfit> outfitList = new ArrayList<>();
        actualCoat.setOutfits(outfitList);

        assertEquals(CoatCategory.JACKET, actualCoat.getCoatCategory());
        assertEquals(123L, actualCoat.getId().longValue());
        assertSame(outfitList, actualCoat.getOutfits());
        assertEquals("Coat{id=123, coatCategory=JACKET}", actualCoat.toString());
    }

//    @Test
//    void testAllArgsConstructor(){
//        List<Outfit> outfitList = new ArrayList<>();
//        Coat coat = new Coat(1L, CoatCategory.JACKET, outfitList);
//
//        assertEquals(1L, coat.getId());
//        assertEquals(CoatCategory.JACKET, coat.getCoatCategory());
//        assertEquals(outfitList, coat.getOutfits());
//    }
}

