package com.example.smartwardrobe.weather;

import java.io.Serializable;

/**
 * Wrapper for the ipInformation
 */

public class IpInformation implements Serializable {
    private String country;
    private String city;
    private String timezone;
    private String countryCode;

    /**
     * Default constructor
     */
    public IpInformation() {
    }

    /**
     * Initializes a new ipInformation
     *
     * @param country     country
     * @param city        city
     * @param timezone    timezone
     * @param countryCode countryCode
     */
    public IpInformation(String country, String city, String timezone, String countryCode) {
        this.country = country;
        this.city = city;
        this.countryCode = countryCode;
        this.timezone = timezone;
    }

    /**
     * Returns the name of the country
     *
     * @return name
     */
    public String getCountry() {
        return this.country;
    }

    /**
     * Returns the city
     *
     * @return city
     */
    public String getCity() {
        return this.city;
    }

    /**
     * Returns the timeZone
     *
     * @return timeZone
     */
    public String getTimezone() {
        return this.timezone;
    }

    /**
     * Returns the code of the country
     *
     * @return country
     */
    public String getCountryCode() {
        return this.countryCode;
    }

    /**
     * Displays the ipInformation
     *
     * @return string
     */
    @Override
    public String toString() {
        return "IpInformation{" +
                "country='" + this.country + '\'' +
                ", city='" + this.city + '\'' +
                ", timezone='" + this.timezone + '\'' +
                ", countryCode='" + this.countryCode + '\'' +
                '}';
    }
}