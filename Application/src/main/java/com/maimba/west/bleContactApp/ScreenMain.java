package com.maimba.west.bleContactApp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

//import com.google.firebase.auth.FirebaseAuth;

public class ScreenMain extends AppCompatActivity {
    TextView verifyMsg;
    Button verifyEmailbtn;
    //FirebaseAuth auth;
    CardView exposure,symptoms,status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        exposure = findViewById(R.id.exposures);
        symptoms = findViewById(R.id.symptoms);
        status = findViewById(R.id.statusReport);


        exposure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ExposureCheck.class));
            }
        });
        symptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Symptoms.class));
            }
        });
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),StatusReport.class));
            }
        });



        /* Verify email
        verifyMsg = findViewById(R.id.tvVerifyEmail);
        verifyEmailbtn = findViewById(R.id.btnVerifyEmail);
        auth = FirebaseAuth.getInstance();

        if (!auth.getCurrentUser().isEmailVerified()){

            verifyEmailbtn.setVisibility(View.VISIBLE);
            verifyMsg.setVisibility(View.VISIBLE);
        }

        verifyEmailbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Send Verification Email
                auth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Verification Email Sent", Toast.LENGTH_SHORT).show();
                        verifyEmailbtn.setVisibility(View.VISIBLE);
                        verifyMsg.setVisibility(View.VISIBLE);
                    }
                });
            }
        });*/

    }
}
