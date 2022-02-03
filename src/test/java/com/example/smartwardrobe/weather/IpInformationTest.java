package com.example.smartwardrobe.weather;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class IpInformationTest {
    @Test
    void testConstructor() {
        IpInformation actualIpInformation = new IpInformation();
        assertNull(actualIpInformation.getCity());
        assertNull(actualIpInformation.getCountry());
        assertNull(actualIpInformation.getCountryCode());
        assertNull(actualIpInformation.getTimezone());
        assertEquals("IpInformation{country='null', city='null', timezone='null', countryCode='null'}",
                actualIpInformation.toString());
    }

    @Test
    void testConstructor2() {
        IpInformation actualIpInformation = new IpInformation("GB", "Oxford", "UTC", "GB");

        assertEquals("Oxford", actualIpInformation.getCity());
        assertEquals("GB", actualIpInformation.getCountry());
        assertEquals("GB", actualIpInformation.getCountryCode());
        assertEquals("UTC", actualIpInformation.getTimezone());
        assertEquals("IpInformation{country='GB', city='Oxford', timezone='UTC', countryCode='GB'}",
                actualIpInformation.toString());
    }
}

