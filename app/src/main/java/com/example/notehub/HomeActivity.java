package com.example.notehub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.notehub.databinding.ActivityHomeBinding;
import com.example.notehub.databinding.FragmentHomeBinding;
import com.example.notehub.fragment.ExploreFragment;
import com.example.notehub.fragment.HomeFragment;
import com.example.notehub.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity { // CONTROLLER

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        replaceFragment(new HomeFragment());

        binding.bottonnav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){

                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;

                case R.id.explore:
                    replaceFragment(new ExploreFragment());
                    //Intent i = new Intent(HomeActivity.this, ExploreActivity.class);
                    //startActivity(i);
                    break;

                case R.id.profile:
                    //ADD CODE HERE AND REMOVE THE TOAST CODE
                    Toast.makeText(getApplicationContext(), "IN DEVELOPMENT", Toast.LENGTH_SHORT)
                            .show();
                    break;
            }
            return true;
        });

        /* COMMENT FOR NOW BECAUSE sample VARIABLE GOT ERROR
        binding.sample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                addNewUser();
            }
        });
        COMMENT FOR NOW BECAUSE sample VARIABLE GOT ERROR */
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
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




}
