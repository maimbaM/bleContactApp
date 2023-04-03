   package com.maimba.west.bleContactApp.Scanner;

import android.Manifest;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

//import com.maimba.west.bleContactApp.Scanner.ScannerFragment.SampleScanCallback;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.maimba.west.bleContactApp.Constants;
import com.maimba.west.bleContactApp.DB.ContractTracingDB;
import com.maimba.west.bleContactApp.DB.PacketsRepository;
import com.maimba.west.bleContactApp.DB.PacketsViewModel;
import com.maimba.west.bleContactApp.DB.ScannedDao;
import com.maimba.west.bleContactApp.DB.ScannedPacket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ScannerWorker extends Worker{
    private static final long SCAN_PERIOD = 10000;
    private static final String TAG = "scanService & location";
    public static boolean Scanner_running = false;
    private static final int FOREGROUND_NOTIFICATION_ID = 2;
    public static final String SCANNING_FAILED =
            "com.maimba.west.bleContactApp.scanning_failed";
    public static final String SCANNING_FAILED_EXTRA_CODE = "scanningFailureCode";

    private BluetoothAdapter mBluetoothAdapter;
private BluetoothManager mBluetoothManager;
    private BluetoothLeScanner mBluetoothLeScanner;

    private Application application;
    private ScanCallback mScanCallback;
    private ArrayList<ScanResult> mArrayList = new ArrayList<>();

    private PacketsViewModel mpacketsViewModel;
    private Long timeseen;
    private FusedLocationProviderClient fusedLocationProviderClient;

    //    private ScanResultAdapter mAdapter;
    private Context mcontext;
    private PacketsRepository mPacketsRepository ;
    public double latitude;
    public double longitude;
    private ScannedDao scannedDao;

    private String location;
    private String lastLocation;
    private String pktdata;



//    private Handler mHandler = new Handler();


//    /**
//     * Must be called after object creation by MainActivity.
//     *
//     * @param btAdapter the local BluetoothAdapter
//     */




//        mcontext = getApplicationContext();


//        mAdapter = new ScanResultAdapter(mcontext,
//                LayoutInflater.from(mcontext));


//        DB = new DBHelper(mcontext);
//         new  PacketsRepository(Application application);
//        mpacketsViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(PacketsViewModel.class);
//        mpacketsViewModel = new ViewModelProvider().get(PacketsViewModel.class);

    public ScannerWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }
    @NonNull
    @Override
    public Result doWork() {
        mcontext = getApplicationContext();
        mBluetoothLeScanner = BluetoothAdapter.getDefaultAdapter().getBluetoothLeScanner();
        mPacketsRepository = new PacketsRepository(application);
//        lastLocation = mPacketsRepository.getLastLocation();
        ContractTracingDB database = ContractTracingDB.getInstance(mcontext);
        scannedDao = database.scannedDao();
        lastLocation = scannedDao.selectLastLocation();
        System.out.println(lastLocation);
//         pktdata = "adfghjklpoiuhygAASD";
//        Log.d(TAG, "doWork: Last location" + lastLocation);
//        Data inputdata = getInputData();
//        lastLocation = inputdata.getString(KEY_RESULT)


//        scannedDao.insertWithTime(pktdata,lastLocation);

//        startLocationService();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        startScanning();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopScanning();
//
        return Result.success();

    }


//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
//                notificationIntent, 0);
//
//        Notification notification = new NotificationCompat.Builder(this,CHANNEL_1_ID)
//                .setContentTitle("BLE Contact Tracing App")
//                .setContentText("This application will alert you in the case of exposure to infectious diseases")
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentIntent(pendingIntent)
//                .build();
//
//        startForeground(1,notification);
//
//        return ;
//    }



//    @Override
//    public void onDestroy() {
//        Scanner_running = false;
//
//        stopForeground(true);
//
//        super.onDestroy();
//    }


    private void stopScanning() {

        Log.d(TAG, "Stopping Scanning");

        // Stop the scan, wipe the callback.
        mBluetoothLeScanner.stopScan(mScanCallback);
        mScanCallback = null;

        // Even if no new results, update 'last seen' times.
//        mAdapter.notifyDataSetChanged();
    }
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            stopScanning();
        }
    };

//    private void initialzeScanning() {
//
//            if (mBluetoothLeScanner == null) {
//                BluetoothManager mBluetoothManager = BluetoothManager
//                        .mcontext.getSystemService(Context.BLUETOOTH_SERVICE);
//                if (mBluetoothManager != null) {
//                    BluetoothAdapter mBluetoothAdapter = mBluetoothManager.getAdapter();
//                    if (mBluetoothAdapter != null) {
//                        mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
//                    } else {
////                        Toast.makeText(this, getString(R.string.bt_null), Toast.LENGTH_LONG).show();
//                        Log.d(TAG, "initialzeScanning: Error: Bluetooth object null" );
//                    }
//                } else {
////                    Toast.makeText(this, getString(R.string.bt_null), Toast.LENGTH_LONG).show();
//                    Log.d(TAG, "initialzeScanning: Error: Bluetooth object null" );
//                }
//
//            }
//    }


    /**
     * Start scanning for BLE Advertisements (& set it up to stop after a set period of time).
     */
    public void startScanning() {
        if (mScanCallback == null) {
            Log.d(TAG, "Starting Scanning");

            // Kick off a new scan.
            mScanCallback = new SampleScanCallback();
            mBluetoothLeScanner.startScan(buildScanFilters(), buildScanSettings(), mScanCallback);


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

//            for (ScanResult result : results){
////                mArrayList.add(result);
//
//            }

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

//    public void add22(){
//        String pktdata = "asdfghjkloiuytrews";
//        String mlocation = "kakamega";
//        mPacketsRepository.insertWithTime(pktdata,mlocation);
//    }
    public void addtoDB(ScanResult result){
        ScanRecord datainpacket = result.getScanRecord();
        byte[] pd = datainpacket.getServiceData(Constants.Service_UUID);
        String pktdata = new String(pd);
//        String pktdata = "jhgbgytrdsjkoptfesa";
//        timeseen = System.currentTimeMillis();
        ScannedPacket scannedPacket = new ScannedPacket(pktdata);

        int existingPosition = getPosition(result.getDevice().getAddress());




        if (existingPosition >= 0) {
            // Device is already in list, update its record.
            mArrayList.set(existingPosition, result);
//            DB.insertpktdata(pktdata);

            mPacketsRepository.insertWithTime(pktdata,lastLocation);

//            mpacketsViewModel.insertWithTime(pktdata,location);
            Log.d(TAG, "add: Added Scan pkts");
        } else {
            // Add new Device's ScanResult to list.
//            mArrayList.add(result);

//            DB.insertpktdata(pktdata);
//
//            mPacketsRepository.insert(scannedPacket);


        }
    };

//    private LocationCallback locationCallback = new LocationCallback() {
//        @Override
//        public void onLocationResult(@NonNull LocationResult locationResult) {
//            super.onLocationResult(locationResult);
//            if (locationResult != null && locationResult.getLastLocation() != null) {
//                latitude = locationResult.getLastLocation().getLatitude();
//                longitude = locationResult.getLastLocation().getLongitude();
//                Log.d(TAG, "onLocationResult: " + latitude + " " + longitude);
//
//                getAddress();
//
//
//            }
//        }
//    };



//    public void getAddress() {
//
//
//        Geocoder geocoder = new Geocoder(mcontext, Locale.getDefault());
//        try {
//
//            List<Address> addresses = geocoder.getFromLocation(latitude,longitude,1);
//            String address =  addresses.get(0).getAddressLine(0);
//            String city = addresses.get(0).getLocality();
//
//
//            location = address ;
//
//
//
//            Log.d(TAG, "location Address line " + " " +  address);
//            Log.d(TAG, "location City" + " " +  city);
//            Log.d(TAG, "getAddress: " + " " + addresses.get(0).getLocality());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

//    private void startLocationService() {
//
//        LocationRequest locationRequest = LocationRequest.create()
//                .setInterval(3600000)
////                .setFastestInterval(1800000)
////                .setInterval(40000)
//                .setFastestInterval(2000)
//                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//
//       if (ActivityCompat.checkSelfPermission(mcontext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(mcontext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
////            ActivityCompat.requestPermissions(ScannerFragment.this,);
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        LocationServices.getFusedLocationProviderClient(mcontext)
//                .requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
//
//    }

}
