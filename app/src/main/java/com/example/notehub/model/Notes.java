package com.example.notehub.model;

public class Notes { // MODEL
    private int fileId;
    private String description;
    private String tags;
    private String title;
    private String uploadedBy;
    private String userName;
    //====================================================
    private int file_id; //the name same as in Firestore
    //====================================================

    public Notes(){} //DEFAULT CONSTRUCTOR

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

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

    //=======================================================================================
    public int getFile_id() { return file_id; }

    public void setFile_id(int file_id) { this.file_id = file_id; }
    //=======================================================================================
}
