package com.example.ekontest_hackathon;

import android.content.Context;
import android.widget.Toast;

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
    public void InsertTag(final Context context,final String val){
        String [] word=val.split(" ");
        String capitalizeEachWord="";
        for(int i=0 ; i<word.length; i++){
            try{
                capitalizeEachWord = capitalizeEachWord+" "+word[i].substring(0, 1).toUpperCase() + word[i].substring(1).toLowerCase();

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        final  String tag =capitalizeEachWord.trim();

        if(!tag.contains(".") && !tag.contains("#") &&!tag.contains("$") && !tag.contains("[")&&!tag.contains("]")){
            databaseReference = FirebaseDatabase.getInstance().getReference("Tags").child(tag)
                    .child(cUser.getUid());
            databaseReference.setValue(cUser.getUid())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(context, "Enregistre", Toast.LENGTH_SHORT).show();

                        }
                    });
        }else{
            Toast.makeText(context, "Svp, supprimer ces caracteres:  . , # , $ , [ ,]", Toast.LENGTH_LONG).show();
        }

    }
    public void InsertUserIdIntoTag(){

    }

    public void removeTag(String tag){
        databaseReference = FirebaseDatabase.getInstance().getReference("Tags").child(tag).child(cUser.getUid());
        databaseReference.removeValue();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
