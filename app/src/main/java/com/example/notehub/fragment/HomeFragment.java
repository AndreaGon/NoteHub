package com.example.notehub.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.notehub.R;
import com.example.notehub.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment { // CONTROLLER

    private FragmentHomeBinding home_fragment_binding;

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
}