package com.maimba.west.bleContactApp.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.maimba.west.bleContactApp.CaseModel;
import com.maimba.west.bleContactApp.R;
import com.maimba.west.bleContactApp.VictimModel;

public class Cases extends AppCompatActivity {
    private FirebaseFirestore fStore;
    private FirebaseAuth fAuth;
    private RecyclerView recyclerView;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cases);

        fAuth = FirebaseAuth.getInstance();




        //Query
        Query query = fStore.collection("cases").whereEqualTo("userID",userID);;
        //Recycler Options
        FirestoreRecyclerOptions<CaseModel> options = new FirestoreRecyclerOptions.Builder<CaseModel>()
                .setQuery(query,CaseModel.class)
                .build();

        FirestoreRecyclerAdapter adapter = new FirestoreRecyclerAdapter<CaseModel, Cases.CaseViewHolder>(options) {
            @NonNull
            @Override
            public Cases.CaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.case_card,parent,false);

                return new Cases.CaseViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull Cases.CaseViewHolder holder, int position, @NonNull CaseModel model) {
                holder.diseaseName.setText(model.getCaseDisease());
                holder.DateReported.setText((CharSequence) model.getCaseDateReported());


            }
        };
    }

    private class CaseViewHolder extends RecyclerView.ViewHolder{
        private TextView diseaseName;
        private TextView DateReported;




        public CaseViewHolder(@NonNull View itemView) {
            super(itemView);
            diseaseName = itemView.findViewById(R.id.cs_DiseaseName);
            DateReported = itemView.findViewById(R.id.cs_dateReported);

        }
    }
    }
