package com.maimba.west.bleContactApp.DB;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PacketsRepository {

    private ScannedDao scannedDao;
    private LiveData<List<ScannedPacket>> allScanPkts;
    private LiveData<List<ExposurePacket>> allExpPkts;
    private LiveData<List<MatchedPackets>> allMatchedPkts;

    public PacketsRepository(Application application){
        ContractTracingDB database = ContractTracingDB.getInstance(application);
        scannedDao = database.scannedDao();
//        allScanPkts = scannedDao.getAllScanPkts();
//        allExpPkts = scannedDao.getAllExpPkts();
        allMatchedPkts = scannedDao.getMatchedPackets();
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
    public LiveData<List<ScannedPacket>> getAllScanPkts(){
        return allScanPkts;
    }

    // Exposure Table methods

    public void insertExp(ExposurePacket exposurePacket){
        new InsertExpAsyncTask(scannedDao).execute(exposurePacket);

    }

    public void deleteExp(ExposurePacket exposurePacket){
        new deleteExpAsyncTask(scannedDao).execute(exposurePacket);

    }

    public void deleteOldExp(){
        new deleteAllExpAsyncTask(scannedDao).execute();

    }

//    Query Methods

    public LiveData<List<MatchedPackets>> getMatchedPackets(){
        return allMatchedPkts;

    }

//    // Matched Packets Async Tasks
//
//    private static class MatchPacketsAsyncTask extends AsyncTask<Void, Void, Void>{
//        private ScannedDao scannedDao;
//
//        private MatchPacketsAsyncTask(ScannedDao scannedDao){
//            this.scannedDao = scannedDao;
//        }
//
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            scannedDao.getMatchedPackets();
//            return null;
//        }
//    }
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
            scannedDao.deleteOldPackets();
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
