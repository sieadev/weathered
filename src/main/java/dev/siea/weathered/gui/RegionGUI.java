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
import org.bukkit.inventory.meta.SkullMeta;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegionGUI {
    private static final HashMap<Integer, String> buttons = new HashMap<>();
    private static final List<String> regions = new ArrayList<>();
    private static Inventory inventory;

    private static Inventory generateInventory(){
        Inventory inventory = Bukkit.createInventory(null,5*9, "Weathered - Change Region");
        for (int i = 0; i < inventory.getSize(); i++){
            inventory.setItem(i, createItem(" ", Material.GRAY_STAINED_GLASS_PANE));
        }

        Weather weather = WeatherManager.getWeather();

        List<String> description = new ArrayList<>();
        description.add("§aRegion: " + weather.getRegion());
        description.add("§aWeather: " + weather.getWeatherType());
        description.add("§aTemperature: " + weather.getTemp());
        description.add("§aTemperature: " + Converter.localDateTimeToString(weather.getLocalDateTime(), MeasurementSystem.METRIC));
        ItemStack current = createItem("§cCurrent", Material.PLAYER_HEAD, description);
        current = setOwner(current);
        inventory.setItem(4,current);

        buttons.clear();

        int slot = 9;
        for (String region : regions){
            if (slot == 17 || slot == 26) {
                slot = slot + 2;
            }
            if (region.equals("§e" + weather.getRegion())) continue;
            ItemStack regionItem = createItem(region, Material.PLAYER_HEAD);
            regionItem = setOwner(regionItem);
            inventory.setItem(slot,regionItem);
            buttons.put(slot, region);
            slot++;
        }
        return inventory;
    }

    public static String whatIsThisButton(int button){
        return buttons.getOrDefault(button, null);
    }

    private static ItemStack createItem(String name, Material material, List<String> lore) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
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

    private static ItemStack setOwner(ItemStack item) {
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        assert meta != null;
        meta.setOwningPlayer(Bukkit.getOfflinePlayer("BlockminersTV"));
        item.setItemMeta(meta);
        return item;
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
        regions.add("Lagos,Nigeria");
        regions.add("Lima,Peru");
        regions.add("Madrid,Spain");
        regions.add("Manila,Philippines");
    }

    public static void regenerate() {
        inventory = getInventory();
    }
}
