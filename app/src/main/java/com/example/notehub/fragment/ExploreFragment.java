package com.example.notehub.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.notehub.MyAdapter;
import com.example.notehub.databinding.FragmentExploreBinding;
import com.example.notehub.model.Notes;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ExploreFragment extends Fragment { // CONTROLLER

    private FragmentExploreBinding explore_fragment_binding;

    RecyclerView mRecyclerView;
    ArrayList<Notes> mNotesArrayList;
    MyAdapter mMyAdapter;
    FirebaseFirestore fs_db;
    ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_explore, container, false);
        explore_fragment_binding = FragmentExploreBinding.inflate(getLayoutInflater());
        return explore_fragment_binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        //VIEW AVAILABLE CATEGORIES BUTTON START
        explore_fragment_binding.exploreBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "CATEGORIES PAGE IN DEVELOPMENT", Toast.LENGTH_SHORT)
                        .show();
            }
        });
        //VIEW AVAILABLE CATEGORIES BUTTON END

        mRecyclerView = explore_fragment_binding.recyclerView1;
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        fs_db = FirebaseFirestore.getInstance();
        mNotesArrayList = new ArrayList<Notes>();
        mMyAdapter = new MyAdapter(getActivity(), mNotesArrayList, new MyAdapter.ItemClickListener() {
            @Override
            public void onItemClick(Notes mNotes) {
                displayToast(mNotes.getTitle()
                        + "\n"
                        + mNotes.getDescription()
                        + "\n"
                        + mNotes.getTags());
            }
        });

        mRecyclerView.setAdapter(mMyAdapter);

        EventChangeListener();
    }

    private void displayToast(String text){
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    private void EventChangeListener(){

        fs_db.collection("notes")
                .orderBy("year", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        if (e != null){

                            if (progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }

                            Toast.makeText(getActivity(), "Firestore Error = " + e, Toast.LENGTH_SHORT).show();
                            return;
                        }

                        for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()){

                            if (dc.getType() == DocumentChange.Type.ADDED){

                                mNotesArrayList.add(dc.getDocument().toObject(Notes.class));

                            }

                            mMyAdapter.notifyDataSetChanged();
                            if (progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }

                        }

                    }
                });

    }

}