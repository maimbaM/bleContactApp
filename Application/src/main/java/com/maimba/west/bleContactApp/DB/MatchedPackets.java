package com.maimba.west.bleContactApp.DB;

import androidx.room.ColumnInfo;

public class MatchedPackets {

    @ColumnInfo(name = "pktData")
    private String packet;
//    private String timeExposed;

    public String getPacket() {

        return packet;
    }

    public void setPacket(String packet) {
        this.packet = packet;
    }

//    public void setTimeExposed(String timeExposed) {
//        this.timeExposed = timeExposed;
//    }

//    public String getTimeExposed() {
//        return timeExposed;
//    }
}
