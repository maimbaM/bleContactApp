package com.maimba.west.bleContactApp;

import androidx.room.Ignore;

public class Symptoms {
    public String diseaseName;
    public String incubationPeriod;
    public String scienceName;
    public String common;
    public String severe;


    private boolean expandable;

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    public Symptoms() {
        this.expandable = expandable;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getIncubationPeriod() {
        return incubationPeriod;
    }

    public void setIncubationPeriod(String incubationPeriod) {
        this.incubationPeriod = incubationPeriod;
    }

    public String getScienceName() {
        return scienceName;
    }

    public void setScienceName(String scienceName) {
        this.scienceName = scienceName;
    }

    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
    }

    public String getSevere() {
        return severe;
    }

    public void setSevere(String severe) {
        this.severe = severe;
    }
}
