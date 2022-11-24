package com.example.notehub;

import static com.example.notehub.R.string.InDevelopment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.notehub.databinding.ActivityMainBinding;
import com.example.notehub.fragment.ExploreFragment;
import com.example.notehub.fragment.HomeFragment;
import com.example.notehub.fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        replaceFragment(new HomeFragment());

        binding.bottonnav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){

                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;

                case R.id.explore:
                    replaceFragment(new ExploreFragment());
                    //Intent i = new Intent(HomeActivity.this, ExploreActivity.class);
                    //startActivity(i);
                    break;

                case R.id.profile:
                    replaceFragment(new ProfileFragment());
                    //ADD CODE HERE AND REMOVE THE TOAST CODE
                    //Toast.makeText(getApplicationContext(), InDevelopment2, Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        });

    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}
