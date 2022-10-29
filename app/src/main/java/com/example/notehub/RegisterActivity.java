package com.example.notehub;

import static com.example.notehub.databinding.RegisterPageBinding.inflate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.notehub.databinding.ActivityLoginBinding;
import com.example.notehub.databinding.ActivityMainBinding;
import com.example.notehub.databinding.RegisterPageBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {


    RegisterPageBinding binding;

    ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=RegisterPageBinding.inflate(getLayoutInflater());
        //View v2= binding.getRoot();
        //setContentView(v2);
        setContentView(binding.getRoot());
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        progressDialog=new ProgressDialog(this);


        binding.signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String username=binding.username1.getText().toString();
                String password=binding.password1.getText().toString();
                String email=binding.emailAddress.getText().toString();

                progressDialog.show();
                firebaseAuth.createUserWithEmailAndPassword(email,password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                progressDialog.cancel();

                                firebaseFirestore.collection("User")
                                        .document(FirebaseAuth.getInstance().getUid())
                                        .set(new UserModel(username,password,email));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }
                        });
            }
        });
        binding.goToLoginActivity.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

            }
        });
    }
}