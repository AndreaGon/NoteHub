package com.example.notehub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.example.notehub.databinding.ActivityFileViewerBinding;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class FileViewerActivity extends AppCompatActivity {

    FirebaseStorage storage = FirebaseStorage.getInstance();


    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_viewer);

        pdfView = findViewById(R.id.pdfView);


        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    InputStream input = new URL("https://firebasestorage.googleapis.com/v0/b/notehub-29750.appspot.com/o/rl-notes.pdf?alt=media&token=575a9189-1a13-4ace-b269-f5dd9b273764").openStream();
                    pdfView.fromStream(input).load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();


        Toast.makeText(this, getIntent().getStringExtra("key"), Toast.LENGTH_SHORT)
                .show();
    }


}