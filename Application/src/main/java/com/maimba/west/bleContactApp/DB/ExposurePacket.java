package com.maimba.west.bleContactApp.DB;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ExposurePackets_Table")
public class ExposurePacket {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String userData;
//    private String dateReported;

    public ExposurePacket(String userData) {
        this.userData = userData;
//        this.dateReported = dateReported;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUserData() {
        return userData;
    }

//    public String getDateReported() {
//        return dateReported;
//    }
}
