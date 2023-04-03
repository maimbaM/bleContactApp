package com.maimba.west.bleContactApp.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.maimba.west.bleContactApp.DB.MatchedPackets;
import com.maimba.west.bleContactApp.DB.PacketsViewModel;
import com.maimba.west.bleContactApp.DB.ScannedPacket;
import com.maimba.west.bleContactApp.R;
import com.maimba.west.bleContactApp.UserDetails;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Statistics extends AppCompatActivity {
    private static final String TAG = Statistics.class.getSimpleName();
    private PacketsViewModel packetsViewModel;
    private TextView exposureCounter,caseCounter,victimCounter;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private FirebaseUser mfirebaseuser;
    private DocumentReference userRef;
    private CollectionReference casesRef,exposerRef;
    private String userID;
    private String expID;
    private String exposerID;
    private Query caseQuery,exposerQuery;
    private String FName,LName,userEmail,userPhone,timeSeen,location;
    private Map<String,Object> userDetails;
    private CardView Cases,Victims,Exposers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        caseCounter = findViewById(R.id.casecounter);
        victimCounter = findViewById(R.id.victimcounter);
        Cases = findViewById(R.id.casesCounter);

        Victims = findViewById(R.id.victimCounter);
        userDetails = new HashMap<>();
        fAuth = FirebaseAuth.getInstance();
        fStore =FirebaseFirestore.getInstance();
        mfirebaseuser = fAuth.getCurrentUser();
        userID = mfirebaseuser.getUid();
        userRef = fStore.collection("users").document(userID);
        casesRef = fStore.collection("cases");
        exposerRef = fStore.collection("users");
        caseQuery = casesRef
                .whereEqualTo("userID",userID);

        exposerQuery = exposerRef.document(userID).collection("VictimsID");

        packetsViewModel = new ViewModelProvider(this).get(PacketsViewModel.class);

        //Get User Details
            FName = UserDetails.Fname;
            LName = UserDetails.Lname;
            userEmail = UserDetails.email;
            userPhone = UserDetails.phone;



            //Set Exposers and victims
        packetsViewModel.getAllMatchedPackets().observe(this, new Observer<List<MatchedPackets>>() {
            @Override
            public void onChanged(List<MatchedPackets> matchedPackets) {

                Log.d(TAG, "onChanged: Exposers" + matchedPackets.size());

                for (MatchedPackets value: matchedPackets) {
                    Log.d(TAG, "onChanged: "+ value);
                    timeSeen = value.getTimeExposed();
                    expID = value.getUserID();
                    location = value.getLocation();
                    Map<String,Object> Exposure = new HashMap<>();
                    Exposure.put("ExposerID",expID);
                    Exposure.put("expTimeSeen",timeSeen);
                    Exposure.put("Location",location);

                    userDetails.put("FistName",FName);
                    userDetails.put("LastName",LName);
                    userDetails.put("Email",userEmail);
                    userDetails.put("Phone",userPhone);
                    userDetails.put("TimeSeen",timeSeen);
                    userDetails.put("Location",location);


                    exposerRef.document(expID).collection("VictimsID").add(userDetails).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "onSuccess: Exposee IDs Added");
                        }
                    });

                    userRef.collection("ExposersIDs").add(Exposure).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "onSuccess: Exposer IDs Added");
                        }
                    });

                }
            }
        });

//        //Exposers
//        packetsViewModel.getAllExpUID().observe(this, new Observer<List<String>>() {
//            @Override
//            public void onChanged(List<String> strings) {
//                exposureCounter.setText(String.valueOf(strings.size()));
//                Log.d(TAG, "onChanged: Exposers" + strings.size());
//                for (String value: strings) {
//                    Log.d(TAG, "onChanged: "+ value);
//                    Map<String,Object> Exposure = new HashMap<>();
//                    Exposure.put("Exposer",value);
//                    exposerID = value;
//
//                    exposerRef.document(exposerID).collection("VictimsID").add(userDetails).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                        @Override
//                        public void onSuccess(DocumentReference documentReference) {
//                            Log.d(TAG, "onSuccess: Exposee IDs Added");
//                        }
//                    });
//
//                    userRef.collection("ExposersIDs").add(Exposure).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                        @Override
//                        public void onSuccess(DocumentReference documentReference) {
//                            Log.d(TAG, "onSuccess: Exposer IDs Added");
//                        }
//                    });
//
//                }
//
//
//            }
//        });
        //Cases Counter
                class Cases{
                    String diseaseName;
                    String dateReported;
                }
                Cases userCases = new Cases();
                List<Cases> casesList = new ArrayList<>();
                        caseQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){

                                    for (QueryDocumentSnapshot documentSnapshot:task.getResult()){
                                        Log.d(TAG, "onComplete: " + documentSnapshot.getString("FirstName"));
                                        userCases.diseaseName = documentSnapshot.getString("Disease");
                                        userCases.dateReported = documentSnapshot.getDate("DateReported").toString();

                                        casesList.add(userCases);
                                        Log.d(TAG, "onComplete: "+ documentSnapshot.getString("Disease"));
                                        caseCounter.setText(String.valueOf(casesList.size()));
                                }


                            }else {
                                    Log.d(TAG, "onComplete: Failed Getting Case data");
                                }

                            }

                    });




        //Victims Counter

        class Victims{
            String FName;
            String LName;
            String Phone;
            String Email;
            String Location;
            Date timeSeen;

        }
        Victims victims = new Victims();
        List<Victims> exposee= new ArrayList<>();


        exposerQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot:task.getResult()){


                        victims.FName = documentSnapshot.getString("FirstName");
                        victims.LName = documentSnapshot.getString("LastName");
                        victims.Phone = documentSnapshot.getString("PhoneNumber");
                        victims.Email = documentSnapshot.getString("Email");
                        victims.Location = documentSnapshot.getString("Location");
//                        victims.timeSeen = documentSnapshot.getDate("TimeSeen");

                        exposee.add(victims);


                        victimCounter.setText(String.valueOf(exposee.size()));
                        Log.d(TAG, "onComplete: Victims" + exposee.size());
                    }
                }else {
                    Log.d(TAG, "onComplete: Failed getting Exposee data");
                }
            }
        });





    }

    @Override
    protected void onStart() {
        super.onStart();

        Cases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Cases.class));
            }
        });
        Victims.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Victims.class));
            }
        });
    }
}