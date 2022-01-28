package com.example.smartwardrobe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class SmartWardrobeApplication {

	public static void main(String[] args) {
//		try{
//		WeatherController.getWeather();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		try{
			WashingController.getInstructions();
		} catch (IOException e) {
			e.printStackTrace();
		}
		SpringApplication.run(SmartWardrobeApplication.class, args);
	}



}
