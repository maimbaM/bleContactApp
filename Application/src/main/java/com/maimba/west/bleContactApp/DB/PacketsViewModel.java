package com.maimba.west.bleContactApp.DB;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PacketsViewModel extends AndroidViewModel {
     private PacketsRepository repository;
     private LiveData<List<ScannedPacket>> allScanPackets;
     private LiveData<List<ExposurePacket>> allExpPackets;
     private LiveData<List<MatchedPackets>> allMatchedPackets;

    public PacketsViewModel(@NonNull Application application) {
        super(application);

        repository = new PacketsRepository(application);
        allScanPackets = repository.getAllScanPkts();
        allMatchedPackets = repository.getMatchedPackets();
    }
    public void insert(ScannedPacket scannedPacket){
        repository.insert(scannedPacket);
    }
    public void delete(ScannedPacket scannedPacket){
        repository.delete(scannedPacket);
    }
    public void deleteOldPackets(){
        repository.deleteOldPackets();
    }
    public void insertWithTime(String pktData){repository.insertWithTime(pktData);}

    public void  insertExp(ExposurePacket exposurePacket){repository.insertExp(exposurePacket);}
    public void  deleteExp(ExposurePacket exposurePacket){repository.deleteExp(exposurePacket);}
    public void  deleteAllExpPackets(){repository.deleteOldExp();}
    public LiveData<List<ScannedPacket>> getAllScanPackets(){
        return allScanPackets;
    }
    public LiveData<List<MatchedPackets>> getAllMatchedPackets(){return allMatchedPackets;}

}
