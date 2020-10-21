package com.example.ekontest_hackathon;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

public class TagModel {
    String tag;
    DatabaseReference databaseReference;
    FirebaseUser cUser= FirebaseAuth.getInstance().getCurrentUser();

    public TagModel(String tag) {
        this.tag = tag;
    }
    public TagModel(){

    }
    public void InsertTag(final String tag){
        databaseReference = FirebaseDatabase.getInstance().getReference("Tags").child(tag)
        .child(cUser.getUid());
        databaseReference.setValue(cUser.getUid())
        .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
      /*  //databaseReference.push().setValue(model);

        //String key = databaseReference.push().getKey();
        Map<String,Object> t= new HashMap<>();
        t.put("tag", tag);
        databaseReference.setValue(t)
        .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                databaseReference = FirebaseDatabase.getInstance().getReference("Tags")
                        .child(tag);
                databaseReference.child(cUser.getUid()).setValue(cUser.getUid());

            }
        });*/

    }
    public void InsertUserIdIntoTag(){

    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
