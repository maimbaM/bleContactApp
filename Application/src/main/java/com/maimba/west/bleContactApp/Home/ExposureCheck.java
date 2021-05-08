package com.maimba.west.bleContactApp.Home;

import android.icu.text.UFormat;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.maimba.west.bleContactApp.DB.ExposurePacket;
import com.maimba.west.bleContactApp.DB.MatchedPackets;
import com.maimba.west.bleContactApp.DB.PacketsViewModel;
import com.maimba.west.bleContactApp.MatchedAdapter;
import com.maimba.west.bleContactApp.R;
//import com.maimba.west.bleContactApp.DBHelper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class ExposureCheck extends AppCompatActivity {

    private FirebaseAuth fAuth ;
    private FirebaseFirestore fStore ;
    public String casesnap;
    private Button checkExposure;
    public String TAG;
    //    private DBHelper DB ;
    private PacketsViewModel mpacketsViewModel;
    private ExposurePacket exposurePacket;
    private DocumentSnapshot mLastQueriedDocument;



    //List<String> mcases = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exposure_check);
        fStore= FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        mpacketsViewModel = new ViewModelProvider(this).get(PacketsViewModel.class);
        mpacketsViewModel.deleteAllExpPackets();

        RecyclerView recyclerView = findViewById(R.id.rc_Exposure);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);
//
        MatchedAdapter adapter = new MatchedAdapter();
        recyclerView.setAdapter(adapter);


        mpacketsViewModel.getAllMatchedPackets().observe(this, new Observer<List<MatchedPackets>>() {
            @Override
            public void onChanged(List<MatchedPackets> matchedPackets) {
                adapter.setMatchedPackets(matchedPackets);
            }
        });


    }

    private class Downloaded {
        private String userName;
        private String userID;
        private String userPacketData;
        private String userPhone;
        private String caseDisease;
        private String caseDateReported;

        public Downloaded(String userName,String userID, String userPacketData, String userPhone, String caseDisease, String caseDateReported) {
            this.userName = userName;
            this.userID = userID;
            this.userPacketData = userPacketData;
            this.userPhone = userPhone;
            this.caseDisease = caseDisease;
            this.caseDateReported = caseDateReported;
        }

        public Downloaded() {
        }



        @Override
        public boolean equals(@Nullable Object obj) {
            if (obj instanceof Downloaded) {
                return ((Downloaded) obj).userPacketData == userPacketData;
            }
            return false;
        }
    }





    private void getExposurePackets2(){

        //get the date before ie currentDate - incubation period

        Date newDay = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(newDay);
        c.add(Calendar.DAY_OF_YEAR,-14);
        Date finalDay = c.getTime();
        System.out.println(newDay);
        System.out.println(finalDay);
        CollectionReference collectionReference = fStore.collection("cases");
        Downloaded caseDownload = new Downloaded();
        List<Downloaded> DownloadedPkts = new ArrayList<>();

        Query caseQuery = null;
        if (mLastQueriedDocument != null){
            caseQuery = collectionReference
                    .whereGreaterThan("Date Reported", finalDay)
                    .startAfter(mLastQueriedDocument);


        }else{
            caseQuery = collectionReference
                    .whereGreaterThan("Date Reported", finalDay);
        }

        caseQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){

                    for (QueryDocumentSnapshot documentSnapshot:task.getResult()){
                        Log.d(TAG, "onComplete: " + documentSnapshot.getString("User Packet Data"));
                        caseDownload.userName = documentSnapshot.getString("User Name");
                        caseDownload.userID = documentSnapshot.getString("USER ID");
                        caseDownload.userPacketData = documentSnapshot.getString("User Packet Data");
                        caseDownload.caseDisease = documentSnapshot.getString("Disease");
                        caseDownload.userPhone = documentSnapshot.getString("User Phone");
                        caseDownload.caseDateReported = documentSnapshot.getDate("Date Reported").toString();
                        DownloadedPkts.add(caseDownload);

                        ExposurePacket exposurePacket = new ExposurePacket(caseDownload.userPacketData,caseDownload.userID,caseDownload.userName,caseDownload.userPhone,caseDownload.caseDisease,caseDownload.caseDateReported);
                        mpacketsViewModel.insertExp(exposurePacket);

                    }
                    if (task.getResult().size() !=0 ){
                        mLastQueriedDocument = task.getResult().getDocuments()
                                .get(task.getResult().size() -1);
                    }
                }else{
                    Log.d(TAG, "onComplete:  Failed getting data");
                }
            }

        });
    }







    @Override
    protected void onStart() {
        super.onStart();

        getExposurePackets2();

    }


}