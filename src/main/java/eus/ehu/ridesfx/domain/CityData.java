package eus.ehu.ridesfx.domain;

public class CityData {
    private String toponymName;
    private String countryName;
    private String citizenCount;
    private int population;

    public CityData(String toponymName, String countryName, String citizenCount, int population) {
        this.toponymName = toponymName;
        this.countryName = countryName;
        this.citizenCount = citizenCount;
        this.population = population;
    }

    public String getToponymName() {
        return toponymName;
    }

    public void setToponymName(String toponymName) {
        this.toponymName = toponymName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCitizenCount() {
        return citizenCount;
    }

    public void setCitizenCount(String citizenCount) {
        this.citizenCount = citizenCount;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    @Override
    public String toString() {
        return "CityData{" +
                "toponymName='" + toponymName + '\'' +
                ", countryName='" + countryName + '\'' +
                ", citizenCount='" + citizenCount + '\'' +
                ", population=" + population +
                '}';
    }

}