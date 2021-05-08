//package com.maimba.west.bleContactApp;
//
//import android.content.Context;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//import androidx.work.Worker;
//import androidx.work.WorkerParameters;
//
//public class ScanWorker extends Worker {
//
//    public static final String TAG = ScanWorker.class.getSimpleName();
//
//    public ScanWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
//        super(context, workerParams);
//
//    }
//
//    private void getSizzly(){
//        for (int i = 0; i < 10; i++) {
//            Log.d(TAG, "getSizzly: hello tt");
//        }
//
//    }
//
//    @NonNull
//    @Override
//    public Result doWork() {
//
//     Context context = getApplicationContext();
//     getSizzly();
//
//        return Result.success();
//    }
//
//    @Override
//    public void onStopped() {
//        super.onStopped();
//        Log.d(TAG, "onStopped: Worker Stoppd");
//    }
//}
