package dev.siea.weathered.commands;

import dev.siea.weathered.manager.WeatherManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Objects;

public class WeatheredCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        System.out.println("dawg");
        if (args.length >= 1) {
            System.out.println("Hello");
            String subcommand = args[0];
            if (sender.hasPermission("weathered.reload") && Objects.equals(subcommand.toLowerCase(), "reload")) {
                sender.sendMessage("§eReloading...");
                double starTime = System.currentTimeMillis();
                WeatherManager.reload();
                sender.sendMessage("§eReloaded successfully in §a" + (System.currentTimeMillis() - starTime) + "ms");
                return true;
            }
            else if (sender.hasPermission("weathered.setRegion") && Objects.equals(subcommand.toLowerCase(), "region")) {
                StringBuilder region = new StringBuilder();
                if (args.length > 1){
                    for (int length = 1; length < args.length; length++){
                        region.append(args[length]);
                        if (length < args.length-1){
                            region.append(" ");
                        }
                    }
                    WeatherManager.changeRegion(region.toString());
                    sender.sendMessage("§eYou changed the region to §6§l" + region);
                }
                else {
                    sender.sendMessage("§cPlease provide a valid region!");
                }
                return true;
            }
        }
        return false;
    }
}
