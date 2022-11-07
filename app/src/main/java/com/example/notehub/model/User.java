package com.example.notehub.model;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class User implements Serializable { // MODEL
    private int id;
    private String username;
    private String campus;
    private ArrayList favouriteNotes;
    private ArrayList uploadedNotes;

    public User(){}

    public User(int id, String username, String campus, ArrayList favouriteNotes, ArrayList uploadedNotes){
        this.id = id;
        this.username = username;
        this.campus = campus;
        this.favouriteNotes = favouriteNotes;
        this.uploadedNotes = uploadedNotes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
