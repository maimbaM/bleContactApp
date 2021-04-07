 package com.maimba.west.bleContactApp;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.type.DateTime;
import com.maimba.west.bleContactApp.DB.ExposurePacket;
import com.maimba.west.bleContactApp.DB.PacketsViewModel;
import com.maimba.west.bleContactApp.DBHelper;
import com.maimba.west.bleContactApp.R;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ExposureCheck extends AppCompatActivity {

    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    public String casesnap;
    private Button checkExposure;
    public String TAG;
    private DBHelper DB ;
    private PacketsViewModel mpacketsViewModel;
    private ExposurePacket exposurePacket;



    //List<String> mcases = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exposure_check);


        checkExposure = findViewById(R.id.buttonCheckExposure);
//        DB = new DBHelper(getApplicationContext());
        mpacketsViewModel = new ViewModelProvider(this).get(PacketsViewModel.class);


        checkExposure.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                //Get days before ie. Incubation Period
                LocalDateTime todayNairobi = LocalDateTime.now(ZoneId.of("Africa/Addis_Ababa"));
                System.out.println("Current Date in EAT ="+todayNairobi);
                LocalDateTime dateBefore = todayNairobi.minus(1, ChronoUnit.DAYS);
                
                System.out.println(dateBefore);




                fStore.collection("cases")
//                        .whereGreaterThanOrEqualTo("Date Reported", dateBefore)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                Log.d(casesnap, "getting data");
                                List<DocumentSnapshot >snapshotList = queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot snapshot: snapshotList){
                                    String userdata = snapshot.getString("User Packet Data");
                                    exposurePacket = new ExposurePacket(userdata);


                                    Log.d(casesnap, "got data");
//                                    DB.insertExposurePktdata(userdata);
                                    mpacketsViewModel.insertExp(exposurePacket);
                                    Log.d(casesnap, "data inserted into exposure db");
//                                    matchPackets();

                                }

                            }

//                            private void matchPackets() {
//                                Cursor c = DB.matchPktdata();
//                                if(c.getCount()==0){
//
//                                    // Not Exposed
//                                    Log.d(casesnap,"not exposed");
//
//                                }else {
//                                    Log.d(casesnap,"You exposed");
//                                }
//
//
//
//                            }


                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e(casesnap,"On Case get failure", e);

                            }
                        });
            }
        });







    }





    @Override
    protected void onStart() {
        super.onStart();
//        caseRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                List<DocumentSnapshot> mcases = queryDocumentSnapshots.getDocuments();
//                for (DocumentSnapshot snapshot:mcases){
//                    Log.d(casesnap, "onSuccess: "+ snapshot.getData());
//
//                }
//
//            }
//        });


    }
}