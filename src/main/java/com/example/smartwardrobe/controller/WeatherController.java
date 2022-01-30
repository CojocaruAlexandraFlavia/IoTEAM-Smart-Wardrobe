package com.example.smartwardrobe.controller;

import com.example.smartwardrobe.weather.IpInformation;
import com.example.smartwardrobe.weather.Weather;
import com.example.smartwardrobe.weather.WeatherGrabber;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

public class WeatherController {
    public static void getWeather() throws Exception {
        InetAddress ip;
        String ipNo;
        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
        ipNo = in.readLine();
        System.out.println(ipNo);
        IpInformation ipInformation = WeatherGrabber.grabLocationFrom(ipNo);
        Weather weatherConditions = WeatherGrabber.grabWeatherFrom(ipInformation);
        System.out.println(weatherConditions.toString());
    }
    public static Double getTemperature() throws Exception {
        Double temperature;
        getWeather();
        JSONParser parser = new JSONParser();
        try{
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("src/main/java/com/example/smartwardrobe/json/weather.json")); ;
            temperature = (Double) jsonObject.get("feelslike_c");
        } catch (ParseException | IOException e) {
            e.printStackTrace();
            return null;
        }
        return temperature;
    }
}