package com.example.smartwardrobe.weather;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.text.Normalizer;

public class WeatherGrabber {

    private WeatherGrabber(){}

    private static final String HUMIDITY = "humidity";
    private static final String IS_DAY = "is_day";
    private static final String FEELS_LIKE_C = "feelslike_c";
    private static final String LAST_UPDATED = "last_updated";

    /**
     * Grabs the weather by ipInformation from Yahoo Weather Api
     *
     * @param ipInformation ipInformation
     * @return weather
     * @throws Exception exception
     */
    public static Weather grabWeatherFrom(IpInformation ipInformation) throws Exception {
        return grabWeatherFrom(ipInformation.getCity(), ipInformation.getCountryCode());
    }

    /**
     *
     * @param src
     * @return
     */
    public static String removeDiacritics(String src) {
        return Normalizer
                .normalize(src, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }
    /**
     * Grabs the weather by city and countryCode from Yahoo Weather Api
     *
     * @param city        city
     * @param countryCode countryCode
     * @return weather
     * @throws Exception exception
     */
    public static Weather grabWeatherFrom(String city, String countryCode) throws Exception {
        String city6 = removeDiacritics(city);
        String data = city6 + ", " + countryCode;
        String YQL = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\") and u='c'", data);
        String endpoint = String.format("http://api.weatherapi.com/v1/current.json?key=7863f9f7b46547e5bf7222325221901&q="+city6+"&aqi=no", URLEncoder.encode(YQL, "UTF-8"));
        URL url = new URL(endpoint);
        try (BufferedReader stream = new BufferedReader(new InputStreamReader(url.openStream()))) {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(stream);
            JSONObject weatherData = (JSONObject) jsonObject.get("current");
            try (FileWriter file = new FileWriter("src/main/java/com/example/smartwardrobe/json/weather.json")) {
                JSONObject newData = new JSONObject();
                newData.put(HUMIDITY, weatherData.get(HUMIDITY));
                newData.put(IS_DAY, weatherData.get(IS_DAY));
                newData.put(FEELS_LIKE_C, weatherData.get(FEELS_LIKE_C));
                newData.put(LAST_UPDATED, weatherData.get(LAST_UPDATED));
                file.write(newData.toJSONString());
            }
            return new Weather(Integer.parseInt(weatherData.get(HUMIDITY).toString()),
                    Integer.parseInt(weatherData.get(IS_DAY).toString()),
                    Float.parseFloat(weatherData.get(FEELS_LIKE_C).toString()),
                    weatherData.get(LAST_UPDATED).toString());
        }
    }

    /**
     * Grabs the ip information by javaSocketAddress from ip-api.com
     *
     * @param ip ip
     * @return information
     */
    public static IpInformation grabLocationFrom(String ip) throws Exception {
        URL url = new URL("http://ip-api.com/json/" + ip);
        try (BufferedReader stream = new BufferedReader(new InputStreamReader(url.openStream()))) {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(stream);
            try (FileWriter file = new FileWriter("src/main/java/com/example/smartwardrobe/json/ip.json")) {
                file.write(jsonObject.toJSONString());
            }
            if (jsonObject.get("regionName").equals("Bucharest")){
                return new IpInformation((String) jsonObject.get("country"), (String) jsonObject.get("regionName"), (String) jsonObject.get("timezone"), (String) jsonObject.get("countryCode"));
            }
            else{
                return new IpInformation((String) jsonObject.get("country"), (String) jsonObject.get("city"), (String) jsonObject.get("timezone"), (String) jsonObject.get("countryCode"));
            }

        }
    }

}