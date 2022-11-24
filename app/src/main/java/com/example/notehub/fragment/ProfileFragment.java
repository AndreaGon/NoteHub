package com.example.notehub.fragment;

import static com.example.notehub.R.string.Indevelopment;

import android.app.ActivityManager;
import android.content.Intent;
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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

//import com.example.notehub.ProfileActivity;
import com.example.notehub.LoginActivity;
import com.example.notehub.MainActivity;
import com.example.notehub.R;
import com.example.notehub.UploadActivity;
import com.example.notehub.databinding.FragmentExploreBinding;
import com.example.notehub.databinding.FragmentProfileBinding;
import com.example.notehub.databinding.ProfilePageBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding profile_fragment_binding;
    //private ProfilePageBinding binding;
    BottomNavigationView bottomNavigationView;
    private FirebaseAuth mAuth;
    String nameResult, campusResult;
    //FirebaseUser currentUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

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

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currendid = user.getUid();
        DocumentReference reference;
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        reference = firestore.collection("user").document(currendid);

        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.getResult().exists()){

                    nameResult = task.getResult().getString("username");
                    profile_fragment_binding.ProfName.setText(nameResult);

                    campusResult = task.getResult().getString("campus");
                    profile_fragment_binding.ProfCampus.setText(campusResult);

                }else{
                    Toast.makeText(getActivity(), "Failed to Get Data", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });


        profile_fragment_binding.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getActivity(), UploadActivity.class);
                startActivity(i1);
                //Toast.makeText(getActivity(), "Users Upload Indevelopment", Toast.LENGTH_SHORT)
                  //      .show();
            }
        });

        profile_fragment_binding.btnLogOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(getActivity(), LoginActivity.class);
                startActivity(i2);
                //Toast.makeText(getActivity(), "Log off Indevelopment", Toast.LENGTH_SHORT)
                  //      .show();
            }
        });

    }

}