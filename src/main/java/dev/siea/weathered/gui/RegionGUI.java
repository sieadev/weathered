package dev.siea.weathered.gui;

import dev.siea.weathered.data.MeasurementSystem;
import dev.siea.weathered.data.Weather;
import dev.siea.weathered.manager.WeatherManager;
import dev.siea.weathered.util.Converter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegionGUI {
    private static final HashMap<Integer, String> buttons = new HashMap<>();
    private static final List<String> regions = new ArrayList<>();
    private static Inventory inventory;

    private static Inventory generateInventory(){
        Inventory inventory = Bukkit.createInventory(null,6*9, "Weathered - Change Region");
        for (int i = 0; i < inventory.getSize(); i++){
            inventory.setItem(i, createItem(" ", Material.GRAY_STAINED_GLASS_PANE));
        }

        Weather weather = WeatherManager.getWeather();

        List<String> description = new ArrayList<>();
        description.add("§aRegion: " + weather.getRegion());
        description.add("§aWeather: " + weather.getWeatherType());
        description.add("§aTemperature: " + weather.getTemp());
        description.add("§aTemperature: " + Converter.localDateTimeToString(weather.getLocalDateTime(), MeasurementSystem.METRIC));
        ItemStack current = createItem("§e§lCurrent", Material.PLAYER_HEAD, description);
        inventory.setItem(4,current);

        buttons.clear();

        int slot = 10;
        for (String region : regions){
            if (slot == 17 || slot == 26 || slot == 35) {
                slot = slot + 2;
            }
            if (slot > 42) break;
            if (region.equals(weather.getRegion())) continue;
            ItemStack regionItem = createItem("§e" + region, Material.PLAYER_HEAD);
            inventory.setItem(slot,regionItem);
            buttons.put(slot, region);
            slot++;
        }


        List<String> extraDescription = new ArrayList<>();
        extraDescription.add("§7You can always add custom regions using: ");
        extraDescription.add("§7/weathered region city,country");
        ItemStack extra = createItem("§eSomething's missing?", Material.GREEN_TERRACOTTA, extraDescription);
        inventory.setItem(43,extra);

        return inventory;
    }

    public static String whatIsThisButton(int button){
        return buttons.getOrDefault(button, null);
    }

    private static ItemStack createItem(String name, Material material, List<String> lore) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    private static ItemStack createItem(String name, Material mat){
        ItemStack item = new ItemStack(mat, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    public static Inventory getInventory() {
        if (inventory == null){
            inventory = generateInventory();
        }
        return inventory;
    }

    static {
        regions.add("Berlin,Germany");
        regions.add("Paris,France");
        regions.add("London,United Kingdom");
        regions.add("New York,United States");
        regions.add("Tokyo,Japan");
        regions.add("Moscow,Russia");
        regions.add("Sydney,Australia");
        regions.add("Rio de Janeiro,Brazil");
        regions.add("Cairo,Egypt");
        regions.add("Toronto,Canada");
        regions.add("Dubai,United Arab Emirates");
        regions.add("Rome,Italy");
        regions.add("Singapore,Singapore");
        regions.add("Seoul,South Korea");
        regions.add("Mexico City,Mexico");
        regions.add("Mumbai,India");
        regions.add("Los Angeles,United States");
        regions.add("Shanghai,China");
        regions.add("Chicago,United States");
        regions.add("Bangkok,Thailand");
        regions.add("Buenos Aires,Argentina");
        regions.add("Istanbul,Turkey");
        regions.add("Jakarta,Indonesia");
        regions.add("Lima,Peru");
        regions.add("Madrid,Spain");
        regions.add("Manila,Philippines");
        regions.add("Warsaw,Poland");
        regions.add("Lagos,Nigeria");
    }

    public static void regenerate() {
        inventory = null;
    }
}
