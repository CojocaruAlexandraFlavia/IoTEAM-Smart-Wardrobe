package com.example.smartwardrobe;

import com.example.smartwardrobe.controller.WeatherController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.example.smartwardrobe.enums.ItemColor.MOHOGAMY;

@SpringBootApplication
public class SmartWardrobeApplication {

	public static void main(String[] args) {
		try{
		WeatherController.getWeather();
		} catch (Exception e) {
			e.printStackTrace();
		}
		SpringApplication.run(SmartWardrobeApplication.class, args);
	}

}
