package com.example.ekontest_hackathon;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.google.firebase.database.Query;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class StudentListFragment extends Fragment {

    GridView mGridView;
    StudentListAdapter adapter;
    SearchView searchStudent;
    // Important when you have a listener with an interface
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    List<StudentModel> mStudents;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_student_list, container, false);
        searchStudent = (SearchView) view.findViewById(R.id.search_student);


        mStudents=new ArrayList<>();
        setDisplayUsers(view);
        searchStudent.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                // Log.e("Main"," data search: "+newText);
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return  view;
    }


    private void setDisplayUsers(View v){
        mGridView = v.findViewById(R.id.student_gridview);

        final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference freelancerRef= FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("Students");
        // Query query = freelancerRef.equalTo("personalInformationModel").equalTo("Student","type");
        freelancerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mStudents.clear();

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//
                    DatabaseReference user = FirebaseDatabase.getInstance().getReference("Users").child(dataSnapshot.getKey());
                    user.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            StudentModel model = snapshot.getValue(StudentModel.class);
                            System.out.println(snapshot);
                           // Toast.makeText(getContext(), "Inside Display user:"+model.getPersonalInformationModel().getLastname(), Toast.LENGTH_SHORT).show();

                            try {

                                    mStudents.add(model);

                                Toast.makeText(getContext(), model.getId(), Toast.LENGTH_SHORT).show();

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            adapter= new StudentListAdapter(getContext(), mStudents);
                            adapter.notifyDataSetChanged();
                            mGridView.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                    //Toast.makeText(getContext(), model.getId()+" "+ model.getPersonalInformationModel().getType(), Toast.LENGTH_SHORT).show();


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}