package com.example.notehub;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.notehub.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.example.notehub.databinding.ActivityHomeBinding;

import java.sql.Array;
import java.util.ArrayList;

/*
public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view =binding.getRoot();
        setContentView(view);

        binding.sample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                addNewUser();
            }
        });
    }

    public void addNewUser(){
        FirebaseFirestore dataBase = FirebaseFirestore.getInstance();
        ArrayList<String> favourites = new ArrayList<String>();
        ArrayList<String> uploaded = new ArrayList<String>();

        favourites.add("123");
        uploaded.add("456");

        User user = new User(0, "andreagon", "INTI", favourites, uploaded);

        dataBase.collection("user")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(HomeActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(HomeActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d("Error", String.valueOf(e));
                    }
                });
    }

}*/
