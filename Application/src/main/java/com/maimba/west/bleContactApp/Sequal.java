//package com.maimba.west.bleContactApp;
//
//import android.content.Context;
//
//import androidx.annotation.NonNull;
//import androidx.work.OneTimeWorkRequest;
//import androidx.work.WorkManager;
//import androidx.work.WorkRequest;
//import androidx.work.Worker;
//import androidx.work.WorkerParameters;
//
//import com.maimba.west.bleContactApp.Scanner.LocationService;
//import com.maimba.west.bleContactApp.Scanner.ScannerWorker;
//
//public class Sequal extends Worker {
//
//    public WorkManager workManager ;
//    public WorkRequest locationWorkRequest;
//    public WorkRequest ScanWorkRequest;
//    public Sequal(@NonNull Context context, @NonNull WorkerParameters workerParams) {
//        super(context, workerParams);
//    }
//
//    @NonNull
//    @Override
//    public Result doWork() {
//        Context mContext = getApplicationContext();
//        workManager = workManager.getInstance(mContext);
//
//        locationWorkRequest = new OneTimeWorkRequest.Builder(LocationService.class).build();
//        ScanWorkRequest = new OneTimeWorkRequest.Builder(ScannerWorker.class).build();
////        location();
////        Scanning();
////        workManager.beginWith(locationWorkRequest)
////                .then(ScanWorkRequest)
////                .enqueue();
//        workManager.beginWith((OneTimeWorkRequest) locationWorkRequest)
//                .then((OneTimeWorkRequest) ScanWorkRequest)
//                .enqueue();
//
//
//        return null;
//    }
////
////    private void Scanning() {
////
////
////    }
////
////    private void location() {
////
////
////    }
//}
