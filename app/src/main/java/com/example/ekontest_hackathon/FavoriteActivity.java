package com.example.ekontest_hackathon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import com.example.ekontest_hackathon.ui.NavDrawerActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {
    GridView mGridView;
    FreelancerListAdapter adapterGrid;
    private DatabaseReference ref ;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    List<FreelancerModel> mUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        /*FavoriteModel favoriteModel = new FavoriteModel();
        favoriteModel.getFavoriteFreelancer(mGridView,this);*/


        getFavoriteFreelancer();
    }

    public void getFavoriteFreelancer(){
        mGridView = findViewById(R.id.freelancer_gridview);
        mUsers=new ArrayList<>();
         ref = FirebaseDatabase.getInstance().getReference("Users")
                .child(user.getUid())
                .child("FavoriteFreelancer");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(!dataSnapshot.getKey().equals(user.getUid())){
                        DatabaseReference refUser = FirebaseDatabase.getInstance().getReference("Users")
                                .child(dataSnapshot.getKey());
                        refUser.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                FreelancerModel userModel1=snapshot.getValue(FreelancerModel.class);
                                mUsers.add(userModel1);
                                //Toast.makeText(getApplicationContext(), "Testing:"+userModel1.getPersonalInformationModel().getLastname(), Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
                adapterGrid= new FreelancerListAdapter(getApplicationContext(), mUsers);
                //adapterGrid.notifyDataSetChanged();
                mGridView.setAdapter(adapterGrid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    // retour

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, NavDrawerActivity.class);
        startActivity(intent);
        finish();
    }
}