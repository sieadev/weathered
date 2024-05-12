package dev.siea.weathered.data;

public class Region {
    private final String city;
    private final String country;
    private final int cityID;

    public Region(String city, String country, int cityID){
        this.city = city;
        this.country = country;
        this.cityID = cityID;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String toString(){
        return city + ", " + country;
    }

    public int getCityID() {
        return cityID;
    }
}
