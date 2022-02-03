package com.example.smartwardrobe.colorpalette;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.smartwardrobe.enums.ItemColor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ColorGeneratorTest {
    @Test
    void testColorNumber() {
        Integer[] actualColorNumberResult = (new ColorGenerator()).colorNumber(ItemColor.MOHOGAMY);
        assertEquals(2, actualColorNumberResult.length);
        assertEquals(0, actualColorNumberResult[0]);
        assertEquals(0, actualColorNumberResult[1]);
    }

    @Test
    void testColorNumber2() {
        Integer[] actualColorNumberResult = (new ColorGenerator()).colorNumber(ItemColor.WINE);
        assertEquals(2, actualColorNumberResult.length);
        assertEquals(0, actualColorNumberResult[0]);
        assertEquals(1, actualColorNumberResult[1]);
    }

    @Test
    void testColorNumber3() {
        Integer[] actualColorNumberResult = (new ColorGenerator()).colorNumber(ItemColor.RUST);
        assertEquals(2, actualColorNumberResult.length);
        assertEquals(1, actualColorNumberResult[0]);
        assertEquals(0, actualColorNumberResult[1]);
    }

    @Test
    void testColorNumber4(){
        Integer[] actualColorNumberResult = (new ColorGenerator()).colorNumber(ItemColor.valueOf("SOMETHING"));
        assertEquals(0, actualColorNumberResult.length);
    }

    @Test
    void testColorKind() {
        assertEquals(ItemColor.BRONZE, (new ColorGenerator()).colorKind(1, 1));
    }

    @Test
    void testMonoChromatic() {
        assertEquals(2, (new ColorGenerator()).monoChromatic(ItemColor.MOHOGAMY).length);
        assertEquals(2, (new ColorGenerator()).monoChromatic(ItemColor.MOHOGAMY).length);
        assertEquals(2, (new ColorGenerator()).monoChromatic(ItemColor.WINE).length);
        assertEquals(2, (new ColorGenerator()).monoChromatic(ItemColor.BERRY).length);
        assertEquals(2, (new ColorGenerator()).monoChromatic(ItemColor.RUST).length);
        assertEquals(2, (new ColorGenerator()).monoChromatic(ItemColor.APRICOT).length);

        ColorGenerator colorGenerator = new ColorGenerator();
        ItemColor[] result1 = colorGenerator.monoChromatic(ItemColor.GREY);
        assertEquals(ItemColor.BLACK, result1[0]);
        assertEquals(ItemColor.WHITE, result1[1]);

        ItemColor[] result2 = colorGenerator.monoChromatic(ItemColor.BLACK);
        assertEquals(ItemColor.GREY, result2[0]);
        assertEquals(ItemColor.WHITE, result2[1]);

        ItemColor[] result3 = colorGenerator.monoChromatic(ItemColor.WHITE);
        assertEquals(ItemColor.BLACK, result3[0]);
        assertEquals(ItemColor.GREY, result3[1]);

    }

    @Test
    void testGetPastel() {
        assertEquals(ItemColor.BLUSH, (new ColorGenerator()).getPastel(ItemColor.MOHOGAMY));
        assertEquals(ItemColor.BLUSH, (new ColorGenerator()).getPastel(ItemColor.WINE));
        assertEquals(ItemColor.CANTALOUPE, (new ColorGenerator()).getPastel(ItemColor.RUST));
    }

    @Test
    void testPastel() {
        assertEquals(2, (new ColorGenerator()).pastel(ItemColor.MOHOGAMY).length);
        assertEquals(2, (new ColorGenerator()).pastel(ItemColor.RUST).length);
    }



    @ParameterizedTest
    @ValueSource(strings = {"GREY", "WHITE", "BLACK"})
    void testGetPastel2(String color){
        ColorGenerator colorGenerator = new ColorGenerator();
        ItemColor result1 = colorGenerator.getPastel(ItemColor.valueOf(color));
        assertEquals(ItemColor.GREY, result1);

    }

    @Test
    void testAnalogous() {
        ItemColor[] actualAnalogousResult = (new ColorGenerator()).analogous(ItemColor.MOHOGAMY);
        assertEquals(2, actualAnalogousResult.length);
        assertEquals(ItemColor.DARK_PINK, actualAnalogousResult[0]);
        assertEquals(ItemColor.RUST, actualAnalogousResult[1]);
    }

    @Test
    void testAnalogous2() {
        ItemColor[] actualAnalogousResult = (new ColorGenerator()).analogous(ItemColor.WINE);
        assertEquals(2, actualAnalogousResult.length);
        assertEquals(ItemColor.BRIGHT_PINK, actualAnalogousResult[0]);
        assertEquals(ItemColor.BRONZE, actualAnalogousResult[1]);
    }

    @Test
    void testAnalogous3() {
        ItemColor[] actualAnalogousResult = (new ColorGenerator()).analogous(ItemColor.RUST);
        assertEquals(2, actualAnalogousResult.length);
        assertEquals(ItemColor.MOHOGAMY, actualAnalogousResult[0]);
        assertEquals(ItemColor.GOLDENROD, actualAnalogousResult[1]);
    }

    @ParameterizedTest
    @ValueSource(strings = {"WHITE", "BLACK", "GREY"})
    void testAnalogous4(String color){
        ItemColor[] actualAnalogousResult = (new ColorGenerator()).analogous(ItemColor.valueOf(color));
        assertEquals(0, actualAnalogousResult.length);
    }

}

