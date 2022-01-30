package com.example.smartwardrobe.controller;

import com.example.smartwardrobe.weather.IpInformation;
import com.example.smartwardrobe.weather.Weather;
import com.example.smartwardrobe.weather.WeatherGrabber;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
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
}
