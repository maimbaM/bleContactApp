package com.maimba.west.bleContactApp.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.maimba.west.bleContactApp.DB.MatchedPackets;
import com.maimba.west.bleContactApp.R;
import com.maimba.west.bleContactApp.VictimModel;

public class Victims extends AppCompatActivity {

    private FirebaseFirestore fStore;
    private FirebaseAuth fAuth;
    private String userID;
    private CollectionReference userDoc;
    private String time,fName,lName,loc,phone;
    private static final String TAG = "Victims";
    private RecyclerView vc_recyclerView;
    private FirestoreRecyclerAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victims);

        vc_recyclerView = findViewById(R.id.vc_Recyc);

       fAuth = FirebaseAuth.getInstance();
       fStore = FirebaseFirestore.getInstance();
       userID = fAuth.getCurrentUser().getUid();


        //Query
        Query query = fStore.collection("users").document(userID).collection("VictimsID").orderBy("TimeSeen" , Query.Direction.DESCENDING);
        //Recycler Options
        FirestoreRecyclerOptions<VictimModel> options = new FirestoreRecyclerOptions.Builder<VictimModel>()
                .setQuery(query,VictimModel.class)
                .build();

         adapter = new FirestoreRecyclerAdapter<VictimModel, VictimViewHolder>(options) {
            @NonNull
            @Override
            public VictimViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.call_card,parent,false);

                return new VictimViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull VictimViewHolder holder, int position, @NonNull VictimModel model) {
                holder.fName.setText(model.getFirstName());
                holder.lName.setText(model.getLastName());
                holder.Loc.setText(model.getLocation());
                holder.Time.setText( model.getTimeSeen());
                holder.Phone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        VictimModel victimModelobj = model.get(getAdapterPosition());
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:0746492828"));
                        startActivity(intent);

                    }
                });

            }
        };

        vc_recyclerView.setHasFixedSize(false);
        vc_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vc_recyclerView.setAdapter(adapter);
}

    private class VictimViewHolder extends RecyclerView.ViewHolder{
        private TextView Time;
        private TextView fName;
        private TextView lName;
        private TextView Loc;
        private String phNumber;
        private Button Phone;



        public VictimViewHolder(@NonNull View itemView) {
            super(itemView);
            Time = itemView.findViewById(R.id.vc_timeseen);
            fName = itemView.findViewById(R.id.vc_fName);
            lName = itemView.findViewById(R.id.vc_lName);
            Loc  = itemView.findViewById(R.id.vc_Location);
            Phone = itemView.findViewById(R.id.vc_phone);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}

