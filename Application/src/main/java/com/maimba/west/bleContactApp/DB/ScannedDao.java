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
    void deleteAllPackets();

    @Query("INSERT INTO ScannedPackets_Table (pktData,location) VALUES (:pktData,:location) ")
    void insertWithTime(String pktData, String location);

    @Query("SELECT * FROM scannedpackets_table ORDER BY timeSeen DESC")
    LiveData<List<ScannedPacket>> getAllScanPkts();
    @Query("DELETE FROM scannedpackets_table WHERE timeSeen < DATETIME('now', '-1 day')")
    void deleteOlder();


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



    //Location Table Methods
    @Insert
    void insertLocation(Location location);

    @Query("SELECT Location_Table.addressLine FROM Location_Table WHERE id=(SELECT max(id) FROM Location_Table)")
    String selectLastLocation();



//Service Data Table
    @Insert
    void insertServiceData(ServiceData serviceData);

    @Query("SELECT ServiceData_Table.serviceData FROM ServiceData_Table WHERE id=(SELECT max(id) FROM ServiceData_Table)")
    String selectServiceData();




    ////    Check Exposure Query
     @Query("SELECT DISTINCT ScannedPackets_Table.timeSeen ,ScannedPackets_Table.location , " +
             "ExposurePackets_Table.caseDisease , ExposurePackets_Table.FirstName , ExposurePackets_Table.userPhone " +
            "FROM ScannedPackets_Table " +
            "INNER JOIN ExposurePackets_Table on ScannedPackets_Table.pktData = ExposurePackets_Table.userData")
   LiveData<List<MatchedPackets>>   getMatchedPackets();

    @Query("SELECT DISTINCT ExposurePackets_Table.userID " +
            "FROM ExposurePackets_Table " +
            "INNER JOIN ScannedPackets_Table on ScannedPackets_Table.pktData = ExposurePackets_Table.userData")
    LiveData<List<String>>   getExpUID();

}
