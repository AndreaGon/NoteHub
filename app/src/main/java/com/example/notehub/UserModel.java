package com.example.notehub;

import com.example.notehub.model.User;

public class UserModel {
    private String username,password,email,campus,uniqueid;

    public UserModel() {
    }

    public UserModel(String username, String password, String email, String campus, String uniqueid) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.campus = campus;
        this.uniqueid = uniqueid;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
    }
}
