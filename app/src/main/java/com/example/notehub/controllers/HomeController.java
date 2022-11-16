package com.example.notehub.controllers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.notehub.abstracts.HomeAbstracts;
import com.example.notehub.adapters.FavouritesRecyclerAdapter;
import com.example.notehub.databinding.FragmentHomeBinding;
import com.example.notehub.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeController{
    private FavouritesRecyclerAdapter mFavouritesRecyclerAdapter;
    private FragmentHomeBinding mFragmentHomeBinding;

    public HomeController(FragmentHomeBinding fragmentHomeBinding){
        this.mFragmentHomeBinding = fragmentHomeBinding;
    }

    public void getCurrentUser(HomeAbstracts homeAbstracts){
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore dataBase = FirebaseFirestore.getInstance();



        dataBase.collection("user")
                .whereEqualTo("uniqueid", currentFirebaseUser.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                homeAbstracts.userData(document.getData());

                            }
                        } else {
                            Log.d("Error: ", String.valueOf(task.getException()));
                        }
                    }
                });

    }

    public void getFavouriteNotes(Context context, List file, GridLayoutManager layoutManager){
        FirebaseFirestore dataBase = FirebaseFirestore.getInstance();
        Log.d("IS COMPLETE", "IS COMPLETE");
        dataBase.collection("notes")
                .whereIn("file_id", file)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            QuerySnapshot doc = task.getResult();
                            mFavouritesRecyclerAdapter = new FavouritesRecyclerAdapter(context, (ArrayList) doc.getDocuments());

                            mFragmentHomeBinding.mFavouritesList.setLayoutManager(layoutManager);
                            mFragmentHomeBinding.mFavouritesList.setAdapter(mFavouritesRecyclerAdapter);

                        } else {
                            Log.d("Error: ", String.valueOf(task.getException()));
                        }
                    }
                });

    }
}
