package com.example.ekontest_hackathon;

import android.content.Context;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FavoriteModel {
    private DatabaseReference databaseReference ;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    List<FreelancerModel> mUsers=new ArrayList<>();

    public FavoriteModel() {
    }

    public void getFavoriteFreelancer(final GridView mGridView, final Context context){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users")
                .child(user.getUid())
                .child("FavoriteFreelancer");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    DatabaseReference refUser = FirebaseDatabase.getInstance().getReference("Users")
                            .child(dataSnapshot.getKey());
                    refUser.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            FreelancerModel userModel1=snapshot.getValue(FreelancerModel.class);
                            mUsers.add(userModel1);
                            Toast.makeText(context, "Testing", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });




                }
                FreelancerListAdapter adapterGrid;
                adapterGrid= new FreelancerListAdapter(context, mUsers);
                mGridView.setAdapter(adapterGrid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}
