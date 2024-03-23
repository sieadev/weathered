package dev.siea.weathered.manager;

import dev.siea.weathered.data.MeasurementSystem;
import dev.siea.weathered.data.Weather;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Objects;

public class ActionBarManager implements Listener {
    private static boolean displayTime;
    private static boolean displayTemp;
    private static Weather weather;
    private static boolean colon;
    private static MeasurementSystem measurementSystem;
    public ActionBarManager(Plugin plugin, MeasurementSystem measurementSystem){
        displayTime = plugin.getConfig().getBoolean("displayTime");
        displayTemp = plugin.getConfig().getBoolean("displayTemp");
        ActionBarManager.measurementSystem = measurementSystem;
        new BukkitRunnable() {
            @Override
            public void run() {
                updateActionbar(plugin.getServer());
            }
        }.runTaskTimer(plugin, 0, 30L); // Update interval in minutes
    }

    public static void updateWeather(Weather weather){
        ActionBarManager.weather = weather;
    }

    private static void updateActionbar(Server server) {
        StringBuilder display = new StringBuilder().append("§7>> ");
        if (weather == null) {
            display.append("§c???");
            return;
        }
        else{
            if (displayTemp){
                display.append("§a").append(weather.getTemp(measurementSystem)).append("°");
            }
            if (displayTime && displayTemp){
                display.append("§7 || ");
            }
            if (displayTime){
                DateTimeFormatter formatterHours = DateTimeFormatter.ofPattern("HH");
                DateTimeFormatter formatterMinute = DateTimeFormatter.ofPattern("mm");
                String hours = weather.getLocalDateTime().format(formatterHours);
                String minutes = weather.getLocalDateTime().format(formatterMinute);
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
                display.append("§6").append(hours);
                if (colon){
                    colon = false;
                    display.append("§8:");
                }
                else{
                    display.append(":");
                    colon = true;
                }
                display.append("§6").append(minutes);
            }
        }
        display.append(" §7<<");
        for (Player player : server.getOnlinePlayers()){
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(String.valueOf(display)));
        }
    }

}
