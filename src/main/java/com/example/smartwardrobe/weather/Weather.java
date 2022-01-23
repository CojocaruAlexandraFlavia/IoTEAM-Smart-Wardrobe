package com.example.smartwardrobe.weather;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Wrapper for the weather
 */

public class Weather implements Serializable {
    private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private int humidity;
    private int is_day;
    private float temp_c;
    private Date last_updated;

    /**
     * Default constructor
     */
    public Weather() {
    }

    /**
     * Initializes a new Weather
     *
     * @param humidity humidity
     * @param is_day is_day
     * @param temp_c temp_c
     * @param last_updated last_updated
     */
    public Weather(int humidity, int is_day, float temp_c, String last_updated) throws Exception {
        this.humidity = humidity;
        this.is_day = is_day;
        this.temp_c = temp_c;
        this.last_updated = DATE_FORMAT.parse(last_updated);
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
        return this.is_day;
    }

    /**
     * Returns the temp
     *
     * @return temp
     */
    public float getTemp() {
        return this.temp_c;
    }

    /**
     * Returns the date
     *
     * @return date
     */
    public Date getDate() {
        return this.last_updated;
    }

    /**
     * Displays the weather
     *
     * @return string
     */
    @Override
    public String toString() {
        String timeOfDay;
        if(this.is_day == 0){
            timeOfDay = "night";
        }else{
            timeOfDay = "day";
        }
        return "Weather{" +
                "humidity=" + this.humidity +
                ", timeOfDay='" + timeOfDay + '\'' +
                ", temp=" + this.temp_c +
                ", date='" + DATE_FORMAT.format(this.last_updated) + '\'' +
                '}';
    }
}


