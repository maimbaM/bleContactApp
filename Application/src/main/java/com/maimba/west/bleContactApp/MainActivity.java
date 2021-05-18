

package com.maimba.west.bleContactApp;

import android.Manifest;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.maimba.west.bleContactApp.Advertiser.AdvertiserFragment;
import com.maimba.west.bleContactApp.DB.PacketsRepository;
import com.maimba.west.bleContactApp.DB.PacketsViewModel;
import com.maimba.west.bleContactApp.Home.ScreenMain;
import com.maimba.west.bleContactApp.Scanner.LocationService;
import com.maimba.west.bleContactApp.Scanner.ScannerFragment;
import com.maimba.west.bleContactApp.Scanner.ScannerWorker;
//import com.maimba.west.bleContactApp.Scanner.ScannerWorker;

import java.util.concurrent.TimeUnit;
//import com.maimba.west.bleContactApp.Scanner.locationService;


/**
 * Setup display fragments and ensure the device supports Bluetooth.
 */

public class MainActivity extends FragmentActivity {

    private static final String KEY_RESULT = "currentLocation";
    private BluetoothAdapter mBluetoothAdapter;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 456;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private DocumentReference userDoc;
    private Button nextBtn;
    private PacketsViewModel packetsViewModel;
    private WorkManager workManager;
    private WorkRequest ScanWorkRequest;
    private WorkRequest locationWorkRequest;
    private WorkRequest sequalWorkRequest;
    private PacketsRepository mPacketsRepository;
    private String lastLocation;
    private String pktdata;
    private Application application;
    private static final String TAG = "MainActivity";
    private String myResult;
    private String userID,Fname,Lname,userData,phone,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.activity_main_title);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        userDoc = fStore.collection("users").document(userID);
        getuserDetails();
        nextBtn = findViewById(R.id.nextButton);
        packetsViewModel = new ViewModelProvider(this).get(PacketsViewModel.class);
        packetsViewModel.deleteOldPackets();
        workManager = workManager.getInstance(getApplicationContext());


        ScanWorkRequest = new PeriodicWorkRequest.Builder(ScannerWorker.class,15,TimeUnit.MINUTES,13,TimeUnit.MINUTES)
                .setInitialDelay(10,TimeUnit.MINUTES)
                .build();




        startLocationService();





        Log.d(TAG, "onCreate: Main thread ID "+ Thread.currentThread().getId());


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ScreenMain.class));
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
        }

        if (savedInstanceState == null) {

            mBluetoothAdapter = ((BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE))
                    .getAdapter();

            // Is Bluetooth supported on this device?
            if (mBluetoothAdapter != null) {

                // Is Bluetooth turned on?
                if (mBluetoothAdapter.isEnabled()) {

                    // Are Bluetooth Advertisements supported on this device?
                    if (mBluetoothAdapter.isMultipleAdvertisementSupported()) {

                        // Everything is supported and enabled, load the fragments.
                        setupFragments();


                    } else {

                        // Bluetooth Advertisements are not supported.
                        showErrorText(R.string.bt_ads_not_supported);
                    }
                } else {

                    // Prompt user to turn on Bluetooth (logic continues in onActivityResult()).
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, Constants.REQUEST_ENABLE_BT);
                }
            } else {

                // Bluetooth is not supported.
                showErrorText(R.string.bt_not_supported);
            }
        }
    }

    private void getuserDetails() {

        userDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot userDocument = task.getResult();
                    if(userDocument.exists()){
                        Fname = userDocument.getString("FirstName");
                        Lname = userDocument.getString("LastName");
                        userData = userDocument.getString("ServiceData");
                        email = userDocument.getString("email");
                        phone = userDocument.getString("PhoneNumber");

                        UserDetails.Fname = Fname;
                        UserDetails.Lname = Lname;
                        UserDetails.userData = userData;
                        UserDetails.email = email;
                        UserDetails.phone = phone;

                        Log.d(TAG, "onComplete: getUserDetails success" + UserDetails.Fname);




                    }
                    Log.d(TAG, "onComplete: Got user details");
                }else{
                    Log.d(TAG,"Failed getting user details");
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser mfirebaseuser =fAuth.getCurrentUser();


        if (mfirebaseuser!=null){
            //user logged in

        }else {
            //Not Registered
            startActivity(new Intent(this,Register.class));
            finish();
        }
        workManager
        .enqueue(ScanWorkRequest);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted
                } else {
                    // Alert the user that this application requires the location permission to perform the scan.
                    Toast.makeText(this,"Application requires location Permision" ,Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.REQUEST_ENABLE_BT:

                if (resultCode == RESULT_OK) {

                    // Bluetooth is now Enabled, are Bluetooth Advertisements supported on
                    // this device?
                    if (mBluetoothAdapter.isMultipleAdvertisementSupported()) {

                        // Everything is supported and enabled, load the fragments.
                        setupFragments();

                    } else {

                        // Bluetooth Advertisements are not supported.
                        showErrorText(R.string.bt_ads_not_supported);
                    }
                } else {

                    // User declined to enable Bluetooth, exit the app.
                    Toast.makeText(this, R.string.bt_not_enabled_leaving,
                            Toast.LENGTH_SHORT).show();
                    finish();
                }

            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void setupFragments() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        ScannerFragment scannerFragment = new ScannerFragment();
        // Fragments can't access system services directly, so pass it the BluetoothAdapter
        scannerFragment.setBluetoothAdapter(mBluetoothAdapter);
        transaction.replace(R.id.scanner_fragment_container, scannerFragment);

        AdvertiserFragment advertiserFragment = new AdvertiserFragment();
        transaction.replace(R.id.advertiser_fragment_container, advertiserFragment);

        transaction.commit();
    }

    private void showErrorText(int messageId) {

        TextView view = (TextView) findViewById(R.id.error_textview);
        view.setText(getString(messageId));
    }
    private static Intent getLocServiceIntent(Context c) {
        return new Intent(c, LocationService.class);
    }
    private void startLocationService(){
        Context l =getApplicationContext();
        l.startService(getLocServiceIntent(l));
    }
}