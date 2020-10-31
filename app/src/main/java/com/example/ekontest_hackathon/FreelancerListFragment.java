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

import java.util.ArrayList;
import java.util.List;


public class FreelancerListFragment extends Fragment {

    freelancerInterface listener;
    ArrayList<CustomFreelancerModel>mArrayList;
    GridView mGridView;
    FreelancerListAdapter adapter;
    SearchView searchFreelancer;
    // Important when you have a listener with an interface
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if( context instanceof freelancerInterface){
            listener = (freelancerInterface) context;
        }else {
            throw new ClassCastException(context.toString()+ "must implement listener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    List<FreelancerModel> mFreelancers;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_freelancer_list, container, false);
        searchFreelancer = (SearchView) view.findViewById(R.id.search_freelancer);

        AvisModel avisModel = new AvisModel();
        // avisModel.InsertAvis("5NsGzmSz2RgtdphvyK3vQJI5u2G2", "Ce professeur est genial",4);
       /* avisModel.InsertAvis("YcvzpV0btkURawrKYJGzeHWGNfb2", "Ce professeur est parfait",5);
        avisModel.InsertAvis("ZLdEAuSTgLfWJME1XFIdDjZvbR73", "Ce professeur est bon",3);
        avisModel.InsertAvis("hRYQaLWw89M0xmBpqwZqsUHkcwY2", "Ce professeur est catastrophique",1);*/

        mFreelancers=new ArrayList<>();

      /*  mGridView = view.findViewById(R.id.freelancer_gridview);
        mArrayList = new ArrayList<CustomFreelancerModel>();
        mArrayList.add(new CustomFreelancerModel("Edouard", "Chevenslove", R.drawable.zidane));
        mArrayList.add(new CustomFreelancerModel("Romulus", "Ronick", R.drawable.messi));
        adapter = new MainAdaper(getContext(), mArrayList);
        mGridView.setAdapter(mainAdaper);
       mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),"You clicked on "+ mArrayList.get(position).getNom() +" "+mArrayList.get(position).getPrenom() , Toast.LENGTH_LONG).show();
               listener.onClickFreelancer(mArrayList.get(position).getNom() +" "+mArrayList.get(position).getPrenom());
            }
        });*/
        setDisplayUsers(view);
        searchFreelancer.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
    public interface freelancerInterface{
        public void onClickFreelancer(String name);
    }

    private void setDisplayUsers(View v){

        mGridView = v.findViewById(R.id.freelancer_gridview);
        //recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference freelancerRef= FirebaseDatabase.getInstance().getReference("Users");
        // Query query = freelancerRef.equalTo("personalInformationModel").equalTo("Student","type");
        freelancerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mFreelancers.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    FreelancerModel model = dataSnapshot.getValue(FreelancerModel.class);
                    if(!model.getId().equals(user.getUid())){
                        try {
                            if(model.getPersonalInformationModel().getType().equals("Freelancer")){
                                mFreelancers.add(model);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                     //Toast.makeText(getContext(), model.getId()+" "+ model.getPersonalInformationModel().getType(), Toast.LENGTH_SHORT).show();


                }
                adapter= new FreelancerListAdapter(getContext(), mFreelancers);
                mGridView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}