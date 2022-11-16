package com.example.notehub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.notehub.controllers.FileViewerController;
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
    FileViewerController mFileViewerController = new FileViewerController();

    PDFView pdfView;
    Integer currentFileId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_viewer);

        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        pdfView = findViewById(R.id.pdfView);


        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    String url_ref = getIntent().getStringExtra("url_key");
                    currentFileId = getIntent().getIntExtra("file_id", 0);
                    InputStream input = new URL(url_ref).openStream();
                    pdfView.fromStream(input).load();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();

        Toast.makeText(this, getIntent().getStringExtra("title_key"), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.viewer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addToFav:
                mFileViewerController.addToFavourites(currentFileId);
                return true;
            case R.id.removeFromFav:
                mFileViewerController.removeFromFavourites(currentFileId);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(getIntent().getBooleanExtra("is_fav", false)){
            menu.findItem(R.id.addToFav).setVisible(false);
        }
        else{
            menu.findItem(R.id.removeFromFav).setVisible(false);
        }


        return true;
    }


}