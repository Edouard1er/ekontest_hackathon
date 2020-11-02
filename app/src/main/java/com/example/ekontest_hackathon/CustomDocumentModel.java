package com.example.ekontest_hackathon;

import java.util.Date;

class CustomDocumentModel {
   private String DocumentName;
   private String timeUploadedDocument;
   private String mDateUploadedDocument;
   private String documentStatus;
   private String id;
   private String fileName;


    public CustomDocumentModel(String documentName, String timeUploadedDocument, String dateUploadedDocument, String id, String documentStatus, String fileName) {
        DocumentName = documentName;
        this.timeUploadedDocument = timeUploadedDocument;
        mDateUploadedDocument = dateUploadedDocument;
        this.id = id;
        this.documentStatus = documentStatus;
        this.fileName = fileName;
    }

    public String getDocumentName() {
        return DocumentName;
    }

    public void setDocumentName(String documentName) {
        DocumentName = documentName;
    }

    public String getTimeUploadedDocument() {
        return timeUploadedDocument;
    }

    public void setTimeUploadedDocument(String timeUploadedDocument) { this.timeUploadedDocument = timeUploadedDocument; }

    public String getDateUploadedDocument() {
        return mDateUploadedDocument;
    }

    public void setDateUploadedDocument(String dateUploadedDocument) { mDateUploadedDocument = dateUploadedDocument; }


    public String getDocumentStatus() { return documentStatus; }

    public void setDocumentStatus(String documentStatus) { this.documentStatus = documentStatus; }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String docLink) {
        this.fileName = fileName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}