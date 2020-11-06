package com.example.ekontest_hackathon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {
    GridView mGridView;
    List<FreelancerModel> mUsers=new ArrayList<>();
    FreelancerListAdapter adapterGrid;
    SearchView searchFreelancer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        mGridView = findViewById(R.id.freelancer_gridview);
        //Toast.makeText(this, ""+getIntent().getStringExtra("tag"), Toast.LENGTH_SHORT).show();
        searchFreelancer = (SearchView) findViewById(R.id.search_freelancer);

        searchFreelancer.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                // Log.e("Main"," data search: "+newText);
                adapterGrid.getFilter().filter(newText);
                return false;
            }
        });
        getUserMatchingtheSearchTerm(getIntent().getStringExtra("tag"));

    }

    public void getUserMatchingtheSearchTerm(final String tag){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tags").child(tag);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    mUsers.clear();
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        if( !dataSnapshot.getKey().equals("tag")){
                            DatabaseReference refUser = FirebaseDatabase.getInstance().getReference("Users")
                                    .child(dataSnapshot.getKey());
                            refUser.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    FreelancerModel userModel1=snapshot.getValue(FreelancerModel.class);
                                    if(!userModel1.getId().equals(user.getUid())){
                                        mUsers.add(userModel1);
                                        adapterGrid= new FreelancerListAdapter(getApplicationContext(), mUsers);
                                        //adapterGrid.notifyDataSetChanged();
                                        mGridView.setAdapter(adapterGrid);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}