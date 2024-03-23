package dev.siea.weathered.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import java.util.ArrayList;
import java.util.List;

public class WeatheredTabCompletions implements TabCompleter{
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            if (sender.hasPermission("weathered.reload")) {
                completions.add("reload");
            }
            if (sender.hasPermission("weathered.setRegion")) {
                completions.add("region");
            }
        }
        else if (args.length == 2 && (args[0].equalsIgnoreCase("region") && sender.hasPermission("weathered.setRegion"))) {
            completions.add("Berlin,Germany");
            completions.add("Paris,France");
            completions.add("London,United Kingdom");
            completions.add("New York,United States");
            completions.add("Tokyo,Japan");
            completions.add("Moscow,Russia");
            completions.add("Sydney,Australia");
            completions.add("Rio de Janeiro,Brazil");
            completions.add("Cairo,Egypt");
            completions.add("Toronto,Canada");
            completions.add("Dubai,United Arab Emirates");
            completions.add("Rome,Italy");
            completions.add("Singapore,Singapore");
            completions.add("Seoul,South Korea");
            completions.add("Mexico City,Mexico");
            completions.add("Mumbai,India");
            completions.add("Los Angeles,United States");
            completions.add("Shanghai,China");
            completions.add("Chicago,United States");
            completions.add("Bangkok,Thailand");
            completions.add("Buenos Aires,Argentina");
            completions.add("Istanbul,Turkey");
            completions.add("Jakarta,Indonesia");
            completions.add("Lagos,Nigeria");
            completions.add("Lima,Peru");
            completions.add("Madrid,Spain");
            completions.add("Manila,Philippines");
            completions.add("Nairobi,Kenya");
            completions.add("Sao Paulo,Brazil");
            completions.add("Tehran,Iran");
            completions.add("Washington,United States");
            completions.add("Hong Kong,China");
            completions.add("Taipei,Taiwan");
            completions.add("Vienna,Austria");
            completions.add("Zurich,Switzerland");
            completions.add("Osaka,Japan");
            completions.add("Helsinki,Finland");
            completions.add("Stockholm,Sweden");
            completions.add("Oslo,Norway");
            completions.add("Copenhagen,Denmark");
            completions.add("Melbourne,Australia");
            completions.add("Vancouver,Canada");
            completions.add("Barcelona,Spain");
            completions.add("Dublin,Ireland");
            completions.add("Athens,Greece");
            completions.add("Warsaw,Poland");
            completions.add("Brussels,Belgium");
            completions.add("Budapest,Hungary");
            completions.add("Prague,Czech Republic");
            completions.add("Edinburgh,United Kingdom");
            completions.add("Amsterdam,Netherlands");
            completions.add("Luxembourg,Luxembourg");
            completions.add("Lisbon,Portugal");
            completions.add("Bucharest,Romania");
            completions.add("Bratislava,Slovakia");
            completions.add("Tallinn,Estonia");
            completions.add("Vilnius,Lithuania");
            completions.add("Riga,Latvia");
        }
        return completions;
    }
}
