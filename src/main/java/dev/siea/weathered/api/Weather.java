package dev.siea.weathered.api;

public class Weather {
    private final WeatherType weatherType;
    private final String region;
    private final long minecraftTicks;

    public Weather(WeatherType weather, String region, long minecraftTicks){
        this.weatherType = weather;
        this.region = region;
        this.minecraftTicks = minecraftTicks;
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
}
