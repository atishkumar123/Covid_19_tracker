package com.atish.covid19_tracker.model;

import java.util.List;

public class State_Model {
    String stateName,totalCase,totalDeath,recovered,active;
    List<DistrictModel>districtModelList;
    public State_Model(){

    }

    public State_Model(String stateName, String totalCase, String totalDeath, String recovered, String active, List<DistrictModel> districtModelList) {
        this.stateName = stateName;
        this.totalCase = totalCase;
        this.totalDeath = totalDeath;
        this.recovered = recovered;
        this.active = active;
        this.districtModelList = districtModelList;
    }

    public List<DistrictModel> getDistrictModelList() {
        return districtModelList;
    }

    public void setDistrictModelList(List<DistrictModel> districtModelList) {
        this.districtModelList = districtModelList;
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
}
