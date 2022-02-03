package com.example.smartwardrobe.weather;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class WeatherTest {
    @Test
    void testConstructor() {
        Weather actualWeather = new Weather();
        assertNull(actualWeather.getDate());
        assertEquals(0, actualWeather.getDay());
        assertEquals(0, actualWeather.getHumidity());
        assertEquals(0.0f, actualWeather.getTemp());
    }

    @Test
    void testConstructor2() {
        Weather actualWeather = new Weather();
        assertNull(actualWeather.getDate());
        assertEquals(0.0f, actualWeather.getTemp());
        assertEquals(0, actualWeather.getHumidity());
        assertEquals(0, actualWeather.getDay());
    }

    @Test
    void testAllArgsConstructor() throws java.text.ParseException {
        Weather weather = new Weather(10, 1, 15f, "2022-02-03 11:56");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        assertEquals(10, weather.getHumidity());
        assertEquals(1, weather.getDay());
        assertEquals(15f, weather.getTemp());
        assertEquals(LocalDateTime.parse("2022-02-03 11:56", dateTimeFormatter), weather.getDate());
    }

    @Test
    void testToStringForDay() {
        Weather weather = new Weather(10, 1, 15f, "2022-02-03 11:56");

        assertEquals("Weather{" +
                "humidity=" + weather.getHumidity()+
                ", timeOfDay='" + "day" + '\'' +
                ", temp=" + weather.getTemp() +
                ", date='" + weather.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + '\'' +
                '}', weather.toString());
    }

    @Test
    void testToStringForNight() {
        Weather weather = new Weather(10, 0, 15f, "2022-02-03 11:56");
        assertEquals("Weather{" +
                "humidity=" + weather.getHumidity()+
                ", timeOfDay='" + "night" + '\'' +
                ", temp=" + weather.getTemp() +
                ", date='" + weather.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + '\'' +
                '}', weather.toString());
    }
}

