package com.example.notehub.controllers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.notehub.R;
import com.example.notehub.abstracts.ExploreAbstracts;
import com.example.notehub.adapters.ExploreRecyclerAdapter;
import com.example.notehub.databinding.FragmentExploreBinding;
import com.example.notehub.model.Notes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ExploreController {

    private ExploreRecyclerAdapter mExploreRecyclerAdapter;
    private FragmentExploreBinding mFragmentExploreBinding;
    ArrayList<Notes> mNotesArrayList3 = new ArrayList<Notes>();;

    public ExploreController(FragmentExploreBinding fragmentExploreBinding){
        this.mFragmentExploreBinding = fragmentExploreBinding;
    }

    /*
    public void getNotesList(Context context, ExploreAbstracts exploreAbstracts, LinearLayoutManager layoutManager){
        FirebaseFirestore dataBase = FirebaseFirestore.getInstance();

        dataBase.collection("notes")
                .orderBy("year", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot doc = task.getResult();
                            mExploreRecyclerAdapter = new ExploreRecyclerAdapter(context, (ArrayList) doc.getDocuments());

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
    */

    public void newGetNotes(Context context,
                            ExploreAbstracts exploreAbstracts,
                            LinearLayoutManager layoutManager){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("notes")
                .orderBy("year", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                        @Nullable FirebaseFirestoreException e) {

                        for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()){
                            if (dc.getType() == DocumentChange.Type.ADDED){
                                mNotesArrayList3.add(dc.getDocument().toObject(Notes.class));
                            }
                        }
                        setAdapter(context, layoutManager, mNotesArrayList3);
                        exploreAbstracts.notesLoad(true);
                    }
                });
    }
    public void setAdapter(Context context,
                           LinearLayoutManager layoutManager,
                           ArrayList<Notes> mNotesArrayList){

        mExploreRecyclerAdapter = new ExploreRecyclerAdapter(context, mNotesArrayList);
        mFragmentExploreBinding.recyclerView1.setHasFixedSize(true);
        mFragmentExploreBinding.recyclerView1.setLayoutManager(layoutManager);
        mFragmentExploreBinding.recyclerView1.setAdapter(mExploreRecyclerAdapter);
        mExploreRecyclerAdapter.notifyDataSetChanged();
    }
    public ArrayList<Notes> getArrayValues(){
        return mNotesArrayList3;
    }
}
