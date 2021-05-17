package com.maimba.west.bleContactApp;

import java.util.Date;

public class VictimModel {

    private String FirstName;
    private String LastName;
    private String Location;
    private String Phone;
    private Date TimeSeen;

    public VictimModel() {
    }

    public VictimModel(String firstName, String lastName, String location, String phone, Date timeSeen) {
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

    public Date getTimeSeen() {
        return TimeSeen;
    }

    public void setTimeSeen(Date timeSeen) {
        TimeSeen = timeSeen;
    }
}
