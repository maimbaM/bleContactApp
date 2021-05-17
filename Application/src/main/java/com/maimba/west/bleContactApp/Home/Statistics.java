package com.maimba.west.bleContactApp.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.maimba.west.bleContactApp.DB.PacketsViewModel;
import com.maimba.west.bleContactApp.DB.ScannedPacket;
import com.maimba.west.bleContactApp.R;

import java.util.ArrayList;
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
    private Query caseQuery,exposerQuery;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        exposureCounter = findViewById(R.id.counter);
        caseCounter = findViewById(R.id.casecounter);
        victimCounter = findViewById(R.id.victimcounter);
        fAuth = FirebaseAuth.getInstance();
        fStore =FirebaseFirestore.getInstance();
        mfirebaseuser = fAuth.getCurrentUser();
        userID = mfirebaseuser.getUid();
        userRef = fStore.collection("users").document(userID);
        casesRef = fStore.collection("cases");
        exposerRef = fStore.collection("users");
        caseQuery = casesRef
                .whereEqualTo("userID",userID);

        exposerQuery = exposerRef.document().collection("ExposersIDs")
                .whereEqualTo("Exposer",userID);

        packetsViewModel = new ViewModelProvider(this).get(PacketsViewModel.class);

        //Exposers
        packetsViewModel.getAllExpUID().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                exposureCounter.setText(String.valueOf(strings.size()));
                Log.d(TAG, "onChanged: Exposers" + strings.size());
                for (String value: strings) {
                    Log.d(TAG, "onChanged: "+ value);
                    Map<String,Object> Exposure = new HashMap<>();
                    Exposure.put("Exposer",value);

                    userRef.collection("ExposersIDs").add(Exposure).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "onSuccess: Exposer IDs Added");
                        }
                    });

                }


            }
        });
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

        }
        Victims victims = new Victims();
        List<Victims> exposers = new ArrayList<>();


        exposerQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot:task.getResult()){


                        victims.FName = documentSnapshot.getString("FirstName");
                        victims.LName = documentSnapshot.getString("LastName");
                        victims.Phone = documentSnapshot.getString("PhoneNumber");
                        exposers.add(victims);


                        victimCounter.setText(String.valueOf(exposers.size()));
                        Log.d(TAG, "onComplete: Victims" + exposers.size());
                    }
                }else {
                    Log.d(TAG, "onComplete: Failed getting Exposee data");
                }
            }
        });





    }

}