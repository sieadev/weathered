package dev.siea.weathered.data;

import dev.siea.weathered.Weathered;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class OpenWeatherAPI {

    private static int fails = 0;
    private static String key;
    public static Weather getWeather(String region) {
        String apiUrl = getApiUrl(region, key);
        try {
            JSONObject jsonResponse = getJsonObject(apiUrl);
            if(jsonResponse == null) return null;

            JSONArray weatherArray = jsonResponse.getJSONArray("weather");
            JSONObject firstWeather = weatherArray.getJSONObject(0);
            String weatherDescription = firstWeather.getString("description");
            JSONObject sysObject = jsonResponse.getJSONObject("sys");

            String city = jsonResponse.getString("name");
            String country = sysObject.getString("country");
            int cityId = jsonResponse.getInt("id");
            int timezone = jsonResponse.getInt("timezone");

            double temp = jsonResponse.getJSONObject("main").getDouble("temp");

            LocalDateTime localDateTime = calculateLocalTime(timezone);

            long minecraftTicks = calculateMinecraftTime(localDateTime);
            fails = 0;
            return new Weather(convertToWeatherType(weatherDescription), city, country, cityId, minecraftTicks, localDateTime, temp);
        } catch (Exception e) {
            fails++;
            if (fails > 5){
                Weathered.disable("§cOpen WeatherAPI is not responding : " + e.getMessage());
            }
            return null;
        }
    }
    private static JSONObject getJsonObject(String apiUrl) {
        try {
            String decodedURL = URLDecoder.decode(apiUrl, StandardCharsets.UTF_8);
            URL url = new URL(decodedURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            return new JSONObject(response.toString());
        } catch (MalformedURLException e) {
            Weathered.disable("§cInvalid URL: " + e.getMessage());
        } catch (IOException e) {
            Weathered.disable("§cUnable to connect to OpenWeatherAPI: " + e.getMessage());
        }
        return null;
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

    public static String getApiUrl(String region, String key) {
        try {
            int regionId = Integer.parseInt(region);
            return "https://api.openweathermap.org/data/2.5/weather?id=" + regionId + "&appid=" + key;
        } catch (NumberFormatException e) {
            return "https://api.openweathermap.org/data/2.5/weather?q=" + region + "&appid=" + key;
        }
    }

}
