package com.example.notehub.controllers;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.notehub.abstracts.ExploreAbstracts;
import com.example.notehub.adapters.ExploreRecyclerAdapter;
import com.example.notehub.databinding.FragmentExploreBinding;
import com.example.notehub.model.Notes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ExploreController {

    private ExploreRecyclerAdapter mExploreRecyclerAdapter;
    private FragmentExploreBinding mFragmentExploreBinding;
    private ArrayList<Notes> mNotesArrayList;

    public ExploreController(FragmentExploreBinding fragmentExploreBinding){
        this.mFragmentExploreBinding = fragmentExploreBinding;
    }

    public void getNotesList(ExploreAbstracts exploreAbstracts, LinearLayoutManager layoutManager){
        FirebaseFirestore dataBase = FirebaseFirestore.getInstance();

        dataBase.collection("notes")
                .orderBy("year", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot doc = task.getResult();
                            mExploreRecyclerAdapter = new ExploreRecyclerAdapter((ArrayList) doc.getDocuments());

                            mFragmentExploreBinding.recyclerView1.setHasFixedSize(true);
                            mFragmentExploreBinding.recyclerView1.setLayoutManager(layoutManager);
                            mFragmentExploreBinding.recyclerView1.setAdapter(mExploreRecyclerAdapter);

                            exploreAbstracts.notesLoad(true);

                        } else {
                            Log.d("Error: ", String.valueOf(task.getException()));
                        }
                    }
                });

    }
}
