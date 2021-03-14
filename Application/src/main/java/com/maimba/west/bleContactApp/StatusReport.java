package com.maimba.west.bleContactApp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static com.maimba.west.bleContactApp.AdvertiserService.EXTRA_MESSAGE;

public class StatusReport extends AppCompatActivity {

    public static final String TAG = "Report Case success";
    public static final String dName = "Report Case success";

    Button reportButton;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    String userid , diseaseName,userpktdata;
    DocumentReference diseaseref =  fStore.collection("Diseases").document("Coronavirus");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_report);


        userpktdata= Constants.bleServiceData;

        reportButton = findViewById(R.id.buttonReport);
        fAuth = FirebaseAuth.getInstance();

        /**
         * Get disease name
         */

        diseaseref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()){
                    diseaseName = documentSnapshot.getString("Name");}
                }else{
                    Log.d(dName,"Failed getting disease name");
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
            userid = mfirebaseuser.getUid();
            reportButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DocumentReference casesCollection = fStore.collection("cases").document();
                    Map<String,Object> positive_case = new HashMap<>();
                    positive_case.put("USER ID",userid);
                    positive_case.put("Disease",diseaseName);
                    positive_case.put("User Packet Data",userpktdata);
                    positive_case.put("Date Reported", FieldValue.serverTimestamp());
                    casesCollection.set(positive_case).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "onSuccess: Case reported for: " + userpktdata);
                        }
                    });



                }
            });



        }
    }
}