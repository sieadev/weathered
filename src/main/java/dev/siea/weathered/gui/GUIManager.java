package dev.siea.weathered.gui;
import dev.siea.weathered.manager.WeatherManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;


public class GUIManager implements Listener {
    private static final HashMap<Player, Inventory> openGUIs = new HashMap<>();

    public static void openRegionGUI(Player player){
        Inventory inventory = RegionGUI.getInventory();
        openGUIs.put(player, inventory);
        player.openInventory(inventory);
    }

    @EventHandler
    public static void onInventoryClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if (!openGUIs.containsKey(player)) return;
        Inventory inventory = openGUIs.get(player);
        if (inventory != event.getInventory()) return;
        event.setCancelled(true);
        String region = RegionGUI.whatIsThisButton(event.getSlot());
        if (region != null){
            WeatherManager.changeRegion(region);
            player.closeInventory();
            RegionGUI.regenerate();
            player.sendMessage("§eYou changed the region to §6§l" + region);
        }
    }
}
