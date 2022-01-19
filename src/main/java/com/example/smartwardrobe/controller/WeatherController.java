package com.example.smartwardrobe.controller;

import com.example.smartwardrobe.weather.IpInformation;
import com.example.smartwardrobe.weather.Weather;
import com.example.smartwardrobe.weather.WeatherGrabber;
import org.json.simple.JSONObject;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class WeatherController {
    public static void main(String[] args) throws Exception {
        InetAddress ip;
        String ipNo;
        ipNo = "193.105.140.131";
//            int editIp = ipNo.indexOf('/');
//            ipNo = ipNo.substring(editIp+1);
        System.out.println(ipNo);

        IpInformation ipInformation = WeatherGrabber.grabLocationFrom(ipNo);
        Weather weatherConditions = WeatherGrabber.grabWeatherFrom(ipInformation);
        System.out.println(weatherConditions.toString());
    }
}
