

package com.maimba.west.bleContactApp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


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
startScanning();
        scannningFailureReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                int scanErrorCode = intent.getIntExtra(ScannerService.SCANNING_FAILED_EXTRA_CODE, -2);

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

    @Override
    public void onResume() {
        super.onResume();

        IntentFilter scanFailureFilter = new IntentFilter(ScannerService.SCANNING_FAILED);
        getActivity().registerReceiver(scannningFailureReceiver,scanFailureFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(scannningFailureReceiver);
    }
    // Use getActivity().getApplicationContext() instead of just getActivity() because this
        // object lives in a fragment and needs to be kept separate from the Activity lifecycle.
        //
        // We could get a LayoutInflater from the ApplicationContext but it messes with the
        // default theme, so generate it from getActivity() and pass it in separately.
//        mAdapter = new ScanResultAdapter(getActivity().getApplicationContext(),
//                LayoutInflater.from(getActivity()));
//        mHandler = new Handler();

//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        final View view = super.onCreateView(inflater, container, savedInstanceState);
//
//        setListAdapter(mAdapter);
//
//        return view;
//    }
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        getListView().setDivider(null);
//        getListView().setDividerHeight(0);
//
//        setEmptyText(getString(R.string.empty_list));
//
//        // Trigger refresh on app's 1st load
//        startScanning();
//
//
//
//    }

//    class SampleScanCallback extends ScanCallback {
//
//        @Override
//        public void onBatchScanResults(List<ScanResult> results) {
//            super.onBatchScanResults(results);
//
//            for (ScanResult result : results) {
//                mAdapter.add(result);
//            }
//            mAdapter.notifyDataSetChanged();
//        }
//
//        @Override
//        public void onScanResult(int callbackType, ScanResult result) {
//            super.onScanResult(callbackType, result);
//
//            mAdapter.add(result);
//            mAdapter.notifyDataSetChanged();
//        }
//
//        @Override
//        public void onScanFailed(int errorCode) {
//            super.onScanFailed(errorCode);
//            Toast.makeText(getActivity(), "Scan failed with error: " + errorCode, Toast.LENGTH_LONG)
//                    .show();
//
//
//        }
//
//
//        }

//    @Override;
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.scanner_menu, menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.refresh:
//                startScanning();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

//    /**
//     * Start scanning for BLE Advertisements (& set it up to stop after a set period of time).*/
    private static Intent getScanServiceIntent(Context s) {
    return new Intent(s, ScannerService.class);
}
    private void startScanning() {
        Context s = getActivity();
        s.startService(getScanServiceIntent(s));

    }
    private void stopScanning(){
        Context s =  getActivity();
        s.stopService(getScanServiceIntent(s));
    }


    }

//
//    /**
//     * Stop scanning for BLE Advertisements.
//     */
//    public void stopScanning() {//    @Override;
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.scanner_menu, menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.refresh:
//                startScanning();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

//    /**
//     * Start scanning for BLE Advertisements (& set it up to stop after a set period of time).
//     */
//        Log.d(TAG, "Stopping Scanning");
//
//        // Stop the scan, wipe the callback.
//        mBluetoothLeScanner.stopScan(mScanCallback);
//        mScanCallback = null;
//
//        // Even if no new results, update 'last seen' times.
//        mAdapter.notifyDataSetChanged();
//    }
//
//    /**
//     * Return a List of {@link ScanFilter} objects to filter by Service UUID.
//     */
//    private List<ScanFilter> buildScanFilters() {
//        List<ScanFilter> scanFilters = new ArrayList<>();
//
//        ScanFilter.Builder builder = new ScanFilter.Builder();
//        // Comment out the below line to see all BLE devices around you
//        builder.setServiceUuid(Constants.Service_UUID);
//        scanFilters.add(builder.build());
//
//        return scanFilters;
//    }
//
//    /**
//     * Return a {@link ScanSettings} object set to use low power (to preserve battery life).
//     */
//    private ScanSettings buildScanSettings() {
//        ScanSettings.Builder builder = new ScanSettings.Builder();
//        builder.setScanMode(ScanSettings.SCAN_MODE_LOW_POWER);
//        return builder.build();
//    }
//
//    /**
//     * Custom ScanCallback object - adds to adapter on success, displays error on failure.
//     */
//    private class SampleScanCallback extends ScanCallback {
//
//        @Override
//        public void onBatchScanResults(List<ScanResult> results) {
//            super.onBatchScanResults(results);
//
//            for (ScanResult result : results) {
//                mAdapter.add(result);
//            }
//            mAdapter.notifyDataSetChanged();
//        }
//
//        @Override
//        public void onScanResult(int callbackType, ScanResult result) {
//            super.onScanResult(callbackType, result);
//
//            mAdapter.add(result);
//            mAdapter.notifyDataSetChanged();
//        }
//
//        @Override
//        public void onScanFailed(int errorCode) {
//            super.onScanFailed(errorCode);
//            Toast.makeText(getActivity(), "Scan failed with error: " + errorCode, Toast.LENGTH_LONG)
//                    .show();
//        }
//    }

