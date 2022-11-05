package com.example.notehub;

import static com.example.notehub.databinding.RegisterPageBinding.inflate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.notehub.databinding.ActivityLoginBinding;
import com.example.notehub.databinding.ActivityMainBinding;
import com.example.notehub.databinding.RegisterPageBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {


    //@Override
    //protected void onCreate(Bundle savedInstanceState) {
    //super.onCreate(savedInstanceState);
      //  setContentView(R.layout.register_page)
    RegisterPageBinding binding;

    ProgressDialog progressDialog;
    Spinner spinner;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    List<String> categories = new ArrayList<>();
    String text;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=RegisterPageBinding.inflate(getLayoutInflater());
        //View v2= binding.getRoot();
        //setContentView(v2);
        setContentView(binding.getRoot());
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();



        progressDialog=new ProgressDialog(this);

        List<String> categories = new ArrayList<>();
        categories.add(0, "Select Campus");
        categories.add("INTI International College Penang");
        categories.add("Coventry University");
        categories.add("Sunway University");
        categories.add("UOW Malaysia KDU Penang University College");
        categories.add("Monash University Malaysia");

        binding.signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String username=binding.username1.getText().toString();
                String password=binding.password1.getText().toString();
                String email=binding.emailAddress.getText().toString();
                String campus=binding.spinner1.getSelectedItem().toString();
                if (username.isEmpty() == true || password.isEmpty() == true || email.isEmpty() == true || campus == "Select Campus") {
                    Toast.makeText(RegisterActivity.this, "Please fill in the details completely", Toast.LENGTH_SHORT).show();

                }
               else {

                    progressDialog.show();
                    firebaseAuth.createUserWithEmailAndPassword(email,password)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                    progressDialog.cancel();
                                    String uniqueid = firebaseFirestore.collection("User").document().getId();


                                    firebaseFirestore.collection("User")
                                            .document(uniqueid)
                                            .set(new UserModel(username, password, email, campus, uniqueid));
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    progressDialog.cancel();
                                }
                            });
               }

            }

        });



        spinner = findViewById(R.id.spinner1);
        //style and populate the spinner
        ArrayAdapter<String>dataAdapter;
        dataAdapter =new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);

        //Dropdown layout style
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //attach data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getItemAtPosition(position).equals("Select Campus"))
                {
                    //do nothing
                }
                else
                {

                    //on selecting a spinner
                    String item = parent.getItemAtPosition(position).toString();
                    //show selected Spinner item
                    text = spinner.getSelectedItem().toString();
                    Toast.makeText(parent.getContext(),"Selected: "+item, Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // TODO Auto-generated method stub

            }
        });

        binding.goToLoginActivity.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

            }
        });
    }
}