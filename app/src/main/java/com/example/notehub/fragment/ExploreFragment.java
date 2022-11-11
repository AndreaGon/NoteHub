package com.example.notehub.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.notehub.abstracts.ExploreAbstracts;
import com.example.notehub.adapters.ExploreRecyclerAdapter;
import com.example.notehub.controllers.ExploreController;
import com.example.notehub.databinding.FragmentExploreBinding;
import com.example.notehub.model.Notes;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ExploreFragment extends Fragment { // CONTROLLER

    private FragmentExploreBinding explore_fragment_binding;
    private ExploreController mExploreController;
    private LinearLayoutManager mLinearLayoutManager;

    RecyclerView mRecyclerView;
    ArrayList<Notes> mNotesArrayList;
    ExploreRecyclerAdapter mExploreRecyclerAdapter;
    FirebaseFirestore fs_db;
    private ProgressDialog mProgressDialog;

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

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Fetching Data...");
        mProgressDialog.show();

        //VIEW AVAILABLE CATEGORIES BUTTON START
        explore_fragment_binding.exploreBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "CATEGORIES PAGE IN DEVELOPMENT", Toast.LENGTH_SHORT)
                        .show();
            }
        });
        //VIEW AVAILABLE CATEGORIES BUTTON END

        mExploreController = new ExploreController(explore_fragment_binding);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());

        mExploreController.getNotesList(getActivity(), new ExploreAbstracts() {
            @Override
            public void notesLoad(boolean isLoaded){
                if (isLoaded && mProgressDialog.isShowing()){
                    mProgressDialog.dismiss();
                }
            }
        }, mLinearLayoutManager);

        


    }

    private void displayToast(String text){
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }


}