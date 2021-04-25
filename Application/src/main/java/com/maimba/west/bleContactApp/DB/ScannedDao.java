package com.maimba.west.bleContactApp.DB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ScannedDao {

    // Scanned Table methods
    @Insert
    void insert(ScannedPacket scannedPacket);
    @Delete
    void delete(ScannedPacket scannedPacket);

    @Query("DELETE FROM ScannedPackets_Table")
    void deleteOldPackets();

    @Query("INSERT INTO ScannedPackets_Table (pktData) VALUES (:pktData) ")
    void insertWithTime(String pktData);

    @Query("SELECT * FROM scannedpackets_table ORDER BY timeSeen DESC")
    LiveData<List<ScannedPacket>> getAllScanPkts();

    //Exposure Table methods

    @Insert
    void insertExposurePkt(ExposurePacket exposurePacket);
    @Delete
    void deleteExposurePkt(ExposurePacket exposurePacket);
    //@Query("DELETE FROM ExposurePackets_Table WHERE ")
    @Query("DELETE FROM EXPOSUREPACKETS_TABLE")
    void deleteOldExpPkts();
//    @Query("SELECT * FROM ExposurePackets_Table ORDER BY dateReported DESC")
//    LiveData<List<ExposurePacket>> getAllExpPkts();


////    Check Exposure Query
     @Query("SELECT DISTINCT ScannedPackets_Table.pktData , ScannedPackets_Table.timeSeen " +
            "FROM ScannedPackets_Table " +
            "INNER JOIN ExposurePackets_Table on ScannedPackets_Table.pktData = ExposurePackets_Table.userData")
   LiveData<List<MatchedPackets>>   getMatchedPackets();

}
