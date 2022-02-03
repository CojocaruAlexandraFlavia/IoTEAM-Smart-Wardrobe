package com.example.smartwardrobe;

import com.example.smartwardrobe.controller.MqttController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmartWardrobeApplication {

	public static void main(String[] args){
		SpringApplication.run(SmartWardrobeApplication.class, args);
		MqttController.publishWeatherConditions();
	}



}
