package com.maimba.west.bleContactApp.DB;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.work.WorkManager;

import java.util.List;
import java.util.concurrent.Executors;

public class PacketsRepository {

    private ScannedDao scannedDao;
    private WorkManager workManager;
    private LiveData<List<ScannedPacket>> allScanPkts;
    private LiveData<List<ExposurePacket>> allExpPkts;
    private LiveData<List<MatchedPackets>> allMatchedPkts;
    private LiveData<List<String>> allExpUID;
    private String deviceLocation2;

    public PacketsRepository(Application application){
        ContractTracingDB database = ContractTracingDB.getInstance(application);
        scannedDao = database.scannedDao();
//        addressLine = scannedDao.selectLastLocation();
//        deviceLocation2 = scannedDao.selectLastLocation();
        allScanPkts = scannedDao.getAllScanPkts();
//        allExpPkts = scannedDao.getAllExpPkts();
        allMatchedPkts = scannedDao.getMatchedPackets();
        allExpUID = scannedDao.getExpUID();
        workManager = WorkManager.getInstance(application);
    }

    public PacketsRepository() {

    }

//    Query Methods

    public LiveData<List<MatchedPackets>> getMatchedPackets(){
        return allMatchedPkts;

    }
    public LiveData<List<ScannedPacket>> getAllScanPkts(){
        return allScanPkts;
    }

    public LiveData<List<String>> getAllExpUID() {
        return allExpUID;
    }
    // Scanned Table Methods

    public void insert(ScannedPacket scannedPacket){
        new InsertScanAsyncTask(scannedDao).execute(scannedPacket);

    }
    public void delete(ScannedPacket scannedPacket){
        new DeleteScanAsyncTask(scannedDao).execute(scannedPacket);

    }
    public void deleteOldPackets(){
        new DeleteAllScanAsyncTask(scannedDao).execute();

    }


    public void insertWithTime(String pktdata, String location){
        new InsertWithTimeAsyncTask(scannedDao).execute(pktdata,location);
    }

    /** Exposure Table Methods */

    public void insertExp(ExposurePacket exposurePacket){
        new InsertExpAsyncTask(scannedDao).execute(exposurePacket);

    }

    public void deleteExp(ExposurePacket exposurePacket){
        new deleteExpAsyncTask(scannedDao).execute(exposurePacket);

    }

  public void deleteOldExp(){
        new deleteAllExpAsyncTask(scannedDao).execute();

    }

     //Location Table Methods

    public void insertLocation(Location location){
        new InsertLocationAsyncTask(scannedDao).execute(location);

    }

    // Service Data Table Methods

    public void insertServiceData(ServiceData serviceData){
        new InsertServiceDataAsyncTask(scannedDao).execute(serviceData);
    }

    // Service Data Table Async Tasks

    private static class InsertServiceDataAsyncTask extends AsyncTask<ServiceData, Void, Void>{
        private ScannedDao scannedDao;

        private InsertServiceDataAsyncTask(ScannedDao scannedDao){
            this.scannedDao = scannedDao;
        }


        @Override
        protected Void doInBackground(ServiceData... serviceData) {
            scannedDao.insertServiceData((serviceData[0]));
            return null;
        }
    }


    // Location Table Aync Tasks

    private static class InsertLocationAsyncTask extends AsyncTask<Location, Void, Void>{
        private ScannedDao scannedDao;

        private InsertLocationAsyncTask(ScannedDao scannedDao){
            this.scannedDao = scannedDao;
        }


        @Override
        protected Void doInBackground(Location... locations) {
            scannedDao.insertLocation((locations[0]));
            return null;
        }
    }



// Scanned Table Async Tasks
    private static class InsertScanAsyncTask extends AsyncTask<ScannedPacket, Void, Void>{
        private ScannedDao scannedDao;

        private InsertScanAsyncTask(ScannedDao scannedDao){
            this.scannedDao = scannedDao;
        }


        @Override
        protected Void doInBackground(ScannedPacket... scannedPackets) {
            scannedDao.insert((scannedPackets[0]));
            return null;
        }
    }
    private static class DeleteScanAsyncTask extends AsyncTask<ScannedPacket, Void, Void>{
        private ScannedDao scannedDao;

        private DeleteScanAsyncTask(ScannedDao scannedDao){
            this.scannedDao = scannedDao;
        }


        @Override
        protected Void doInBackground(ScannedPacket... scannedPackets) {
            scannedDao.delete((scannedPackets[0]));
            return null;
        }
    }
    private static class DeleteAllScanAsyncTask extends AsyncTask<Void, Void, Void>{
        private ScannedDao scannedDao;

        private DeleteAllScanAsyncTask(ScannedDao scannedDao){
            this.scannedDao = scannedDao;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            scannedDao.deleteOlder();
            return null;
        }
    }
    private static class InsertWithTimeAsyncTask extends AsyncTask<String,Void,Void>{
        private ScannedDao scannedDao;

        private InsertWithTimeAsyncTask(ScannedDao scannedDao){
            this.scannedDao = scannedDao;
        }

        @Override
        protected Void doInBackground(String... params) {
            scannedDao.insertWithTime((params[0]),(params[1]));
            return null;
        }

    }

    // Exposure Table Async Tasks

    private static class InsertExpAsyncTask extends AsyncTask<ExposurePacket, Void, Void>{
        private ScannedDao scannedDao;

        private InsertExpAsyncTask(ScannedDao scannedDao){
            this.scannedDao = scannedDao;
        }


        @Override
        protected Void doInBackground(ExposurePacket... exposurePackets) {
            scannedDao.insertExposurePkt((exposurePackets[0]));
            return null;
        }
    }
    private static class deleteExpAsyncTask extends AsyncTask<ExposurePacket, Void, Void>{
        private ScannedDao scannedDao;

        private deleteExpAsyncTask(ScannedDao scannedDao){
            this.scannedDao = scannedDao;
        }


        @Override
        protected Void doInBackground(ExposurePacket... exposurePackets) {
            scannedDao.deleteExposurePkt((exposurePackets[0]));
            return null;
        }
    }
    private static class deleteAllExpAsyncTask extends AsyncTask<Void, Void, Void>{
        private ScannedDao scannedDao;

        private deleteAllExpAsyncTask(ScannedDao scannedDao){
            this.scannedDao = scannedDao;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            scannedDao.deleteOldExpPkts();
            return null;
        }
    }
}
