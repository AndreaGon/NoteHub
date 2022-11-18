package com.example.notehub.fragment;

import static com.example.notehub.R.string.Indevelopment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.notehub.R;
import com.example.notehub.abstracts.ExploreAbstracts;
import com.example.notehub.adapters.ExploreRecyclerAdapter;
import com.example.notehub.controllers.ExploreController;
import com.example.notehub.databinding.FragmentExploreBinding;
import com.example.notehub.model.Notes;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ExploreFragment extends Fragment { // CONTROLLER

    private FragmentExploreBinding explore_fragment_binding;
    private ExploreController mExploreController;
    private LinearLayoutManager mLinearLayoutManager;
    int filter_number = 0;

    RecyclerView mRecyclerView;
    ArrayList<Notes> mNotesArrayList2;
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
        mProgressDialog.setMessage(getString(R.string.FetchingData));
        mProgressDialog.show();

        //VIEW AVAILABLE CATEGORIES BUTTON START
        explore_fragment_binding.exploreBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), Indevelopment, Toast.LENGTH_SHORT)
                        .show();
            }
        });
        //VIEW AVAILABLE CATEGORIES BUTTON END

        mExploreController = new ExploreController(explore_fragment_binding);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());

        /*
        mExploreController.getNotesList(getActivity(), new ExploreAbstracts() {
            @Override
            public void notesLoad(boolean isLoaded){
                if (isLoaded && mProgressDialog.isShowing()){
                    mProgressDialog.dismiss();
                }
            }
        }, mLinearLayoutManager);
        */
        mExploreController.newGetNotes(getActivity(), new ExploreAbstracts() {
            @Override
            public void notesLoad(boolean isLoaded){
                if (isLoaded && mProgressDialog.isShowing()){
                    mProgressDialog.dismiss();
                }
            }
        }, mLinearLayoutManager);

        explore_fragment_binding.exploreView1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ArrayList<Notes> filtered_list = new ArrayList<Notes>();
                mNotesArrayList2 = new ArrayList<Notes>();

                mNotesArrayList2 = mExploreController.getArrayValues();

                for (Notes mNotes : mNotesArrayList2){
                    if (mNotes.getTitle().contains(editable)){
                        filtered_list.add(mNotes);
                    }
                    else if (mNotes.getTitle().toLowerCase().contains(editable)){
                        filtered_list.add(mNotes);
                    }
                    else{

                    }
                    Log.d("SIZE", "" + filtered_list.size());
                }
                mExploreRecyclerAdapter = new ExploreRecyclerAdapter(getActivity(), mNotesArrayList2);
                explore_fragment_binding.recyclerView1.setHasFixedSize(true);
                explore_fragment_binding.recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));
                explore_fragment_binding.recyclerView1.setAdapter(mExploreRecyclerAdapter);
                mExploreRecyclerAdapter.exploreSearchFilter(filtered_list);
            }
        });

    }

    private void displayToast(String text){
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

}