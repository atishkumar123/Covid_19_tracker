package com.atish.covid19_tracker.model;

public class DistrictModel {
    String DistrictName, totalCase, deceased, recovered, active,today_case,today_death,today_recovered;


    public DistrictModel() {

    }

    public DistrictModel(String districtName, String totalCase, String deceased, String recovered, String active, String today_case, String today_death, String today_recovered) {
        DistrictName = districtName;
        this.totalCase = totalCase;
        this.deceased = deceased;
        this.recovered = recovered;
        this.active = active;
        this.today_case = today_case;
        this.today_death = today_death;
        this.today_recovered = today_recovered;
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

    public String getDeceased() {
        return deceased;
    }

    public void setDeceased(String deceased) {
        this.deceased = deceased;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getToday_case() {
        return today_case;
    }

    public void setToday_case(String today_case) {
        this.today_case = today_case;
    }

    public String getToday_death() {
        return today_death;
    }

    public void setToday_death(String today_death) {
        this.today_death = today_death;
    }

    public String getToday_recovered() {
        return today_recovered;
    }

    public void setToday_recovered(String today_recovered) {
        this.today_recovered = today_recovered;
    }
}