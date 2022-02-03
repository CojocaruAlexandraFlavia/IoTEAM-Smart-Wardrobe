package com.example.smartwardrobe.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.example.smartwardrobe.enums.CoatCategory;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.junit.jupiter.api.Test;

class HistoryTest {
    @Test
    void testConstructor() {
        History actualHistory = new History();
        LocalDate ofEpochDayResult = LocalDate.ofEpochDay(1L);
        actualHistory.setDateTime(ofEpochDayResult);
        actualHistory.setId(123L);
        Coat coat = new Coat();
        coat.setCoatCategory(CoatCategory.JACKET);
        coat.setId(123L);
        coat.setOutfits(new ArrayList<>());
        Outfit outfit = new Outfit();
        outfit.setCoat(coat);
        outfit.setDescription("The characteristics of someone or something");
        outfit.setHistories(new ArrayList<>());
        outfit.setId(123L);
        outfit.setItems(new ArrayList<>());
        outfit.setNrOfReviews(1);
        outfit.setNrOfStars(1);
        outfit.setRating(10.0);
        actualHistory.setOutfit(outfit);
        assertSame(ofEpochDayResult, actualHistory.getDateTime());
        assertEquals(123L, actualHistory.getId().longValue());
        assertSame(outfit, actualHistory.getOutfit());
    }

    @Test
    void testAllArgsConstructor(){
        Outfit outfit = new Outfit();
        LocalDate localDate = LocalDate.parse("2022-01-31");
        History history = new History(1L, localDate, outfit);
        assertEquals(1L, history.getId());
        assertEquals(localDate, history.getDateTime());
        assertSame(outfit, history.getOutfit());
    }

    @Test
    void testSerializeLocalDate() throws IOException {
        History history = new History();
        history.setDateTime(LocalDate.parse("2022-01-31"));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String dateAsString = objectMapper.writeValueAsString(history.getDateTime());
        assertEquals("[2022,1,31]", dateAsString);
    }

    @Test
    void testDeserializeLocalDate() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        LocalDate localDate = objectMapper.readValue(objectMapper.writeValueAsString("2022-01-31"), LocalDate.class);
        assertEquals(localDate, LocalDate.parse("2022-01-31"));
    }
}

