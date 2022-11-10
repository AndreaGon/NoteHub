package com.example.notehub;

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
                    //ADD CODE HERE AND REMOVE THE TOAST CODE
                    Toast.makeText(getApplicationContext(), "IN DEVELOPMENT", Toast.LENGTH_SHORT)
                            .show();
                    break;
            }
            return true;
        });


        /* COMMENT FOR NOW BECAUSE sample VARIABLE GOT ERROR
        binding.sample.setOnClickListener(new View.OnClickListener() {
        ProgressBar progressBar = binding.progressBar;

        layoutManager=new GridLayoutManager(this,1);

        getCurrentUser(new HomeAbstracts(){
            @Override
            public void userData(Map user){
                getFavouriteNotes((ArrayList) user.get("favouriteNotes"));
                progressBar.setVisibility(View.GONE);
            }
        });
        COMMENT FOR NOW BECAUSE sample VARIABLE GOT ERROR */
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}