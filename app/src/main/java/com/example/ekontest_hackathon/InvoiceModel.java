package com.example.ekontest_hackathon;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InvoiceModel {
    String invoiceId;
    String senderId;
    String receiverId;
    int amount;
    String status;
    int duration;
    String freelanceName;
    DatabaseReference databaseReference;


    public InvoiceModel() {};

    public InvoiceModel(String invoiceId, String senderId, String receiverId, int amount, String status, int duration, String freelanceName) {
        this.invoiceId = invoiceId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amount = amount;
        this.status = status;
        this.duration = duration;
        this.freelanceName = freelanceName;
    }

    public void insertInvoice(InvoiceModel model) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Invoice");
        String key = databaseReference.push().getKey();
        model.setInvoiceId(key);
        databaseReference.child(key).setValue(model);
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getFreelanceName() {
        return freelanceName;
    }

    public void setFreelanceName(String freelanceName) {
        this.freelanceName = freelanceName;
    }

//    public static void unsetInvoice(String idInvoice) {

//    }
}
