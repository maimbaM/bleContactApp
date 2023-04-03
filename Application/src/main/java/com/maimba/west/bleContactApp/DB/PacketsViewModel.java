package com.maimba.west.bleContactApp.DB;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.work.WorkManager;

import java.util.List;

public class PacketsViewModel extends AndroidViewModel {
     private PacketsRepository repository;
     private LiveData<List<ScannedPacket>> allScanPackets;
     private LiveData<List<ExposurePacket>> allExpPackets;
     private LiveData<List<MatchedPackets>> allMatchedPackets;
     private LiveData<List<String>> allExpUID;
//     private LiveData<List<String >> allServData;


    public PacketsViewModel(@NonNull Application application) {
        super(application);

        repository = new PacketsRepository(application);
        allScanPackets = repository.getAllScanPkts();
        allMatchedPackets = repository.getMatchedPackets();
        allExpUID = repository.getAllExpUID();
//        allServData = repository.getAllServData();


    }


    //Scanned Table

    public void insert(ScannedPacket scannedPacket){
        repository.insert(scannedPacket);
    }
    public void delete(ScannedPacket scannedPacket){
        repository.delete(scannedPacket);
    }
    public void deleteOldPackets(){
        repository.deleteOldPackets();
    }
    public void insertWithTime(String pktData, String location){repository.insertWithTime(pktData,location);}


    // Exposure Table
    public void  insertExp(ExposurePacket exposurePacket){repository.insertExp(exposurePacket);}
    public void  deleteExp(ExposurePacket exposurePacket){repository.deleteExp(exposurePacket);}
    public void  deleteAllExpPackets(){repository.deleteOldExp();}

    // Location Table
    public void insertLocation(Location location){repository .insertLocation(location);}


    // ServiceData Table

    public void insertServiceData(ServiceData serviceData){repository.insertServiceData(serviceData);}

    // LiveData Methods
    public LiveData<List<ScannedPacket>> getAllScanPackets(){
        return allScanPackets;
    }
    public LiveData<List<MatchedPackets>> getAllMatchedPackets(){return allMatchedPackets;}
    public LiveData<List<String>> getAllExpUID() {
        return allExpUID;
    }



}
