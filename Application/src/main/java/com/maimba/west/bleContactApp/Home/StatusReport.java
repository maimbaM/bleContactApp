package com.maimba.west.bleContactApp.Home;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import com.maimba.west.bleContactApp.Constants;
import com.maimba.west.bleContactApp.DB.PacketsViewModel;
import com.maimba.west.bleContactApp.R;

import java.util.HashMap;
import java.util.Map;

public class StatusReport extends AppCompatActivity {

    private static final String TAG = "Status Report";


    Button reportButton;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    String userid , diseaseName,userpktdata,FName,LName,userPhone,currentStatus;
    DocumentReference diseaseref;
    DocumentReference userRef;
    private TextView Status;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_report);

        fAuth = FirebaseAuth.getInstance();
        FirebaseUser mfirebaseuser = fAuth.getCurrentUser();
        userid = mfirebaseuser.getUid();
        Status = findViewById(R.id.tvCurrentStatus);


        reportButton = findViewById(R.id.buttonReport);

        diseaseref =  fStore.collection("Diseases").document("Tuberculosis");
        userRef = fStore.collection("users").document(userid);


        //Get User Current Status

        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot userDocument = task.getResult();
                    if(userDocument.exists()){
                        currentStatus = userDocument.getString("Status");
                        }
                    Log.d(TAG, "onComplete: Got Status");
                }else{
                    Log.d(TAG,"Failed getting user Status");
                }
            }
        });

        Status.setText(currentStatus);

        /*
          Get disease name
         */

        diseaseref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()){
                    diseaseName = documentSnapshot.getString("Name");}
                    Log.d(TAG, "onComplete: Got disease Name");
                }else{
                    Log.d(TAG,"Failed getting disease name");
                }
            }
        });
        //Get User Name & Phone
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot userDocument = task.getResult();
                    if(userDocument.exists()){
                        FName = userDocument.getString("First Name");
                        LName = userDocument.getString("Last Name");
                        userpktdata = userDocument.getString("Service Data");

                        userPhone = userDocument.getString("Phone Number");}
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


            //user logged in


            reportButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DocumentReference casesCollection = fStore.collection("cases").document();
                    Map<String,Object> positive_case = new HashMap<>();
                    positive_case.put("USER ID",userid);
                    positive_case.put("First Name", FName);
                    positive_case.put("Last Name", LName);
                    positive_case.put("User Phone", userPhone);
                    positive_case.put("Disease",diseaseName);
                    positive_case.put("User Packet Data",userpktdata);
                    positive_case.put("Date Reported", FieldValue.serverTimestamp());
                    casesCollection.set(positive_case).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "onSuccess: Case reported for: " + userid);
                            Toast.makeText(StatusReport.this, "Case successfully reported", Toast.LENGTH_LONG).show();
                        }
                    });



                }
            });



        }
    }
