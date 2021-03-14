package com.maimba.west.bleContactApp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class ExposureCheck extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private String casesnap;
    Button checkExposure;
    private String TAG;
    DBHelper DB;
    //List<String> mcases = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exposure_check);


        checkExposure = findViewById(R.id.buttonCheckExposure);

        checkExposure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Calendar dayPeriod = Calendar.getInstance();
//                dayPeriod.add(Calendar.DATE,-2);
//                dayPeriod.getTime();
//                dayPeriod.setTimeInMillis();


                fStore.collection("cases")
//                        .whereGreaterThan("Date Reported", dayPeriod)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                Log.d(casesnap, "getting data");
                                List<DocumentSnapshot >snapshotList = queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot snapshot: snapshotList){
                                    String userdata = snapshot.getString("User Packet Data");
//                                    Timestamp dateReported = snapshot.getTimestamp(("Date Reported"));
                                    DB.insertExposurePktdata(userdata);


                                }

                            }
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