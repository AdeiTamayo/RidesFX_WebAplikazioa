package eus.ehu.ridesfx.domain;

public class CityData {
    private String name;
    private String country;
    private String citizenCount;
    private String imageUrl;

    public CityData(String name, String country, String citizenCount, String imageUrl) {
        this.name = name;
        this.country = country;
        this.citizenCount = citizenCount;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getCitizenCount() {
        return citizenCount;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}