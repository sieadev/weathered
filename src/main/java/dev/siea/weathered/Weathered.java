package dev.siea.weathered;

import dev.siea.weathered.api.OpenWeatherAPI;
import dev.siea.weathered.manager.WeatherManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Weathered extends JavaPlugin {
    @Override
    public void onEnable() {
        saveDefaultConfig();

        boolean keyFound = false;
        if (getConfig().getString("openweatherkey") != null){
            OpenWeatherAPI.setKey(getConfig().getString("openweatherkey"));
        } else{

        }

        getServer().getPluginManager().registerEvents(new WeatherManager(getConfig().getString("region"), this),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
