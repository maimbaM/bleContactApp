package com.maimba.west.bleContactApp.DB;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;

import java.sql.Timestamp;
import java.util.Date;

public class MatchedPackets {

//    @ColumnInfo(name = "pktData")
//    private String packet;
    @ColumnInfo(name = "timeSeen")
    private String timeExposed;
    @ColumnInfo(name = "location")
    private String location;
    @ColumnInfo(name = "caseDisease")
    private String disease;
    @ColumnInfo(name = "userName")
    private String name;
    @ColumnInfo(name = "userPhone")
    private String phone;

    @Ignore
    private boolean expandable;

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }



    public MatchedPackets() {
       this.expandable = false;
    }



    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }





    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }



//    public String getPacket() {
//
//        return packet;
//    }

//    public void setPacket(String packet) {
//        this.packet = packet;
//    }

    public void setTimeExposed(String timeExposed) {
        this.timeExposed = timeExposed;
    }

    public String getTimeExposed() {
        return timeExposed;
    }
}
