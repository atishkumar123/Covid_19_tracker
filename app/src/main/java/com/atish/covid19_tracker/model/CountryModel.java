package com.atish.covid19_tracker.model;

import java.util.List;

public class CountryModel {

    String countryName,totalCases,totalDeath,recovered,active,critical,todayCase,todayDeath,todayRecover,flag;


    public CountryModel(){

    }

    public CountryModel(String countryName, String totalCases, String totalDeath, String recovered, String active, String critical, String todayCase, String todayDeath, String todayRecover, String flag) {
        this.countryName = countryName;
        this.totalCases = totalCases;
        this.totalDeath = totalDeath;
        this.recovered = recovered;
        this.active = active;
        this.critical = critical;
        this.todayCase = todayCase;
        this.todayDeath = todayDeath;
        this.todayRecover = todayRecover;
        this.flag = flag;

    }


    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(String totalCases) {
        this.totalCases = totalCases;
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

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getCriticle() {
        return critical;
    }

    public void setCriticle(String criticle) {
        this.critical = criticle;
    }

    public String getTodayCase() {
        return todayCase;
    }

    public void setTodayCase(String todayCase) {
        this.todayCase = todayCase;
    }

    public String getTodayDeath() {
        return todayDeath;
    }

    public void setTodayDeath(String todayDeath) {
        this.todayDeath = todayDeath;
    }

    public String getTodayRecover() {
        return todayRecover;
    }

    public void setTodayRecover(String todayRecover) {
        this.todayRecover = todayRecover;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
