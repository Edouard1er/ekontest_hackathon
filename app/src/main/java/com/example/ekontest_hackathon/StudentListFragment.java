package com.example.ekontest_hackathon;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentListFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("Students");
    private StudentAdapter adapter_;
    List<UserModel> mUsers=new ArrayList<>();
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_list, container, false);
        setDisplayUsers(view);
        return view;
    }

    private void setDisplayUsers(View v){

        recyclerView = v.findViewById(R.id.student_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        System.out.println("Inside set display");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                System.out.println("Snapshot: " + snapshot);
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String stId = dataSnapshot.getKey();
                    FirebaseDatabase.getInstance().getReference("Users").child(stId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            System.out.println("Student user: " + snapshot);
                            UserModel u = snapshot.getValue(UserModel.class);
                            mUsers.add(u);
                            adapter_.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                adapter_= new StudentAdapter(getContext(), mUsers,false);
                recyclerView.setAdapter(adapter_);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}