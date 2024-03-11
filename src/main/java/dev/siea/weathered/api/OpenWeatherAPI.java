package dev.siea.weathered.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class OpenWeatherAPI {
    private static String key;
    public static Weather getWeather(String region) {
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + region + "&appid=" + key;
        try {
            JSONObject jsonResponse = getJsonObject(apiUrl);

            JSONArray weatherArray = jsonResponse.getJSONArray("weather");
            JSONObject firstWeather = weatherArray.getJSONObject(0);
            String weatherDescription = firstWeather.getString("description");

            int timezone = jsonResponse.getInt("timezone");

            LocalDateTime localDateTime = calculateLocalTime(timezone);

            long minecraftTicks = calculateMinecraftTime(localDateTime);

            return new Weather(convertToWeatherType(weatherDescription), region,minecraftTicks);
        } catch (Exception e) {
            return null;
        }
    }
    private static JSONObject getJsonObject(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);

        // Establish HTTP connection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Read response
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        // Parse JSON response
        return new JSONObject(response.toString());
    }

    public static void setKey(String key) {
        OpenWeatherAPI.key = key;
    }

    private static WeatherType convertToWeatherType(String description) {
        switch (description.toLowerCase()) {
            case "few clouds":
            case "scattered clouds":
            case "broken clouds":
            case "overcast clouds":
                return WeatherType.CLOUDS;
            case "shower rain":
            case "rain":
            case "light rain":
            case "moderate rain":
            case "heavy intensity rain":
            case "very heavy rain":
            case "extreme rain":
                return WeatherType.RAIN;
            case "thunderstorm":
            case "thunderstorm with light rain":
            case "thunderstorm with rain":
            case "thunderstorm with heavy rain":
            case "light thunderstorm":
            case "heavy thunderstorm":
            case "ragged thunderstorm":
            case "thunderstorm with drizzle":
                return WeatherType.STORM;
            case "snow":
            case "light snow":
            case "heavy snow":
            case "sleet":
            case "shower sleet":
                return WeatherType.SNOW;
            default:
                return WeatherType.CLEAR;
        }
    }

    private static long calculateMinecraftTime(LocalDateTime localDateTime) {
        long secondsOfDay = localDateTime.toLocalTime().toSecondOfDay();
        double ticks = ((secondsOfDay / 60.0 / 60.0) * 1000.0) + 18000.0;
        return (long) ticks;
    }

    private static LocalDateTime calculateLocalTime(int timezoneOffsetSeconds) {
        ZoneOffset offset = ZoneOffset.ofTotalSeconds(timezoneOffsetSeconds);
        LocalDateTime utcTime = LocalDateTime.now(ZoneOffset.UTC);
        return utcTime.plusSeconds(offset.getTotalSeconds());
    }

}
