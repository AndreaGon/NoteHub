package com.example.notehub.model;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable { // MODEL
    private String uniqueid;
    private String username;
    private String campus;
    private String email;
    private ArrayList<String> favouriteNotes;
    private ArrayList<String> uploadedNotes;

    public User(String uniqueid, String username, String campus, String email, ArrayList favouriteNotes, ArrayList uploadedNotes){
        this.uniqueid = uniqueid;
        this.username = username;
        this.campus = campus;
        this.email = email;
        this.favouriteNotes = favouriteNotes;
        this.uploadedNotes = uploadedNotes;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public ArrayList getFavouriteNotes() {
        return favouriteNotes;
    }

    public String getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFavouriteNotes(ArrayList favouriteNotes) {
        this.favouriteNotes = favouriteNotes;
    }

    public ArrayList getUploadedNotes() {
        return uploadedNotes;
    }

    public void setUploadedNotes(ArrayList uploadedNotes) {
        this.uploadedNotes = uploadedNotes;
    }
}
