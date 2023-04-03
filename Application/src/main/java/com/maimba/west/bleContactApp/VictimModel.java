package com.maimba.west.bleContactApp;

import java.util.Date;

public class VictimModel {

    private String FirstName;
    private String LastName;
    private String Location;
    private String Phone;
    private String TimeSeen;

    public VictimModel() {
    }

    public VictimModel(String firstName, String lastName, String location, String phone, String timeSeen) {
        FirstName = firstName;
        LastName = lastName;
        Location = location;
        Phone = phone;
        TimeSeen = timeSeen;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getTimeSeen() {
        return TimeSeen;
    }

    public void setTimeSeen(String timeSeen) {
        TimeSeen = timeSeen;
    }
}
