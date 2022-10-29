package com.example.notehub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.notehub.databinding.ActivityLoginBinding;
import com.example.notehub.databinding.LoginPageBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.security.PrivateKey;

public class LoginActivity extends AppCompatActivity {
    private LoginPageBinding binding;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=LoginPageBinding.inflate(getLayoutInflater());
        View v1 = binding.getRoot();
        setContentView(v1);


        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);

        binding.login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String email=binding.emailAddress.getText().toString().trim();
                String password=binding.password2.getText().toString().trim();
                progressDialog.show();
                firebaseAuth.signInWithEmailAndPassword(email,password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                progressDialog.cancel();
                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                startActivity (new Intent(LoginActivity.this,HomeActivity.class));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.cancel();
                                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        binding.resetPassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String email=binding.emailAddress.getText().toString();
                progressDialog.setTitle("Sending Mail");
                progressDialog.show();
                firebaseAuth.sendPasswordResetEmail(email)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                progressDialog.cancel();
                                Toast.makeText(LoginActivity.this, "Email Sent", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.cancel();
                                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
        binding.register.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                //Intent i1= new Intent(LoginActivity.this,RegisterActivity.class);
                //resultLauncher.launch(i1);

            }
        });
    }
}