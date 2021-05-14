package com.maimba.west.bleContactApp.Home;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.maimba.west.bleContactApp.R;
import com.maimba.west.bleContactApp.Symptoms;
import com.maimba.west.bleContactApp.SymptomsAdapter;

import java.util.ArrayList;
import java.util.List;

public class DiseaseSymptoms extends AppCompatActivity {
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private List<Symptoms> symptomsList;
    private static final String TAG = "DiseaseSymptoms";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        CollectionReference collectionReference = fStore.collection("Diseases");
        symptomsList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.rc_Symptoms);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);

        SymptomsAdapter symptomsAdapter = new SymptomsAdapter();





//        Query diseaseQuery = collectionReference;

        collectionReference
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Symptoms symptoms = new Symptoms();


                if (task.isSuccessful()){

                    for (QueryDocumentSnapshot queryDocumentSnapshot: task.getResult()){
                        symptoms.diseaseName = queryDocumentSnapshot.getString("Name");
                        symptoms.incubationPeriod = queryDocumentSnapshot.getString("Incubation Period");
                        symptoms.scienceName = queryDocumentSnapshot.getString("Scientific name");
                        symptoms.common = queryDocumentSnapshot.getString("Common Symptoms");
                        symptoms.severe = queryDocumentSnapshot.getString("Severe Symptoms");

                        symptomsList.add(symptoms);
                        symptomsAdapter.setSymptomsList(symptomsList);
                        recyclerView.setAdapter(symptomsAdapter);

                        Log.d(TAG, "onComplete: " + symptomsList.size());


                    }
                }else {
                    Log.d(TAG, "onComplete:  Failed getting diseases");
                }
            }
        });




    }


}