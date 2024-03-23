package dev.siea.weathered.api;

import dev.siea.weathered.data.MeasurementSystem;
import dev.siea.weathered.data.Weather;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PlaceHolderManager extends PlaceholderExpansion {
    private static Weather weather;
    private static Plugin plugin;

    private static MeasurementSystem measurementSystem;

    public PlaceHolderManager(Plugin plugin, MeasurementSystem measurementSystem){
        PlaceHolderManager.plugin = plugin;
        PlaceHolderManager.measurementSystem = measurementSystem;
    }
    public static void update(Weather weather) {
        PlaceHolderManager.weather = weather;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String param) {
        switch (param){
            case "weather":
                return weather.getWeatherType().toString();
            case "region":
                return weather.getRegion();
            case "temperature":
            case "temp":
                return String.valueOf(weather.getTemp());
            case "time":
                return convertTimeFormat(weather.getLocalDateTime());
        }
        return null; //
    }

    private String convertTimeFormat(LocalDateTime localDateTime) {
        StringBuilder time = new StringBuilder();
        DateTimeFormatter formatterHours = DateTimeFormatter.ofPattern("HH");
        DateTimeFormatter formatterMinute = DateTimeFormatter.ofPattern("mm");
        String hours = localDateTime.format(formatterHours);
        String minutes = localDateTime.format(formatterMinute);
        if (measurementSystem == MeasurementSystem.IMPERIAL){
            int hourInt = Integer.parseInt(hours);
            if (hourInt > 12){
                hours = String.valueOf(hourInt-12);
                minutes = minutes + "pm";
            }
            else{
                minutes = minutes + "am";
            }
        }
        time.append("§6").append(hours);
        time.append(":");
        time.append("§6").append(minutes);
        return time.toString();
    }


    @Override
    public @NotNull String getIdentifier() {
        return plugin.getName();
    }

    @Override
    public @NotNull String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }
}
