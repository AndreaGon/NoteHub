package com.example.notehub;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.example.notehub.databinding.ProfilePageBinding;
import com.example.notehub.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

/*
public class ProfileActivity extends AppCompatActivity { //implements BottomNavigationView.OnItemSelectedListener

    private ProfilePageBinding binding;
    BottomNavigationView bottomNavigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ProfilePageBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();

        //To Add Background to the pic
        ImageView profile_pic = binding.ivProfile;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.shrek);
        RoundedBitmapDrawable mDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        mDrawable.setCircular(true);
        mDrawable.setColorFilter(ContextCompat.getColor(ProfileActivity.this, R.color.teal_200), PorterDuff.Mode.DST_OVER);
        profile_pic.setImageDrawable(mDrawable);

        bottomNavigationView = binding.bottonnav;
        bottomNavigationView.OnItemSelectedListener(this);
        loadFragment(new DashbordFragment());

    }

    //Navigation
   @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.home:
                fragment = new DashbordFragment();
                break;
            case R.id.explore:
                fragment = new usersFragment();
                break;
            case R.id.profile:
                fragment = new ProfileFragment();
                break;
        }
        if (fragment != null) {
            loadFragment(fragment);
        }
        return true;
    }
    void loadFragment(Fragment fragment) {
        //to attach fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.rela, fragment).commit();
    }
}*/