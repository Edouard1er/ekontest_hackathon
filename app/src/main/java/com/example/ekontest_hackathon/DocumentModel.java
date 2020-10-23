package com.example.ekontest_hackathon;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class DocumentModel {
    private String idDocument;
    private String idUser;
    private String title;
    private double price=0;

    private DatabaseReference databaseReference ;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    public DocumentModel( String title, double price) {
        this.title = title;
        this.price = price;
    }
    public DocumentModel(){}
   public void InsertDocument( String title, double price){
       databaseReference= FirebaseDatabase.getInstance().getReference("Documents");
       String key = databaseReference.push().getKey();
       Map<String,Object> doc= new HashMap<>();
       doc.put("idDocument", key);
       doc.put("idUser", user.getUid());
       doc.put("title", title);
       doc.put("price", price);
       //avis.put("datetime", ServerValue.TIMESTAMP);
       databaseReference.child(key).setValue(doc);
   }
   public void getPaidDocumentQuantity(String idUser, final TextView textView){
       databaseReference= FirebaseDatabase.getInstance().getReference("Documents");
       Query query=databaseReference.orderByChild("idUser").equalTo(idUser);
       query.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               int countId=0;
               DocumentModel doc;
               for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                 /*  DocumentModel doc = dataSnapshot.getValue(DocumentModel.class);
                   try{
                       if(doc.getIdUser().equals(user.getUid())){
                           countId+=1;

                       }
                   }catch (Exception e){

                   }*/
                 doc = dataSnapshot.getValue(DocumentModel.class);

                 try {
                     if(doc.getPrice()!=0){
                         countId+=1;

                     }
                 }catch (Exception e){

                 }

               }
               textView.setText(""+countId);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

   }
   public void getFreeDocumentQuantity(String idUser, final TextView textView){
        databaseReference= FirebaseDatabase.getInstance().getReference("Documents");
        Query query=databaseReference.orderByChild("idUser").equalTo(idUser);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int countId=0;
                DocumentModel doc;
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                 /*  DocumentModel doc = dataSnapshot.getValue(DocumentModel.class);
                   try{
                       if(doc.getIdUser().equals(user.getUid())){
                           countId+=1;

                       }
                   }catch (Exception e){

                   }*/
                    doc = dataSnapshot.getValue(DocumentModel.class);

                    try {
                        if(doc.getPrice()==0){
                            countId+=1;

                        }
                    }catch (Exception e){

                    }

                }
                textView.setText(""+countId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void getDocumentQuantity(String idUser, final TextView textView){
        databaseReference= FirebaseDatabase.getInstance().getReference("Documents");
        Query query=databaseReference.orderByChild("idUser").equalTo(idUser);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int countId=0;
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                 countId+=1;
                }
                textView.setText(""+countId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



    public String getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(String idDocument) {
        this.idDocument = idDocument;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
