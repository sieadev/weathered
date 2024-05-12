package dev.siea.weathered.manager;

import dev.siea.weathered.Weathered;
import dev.siea.weathered.data.*;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.ArrayList;
import java.util.List;

public class WeatherManager implements Listener {
    private static Plugin plugin;
    private static Weather weather;
    private static String region;
    private static final List<World> worlds = new ArrayList<>();
    private static API api;
    public WeatherManager(String region, Plugin plugin, API api){
        WeatherManager.region = region;
        WeatherManager.api = api;
        WeatherManager.plugin = plugin;
        for(String worldString : plugin.getConfig().getStringList("worlds")){
            World world = plugin.getServer().getWorld(worldString);
            if (world == null) return;
            worlds.add(world);
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        }
        gatherWeatherData();
        applyWeather();
        new BukkitRunnable() {
            @Override
            public void run() {
                updateWeather();
            }
        }.runTaskTimer(plugin, 0, 20L * plugin.getConfig().getInt("update_interval_seconds"));
    }

    private static void updateWeather(){
        gatherWeatherData();
        applyWeather();
        try{
            ActionBarManager.updateWeather(weather);
        } catch (Exception e){
            Weathered.disable("§cUnable to update Actionbar! §lPLEASE REPORT THIS AT https://github.com/sieadev/weathered");
        }
    }

    public static void changeRegion(String region) {
        plugin.getConfig().set("region", region);
        plugin.saveConfig();
        reload();
        WeatherManager.region = region;
    }

    public static void changeRegion(Region region){
        plugin.getConfig().set("region", region.getCityID());
        plugin.saveConfig();
        reload();
        WeatherManager.region = region.toString();
    }

    public static void reload() {
        region = plugin.getConfig().getString("region");
        updateWeather();
    }



    private static void gatherWeatherData(){
        switch (api){
            case OPEN_WEATHER_API:
                weather = OpenWeatherAPI.getWeather(region);
                break;
            case OPEN_METEO:
                //Will add later
                break;
            default:
                Weathered.disable("No API connection");
                return;
        }
        Weathered.updatePlaceHolders(weather);
    }

    private static void applyWeather(){
        if (weather == null) return;
        WeatherType weatherType = weather.getWeatherType();
        long time = weather.getMinecraftTicks();
        for (World world : worlds){
            world.setTime(time);
            switch (weatherType){
                case RAIN:
                    world.setStorm(true);
                    world.setThundering(false);
                    break;
                case SNOW:
                    world.setStorm(true);
                    world.setThundering(false);
                    for (Player player : world.getPlayers()){
                        sendSnowPackets(player);
                    }
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

    private static void sendSnowPackets(Player player){

    }

    public static Weather getWeather() {
        return weather;
    }
}
