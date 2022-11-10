package com.example.notehub.model;

public class UploadData {

    private String upTitleStr, upDescStr, spinner_type, username, fileID;

    public UploadData() {
    }

    public UploadData(String fileID, String upTitleStr, String upDescStr, String spinner_type) {
        this.fileID = fileID;
        this.upTitleStr = upTitleStr;
        this.upDescStr = upDescStr;
        this.spinner_type = spinner_type;

    }
    ////, String username, this.username = username;

    public String getUpTitleStr() {
        return upTitleStr;
    }

    public void setUpTitleStr(String upTitleStr) {
        this.upTitleStr = upTitleStr;
    }

    public String getUpDescStr() {
        return upDescStr;
    }

    public void setUpDescStr(String upDescStr) {
        this.upDescStr = upDescStr;
    }

    public String getSpinner_type() {
        return spinner_type;
    }

    public void setSpinner_type(String spinner_type) {
        this.spinner_type = spinner_type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
