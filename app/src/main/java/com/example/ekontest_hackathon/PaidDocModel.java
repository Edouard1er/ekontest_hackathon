package com.example.ekontest_hackathon;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PaidDocModel {
    private String idDocument;
    private String idUser;
    private String fileName;
    private String dateAdded;
    private String title;
    private String originalName;
    private String status;
    private String sharingCode;
    private String url;
    private String scanId;
    private String scanResultId;
    private float price;
    private DatabaseReference databaseReference;

    public PaidDocModel(String fileName, String dateAdded, String title,
                        String originalName, String status, String sharingCode,
                        String url, float price, String scanId, String scanResultId,
                        String idUser, String idDocument) {
        this.fileName = fileName;
        this.dateAdded = dateAdded;
        this.title = title;
        this.originalName = originalName;
        this.status = status;
        this.sharingCode = sharingCode;
        this.url = url;
        this.price = price;
        this.scanId = scanId;
        this.scanResultId = scanResultId;
        this.idUser = idUser;
        this.idDocument = idDocument;
    }

    public void insertPaidDoc(PaidDocModel model) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        database.useEmulator("10.0.2.2", 9000);
        databaseReference = database.getReference("Documents").child("PaidDoc");
        String key = databaseReference.push().getKey();
        model.setIdDocument(key);
        databaseReference.child(key).setValue(model);
//        databaseReference.push().setValue(model);
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSharingCode() {
        return sharingCode;
    }

    public void setSharingCode(String sharingCode) {
        this.sharingCode = sharingCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getScanId() { return scanId; }

    public void setScanId(String scanId) { this.scanId = scanId; }

    public String getScanResultId() { return scanResultId; }

    public void setScanResultId(String scanResultId) { this.scanResultId = scanResultId; }

    public String getIdDocument() { return idDocument; }

    public void setIdDocument(String idDocument) { this.idDocument = idDocument; }

    public String getIdUser() { return idUser; }

    public void setIdUser(String idUser) { this.idUser = idUser; }
}
