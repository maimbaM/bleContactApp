package com.maimba.west.bleContactApp.DB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ScannedPackets_Table")
public class ScannedPacket {


    private @PrimaryKey Long id;
    private String pktData;
    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    private String timeSeen;
    private String location;

    public ScannedPacket(String pktData) {
        this.pktData = pktData;


    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getTimeSeen() {
        return timeSeen;
    }
    public void setTimeSeen(String  timeSeen) {
        this.timeSeen = timeSeen;
    }


    public String getPktData() {
        return pktData;
    }
    public void setPktData(String pktData) {
        this.pktData = pktData;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }




}
