package com.example.smartwardrobe.controller;

import com.example.smartwardrobe.weather.IpInformation;
import com.example.smartwardrobe.weather.Weather;
import com.example.smartwardrobe.weather.WeatherGrabber;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class WeatherController {

    private WeatherController(){}

    public static void getWeather() throws Exception {
        URL myIp = new URL("http://checkip.amazonaws.com");
        String ipNo;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(myIp.openStream()))) {
            ipNo = in.readLine();
        }
        IpInformation ipInformation = WeatherGrabber.grabLocationFrom(ipNo);
        WeatherGrabber.grabWeatherFrom(ipInformation);
    }

    //this is used for mqqt
    public static String getWeatherConditions() throws Exception {
        String ipNo;
        URL myIp = new URL("http://checkip.amazonaws.com");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(myIp.openStream()))) {
            ipNo = in.readLine();
        }
        IpInformation ipInformation = WeatherGrabber.grabLocationFrom(ipNo);
        Weather weatherConditions = WeatherGrabber.grabWeatherFrom(ipInformation);
        return(weatherConditions.toString());
    }

    public static Double getTemperature() throws Exception {
        Double temperature;
        getWeather();
        JSONParser parser = new JSONParser();
        try{
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("src/main/java/com/example/smartwardrobe/json/weather2.json"));
            temperature = (Double) jsonObject.get("feelslike_c");
        } catch (ParseException | IOException e) {
            e.printStackTrace();
            return null;
        }
        return temperature;
    }
}