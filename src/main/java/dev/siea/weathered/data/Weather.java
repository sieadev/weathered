package dev.siea.weathered.data;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

public class Weather {
    private final WeatherType weatherType;
    private final String region;
    private final long minecraftTicks;

    private final LocalDateTime localDateTime;
    private final double temp;

    public Weather(WeatherType weather, String region, long minecraftTicks, LocalDateTime localDateTime, double temp){
        this.weatherType = weather;
        this.region = region;
        this.minecraftTicks = minecraftTicks;
        this.localDateTime = localDateTime;
        this.temp = Math.round((temp - 273.15) * 100.0) / 100.0;
    }

    public WeatherType getWeatherType() {
        return weatherType;
    }

    public String getRegion() {
        return region;
    }

    public long getMinecraftTicks(){
        return minecraftTicks;
    }
    public LocalDateTime getLocalDateTime(){
        return localDateTime;
    }
    public double getTemp(MeasurementSystem measurementSystem) {
        return (measurementSystem == MeasurementSystem.METRIC ? temp : Math.round(((temp * 1.8) + 32) * 100.0) / 100.0);
    }

    public double getTemp() {
        return getTemp(MeasurementSystem.METRIC);
    }
}
