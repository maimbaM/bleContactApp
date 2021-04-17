

package com.maimba.west.bleContactApp;

import android.bluetooth.le.AdvertiseCallback;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;
import androidx.fragment.app.Fragment;



/**
 * Allows user to start & stop Bluetooth LE Advertising of their device.
 */
public class AdvertiserFragment extends Fragment  {

    /**
     * Lets user toggle BLE Advertising.
     */
//    private Switch mSwitch;

    Button NexttoMaintwo;



    /**
     * Listens for notifications that the {@code AdvertiserService} has failed to start advertising.
     * This Receiver deals with Fragment UI elements and only needs to be active when the Fragment
     * is on-screen, so it's defined and registered in code instead of the Manifest.
     */
    private BroadcastReceiver advertisingFailureReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        NexttoMaintwo = getActivity().getApplicationContext()findViewById(R.id.buttonNext);


       NexttoMaintwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getView(),ExposureCheck.class));
            }
        });

       */
        advertisingFailureReceiver = new BroadcastReceiver() {

            /**
             * Receives Advertising error codes from {@code AdvertiserService} and displays error messages
             * to the user. Sets the advertising toggle to 'false.'
             */
            @Override
            public void onReceive(Context context, Intent intent) {

                int AdvErrorCode = intent.getIntExtra(AdvertiserService.ADVERTISING_FAILED_EXTRA_CODE, -1);

//                mSwitch.setChecked(false);


                String AdvertisingErrorMessage = getString(R.string.start_error_prefix);
                switch (AdvErrorCode) {
                    case AdvertiseCallback.ADVERTISE_FAILED_ALREADY_STARTED:
                        AdvertisingErrorMessage += " " + getString(R.string.start_error_already_started);
                        break;
                    case AdvertiseCallback.ADVERTISE_FAILED_DATA_TOO_LARGE:
                        AdvertisingErrorMessage += " " + getString(R.string.start_error_too_large);
                        break;
                    case AdvertiseCallback.ADVERTISE_FAILED_FEATURE_UNSUPPORTED:
                        AdvertisingErrorMessage += " " + getString(R.string.start_error_unsupported);
                        break;
                    case AdvertiseCallback.ADVERTISE_FAILED_INTERNAL_ERROR:
                        AdvertisingErrorMessage += " " + getString(R.string.start_error_internal);
                        break;
                    case AdvertiseCallback.ADVERTISE_FAILED_TOO_MANY_ADVERTISERS:
                        AdvertisingErrorMessage += " " + getString(R.string.start_error_too_many);
                        break;
                    case AdvertiserService.ADVERTISING_TIMED_OUT:
                        AdvertisingErrorMessage = " " + getString(R.string.advertising_timedout);
                        break;
                    default:
                        AdvertisingErrorMessage += " " + getString(R.string.start_error_unknown);
                }

                Toast.makeText(getActivity(), AdvertisingErrorMessage, Toast.LENGTH_LONG).show();
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_advertiser, container, false);

//        mSwitch = (Switch) view.findViewById(R.id.advertise_switch);
//        mSwitch.setOnClickListener(this);


        return view;
    }

    /**
     * When app comes on screen, check if BLE Advertisements are running, set switch accordingly,
     * and register the Receiver to be notified if Advertising fails.
     */
    @Override
    public void onResume() {
        super.onResume();

        if (AdvertiserService.running) {
//            mSwitch.setChecked(true);
            Toast.makeText(getActivity(), "Advertising Ongoing", Toast.LENGTH_LONG).show();
        } else {
            startAdvertising();
            Toast.makeText(getActivity(), "Advertising Started", Toast.LENGTH_LONG).show();
        }

        IntentFilter failureFilter = new IntentFilter(AdvertiserService.ADVERTISING_FAILED);
        getActivity().registerReceiver(advertisingFailureReceiver, failureFilter);

    }

    /**
     * When app goes off screen, unregister the Advertising failure Receiver to stop memory leaks.
     * (and because the app doesn't care if Advertising fails while the UI isn't active)
     */
    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(advertisingFailureReceiver);
    }

    /**
     * Returns Intent addressed to the {@code AdvertiserService} class.
     */
    private static Intent getAdvServiceIntent(Context c) {
        return new Intent(c, AdvertiserService.class);
    }

    /**
     * Called when switch is toggled - starts or stops advertising.
     */
//    @Override
//    public void onClick(View v) {
//        // Is the toggle on?
//        boolean on = ((Switch) v).isChecked();
//
//        if (on) {
//            startAdvertising();
//        } else {
//            stopAdvertising();
//        }
//    }

    /**
     * Starts BLE Advertising by starting {@code AdvertiserService}.
     */
    private void startAdvertising() {
//        Context c = getActivity();
//        c.startService(getAdvServiceIntent(c));
        Context c = getActivity();
        c.startService(getAdvServiceIntent(c));
    }

    /**
     * Stops BLE Advertising by stopping {@code AdvertiserService}.
     */
    private void stopAdvertising() {
        Context c = getActivity();
        c.stopService(getAdvServiceIntent(c));
//        mSwitch.setChecked(false);
    }

}