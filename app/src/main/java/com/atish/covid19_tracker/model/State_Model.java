package com.atish.covid19_tracker.model;

import java.util.List;

public class State_Model {
    String stateName,totalCase,totalDeath,recovered,active,today_Case,today_death,today_recovered;

    public State_Model(String stateName, String totalCase, String totalDeath, String recovered, String active, String today_Case, String today_death, String today_recovered) {
        this.stateName = stateName;
        this.totalCase = totalCase;
        this.totalDeath = totalDeath;
        this.recovered = recovered;
        this.active = active;
        this.today_Case = today_Case;
        this.today_death = today_death;
        this.today_recovered = today_recovered;

    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
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

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getToday_Case() {
        return today_Case;
    }

    public void setToday_Case(String today_Case) {
        this.today_Case = today_Case;
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
