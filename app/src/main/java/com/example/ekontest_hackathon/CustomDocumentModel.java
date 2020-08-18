package com.example.ekontest_hackathon;

import java.util.Date;

class CustomDocumentModel {
   private String DocumentName;
   private String  timeUploadedDocument;
   private String mDateUploadedDocument;

    public CustomDocumentModel(String documentName, String timeUploadedDocument, String dateUploadedDocument) {
        DocumentName = documentName;
        this.timeUploadedDocument = timeUploadedDocument;
        mDateUploadedDocument = dateUploadedDocument;

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

    public void setTimeUploadedDocument(String timeUploadedDocument) {
        this.timeUploadedDocument = timeUploadedDocument;
    }

    public String getDateUploadedDocument() {
        return mDateUploadedDocument;
    }

    public void setDateUploadedDocument(String dateUploadedDocument) {
        mDateUploadedDocument = dateUploadedDocument;
    }
}