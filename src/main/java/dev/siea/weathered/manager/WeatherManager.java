package dev.siea.weathered.manager;

import dev.siea.weathered.api.OpenWeatherAPI;
import dev.siea.weathered.api.Weather;
import dev.siea.weathered.api.WeatherType;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.ArrayList;
import java.util.List;

public class WeatherManager implements Listener {
    private Weather weather;
    private final String region;
    private final List<World> worlds = new ArrayList<>();
    public WeatherManager(String region, Plugin plugin){
        this.region = region;
        for(String worldString : plugin.getConfig().getStringList("worlds")){
            World world = plugin.getServer().getWorld(worldString);
            if (world == null) return;
            worlds.add(world);
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        }
        gatherWeatherData();
        updateWeather();
        new BukkitRunnable() {
            @Override
            public void run() {
                gatherWeatherData();
                updateWeather();
            }
        }.runTaskTimer(plugin, 0, 20L * plugin.getConfig().getInt("update_interval_seconds")); // Update interval in minutes
    }

    private void gatherWeatherData(){
        weather = OpenWeatherAPI.getWeather(region);
    }

    private void updateWeather(){
        if (weather == null) return;
        WeatherType weatherType = weather.getWeatherType();
        long time = weather.getMinecraftTicks();
        for (World world : worlds){
            world.setTime(time);
            switch (weatherType){
                case RAIN:
                case SNOW:
                    world.setStorm(true);
                    world.setThundering(false);
                    break;
                case STORM:
                    world.setStorm(true);
                    world.setThundering(true);
                    break;
                default:
                    world.setStorm(false);
                    world.setThundering(false);
                    break;
            }
        }
    }
}
