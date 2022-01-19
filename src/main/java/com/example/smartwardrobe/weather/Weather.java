package com.example.smartwardrobe.weather;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Wrapper for the weather
 */

public class Weather implements Serializable {
    private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("EEE, d MMM yyyy hh:mm a z", Locale.US);
    private int code;
    private String text;
    private int temp;
    private Date date;

    /**
     * Default constructor
     */
    public Weather() {
    }

    /**
     * Initializes a new Weather
     *
     * @param code code
     * @param text text
     * @param temp temp
     * @param date date
     */
    public Weather(int code, String text, int temp, String date) throws Exception {
        this.code = code;
        this.text = text;
        this.temp = temp;
        this.date = DATE_FORMAT.parse(date);
    }

    /**
     * Returns the code of the weather
     *
     * @return
     */
    public int getCode() {
        return this.code;
    }

    /**
     * Returns the text
     *
     * @return text
     */
    public String getText() {
        return this.text;
    }

    /**
     * Returns the temp
     *
     * @return temp
     */
    public int getTemp() {
        return this.temp;
    }

    /**
     * Returns the date
     *
     * @return date
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * Displays the weather
     *
     * @return string
     */
    @Override
    public String toString() {
        return "Weather{" +
                "code=" + this.code +
                ", text='" + this.text + '\'' +
                ", temp=" + this.temp +
                ", date='" + DATE_FORMAT.format(this.date) + '\'' +
                '}';
    }
}


