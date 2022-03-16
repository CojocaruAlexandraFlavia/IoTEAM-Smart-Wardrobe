package com.example.smartwardrobe.weather;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class WeatherGrabberTest {
    @Test
    void testRemoveDiacritics() {
        assertEquals("Src", WeatherGrabber.removeDiacritics("Src"));
        assertEquals("", WeatherGrabber.removeDiacritics(""));
        assertEquals("", WeatherGrabber.removeDiacritics(""));
        assertEquals("[^\\p{ASCII}]", WeatherGrabber.removeDiacritics("[^\\p{ASCII}]"));
        assertEquals("Src", WeatherGrabber.removeDiacritics("Src"));
        assertEquals("Bucharest", WeatherGrabber.removeDiacritics("Bucharest"));
        assertEquals("42", WeatherGrabber.removeDiacritics("42"));
        assertEquals("UTF-8", WeatherGrabber.removeDiacritics("UTF-8"));
        assertEquals("countryCode", WeatherGrabber.removeDiacritics("countryCode"));
        assertEquals("current", WeatherGrabber.removeDiacritics("current"));
        assertEquals("java.lang.String", WeatherGrabber.removeDiacritics("java.lang.String"));
        assertEquals("last_updated", WeatherGrabber.removeDiacritics("last_updated"));
        assertEquals("org.json.simple.JSONObject", WeatherGrabber.removeDiacritics("org.json.simple.JSONObject"));
        assertEquals("regionName", WeatherGrabber.removeDiacritics("regionName"));
        assertEquals("src/main/java/com/example/smartwardrobe/json/ip.json",
                WeatherGrabber.removeDiacritics("src/main/java/com/example/smartwardrobe/json/ip.json"));
        assertEquals("src/main/java/com/example/smartwardrobe/json/weather.json",
                WeatherGrabber.removeDiacritics("src/main/java/com/example/smartwardrobe/json/weather.json"));
        assertEquals("timezone", WeatherGrabber.removeDiacritics("timezone"));
        assertEquals("[^\\p{ASCII}]", WeatherGrabber.removeDiacritics("[^\\p{ASCII}]"));
        assertEquals("Src", WeatherGrabber.removeDiacritics("Src"));
        assertEquals("Bucharest", WeatherGrabber.removeDiacritics("Bucharest"));
        assertEquals("42", WeatherGrabber.removeDiacritics("42"));
        assertEquals("UTF-8", WeatherGrabber.removeDiacritics("UTF-8"));
        assertEquals("countryCode", WeatherGrabber.removeDiacritics("countryCode"));
        assertEquals("current", WeatherGrabber.removeDiacritics("current"));
        assertEquals("java.lang.String", WeatherGrabber.removeDiacritics("java.lang.String"));
        assertEquals("last_updated", WeatherGrabber.removeDiacritics("last_updated"));
        assertEquals("org.json.simple.JSONObject", WeatherGrabber.removeDiacritics("org.json.simple.JSONObject"));
        assertEquals("regionName", WeatherGrabber.removeDiacritics("regionName"));
        assertEquals("src/main/java/com/example/smartwardrobe/json/ip.json",
                WeatherGrabber.removeDiacritics("src/main/java/com/example/smartwardrobe/json/ip.json"));
        assertEquals("src/main/java/com/example/smartwardrobe/json/weather.json",
                WeatherGrabber.removeDiacritics("src/main/java/com/example/smartwardrobe/json/weather.json"));
        assertEquals("timezone", WeatherGrabber.removeDiacritics("timezone"));
        assertEquals("", WeatherGrabber.removeDiacritics(""));
        assertEquals("[^\\p{ASCII}]", WeatherGrabber.removeDiacritics("[^\\p{ASCII}]"));
        assertEquals("Src", WeatherGrabber.removeDiacritics("Src"));
        assertEquals("Bucharest", WeatherGrabber.removeDiacritics("Bucharest"));
        assertEquals("42", WeatherGrabber.removeDiacritics("42"));
        assertEquals("UTF-8", WeatherGrabber.removeDiacritics("UTF-8"));
        assertEquals("countryCode", WeatherGrabber.removeDiacritics("countryCode"));
        assertEquals("current", WeatherGrabber.removeDiacritics("current"));
        assertEquals("java.lang.String", WeatherGrabber.removeDiacritics("java.lang.String"));
        assertEquals("last_updated", WeatherGrabber.removeDiacritics("last_updated"));
        assertEquals("org.json.simple.JSONObject", WeatherGrabber.removeDiacritics("org.json.simple.JSONObject"));
        assertEquals("regionName", WeatherGrabber.removeDiacritics("regionName"));
        assertEquals("src/main/java/com/example/smartwardrobe/json/ip.json",
                WeatherGrabber.removeDiacritics("src/main/java/com/example/smartwardrobe/json/ip.json"));
        assertEquals("src/main/java/com/example/smartwardrobe/json/weather.json",
                WeatherGrabber.removeDiacritics("src/main/java/com/example/smartwardrobe/json/weather.json"));
        assertEquals("timezone", WeatherGrabber.removeDiacritics("timezone"));
        assertEquals("[^\\p{ASCII}]", WeatherGrabber.removeDiacritics("[^\\p{ASCII}]"));
        assertEquals("[^\\p{ASCII}][^\\p{ASCII}]", WeatherGrabber.removeDiacritics("[^\\p{ASCII}][^\\p{ASCII}]"));
        assertEquals("[^\\p{ASCII}]Src", WeatherGrabber.removeDiacritics("[^\\p{ASCII}]Src"));
        assertEquals("[^\\p{ASCII}]Bucharest", WeatherGrabber.removeDiacritics("[^\\p{ASCII}]Bucharest"));
        assertEquals("[^\\p{ASCII}]42", WeatherGrabber.removeDiacritics("[^\\p{ASCII}]42"));
        assertEquals("[^\\p{ASCII}]UTF-8", WeatherGrabber.removeDiacritics("[^\\p{ASCII}]UTF-8"));
        assertEquals("[^\\p{ASCII}]countryCode", WeatherGrabber.removeDiacritics("[^\\p{ASCII}]countryCode"));
        assertEquals("[^\\p{ASCII}]current", WeatherGrabber.removeDiacritics("[^\\p{ASCII}]current"));
        assertEquals("[^\\p{ASCII}]java.lang.String", WeatherGrabber.removeDiacritics("[^\\p{ASCII}]java.lang.String"));
        assertEquals("[^\\p{ASCII}]last_updated", WeatherGrabber.removeDiacritics("[^\\p{ASCII}]last_updated"));
        assertEquals("[^\\p{ASCII}]org.json.simple.JSONObject",
                WeatherGrabber.removeDiacritics("[^\\p{ASCII}]org.json.simple.JSONObject"));
        assertEquals("[^\\p{ASCII}]regionName", WeatherGrabber.removeDiacritics("[^\\p{ASCII}]regionName"));
        assertEquals("[^\\p{ASCII}]src/main/java/com/example/smartwardrobe/json/ip.json",
                WeatherGrabber.removeDiacritics("[^\\p{ASCII}]src/main/java/com/example/smartwardrobe/json/ip.json"));
        assertEquals("[^\\p{ASCII}]src/main/java/com/example/smartwardrobe/json/weather.json",
                WeatherGrabber.removeDiacritics("[^\\p{ASCII}]src/main/java/com/example/smartwardrobe/json/weather.json"));
        assertEquals("[^\\p{ASCII}]timezone", WeatherGrabber.removeDiacritics("[^\\p{ASCII}]timezone"));
        assertEquals("Src", WeatherGrabber.removeDiacritics("Src"));
        assertEquals("Src[^\\p{ASCII}]", WeatherGrabber.removeDiacritics("Src[^\\p{ASCII}]"));
        assertEquals("SrcSrc", WeatherGrabber.removeDiacritics("SrcSrc"));
        assertEquals("SrcBucharest", WeatherGrabber.removeDiacritics("SrcBucharest"));
        assertEquals("Src42", WeatherGrabber.removeDiacritics("Src42"));
        assertEquals("SrcUTF-8", WeatherGrabber.removeDiacritics("SrcUTF-8"));
        assertEquals("SrccountryCode", WeatherGrabber.removeDiacritics("SrccountryCode"));
        assertEquals("Srccurrent", WeatherGrabber.removeDiacritics("Srccurrent"));
        assertEquals("Srcjava.lang.String", WeatherGrabber.removeDiacritics("Srcjava.lang.String"));
        assertEquals("Srclast_updated", WeatherGrabber.removeDiacritics("Srclast_updated"));
        assertEquals("Srcorg.json.simple.JSONObject", WeatherGrabber.removeDiacritics("Srcorg.json.simple.JSONObject"));
        assertEquals("SrcregionName", WeatherGrabber.removeDiacritics("SrcregionName"));
        assertEquals("Srcsrc/main/java/com/example/smartwardrobe/json/ip.json",
                WeatherGrabber.removeDiacritics("Srcsrc/main/java/com/example/smartwardrobe/json/ip.json"));
        assertEquals("Srcsrc/main/java/com/example/smartwardrobe/json/weather.json",
                WeatherGrabber.removeDiacritics("Srcsrc/main/java/com/example/smartwardrobe/json/weather.json"));
        assertEquals("Srctimezone", WeatherGrabber.removeDiacritics("Srctimezone"));
        assertEquals("Bucharest", WeatherGrabber.removeDiacritics("Bucharest"));
        assertEquals("Bucharest[^\\p{ASCII}]", WeatherGrabber.removeDiacritics("Bucharest[^\\p{ASCII}]"));
        assertEquals("BucharestSrc", WeatherGrabber.removeDiacritics("BucharestSrc"));
        assertEquals("BucharestBucharest", WeatherGrabber.removeDiacritics("BucharestBucharest"));
        assertEquals("Bucharest42", WeatherGrabber.removeDiacritics("Bucharest42"));
        assertEquals("BucharestUTF-8", WeatherGrabber.removeDiacritics("BucharestUTF-8"));
        assertEquals("BucharestcountryCode", WeatherGrabber.removeDiacritics("BucharestcountryCode"));
        assertEquals("Bucharestcurrent", WeatherGrabber.removeDiacritics("Bucharestcurrent"));
        assertEquals("Bucharestjava.lang.String", WeatherGrabber.removeDiacritics("Bucharestjava.lang.String"));
        assertEquals("Bucharestlast_updated", WeatherGrabber.removeDiacritics("Bucharestlast_updated"));
        assertEquals("Bucharestorg.json.simple.JSONObject",
                WeatherGrabber.removeDiacritics("Bucharestorg.json.simple.JSONObject"));
        assertEquals("BucharestregionName", WeatherGrabber.removeDiacritics("BucharestregionName"));
        assertEquals("Bucharestsrc/main/java/com/example/smartwardrobe/json/ip.json",
                WeatherGrabber.removeDiacritics("Bucharestsrc/main/java/com/example/smartwardrobe/json/ip.json"));
        assertEquals("Bucharestsrc/main/java/com/example/smartwardrobe/json/weather.json",
                WeatherGrabber.removeDiacritics("Bucharestsrc/main/java/com/example/smartwardrobe/json/weather.json"));
        assertEquals("Bucharesttimezone", WeatherGrabber.removeDiacritics("Bucharesttimezone"));
        assertEquals("42", WeatherGrabber.removeDiacritics("42"));
        assertEquals("42[^\\p{ASCII}]", WeatherGrabber.removeDiacritics("42[^\\p{ASCII}]"));
        assertEquals("42Src", WeatherGrabber.removeDiacritics("42Src"));
        assertEquals("42Bucharest", WeatherGrabber.removeDiacritics("42Bucharest"));
        assertEquals("4242", WeatherGrabber.removeDiacritics("4242"));
        assertEquals("42UTF-8", WeatherGrabber.removeDiacritics("42UTF-8"));
        assertEquals("42countryCode", WeatherGrabber.removeDiacritics("42countryCode"));
        assertEquals("42current", WeatherGrabber.removeDiacritics("42current"));
        assertEquals("42java.lang.String", WeatherGrabber.removeDiacritics("42java.lang.String"));
        assertEquals("42last_updated", WeatherGrabber.removeDiacritics("42last_updated"));
        assertEquals("42org.json.simple.JSONObject", WeatherGrabber.removeDiacritics("42org.json.simple.JSONObject"));
        assertEquals("42regionName", WeatherGrabber.removeDiacritics("42regionName"));
        assertEquals("42src/main/java/com/example/smartwardrobe/json/ip.json",
                WeatherGrabber.removeDiacritics("42src/main/java/com/example/smartwardrobe/json/ip.json"));
        assertEquals("42src/main/java/com/example/smartwardrobe/json/weather.json",
                WeatherGrabber.removeDiacritics("42src/main/java/com/example/smartwardrobe/json/weather.json"));
        assertEquals("42timezone", WeatherGrabber.removeDiacritics("42timezone"));
    }

    @Test
    void testGrabWeatherFrom(){

    }

}

