package com.example.notehub.model;

import java.lang.reflect.Array;

public class User {
    private int id;
    private String username;
    private String campus;
    private Array favouriteNotes;
    private Array uploadedNotes;

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

    public Array getFavouriteNotes() {
        return favouriteNotes;
    }

    public void setFavouriteNotes(Array favouriteNotes) {
        this.favouriteNotes = favouriteNotes;
    }

    public Array getUploadedNotes() {
        return uploadedNotes;
    }

    public void setUploadedNotes(Array uploadedNotes) {
        this.uploadedNotes = uploadedNotes;
    }
}
