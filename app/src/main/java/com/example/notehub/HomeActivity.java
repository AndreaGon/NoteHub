package com.example.notehub;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.notehub.abstracts.HomeAbstracts;
import com.example.notehub.adapters.FavouritesRecyclerAdapter;
import com.example.notehub.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.example.notehub.databinding.ActivityHomeBinding;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private FavouritesRecyclerAdapter mFavouritesRecyclerAdapter;
    private GridLayoutManager layoutManager;
    private ProgressBar mProgressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view =binding.getRoot();
        setContentView(view);

        ProgressBar progressBar = binding.progressBar;

        layoutManager=new GridLayoutManager(this,1);

        getCurrentUser(new HomeAbstracts(){
            @Override
            public void userData(Map user){
                getFavouriteNotes((ArrayList) user.get("favouriteNotes"));
                progressBar.setVisibility(View.GONE);
            }
        });
    }



    public void getCurrentUser(HomeAbstracts homeAbstracts){
        FirebaseFirestore dataBase = FirebaseFirestore.getInstance();

        dataBase.collection("user")
                .whereEqualTo("id", "2GyFRiETIjK0rDUUzGrQ")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Toast.makeText(HomeActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                                homeAbstracts.userData(document.getData());

                            }
                        } else {
                            Toast.makeText(HomeActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                            Log.d("Error: ", String.valueOf(task.getException()));
                        }
                    }
                });

    }

    public void getFavouriteNotes(List file){
        FirebaseFirestore dataBase = FirebaseFirestore.getInstance();
        dataBase.collection("notes")
                .whereIn("file_id", file)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot doc = task.getResult();
                            Toast.makeText(HomeActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                            mFavouritesRecyclerAdapter = new FavouritesRecyclerAdapter(doc.getDocuments());

                            binding.favouritesList.setLayoutManager(layoutManager);
                            binding.favouritesList.setAdapter(mFavouritesRecyclerAdapter);
                        } else {
                            Toast.makeText(HomeActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                            Log.d("Error: ", String.valueOf(task.getException()));
                        }
                    }
                });

    }

    public void addNewUser(){
        FirebaseFirestore dataBase = FirebaseFirestore.getInstance();
        ArrayList<String> favourites = new ArrayList<String>();
        ArrayList<String> uploaded = new ArrayList<String>();

        favourites.add("123");
        uploaded.add("456");

        User user = new User(0, "andreagon", "INTI", favourites, uploaded);

        dataBase.collection("user")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(HomeActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(HomeActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d("Error", String.valueOf(e));
                    }
                });
    }




}
