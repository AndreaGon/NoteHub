package com.example.notehub.controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.notehub.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class RegisterController {
    public void addNewUser(String uniqueid, String username, String campus, String email, ArrayList favouriteNotes, ArrayList uploadedNotes){
        FirebaseFirestore dataBase = FirebaseFirestore.getInstance();

        User user = new User(uniqueid, username, campus, email, favouriteNotes, uploadedNotes);

        dataBase.collection("user")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Error", String.valueOf(e));
                    }
                });
    }
}
