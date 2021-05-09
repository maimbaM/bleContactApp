package com.maimba.west.bleContactApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.maimba.west.bleContactApp.DB.ContractTracingDB;
import com.maimba.west.bleContactApp.DB.PacketsViewModel;
import com.maimba.west.bleContactApp.DB.ScannedDao;
import com.maimba.west.bleContactApp.DB.ServiceData;

import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;

public class login extends AppCompatActivity {

    public static final String TAG = login.class.getSimpleName();
    private EditText loginEmail, loginPassword  ;
    private Button loginbtn;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String userID;
    private PacketsViewModel packetsViewModel;
    private String packetData;
    private DocumentReference userRef;
    private ScannedDao scannedDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ContractTracingDB database = ContractTracingDB.getInstance(getApplicationContext());
        scannedDao = database.scannedDao();
        packetData = scannedDao.selectServiceData();
        ServiceData serviceData = new ServiceData(packetData);
        packetsViewModel.insertServiceData(serviceData);



        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        loginbtn = findViewById(R.id.loginbtn);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });


    }

    private void loginUser() {

        String email = loginEmail.getText().toString();
        String password = loginPassword.getText().toString();

        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if (!password.isEmpty()){
                fAuth.signInWithEmailAndPassword(email,password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                FirebaseUser mfirebaseuser = fAuth.getCurrentUser();
                                userID= mfirebaseuser.getUid();
                                userRef = fStore.collection("users").document(userID);


                              startActivity(new Intent(login.this,MainActivity.class));
                              finish();
                            }
                        });

            }else{
                loginPassword.setError("Enter your Password");
            }
        }else if (email.isEmpty()) {
            loginEmail.setError("Enter your Email");

        }else {
            loginEmail.setError(("Please Enter Correct Email"));
        }
    }


}