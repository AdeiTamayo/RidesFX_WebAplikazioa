package eus.ehu.ridesfx.domain;

import com.google.gson.annotations.SerializedName;


public class CityInfo {

    @SerializedName("toponymName")

    private String toponymName;

    @SerializedName("countryName")
    private String countryName;

    @SerializedName("adminName1")
    private String adminName1;

    @SerializedName("fcodeName")
    private String fcodeName;

    @SerializedName("population")
    private int population;


    public CityInfo(String toponymName, String countryName, String citizenCount, String fcodeName, int population, int totalResultsCount) {
        this.toponymName = toponymName;
        this.countryName = countryName;
        this.adminName1 = citizenCount;
        this.fcodeName = fcodeName;
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

    public String getAdminName1() {
        return adminName1;
    }

    public void setAdminName1(String adminName1) {
        this.adminName1 = adminName1;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getFcodeName() {
        return fcodeName;
    }

    public void setFcodeName(String fcodeName) {
        this.fcodeName = fcodeName;
    }


    @Override
    public String toString() {
        return "CityData{" +
                "toponymName='" + toponymName + '\'' +
                ", countryName='" + countryName + '\'' +
                ", citizenCount='" + adminName1 + '\'' +
                ", fcodeName='" + fcodeName + '\'' +
                ", population=" + population +

                '}';
    }

}