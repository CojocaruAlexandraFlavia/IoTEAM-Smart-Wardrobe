package com.example.smartwardrobe;

import com.example.smartwardrobe.controller.ItemController;
import com.example.smartwardrobe.controller.MqttController;
import com.example.smartwardrobe.controller.WeatherController;
import com.example.smartwardrobe.service.impl.ItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Timer;
import java.util.TimerTask;


@SpringBootApplication
public class SmartWardrobeApplication {
	@Autowired
	MqttController mqttController;
	public static void main(String[] args) throws Exception {
		SpringApplication.run(SmartWardrobeApplication.class, args);
		MqttController.publishWeatherConditions();

	}



}
