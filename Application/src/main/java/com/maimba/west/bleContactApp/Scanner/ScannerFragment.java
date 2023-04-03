

package com.maimba.west.bleContactApp.Scanner;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;


import com.maimba.west.bleContactApp.DB.PacketsViewModel;
import com.maimba.west.bleContactApp.DB.ScannedPacket;
import com.maimba.west.bleContactApp.R;

import java.util.List;


/**
 * Scans for Bluetooth Low Energy Advertisements matching a filter and displays them to the user.
 */
public class   ScannerFragment extends Fragment {

    private static final String TAG = ScannerFragment.class.getSimpleName();

    /**
     * Stops scanning after 5 seconds.
     */
    private static final long SCAN_PERIOD = 10000;


    private BluetoothAdapter mBluetoothAdapter;

    private BluetoothLeScanner mBluetoothLeScanner;

    private ScanCallback mScanCallback;

//    private ScanResultAdapter mAdapter;

    private Handler mHandler;
//    private Intent getScanServiceIntent;
    private BroadcastReceiver scannningFailureReceiver;
    private RecyclerView recyclerView;
    private PacketsViewModel packetsViewModel;
    private WorkManager workManager;
    private WorkRequest workRequest;

    /**
     * Must be called after object creation by MainActivity.
     *
     * @param btAdapter the local BluetoothAdapter
     */
    public void setBluetoothAdapter(BluetoothAdapter btAdapter) {
        this.mBluetoothAdapter = btAdapter;
        mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
        setRetainInstance(true);
//        startLocationService();
        Log.d(TAG, "onCreate: loc running");

//        startScanning();


        scannningFailureReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                int scanErrorCode = intent.getIntExtra(ScannerWorker.SCANNING_FAILED_EXTRA_CODE, -2);

                String ScannerErrorMessage = getString(R.string.Scan_start_error_prefix);
                switch (scanErrorCode) {
                    case ScanCallback.SCAN_FAILED_ALREADY_STARTED:
                        ScannerErrorMessage += " " + getString(R.string.Scan_start_error_already_started);
                        break;
                    case ScanCallback.SCAN_FAILED_APPLICATION_REGISTRATION_FAILED:
                        ScannerErrorMessage += " " + getString(R.string.Scan_start_error_registration_failed);
                        break;
                    case ScanCallback.SCAN_FAILED_FEATURE_UNSUPPORTED:
                        ScannerErrorMessage += " " + getString(R.string.Scan_start_error_unsupported);
                        break;
                    case ScanCallback.SCAN_FAILED_INTERNAL_ERROR:
                        ScannerErrorMessage += " " + getString(R.string.Scan_start_error_internal);
                        break;
                    default:
                        ScannerErrorMessage += " " + getString(R.string.start_error_unknown);
                }
                Toast.makeText(getActivity(), ScannerErrorMessage, Toast.LENGTH_LONG).show();


            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_scanner, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rc_Scanner);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(false);

        ScannedAdapter sAdapter = new ScannedAdapter();
        recyclerView.setAdapter(sAdapter);

//        checkExposure = findViewById(R.id.buttonCheckExposure);
//        DB = new DBHelper(getApplicationContext());
        packetsViewModel = new ViewModelProvider(this).get(PacketsViewModel.class);
     packetsViewModel.getAllScanPackets().observe(getViewLifecycleOwner(), new Observer<List<ScannedPacket>>() {
         @Override
         public void onChanged(List<ScannedPacket> scannedPackets) {
             sAdapter.setScannedPackets(scannedPackets);
         }
     });
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();

        IntentFilter scanFailureFilter = new IntentFilter(ScannerWorker.SCANNING_FAILED);
        getActivity().registerReceiver(scannningFailureReceiver,scanFailureFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(scannningFailureReceiver);
    }

//     * Start scanning for BLE Advertisements (& set it up to stop after a set period of time).*/
//    private static Intent getScanServiceIntent(Context s) {
//    return new Intent(s, ScannerWorker.class);
//}

//    private void startScanning() {
//        Context s = getActivity();
//        s.startService(getScanServiceIntent(s));
//
//    }
//    private void stopScanning(){
//        Context s =  getActivity();
//        s.stopService(getScanServiceIntent(s));
//    }


    }



