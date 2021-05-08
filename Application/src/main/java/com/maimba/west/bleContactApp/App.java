package com.maimba.west.bleContactApp;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.maimba.west.bleContactApp.Scanner.LocationService;

import static com.maimba.west.bleContactApp.Register.TAG;

public class App extends Application {
    FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();





    }

    public static final String CHANNEL_1_ID = "Channel 1";


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "BLE Contact Tracing APP",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel1.setDescription("Advertising and Scanning nearby devices");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);


        }
    }

}
