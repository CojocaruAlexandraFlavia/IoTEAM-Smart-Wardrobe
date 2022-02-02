package com.example.smartwardrobe;

import com.example.smartwardrobe.controller.ItemController;
import com.example.smartwardrobe.controller.WeatherController;
import com.example.smartwardrobe.service.impl.ItemServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class SmartWardrobeApplication {

	public static void main(String[] args) {

		SpringApplication.run(SmartWardrobeApplication.class, args);
	}



}
