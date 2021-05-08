package com.maimba.west.bleContactApp.DB;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Location_Table")
public class Location {

    private @PrimaryKey (autoGenerate = true)Long id;

    private String locationCoordinates;
    private String addressLine;

    public Location(String locationCoordinates, String addressLine) {
        this.locationCoordinates = locationCoordinates;
        this.addressLine = addressLine;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocationCoordinates() {
        return locationCoordinates;
    }

    public void setLocationCoordinates(String locationCoordinates) {
        this.locationCoordinates = locationCoordinates;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }








}
