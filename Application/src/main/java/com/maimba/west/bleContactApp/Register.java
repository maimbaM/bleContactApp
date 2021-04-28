package com.maimba.west.bleContactApp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    public static final String TAG = "fstoreCreateUser";
    private EditText registerEmail,registerPassword,registerName,registerPhone;
    private Button registerbtn;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        registerName = findViewById(R.id.editTextName);
        registerPhone = findViewById(R.id.editTextPhone);
        registerbtn = findViewById(R.id.btnRegister);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();





        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String Name = registerName.getText().toString();
                String phone = registerPhone.getText().toString();
                final String email = registerEmail.getText().toString();
                String password = registerPassword.getText().toString();


                if (Name.isEmpty()){
                    registerName.setError("Name is Required");
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
                        user.put("Name",Name);
                        user.put("Phone Number",phone);
                        user.put("email",email);
                        userCollection.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "onSuccess: User Profile created for: " + userID);
                                Toast.makeText(Register.this, "You have been registered Successfully", Toast.LENGTH_LONG).show();
                            }
                        });

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
    }

}