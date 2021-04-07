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

    @Query("SELECT * FROM scannedpackets_table ORDER BY timeSeen DESC")
    LiveData<List<ScannedPacket>> getAllScanPkts();

    //Exposure Table methods

    @Insert
    void insertExposurePkt(ExposurePacket exposurePacket);
    @Delete
    void deleteExposurePkt(ExposurePacket exposurePacket);
    @Query("DELETE FROM EXPOSUREPACKETS_TABLE")
    void deleteOldExpPkts();
//    @Query("SELECT * FROM ExposurePackets_Table ORDER BY dateReported DESC")
//    LiveData<List<ExposurePacket>> getAllExpPkts();

}
