package dev.siea.weathered.util;

import dev.siea.weathered.data.MeasurementSystem;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Converter {
    public static String localDateTimeToString(LocalDateTime time, MeasurementSystem measurementSystem){
        DateTimeFormatter formatterHours = DateTimeFormatter.ofPattern("HH");
        DateTimeFormatter formatterMinute = DateTimeFormatter.ofPattern("mm");
        String hours = time.format(formatterHours);
        String minutes = time.format(formatterMinute);
        StringBuilder sb = new StringBuilder();
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
        sb.append("§6").append(hours);
        sb.append("§8:");
        sb.append("§6").append(minutes);
        return sb.toString();
    }
}
