package com.example.ekontest_hackathon;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NormalDocModel {
    private String idDocument;
    private String idUser;
    private String fileName;
    private String dateAdded;
    private String title;
    private String originalName;
    private String url;
    private DatabaseReference databaseReference;

    public NormalDocModel() {}

    public NormalDocModel(String fileName_, String dateAdded_, String title_, String originalName_, String url_, String idDocument_, String idUser_) {
        this.fileName = fileName_;
        this.dateAdded = dateAdded_;
        this.title = title_;
        this.originalName = originalName_;
        this.url = url_;
        this.idDocument = idDocument_;
        this.idUser = idUser_;
    }

    public void insertNormalDoc(NormalDocModel model) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Documents").child("NormalDoc");
        String key = databaseReference.push().getKey();
        model.setIdDocument(key);
        databaseReference.child(key).setValue(model);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getIdDocument() { return idDocument; }

    public void setIdDocument(String idDocument) { this.idDocument = idDocument; }

    public String getIdUser() { return idUser; }

    public void setIdUser(String idUser) { this.idUser = idUser; }
}
