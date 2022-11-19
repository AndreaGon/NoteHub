package com.example.notehub.fragment;

import static com.example.notehub.R.string.Indevelopment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

//import com.example.notehub.ProfileActivity;
import com.example.notehub.R;
import com.example.notehub.databinding.FragmentExploreBinding;
import com.example.notehub.databinding.FragmentProfileBinding;
import com.example.notehub.databinding.ProfilePageBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding profile_fragment_binding;
    private ProfilePageBinding binding;
    BottomNavigationView bottomNavigationView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        profile_fragment_binding = FragmentProfileBinding.inflate(getLayoutInflater());
        return profile_fragment_binding.getRoot();
        //return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //To Add Background to the pic
        /*ImageView profile_pic = binding.ivProfile;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.shrek);
        RoundedBitmapDrawable mDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        mDrawable.setCircular(true);
        mDrawable.setColorFilter(ContextCompat.getColor(getActivity(), R.color.teal_200), PorterDuff.Mode.DST_OVER);
        profile_pic.setImageDrawable(mDrawable);*/

        profile_fragment_binding.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Users Upload Indevelopment", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        profile_fragment_binding.btnLogOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Log off Indevelopment", Toast.LENGTH_SHORT)
                        .show();
            }
        });


    }
}