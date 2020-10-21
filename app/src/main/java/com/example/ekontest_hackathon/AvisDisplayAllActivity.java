package com.example.ekontest_hackathon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AvisDisplayAllActivity extends AppCompatActivity {
    AvisAdapter adapter;
    List<AvisModel> mAvis=new ArrayList<>();
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avis_display_all);
        recyclerView = findViewById(R.id.list_avis_on_click);
        try {
            readAvis();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private void readAvis() {
        DatabaseReference avisRef = FirebaseDatabase.getInstance().getReference("Users")
                .child(getIntent().getStringExtra("idUser"))
                .child("avisModel");
        avisRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                mAvis.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    AvisModel avis = dataSnapshot.getValue(AvisModel.class);
                    mAvis.add(avis);
                    //System.out.println("This is the text message :"+ avis.getComment());
                    // System.out.println("This is the text message :"+ mAvis.size());

                }


                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter=new AvisAdapter(getApplicationContext(), mAvis);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}