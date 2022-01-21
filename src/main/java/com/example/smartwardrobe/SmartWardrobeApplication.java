package com.example.smartwardrobe;

import com.example.smartwardrobe.colorpalette.ColorGenerator;
import com.example.smartwardrobe.controller.WeatherController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

import static com.example.smartwardrobe.enums.ItemColor.*;

@SpringBootApplication
public class SmartWardrobeApplication {

	public static void main(String[] args) {
		try{
		WeatherController.getWeather();
		ColorGenerator colorGenerator = new ColorGenerator();
		System.out.println(Arrays.toString(colorGenerator.colorNumber(WATERMELON)));
		//System.out.println(colorGenerator.colorKind(3,5));
		System.out.println(Arrays.toString(colorGenerator.monoChromatic(WATERMELON)));
		System.out.println(Arrays.toString(colorGenerator.pastel(BLUE)));
		System.out.println(Arrays.toString(colorGenerator.analogous(DENIM)));
		System.out.println(Arrays.toString(colorGenerator.splitComplementary(YELLOW)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		SpringApplication.run(SmartWardrobeApplication.class, args);
	}

}
