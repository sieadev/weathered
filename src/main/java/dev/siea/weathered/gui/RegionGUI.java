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
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class RegionGUI {
    private static final HashMap<Integer, String> buttons = new HashMap<>();
    private static final HashMap<String,String> regions = new HashMap();
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
        PlayerProfile profile = getProfile(regions.get(weather.getRegion()));
        if (profile != null) {
            SkullMeta meta = (SkullMeta) current.getItemMeta();
            assert meta != null;
            meta.setOwnerProfile(profile);
            current.setItemMeta(meta);
        }
        inventory.setItem(4,current);

        buttons.clear();

        int slot = 10;
        for (String region : regions.keySet()){
            if (slot == 17 || slot == 26 || slot == 35) {
                slot = slot + 2;
            }
            if (slot > 42) break;
            if (region.equals(weather.getRegion())) continue;

            ItemStack regionItem = createItem("§e" + region, Material.PLAYER_HEAD);

            PlayerProfile profile2 = getProfile(regions.get(region));
            if (profile2 != null) {
                SkullMeta meta = (SkullMeta) regionItem.getItemMeta();
                assert meta != null;
                meta.setOwnerProfile(profile2);
                regionItem.setItemMeta(meta);
            }
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
        regions.put("Berlin,Germany","5e7899b4806858697e283f084d9173fe487886453774626b24bd8cfecc77b3f");
        regions.put("Paris,France", "6903349fa45bdd87126d9cd3c6c0abba7dbd6f56fb8d78701873a1e7c8ee33cf");
        regions.put("London,United Kingdom", "879d99d9c46474e2713a7e84a95e4ce7e8ff8ea4d164413a592e4435d2c6f9dc");
        regions.put("New York,United States", "4cac9774da1217248532ce147f7831f67a12fdcca1cf0cb4b3848de6bc94b4");
        regions.put("Tokyo,Japan", "d6c2ca7238666ae1b9dd9daa3d4fc829db22609fb569312dec1fb0c8d6dd6c1d");
        regions.put("Moscow,Russia", "16eafef980d6117dabe8982ac4b4509887e2c4621f6a8fe5c9b735a83d775ad");
        regions.put("Sydney,Australia", "cf4aa2a244784d48b155ff044b8cf96df5bd4e87e01924a75d62a9242a16cf");
        regions.put("Rio de Janeiro,Brazil", "9a46475d5dcc815f6c5f2859edbb10611f3e861c0eb14f088161b3c0ccb2b0d9");
        regions.put("Cairo,Egypt","826e742b32f0f8db59c07b1bcdde6f8a93f85c929e598c7e9273b9211f2ce78");
        regions.put("Toronto,Canada","f241a697f6dfb1c57cda327baa6732a7828c398be4ebfdbd166c232bcae2b");
        regions.put("Dubai,United Arab Emirates", "dd9f2944d48fb967de7aa5d4b8c848307f467323aea93365b7ed2c16a8f1a939");
        regions.put("Rome,Italy", "85ce89223fa42fe06ad65d8d44ca412ae899c831309d68924dfe0d142fdbeea4");
        regions.put("Singapore,Singapore", "8b5ed11f797f3fc61eaf8dafb6bf3234d31b96ab7596bd2df722d2ef3473c27");
        regions.put("Seoul,South Korea", "795b8c051cd9246a6b2709a5a20e3d1f9730c96b2167d6f92805f1491cb8f621");
        regions.put("Mexico City,Mexico", "ca971bfcf33b439821b4e5f0c86cd61435cd3e0c819e35dd9774ef0399f132b");
        regions.put("Mumbai,India", "2f89e46f1d5454c967676efb4977a84a18d0084326c76c7bfc4e7843bb1a901");
        regions.put("Los Angeles,United States", "4cac9774da1217248532ce147f7831f67a12fdcca1cf0cb4b3848de6bc94b4");
        regions.put("Shanghai,China", "7f9bc035cdc80f1ab5e1198f29f3ad3fdd2b42d9a69aeb64de990681800b98dc");
        regions.put("Chicago,United States", "4cac9774da1217248532ce147f7831f67a12fdcca1cf0cb4b3848de6bc94b4");
        regions.put("Bangkok,Thailand", "2a7916e4a852f7e6f3f3de19c7fb57686a37bce834bd54684a7dbef8d53fb");
        regions.put("Buenos Aires,Argentina", "f6d033dc5f675ad51bc06c7a1949c35a1d37e48a2e1c2789c2cf7d30ec58f32c");
        regions.put("Istanbul,Turkey", "9852b9aba3482348514c1034d0affe73545c9de679ae4647f99562b5e5f47d09");
        regions.put("Jakarta,Indonesia", "cbe63fcaa0203f8046fb96375d079a5c09b4161d0119d7194566e689cab18f68");
        regions.put("Lima,Peru", "24d03bd4410babdc682493b3c2bba26e730e6bc658d3888e79bf712f853");
        regions.put("Madrid,Spain", "32bd4521983309e0ad76c1ee29874287957ec3d96f8d889324da8c887e485ea8");
        regions.put("Manila,Philippines", "55da7eb0ce466c85be0757ae98b43edd45138a24ed92622b18dfc314ad8012d7");
        regions.put("Warsaw,Poland", "921b2af8d2322282fce4a1aa4f257a52b68e27eb334f4a181fd976bae6d8eb");
        regions.put("Lagos,Nigeria", "b5e0ceb0461fc951354f6d4777802d5e4268c1f81719e46667832b183df9510");
    }

    public static void regenerate() {
        inventory = null;
    }

    private static final UUID RANDOM_UUID = UUID.fromString("92864445-51c5-4c3b-9039-517c9927d1b4");

    private static PlayerProfile getProfile(String url) {
        PlayerProfile profile = Bukkit.createPlayerProfile(RANDOM_UUID);
        PlayerTextures textures = profile.getTextures();
        URL urlObject;
        try {
            urlObject = new URL("https://textures.minecraft.net/texture/" + url);
        } catch (MalformedURLException exception) {
            System.err.println("Malformed URL: " + "https://textures.minecraft.net/texture/" + url);
            return null;
        }
        textures.setSkin(urlObject);
        profile.setTextures(textures);
        return profile;
    }
}
