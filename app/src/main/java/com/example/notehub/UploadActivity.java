package com.example.notehub;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistryOwner;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.notehub.databinding.UploadPageBinding;
import com.example.notehub.model.Notes;
import com.example.notehub.model.UploadData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class UploadActivity extends AppCompatActivity {
    private UploadPageBinding binding;

    Uri pdfURI;
    ActivityResultLauncher<Intent> mTakeFile;
    //Task<Uri> storageUrl;
    ProgressDialog progressDialog;

    //private FirebaseDatabase database;
    private FirebaseFirestore db; //store the urls of the files
    private FirebaseStorage storage; //store the files itself

    private int file_id;
    private String upTitleStr, upDescStr, spinnerStr, testUri, generatedFilePath;
    private String storageUrl, usrname, uniqueID;
    private EditText upTitleEdt, upDescEdt;
    private TextView fileName;
    //private Button filebtn, uploadbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = UploadPageBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);

        //For Dropdown Spinner
        List<String> types = Arrays.asList("Notes", "Exam Paper", "Tutorial");
        Spinner spinner_type = binding.spinnerTypes;
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_type.setAdapter(adapter);
        //String of users selection

        //Select File button
        //filebtn = binding.UploadFile;
        mTakeFile = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        Intent data = result.getData();
                        if (data != null) {
                            // When data is not equal to empty
                            // Get PDf uri from android storage
                            pdfURI = data.getData();
                            testUri = pdfURI.toString();

                            //fileName = binding.uploadFileID ;

                            binding.uploadFileID.setText(testUri);

                            /*// Get PDF path
                            String sPath = sUri.getPath(); */
                        }
                    }
                }
        );

        //Select File button
        //filebtn = binding.UploadFile;
        binding.UploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check condition
                if (ActivityCompat.checkSelfPermission(UploadActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // When permission is not granted
                    // Result permission
                    ActivityCompat.requestPermissions(UploadActivity.this,
                            new String[] {Manifest.permission.READ_EXTERNAL_STORAGE },1);
                }
                else {
                    // When permission is granted
                    // Create method
                    selectPDF();
                }
            }
        });

        //Firebase
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        // initializing our edittext and buttons
        upTitleEdt = binding.uploadTitle;
        upDescEdt = binding.uploadDesc;

        Random rand = new Random();
        file_id = rand.nextInt(999);

        //upload btn feature
        //uploadbtn.setOnClickListener(new View.OnClickListener() {
        binding.btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                upTitleStr = upTitleEdt.getText().toString();
                upDescStr = upDescEdt.getText().toString();
                spinnerStr = spinner_type.getSelectedItem().toString();

                if (TextUtils.isEmpty(upTitleStr)) {
                    upTitleEdt.setError("Please enter Title");
                } else if (TextUtils.isEmpty(upDescStr)) {
                    upDescEdt.setError("Please enter Description");
                }else {
                    //add file to storage
                    uploadFile(pdfURI);

                    //(Description, file_id, dropdown value, Title, uploadedBy,
                    // url, userName, year);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i1 = new Intent(UploadActivity.this, MainActivity.class);
                            startActivity(i1);
                        }
                    }, 5000);
                    /*Intent i1 = new Intent(UploadActivity.this, MainActivity.class);
                    startActivity(i1);*/
                    Log.d("UploadActivity",storageUrl+" in publish btn");


                }
            }
        });
    }

    private void selectPDF()
    {
        // Initialize intent
        Intent intent= new Intent(Intent.ACTION_GET_CONTENT);
        // set type
        intent.setType("application/*");
        // Launch intent
        mTakeFile.launch(intent);
    }

    private void uploadFile(Uri mTakeFile){
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading File...");
        progressDialog.setProgress(0);
        progressDialog.setCancelable(false);
        progressDialog.show();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currendid = user.getUid();
        DocumentReference reference;
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        reference = firestore.collection("user").document(currendid);

        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.getResult().exists()){

                    usrname = task.getResult().getString("username");
                    uniqueID = task.getResult().getString("uniqueid");


                }else{
                    Toast.makeText(UploadActivity.this, "Failed to Get Data", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        String fileName = System.currentTimeMillis()+"";
        StorageReference storageReference = storage.getReference();

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        //remove .child if you dont want a file for the pdfs //.child("notes")
        storageReference.child("notes").child(fileName).putFile(pdfURI)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task downloadUrl = taskSnapshot.getStorage().getDownloadUrl();

                        downloadUrl.addOnSuccessListener(result ->{
                            Log.d("Uploading Success", result.toString());
                            addDataToFirestore(upDescStr, file_id, spinnerStr, upTitleStr, uniqueID,
                               result.toString(), usrname, year);
                        });


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UploadActivity.this, "File & Data Not Successfully Uploaded", Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        int currentProgress = (int)(100* snapshot.getBytesTransferred()/ snapshot.getTotalByteCount());
                        progressDialog.setProgress(currentProgress);
                    }
                });
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mTakeFile.launch("application/*");
        } else {
            Toast.makeText(UploadActivity.this, "Please provide permission", Toast.LENGTH_SHORT).show();
        }
    }*/

    private void addDataToFirestore(String upDescStr, int fileID, String spinnerStr,  String upTitleStr,
                                    String uploadedBy, String url, String userName, int year) { // Uri url,

        // creating a collection reference
        // for our Firebase Firetore database.

        CollectionReference dbuploads = db.collection("notes"); // change to "notes" later

        // adding our data to our courses object class.
        Notes upload = new Notes(upDescStr, fileID, spinnerStr, upTitleStr, uploadedBy, url, userName, year);

        // below method is use to add data to Firebase Firestore.
        dbuploads.add(upload).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                // after the data addition is successful, display a success toast message.
                Toast.makeText(UploadActivity.this, "The file has been uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // this method is called when the data addition process is failed.
                // displaying a toast message when data addition is failed.
                Toast.makeText(UploadActivity.this, "Fail to add file \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            selectPDF();
        }
        else{
            // When permission is denied
            Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }



}
    /*private void selectFile(){
        //to allow user to select file using filemanager

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);//intent made to fetch files
        startActivityForResult(intent, 86);
        registerForActivityResult()
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==86 && resultCode == RESULT_OK && data!=null)
        {
            pdfURI = data.getData();//returns URI of selected file
        }
        else
        {
            Toast.makeText(UploadActivity.this, "Please select a file", Toast.LENGTH_SHORT).show();
        }

    }*/


