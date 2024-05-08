package dev.siea.weathered;

import dev.siea.weathered.api.PlaceHolderManager;
import dev.siea.weathered.data.API;
import dev.siea.weathered.data.MeasurementSystem;
import dev.siea.weathered.data.OpenWeatherAPI;
import dev.siea.weathered.commands.WeatheredCommand;
import dev.siea.weathered.commands.WeatheredTabCompletions;
import dev.siea.weathered.data.Weather;
import dev.siea.weathered.gui.GUIManager;
import dev.siea.weathered.manager.ActionBarManager;
import dev.siea.weathered.manager.WeatherManager;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.CustomChart;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Weathered extends JavaPlugin {
    private static Plugin plugin;
    private static API api;
    private static boolean placeHolderAPI;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        plugin = this;

        //Load Measurement System
        MeasurementSystem measurementSystem;
        try{
            measurementSystem = MeasurementSystem.valueOf(Objects.requireNonNull(plugin.getConfig().getString("measurement_system")).toUpperCase());
        } catch (NullPointerException | IllegalArgumentException e){
            measurementSystem = MeasurementSystem.METRIC;
        }

        //Search for WeatherAPI
        if (getConfig().getString("openweatherkey") != null){
            OpenWeatherAPI.setKey(getConfig().getString("openweatherkey"));
            api = API.OPEN_WEATHER_API;
        } else{
            disable("No API found");
            return;
        }

        //Try enabling PlaceHolderAPI
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceHolderManager(this, measurementSystem).register();
            placeHolderAPI = true;
            getLogger().info("Custom placeholders registered successfully.");
        } else {
            getLogger().warning("PlaceholderAPI not found. Custom placeholders will not work.");
            placeHolderAPI = false;
        }

        //Registering MainManager
        getServer().getPluginManager().registerEvents(new WeatherManager(getConfig().getString("region"), this, api),this);

        //Registering ActionbarManager
        if (plugin.getConfig().getBoolean("displayTime") || plugin.getConfig().getBoolean("displayTemp")){
            plugin.getServer().getPluginManager().registerEvents(new ActionBarManager(plugin, measurementSystem),plugin);
            plugin.getServer().getPluginManager().registerEvents(new GUIManager(),plugin);
        }

        //Registering Commands
        Objects.requireNonNull(getCommand("weathered")).setExecutor(new WeatheredCommand());
        Objects.requireNonNull(getCommand("weathered")).setTabCompleter(new WeatheredTabCompletions());

        //Load BStats
        enableBStats();
    }

    private void enableBStats(){
        int pluginID = 21840;
        new Metrics(this, pluginID);
    }

    public void onDisable() {
    }

    public static void disable(String reason) {
        Bukkit.getConsoleSender().sendMessage("Weathered is disabling: " + reason);
        Bukkit.getPluginManager().disablePlugin(plugin);
    }

    public static API getApi(){
        return api;
    }

    public static void updatePlaceHolders(Weather weather){
        if (placeHolderAPI) PlaceHolderManager.update(weather);
    }
}
