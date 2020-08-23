package com.example.ekontest_hackathon;



import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MessageModel {
    private String receiver;
    private String sender;
    private String message;
    private String TAG ="Inside Message Class";
    private long datetime;

    DatabaseReference databaseReference;

    public MessageModel(String receiver, String sender, String message) {
        this.receiver = receiver;
        this.sender = sender;
        this.message = message;
        databaseReference = FirebaseDatabase.getInstance().getReference("Chats");

    }
    public MessageModel(){
    }

    public void InsertMessage(String receiver,String sender, String message){
        databaseReference = FirebaseDatabase.getInstance().getReference("Chats");
        //databaseReference.push().setValue(model);

        String key = databaseReference.push().getKey();
        Map<String,Object> messageModel= new HashMap<>();
        messageModel.put("receiver", receiver);
        messageModel.put("sender", sender);
        messageModel.put("message", message);
        messageModel.put("datetime", ServerValue.TIMESTAMP);
        databaseReference.child(key).setValue(messageModel);
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime( long datetime) {
        this.datetime = datetime;
    }
}
