package com.maimba.west.bleContactApp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.maimba.west.bleContactApp.DB.PacketsViewModel;
import com.maimba.west.bleContactApp.DB.ServiceData;

import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    public static final String TAG = Register.class.getSimpleName();
    private EditText registerEmail,registerPassword,registerFName,registerLName,registerPhone,registerID;
    private Button registerbtn;
    private TextView goToLogin;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String userID;
    private String packetData;
    private String status;
    private PacketsViewModel packetsViewModel;
    private DocumentReference statusref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        // Set packet Service Data
        packetsViewModel = new ViewModelProvider(this).get(PacketsViewModel.class);
        packetData =Constants.SERVICE_DATA;
        Log.d(TAG, "onCreate: Data : " + packetData);

        registerEmail = findViewById(R.id.editTextTextEmailAddress);
        registerPassword = findViewById(R.id.registerPassword);
        registerFName = findViewById(R.id.editTextFName);
        registerLName = findViewById(R.id.editTextLName);
        registerPhone = findViewById(R.id.editTextPhone);
        registerID = findViewById(R.id.editTextIDNumber);
        registerbtn = findViewById(R.id.btnRegister);
        goToLogin = findViewById(R.id.goToLogin);
        status = "Negative";
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        statusref = fStore.collection("status").document("Negative");





        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String FName = registerFName.getText().toString();
                String LName = registerLName.getText().toString();
                String ID = registerID.getText().toString();
                String phone = registerPhone.getText().toString();
                final String email = registerEmail.getText().toString();
                String password = registerPassword.getText().toString();


                if (FName.isEmpty()){
                    registerFName.setError("First Name is Required");
                    return;
                }
                if (LName.isEmpty()){
                    registerLName.setError("Last Name is Required");
                    return;
                }
                if (ID.isEmpty()){
                    registerID.setError("ID number is Required");
                    return;
                }
                if (phone.isEmpty()){
                    registerPhone.setError("Phone number is Required");
                    return;
                }
                if (email.isEmpty()){
                    registerEmail.setError("Email is Required");
                    return;
                }
                if (password.isEmpty()){
                    registerPassword.setError("Password is Required");
                    return;
                }


                fAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        userID = fAuth.getCurrentUser().getUid();
                        DocumentReference userCollection = fStore.collection("users").document(userID);
                        Map<String,Object> user = new HashMap<>();
                        user.put("FirstName",FName);
                        user.put("LastName",LName);
                        user.put("IDNumber",ID);
                        user.put("Status",status);
                        user.put("ServiceData",packetData);
                        user.put("PhoneNumber",phone);
                        user.put("email",email);
                        userCollection.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "onSuccess: User Profile created for: " + userID);
                                Toast.makeText(Register.this, "You have been registered Successfully", Toast.LENGTH_LONG).show();
                            }
                        });
                        statusref.update("Counter", FieldValue.increment(1));

                        //Send user to main activity
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this,login.class));
                finish();
            }
        });
    }

}