package com.example.smartwardrobe.weather;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Wrapper for the weather
 */

public class Weather implements Serializable {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private int humidity;
    private int isDay;
    private float tempC;
    private LocalDateTime lastUpdated;

    /**
     * Default constructor
     */
    public Weather() {
    }

    /**
     * Initializes a new Weather
     *
     * @param humidity humidity
     * @param isDay is_day
     * @param tempC temp_c
     * @param lastUpdated last_updated
     */
    public Weather(int humidity, int isDay, float tempC, String lastUpdated) {
        this.humidity = humidity;
        this.isDay = isDay;
        this.tempC = tempC;
        this.lastUpdated = LocalDateTime.parse(lastUpdated, DATE_FORMAT);
    }

    /**
     * Returns the code of the weather
     *
     * @return
     */
    public int getHumidity() {
        return this.humidity;
    }

    /**
     * Returns the text
     *
     * @return text
     */
    public int getDay() {
        return this.isDay;
    }

    /**
     * Returns the temp
     *
     * @return temp
     */
    public float getTemp() {
        return this.tempC;
    }

    /**
     * Returns the date
     *
     * @return date
     */
    public LocalDateTime getDate() {
        return this.lastUpdated;
    }

    /**
     * Displays the weather
     *
     * @return string
     */
    @Override
    public String toString() {
        String timeOfDay;
        if(this.isDay == 0){
            timeOfDay = "night";
        }else{
            timeOfDay = "day";
        }
        return "Weather{" +
                "humidity=" + this.humidity +
                ", timeOfDay='" + timeOfDay + '\'' +
                ", temp=" + this.tempC +
                ", date='" + DATE_FORMAT.format(this.lastUpdated) + '\'' +
                '}';
    }
}


