package com.example.smartwardrobe.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class HistoryDtoTest {
    @Test
    void testGetLocalDateTime() {
        HistoryDto historyDto = new HistoryDto();
        historyDto.setDatetime("2020-03-01");
        assertEquals("2020-03-01", historyDto.getLocalDateTime().toString());
    }

    @Test
    void testConstructor() {
        HistoryDto actualHistoryDto = new HistoryDto();
        actualHistoryDto.setDatetime("2020-03-01");
        actualHistoryDto.setOutfitId(123L);
        assertEquals("2020-03-01", actualHistoryDto.getDatetime());
        assertEquals(123L, actualHistoryDto.getOutfitId());
    }
}

