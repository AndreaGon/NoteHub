package com.example.notehub;

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
import com.example.notehub.model.UploadData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
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
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class UploadActivity extends AppCompatActivity {
    private UploadPageBinding binding;

    Uri pdfURI;
    ActivityResultLauncher<String> mTakeFile;
    ProgressDialog progressDialog;

    private FirebaseFirestore db; //store the urls of the files
    private FirebaseStorage storage; //store the files itself

    private EditText upTitleEdt, upDescEdt;
    private String upTitleStr, upDescStr, spinnerStr, fileId;
    private Button filebtn, uploadbtn;

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
        spinnerStr = spinner_type.getSelectedItem().toString();

        //Select File button
        filebtn = binding.UploadFile;
        mTakeFile = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        fileId = result.toString();
                        binding.uploadFileID.setText(fileId);
                    }
                }
        );

        filebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(UploadActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                {
                    mTakeFile.launch("application/*");
                }
                else{
                    //set a specific request code or else any request can enteer
                    ActivityCompat.requestPermissions(UploadActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9 );
                }
            }
        });

        //Firebase
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        // initializing our edittext and buttons
        upTitleEdt = binding.uploadTitle;
        upDescEdt = binding.uploadDesc;

        //upload btn feature
        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upTitleStr = upTitleEdt.getText().toString();
                upDescStr = upDescEdt.getText().toString();

                if (TextUtils.isEmpty(upTitleStr)) {
                    upTitleEdt.setError("Please enter Title");
                } else if (TextUtils.isEmpty(upDescStr)) {
                    upDescEdt.setError("Please enter Description");
                }else if(TextUtils.isEmpty(spinnerStr)){
                    Toast.makeText(UploadActivity.this, "Please Select a Note Type", Toast.LENGTH_SHORT);
                }else {
                    //add file to storage
                    //uploadFile(mTakeFile);
                    // calling method to add data to Firebase Firestore.
                    //add username into function once integrated
                    //addDataToFirestore(fileId, upTitleStr, upDescStr, spinnerStr);


                }
            }
        });
    }

    private void uploadFile(Uri mTakeFile){
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading File...");
        progressDialog.setProgress(0);
        progressDialog.show();

        String fileName = System.currentTimeMillis()+"";
        StorageReference storageReference = storage.getReference();
        storageReference.child("Upload").child(fileName).putFile(mTakeFile)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //returns url of uploaded file
                        String url = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                        //upload image and data
                        addDataToFirestore(url, upTitleStr, upDescStr,spinnerStr);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UploadActivity.this, "File Not Successfully Uploaded", Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        int currentProgress = (int)(100* snapshot.getBytesTransferred()/ snapshot.getTotalByteCount());
                        progressDialog.setProgress(currentProgress);
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mTakeFile.launch("application/*");
        } else {
            Toast.makeText(UploadActivity.this, "Please provide permission", Toast.LENGTH_SHORT).show();
        }
    }

    private void addDataToFirestore(String fileID ,String upTitleStr, String upDescStr, String spinnerStr ) {

        // creating a collection reference
        // for our Firebase Firetore database.
        CollectionReference dbuploads = db.collection("Uploads");

        // adding our data to our courses object class.
        UploadData upload = new UploadData(fileID, upTitleStr, upDescStr, spinnerStr);

        // below method is use to add data to Firebase Firestore.
        dbuploads.add(upload).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                // after the data addition is successful
                // we are displaying a success toast message.
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

}
