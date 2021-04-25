package com.maimba.west.bleContactApp.Scanner;

import android.app.Application;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

//import com.maimba.west.bleContactApp.Scanner.ScannerFragment.SampleScanCallback;

import com.maimba.west.bleContactApp.Constants;
import com.maimba.west.bleContactApp.DB.PacketsRepository;
import com.maimba.west.bleContactApp.DB.PacketsViewModel;
import com.maimba.west.bleContactApp.DB.ScannedPacket;
import com.maimba.west.bleContactApp.MainActivity;
import com.maimba.west.bleContactApp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.maimba.west.bleContactApp.App.CHANNEL_1_ID;

public class ScannerService extends Service {
    private static final long SCAN_PERIOD = 10000;
    private static final String TAG = "scanService" ;
    public static boolean Scanner_running = false;
    private static final int FOREGROUND_NOTIFICATION_ID = 2;
    public static final String SCANNING_FAILED =
            "com.maimba.west.bleContactApp.scanning_failed";
    public static final String SCANNING_FAILED_EXTRA_CODE = "scanningFailureCode";

    private BluetoothAdapter mBluetoothAdapter;

    private BluetoothLeScanner mBluetoothLeScanner;


    private ScanCallback mScanCallback;
    private ArrayList<ScanResult> mArrayList;
    private PacketsRepository mPacketsRepository;
    private PacketsViewModel mpacketsViewModel;
    private Long timeseen;
//    private DBHelper DB;


    //    private ScanResultAdapter mAdapter;
    private Context mcontext;


    private Handler mHandler;

    /**
     * Must be called after object creation by MainActivity.
     *
     * @param btAdapter the local BluetoothAdapter
     */
    public void setBluetoothAdapter(BluetoothAdapter btAdapter) {
        this.mBluetoothAdapter = btAdapter;
        mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @Override
    public void onCreate() {

        Scanner_running = true;
        initialzeScanning();
        startScanning();
        super.onCreate();
        mcontext = this;

//        mAdapter = new ScanResultAdapter(mcontext,
//                LayoutInflater.from(mcontext));
        mArrayList = new ArrayList<>();
        mHandler = new Handler();
//        DB = new DBHelper(mcontext);
        mPacketsRepository = new PacketsRepository();
        mpacketsViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(PacketsViewModel.class);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this,CHANNEL_1_ID)
                .setContentTitle("BLE Contact Tracing App")
                .setContentText("This application will alert you in the case of exposure to infectious diseases")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1,notification);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Scanner_running = false;
        stopScanning();
        stopForeground(true);

        super.onDestroy();
    }

    private void stopScanning() {

        Log.d(TAG, "Stopping Scanning");

        // Stop the scan, wipe the callback.
        mBluetoothLeScanner.stopScan(mScanCallback);
        mScanCallback = null;

        // Even if no new results, update 'last seen' times.
//        mAdapter.notifyDataSetChanged();
    }

    private void initialzeScanning() {

            if (mBluetoothLeScanner == null) {
                BluetoothManager mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
                if (mBluetoothManager != null) {
                    BluetoothAdapter mBluetoothAdapter = mBluetoothManager.getAdapter();
                    if (mBluetoothAdapter != null) {
                        mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
                    } else {
                        Toast.makeText(this, getString(R.string.bt_null), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, getString(R.string.bt_null), Toast.LENGTH_LONG).show();
                }

            }
    }

//    private void goForeground() {
//        Intent notificationIntent2 = new Intent(this, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
//                notificationIntent2, 0);
//        Notification n = new Notification.Builder(this)
//                .setContentTitle("Scanning nearby devices via Bluetooth")
//                .setContentText("This device will scan close by devices")
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentIntent(pendingIntent)
//                .build();
//        startForeground(FOREGROUND_NOTIFICATION_ID, n);
//    }




    /**
     * Start scanning for BLE Advertisements (& set it up to stop after a set period of time).
     */
    public void startScanning() {
//        goForeground();
        if (mScanCallback == null) {
            Log.d(TAG, "Starting Scanning");


             // Will stop the scanning after a set time.
//            mHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    stopScanning();
//                }
//            }, SCAN_PERIOD);

            // Kick off a new scan.
            mScanCallback = new SampleScanCallback();
            mBluetoothLeScanner.startScan(buildScanFilters(), buildScanSettings(), mScanCallback);

//            String toastText = getString(R.string.scan_start_toast) + " "
//                    + TimeUnit.SECONDS.convert(SCAN_PERIOD, TimeUnit.MILLISECONDS) + " "
//                    + getString(R.string.seconds);
//            Toast.makeText(getActivity(), toastText, Toast.LENGTH_LONG).show();
//        } else {
//            Toast.makeText(getActivity(), R.string.already_scanning, Toast.LENGTH_SHORT);
//        }
    }}

    private ScanSettings buildScanSettings() {
        ScanSettings.Builder builder = new ScanSettings.Builder();
        builder.setScanMode(ScanSettings.SCAN_MODE_LOW_POWER);
        return builder.build();
    }

    private List<ScanFilter> buildScanFilters() {
        List<ScanFilter> scanFilters = new ArrayList<>();

        ScanFilter.Builder builder = new ScanFilter.Builder();
        // Comment out the below line to see all BLE devices around you
        builder.setServiceUuid(Constants.Service_UUID);
        scanFilters.add(builder.build());

        return scanFilters;

    }
    private class SampleScanCallback extends ScanCallback {



        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);

            for (ScanResult result : results){
                mArrayList.add(result);

            }

        }

        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);

            mArrayList.add(result);

                addtoDB(result);




        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            Log.d(TAG, "onScanFailed: " + errorCode);
        }

    }

    /**
     * Search the adapter for an existing device address and return it, otherwise return -1.
     */
    private int getPosition(String address) {
        int position = -1;
        for (int i = 0; i < mArrayList.size(); i++) {
            if (mArrayList.get(i).getDevice().getAddress().equals(address)) {
                position = i;
                break;

            }
        }
        return position;
    }
    public void addtoDB(ScanResult result){
        ScanRecord datainpacket = result.getScanRecord();
        byte[] pd = datainpacket.getServiceData(Constants.Service_UUID);
        String pktdata = new String(pd);
//        timeseen = System.currentTimeMillis();
        ScannedPacket scannedPacket = new ScannedPacket(pktdata);

        int existingPosition = getPosition(result.getDevice().getAddress());


        

        if (existingPosition >= 0) {
            // Device is already in list, update its record.
            mArrayList.set(existingPosition, result);
//            DB.insertpktdata(pktdata);

//            mPacketsRepository.insert(scannedPacket);

            mpacketsViewModel.insertWithTime(pktdata);
            Log.d(TAG, "add: Added Scan pkts");
        } else {
            // Add new Device's ScanResult to list.
            mArrayList.add(result);

//            DB.insertpktdata(pktdata);
//
//            mPacketsRepository.insert(scannedPacket);


        }
    };

}
