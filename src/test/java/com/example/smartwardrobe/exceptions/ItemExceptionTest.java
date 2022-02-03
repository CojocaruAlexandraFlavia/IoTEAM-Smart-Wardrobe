package com.example.smartwardrobe.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ItemExceptionTest {
    @Test
    void testConstructor() {
        ItemException actualItemException = new ItemException("Code");
        actualItemException.setCode("Code");
        assertEquals("Code", actualItemException.getCode());
    }

    @Test
    void testConstructor2() {
        assertEquals("Code", (new ItemException("Code")).getCode());
    }
}

