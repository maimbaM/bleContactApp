package com.maimba.west.bleContactApp.Scanner;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.maimba.west.bleContactApp.DB.Location;
import com.maimba.west.bleContactApp.DB.PacketsViewModel;
import com.maimba.west.bleContactApp.MainActivity;
import com.maimba.west.bleContactApp.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.maimba.west.bleContactApp.App.CHANNEL_1_ID;

public class LocationService extends Service {
    public double latitude;
    public double longitude;
    private String TAG = "location Service";
    public String address;
    public String locationCoordinates;
    private Data outPutData;
    public static final String deviceLocation = "deviceLocation";
    private Context mContext;
    private LocationCallback locationCallback;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private PacketsViewModel mPacketsViewModel;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mContext);
        mPacketsViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(PacketsViewModel.class);

        getCurrentLocation();
        startLocationCallbacks();



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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    private void getCurrentLocation() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationResult != null && locationResult.getLastLocation() != null) {
                    latitude = locationResult.getLastLocation().getLatitude();
                    longitude = locationResult.getLastLocation().getLongitude();
                    Log.d(TAG, "onLocationResult: " + latitude + " " + longitude);

                    getAddress();





                }
            }
        };
    }

    public void getAddress() {


        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        try {

            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            address = addresses.get(0).getAddressLine(0);
            String city = addresses.get(0).getLocality();


//            outLocation = address;



            Log.d(TAG, "location Address line " + " " + address);
            Log.d(TAG, "location City" + " " + city);
            Log.d(TAG, "getAddress: " + " " + addresses.get(0).getPremises());
            
            locationCoordinates = Double.toString(latitude) +" " +Double.toString(longitude);

            Location location = new Location(locationCoordinates,address);
            mPacketsViewModel.insertLocation(location);
            Log.d(TAG, "added loc");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void startLocationCallbacks() {

        LocationRequest locationRequest = LocationRequest.create()
                .setWaitForAccurateLocation(true)
//                .setInterval(3600000)
//                .setFastestInterval(1800000)
                .setInterval(10000)
                .setFastestInterval(5000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
//            ActivityCompat.requestPermissions(ScannerFragment.this,);
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.getFusedLocationProviderClient(mContext)
                .requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());

    }


}
