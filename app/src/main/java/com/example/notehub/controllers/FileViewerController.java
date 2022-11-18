package com.example.notehub.controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.notehub.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class FileViewerController {

    public void addToFavourites(Integer fileId){
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore dataBase = FirebaseFirestore.getInstance();

        DocumentReference user = dataBase.collection("user").document(currentFirebaseUser.getUid());

        user.update("favouriteNotes", FieldValue.arrayUnion(fileId));
    }

    public void removeFromFavourites(Integer fileId) {
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore dataBase = FirebaseFirestore.getInstance();

        DocumentReference user = dataBase.collection("user").document(currentFirebaseUser.getUid());

        user.update("favouriteNotes", FieldValue.arrayRemove(fileId));
    }
}
