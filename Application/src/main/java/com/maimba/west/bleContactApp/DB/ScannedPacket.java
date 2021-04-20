package com.maimba.west.bleContactApp.DB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ScannedPackets_Table")
public class ScannedPacket {


    private @PrimaryKey Long id;
    private String pktData;
    @ColumnInfo(defaultValue = "(datetime('now'))")
    private Long timeSeen;

    public void setTimeSeen(Long timeSeen) {
        this.timeSeen = timeSeen;
    }

    public ScannedPacket(String pktData, Long timeSeen) {
        this.pktData = pktData;
        this.timeSeen = timeSeen;
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

    public String getPktData() {
        return pktData;
    }

    public Long getTimeSeen() {
        return timeSeen;
    }
}
