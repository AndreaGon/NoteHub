package com.example.notehub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
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

                if (email.isEmpty() == true || password.isEmpty() == true ) {
                    Toast.makeText(LoginActivity.this, R.string.login_EmptyCredentials, Toast.LENGTH_SHORT).show();

                }
                else {
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    progressDialog.cancel();
                                    Toast.makeText(LoginActivity.this, R.string.login_Successful, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    LoginActivity.this.finish();
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
            }
        });

        binding.resetPassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String email=binding.emailAddress.getText().toString();
                if (email.isEmpty() == true) {
                    Toast.makeText(LoginActivity.this, R.string.reset_EmptyEmail, Toast.LENGTH_SHORT).show();
                }

                else{
                    progressDialog.setTitle(getString(R.string.resetPassword_OnClick));
                    progressDialog.show();
                    firebaseAuth.sendPasswordResetEmail(email)

                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    progressDialog.cancel();
                                    Toast.makeText(LoginActivity.this, R.string.resetPassword_Successful, Toast.LENGTH_SHORT).show();
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