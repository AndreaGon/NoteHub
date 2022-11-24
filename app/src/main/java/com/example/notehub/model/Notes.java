package com.example.notehub.model;

public class Notes { // MODEL
    private String description;
    private String tags;
    private String title;
    private String uploadedBy;
    private String userName;
    private int year;
    //====================================================
    private int file_id; //the name same as in Firestore
    private String url;
    //====================================================

    public Notes(){} //DEFAULT CONSTRUCTOR

    public Notes(String description,int file_id, String tags, String title,
                 String uploadedBy, String url,  String userName, int year){
        this.description = description;
        this.file_id = file_id;
        this.tags = tags;
        this.title = title;
        this.uploadedBy = uploadedBy;
        this.url = url;
        this.userName = userName;
        this.year = year;

    } //DEFAULT CONSTRUCTOR

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    //=======================================================================================
    public int getFile_id() { return file_id; }

    public void setFile_id(int file_id) { this.file_id = file_id; }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }
    //=======================================================================================
}
