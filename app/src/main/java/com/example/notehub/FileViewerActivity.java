package com.example.notehub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
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



        // calling the action bar
        ActionBar actionBar = getSupportActionBar();


        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setDisplayShowTitleEnabled(false);
        toolBar.setNavigationOnClickListener(view -> onBackPressed());


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
            case R.id.showFileInfo:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Title: " + getIntent().getStringExtra("title_key")
                        + "\nDescription: " + getIntent().getStringExtra("description")
                        + "\nUploaded by: " + getIntent().getStringExtra("username"));
                builder.setCancelable(true);

                builder.setPositiveButton(
                        "Close",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });


                AlertDialog alert11 = builder.create();
                alert11.show();

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