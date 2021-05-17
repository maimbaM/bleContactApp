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


    Button reportButton,negateButton;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    String userid , diseaseName,userpktdata,FName,LName,userPhone;
    DocumentReference diseaseref;
     DocumentReference userRef;
     TextView Status;
     String newStatus;
    String currentStatus;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_report);

        fAuth = FirebaseAuth.getInstance();
        FirebaseUser mfirebaseuser = fAuth.getCurrentUser();
        userid = mfirebaseuser.getUid();
        Status = findViewById(R.id.tvCurrentStatus);
        Log.d(TAG, "onCreate: " + userid);



        reportButton = findViewById(R.id.buttonReport);
        negateButton = findViewById(R.id.NtoP);
        negateButton.setVisibility(View.INVISIBLE);
//        reportButton.setVisibility(View.INVISIBLE);

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
                        Status.setText(currentStatus);
                        if(currentStatus.equals("Negative")){
                            newStatus = "Positive";
                            negateButton.setVisibility(View.INVISIBLE);
                        }else if (currentStatus.equals("Positive")){
                            newStatus = "Negative";
                            reportButton.setVisibility(View.INVISIBLE);
                            negateButton.setVisibility(View.VISIBLE);
                        }
                        }
                    Log.d(TAG, "onComplete: Got Status");
                    Log.d(TAG, "onComplete: "+ newStatus);
                    Log.d(TAG, "onComplete: "+  currentStatus);
                }else{
                    Log.d(TAG,"Failed getting user Status");
                }
            }
        });




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
                        FName = userDocument.getString("FirstName");
                        LName = userDocument.getString("LastName");
                        userpktdata = userDocument.getString("ServiceData");

                        userPhone = userDocument.getString("PhoneNumber");}
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


            //report status

        Log.d(TAG, "onStart: "+ currentStatus);
            reportButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    DocumentReference casesCollection = fStore.collection("cases").document();
                    Map<String,Object> positive_case = new HashMap<>();
                    positive_case.put("userID",userid);
                    positive_case.put("FirstName", FName);
                    positive_case.put("LastName", LName);
                    positive_case.put("UserPhone", userPhone);
                    positive_case.put("Disease",diseaseName);
                    positive_case.put("UserPacketData",userpktdata);
                    positive_case.put("DateReported", FieldValue.serverTimestamp());


                    userRef.update("Status",newStatus);
                    casesCollection.set(positive_case).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "onSuccess: Case reported for: " + userid);
                            Toast.makeText(StatusReport.this, "Case successfully reported", Toast.LENGTH_LONG).show();
                        }
                    });



                }
            });

            negateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userRef.update("Status",newStatus);

                    Toast.makeText(StatusReport.this, "Status Successfully Changed", Toast.LENGTH_LONG).show();
                }
            });



        }
    }
