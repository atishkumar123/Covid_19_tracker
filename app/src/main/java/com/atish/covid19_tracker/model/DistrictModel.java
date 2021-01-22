package com.atish.covid19_tracker.model;

public class DistrictModel {
    String DistrictName,totalCase,totalDeath,recovered;


    public DistrictModel(){

    }

    public DistrictModel(String districtName, String totalCase, String totalDeath, String recovered) {
        DistrictName = districtName;
        this.totalCase = totalCase;
        this.totalDeath = totalDeath;
        this.recovered = recovered;

    }

    public String getDistrictName() {
        return DistrictName;
    }

    public void setDistrictName(String districtName) {
        DistrictName = districtName;
    }

    public String getTotalCase() {
        return totalCase;
    }

    public void setTotalCase(String totalCase) {
        this.totalCase = totalCase;
    }

    public String getTotalDeath() {
        return totalDeath;
    }

    public void setTotalDeath(String totalDeath) {
        this.totalDeath = totalDeath;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }


}


