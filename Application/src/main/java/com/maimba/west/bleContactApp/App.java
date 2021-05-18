package com.maimba.west.bleContactApp;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.maimba.west.bleContactApp.DB.ContractTracingDB;
import com.maimba.west.bleContactApp.DB.PacketsRepository;
import com.maimba.west.bleContactApp.DB.PacketsViewModel;
import com.maimba.west.bleContactApp.DB.ScannedDao;
import com.maimba.west.bleContactApp.DB.ServiceData;
import com.maimba.west.bleContactApp.Scanner.LocationService;

import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;
import java.util.List;

import static com.maimba.west.bleContactApp.Register.TAG;

public class App extends Application {
    private FirebaseFirestore firestore;
    public String currentDiseaseName;
    public long currentDiseaseInc;
    private CollectionReference collectionReference;
    private PacketsViewModel packetsViewModel;
    private PacketsRepository packetsRepository;
    private Context mcontext;
    private ScannedDao scannedDao;
    private String servData;




    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        firestore = FirebaseFirestore.getInstance();
        mcontext = getApplicationContext();

//        packetsViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(PacketsViewModel.class);
        ContractTracingDB database = ContractTracingDB.getInstance(mcontext);
        scannedDao = database.scannedDao();


        class newThread extends Thread{
            @Override
            public void run() {
//                LL =  scannedDao.selectLastLocation();
//                Constants.SERVICE_DATA = RandomStringUtils.random(20, 0, 0, true, false, null, new SecureRandom());;
                servData = scannedDao.selectServiceData();

                if (servData == null){

                    servData = RandomStringUtils.random(20, 0, 0, true, false, null, new SecureRandom());;

                    ServiceData serviceData = new ServiceData(servData);
                    scannedDao.insertServiceData(serviceData);
                    Log.d(TAG, "run: Added SErvice Data");
                }else{
                    Constants.SERVICE_DATA = scannedDao.selectServiceData();
                    Log.d(TAG, "onCreate: Service Data" + Constants.SERVICE_DATA);
                }
            }
        }





        newThread serv = new newThread();
        serv.start();






        collectionReference = firestore.collection("Diseases");
        Query dQuery = collectionReference.whereEqualTo("Current" , true);

        dQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot:task.getResult()){


                        currentDiseaseName = documentSnapshot.getString("DiseaseName");
                        currentDiseaseInc = documentSnapshot.getLong("IncubationPeriod");

                        Constants.currentDiseaseName = currentDiseaseName;
                        Constants.currentDiseaseInc = currentDiseaseInc;


                        Log.d(TAG, "onComplete: D Name" + currentDiseaseName);
                        Log.d(TAG, "onComplete: D Name" + currentDiseaseInc);

                        Log.d(TAG, "onComplete: Got Disease name & Inc");
                    }
                }else {
                    Log.d(TAG, "onComplete: Failed getting disease data");
                }
            }
        });


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
