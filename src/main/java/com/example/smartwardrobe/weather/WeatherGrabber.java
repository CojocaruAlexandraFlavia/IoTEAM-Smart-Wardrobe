package com.example.smartwardrobe.weather;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Copyright 2017 Shynixn
 * <p>
 * Do not remove this header!
 * <p>
 * Version 1.0
 * <p>
 * MIT License
 * <p>
 * Copyright (c) 2016
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
public class WeatherGrabber {

    /**
     * Grabs the weather by ip from Yahoo Weather Api and ip-api.com
     *
     * @param ip ip
     * @return weather
     */
    public static Weather grabWeatherFrom(String ip) throws Exception {
        return grabWeatherFrom(grabLocationFrom(ip));
    }

    /**
     * Grabs the weather by ipInformation from Yahoo Weather Api
     *
     * @param ipInformation ipInformation
     * @return weather
     * @throws Exception exception
     */
    public static Weather grabWeatherFrom(IpInformation ipInformation) throws Exception {
        return grabWeatherFrom(ipInformation.getCountry(), ipInformation.getCountryCode());
    }

    /**
     * Grabs the weather by city and countryCode from Yahoo Weather Api
     *
     * @param city        city
     * @param countryCode countryCode
     * @return weather
     * @throws Exception exception
     */
    public static Weather grabWeatherFrom(String city, String countryCode) throws Exception {
        String data = city + ", " + countryCode;
        String YQL = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\") and u='c'", data);
        String endpoint = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", URLEncoder.encode(YQL, "UTF-8"));
        URL url = new URL(endpoint);
        try (BufferedReader stream = new BufferedReader(new InputStreamReader(url.openStream()))) {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(stream);
            JSONObject result = (JSONObject) getValue(getValue(getValue(jsonObject, "query"), "results"), "channel").get("item");
            JSONObject weatherData = (JSONObject) result.get("condition");
            return new Weather(Integer.parseInt((String) weatherData.get("code")), (String) weatherData.get("text"), Integer.parseInt((String) weatherData.get("temp")), (String) weatherData.get("date"));
        }
    }

    /**
     * Grabs the ip information by javaSocketAddress from ip-api.com
     *
     * @param ip ip
     * @return information
     */
    public static IpInformation grabLocationFrom(InetSocketAddress ip) throws Exception {
        return grabLocationFrom(ip.getHostName());
    }

    /**
     * Grabs the ip information by javaSocketAddress from ip-api.com
     *
     * @param ip ip
     * @return information
     */
    public static IpInformation grabLocationFrom(String ip) throws Exception {
        URL url = new URL("http://ip-api.com/json/" + ip);
        try (BufferedReader stream = new BufferedReader(new InputStreamReader(url.openStream()))) {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(stream);
            return new IpInformation((String) jsonObject.get("country"), (String) jsonObject.get("city"), (String) jsonObject.get("timezone"), (String) jsonObject.get("countryCode"));
        }
    }

    /**
     * JsonKeyHelper
     *
     * @param jsonObject jsonObject
     * @param key        key
     * @return value
     */
    private static JSONObject getValue(JSONObject jsonObject, String key) {
        return (JSONObject) jsonObject.get(key);
    }

}