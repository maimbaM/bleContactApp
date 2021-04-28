package com.maimba.west.bleContactApp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

public class StatusReport extends AppCompatActivity {

    private static final String TAG = "Status Report";


    Button reportButton;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    String userid , diseaseName,userpktdata,userName,userPhone;
    DocumentReference diseaseref;
    DocumentReference userRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_report);

        fAuth = FirebaseAuth.getInstance();
        FirebaseUser mfirebaseuser = fAuth.getCurrentUser();
        userid = mfirebaseuser.getUid();
        userpktdata= Constants.bleServiceData;

        reportButton = findViewById(R.id.buttonReport);

        diseaseref =  fStore.collection("Diseases").document("Coronavirus");
        userRef = fStore.collection("users").document(userid);

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
                    Log.d(TAG,"Failed getting disease name");
                }
            }
        });

        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot userDocument = task.getResult();
                    if(userDocument.exists()){
                        userName = userDocument.getString("Name");
                        userPhone = userDocument.getString("Phone Number");}
                    }else{
                    Log.d(TAG,"Failed getting user details");
                }
            }
        });





    }

    @Override
    protected void onStart() {
        super.onStart();


            //user logged in


            reportButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DocumentReference casesCollection = fStore.collection("cases").document();
                    Map<String,Object> positive_case = new HashMap<>();
                    positive_case.put("USER ID",userid);
                    positive_case.put("User Name", userName);
                    positive_case.put("User Phone", userPhone);
                    positive_case.put("Disease",diseaseName);
                    positive_case.put("User Packet Data",userpktdata);
                    positive_case.put("Date Reported", FieldValue.serverTimestamp());
                    casesCollection.set(positive_case).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "onSuccess: Case reported for: " + userpktdata);
                            Toast.makeText(StatusReport.this, "Case successfully reported", Toast.LENGTH_LONG).show();
                        }
                    });



                }
            });



        }
    }
