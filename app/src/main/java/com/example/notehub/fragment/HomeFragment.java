package com.example.notehub.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.notehub.RegisterActivity;
import com.example.notehub.abstracts.HomeAbstracts;
import com.example.notehub.controllers.HomeController;
import com.example.notehub.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.Map;

public class HomeFragment extends Fragment { // CONTROLLER

    private FragmentHomeBinding home_fragment_binding;
    private HomeController mHomeController;
    private ProgressDialog mProgressDialog;
    private GridLayoutManager layoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false);
        home_fragment_binding = FragmentHomeBinding.inflate(getLayoutInflater());
        return home_fragment_binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setCancelable(true);
        mProgressDialog.setMessage("Fetching Data...");
        mProgressDialog.show();

        mHomeController = new HomeController(home_fragment_binding);

        layoutManager=new GridLayoutManager(getActivity(),1);

        mHomeController.getCurrentUser(new HomeAbstracts(){
            @Override
            public void userData(Map user){
                ArrayList listOfNotes = (ArrayList) user.get("favouriteNotes");
                if(listOfNotes.isEmpty()){
                    Toast.makeText(getActivity(), "No Favourite Notes!", Toast.LENGTH_SHORT).show();
                    mHomeController.refreshOnDataChange(getActivity(), layoutManager);
                }
                else{
                    mHomeController.getFavouriteNotes(getActivity(), (ArrayList) user.get("favouriteNotes"), layoutManager);
                }

                if (mProgressDialog.isShowing()){
                    mProgressDialog.dismiss();
                }
            }
        });
    }
}