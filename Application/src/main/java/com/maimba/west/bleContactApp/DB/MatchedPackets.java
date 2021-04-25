package com.maimba.west.bleContactApp.DB;

import androidx.room.ColumnInfo;

import java.sql.Timestamp;

public class MatchedPackets {

    @ColumnInfo(name = "pktData")
    private String packet;
    private Timestamp timeExposed;

    public String getPacket() {

        return packet;
    }

    public void setPacket(String packet) {
        this.packet = packet;
    }

    public void setTimeExposed(Timestamp timeExposed) {
        this.timeExposed = timeExposed;
    }

    public Timestamp getTimeExposed() {
        return timeExposed;
    }
}
