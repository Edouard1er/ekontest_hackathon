package com.example.ekontest_hackathon;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MyFreelancerModel implements Parcelable {
    private String freelancerId;
    static DatabaseReference databaseReference;
    static FirebaseUser cUser= FirebaseAuth.getInstance().getCurrentUser();

    public MyFreelancerModel(String freelancerId) {
        this.freelancerId = freelancerId;
    }


    public static void InsertMyFreelancer(String id){
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
                .child(cUser.getUid()).child("myFreelancers");
        //databaseReference.push().setValue(model);
        // String key = databaseReference.push().getKey();
        Map<String,Object> freelancer= new HashMap<>();
        freelancer.put("idUser", id);
       // avis.put("datetime", ServerValue.TIMESTAMP);
        databaseReference.child(id).setValue(freelancer);
    }

    public void MakeRatingOptionVisible(String id, final LinearLayout layout){
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
                .child(cUser.getUid()).child("myFreelancers");
        databaseReference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    // user exists in the database
                    layout.setVisibility(View.VISIBLE);

                }else{
                    layout.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public String getFreelancerId() {
        return freelancerId;
    }

    public MyFreelancerModel() {
    }

    public void setFreelancerId(String freelancerId) {
        this.freelancerId = freelancerId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.freelancerId);

    }


    public MyFreelancerModel(Parcel in) {
        this.freelancerId = in.readString();


    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public MyFreelancerModel createFromParcel(Parcel in) {
            return new MyFreelancerModel(in);
        }

        public MyFreelancerModel[] newArray(int size) {
            return new MyFreelancerModel[size];
        }
    };
}
