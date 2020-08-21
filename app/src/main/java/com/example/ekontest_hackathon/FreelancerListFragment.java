package com.example.ekontest_hackathon;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;


public class FreelancerListFragment extends Fragment {
    GridView mGridView;
    freelancerInterface listener;
    ArrayList<CustomFreelancerModel>mArrayList;
    MainAdaper mainAdaper;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_freelancer_list, container, false);

        mGridView = view.findViewById(R.id.freelancer_gridview);
        mArrayList = new ArrayList<CustomFreelancerModel>();
        mArrayList.add(new CustomFreelancerModel("Edouard", "Chevenslove", R.drawable.zidane));
        mArrayList.add(new CustomFreelancerModel("Romulus", "Ronick", R.drawable.messi));
        mainAdaper = new MainAdaper(getContext(), mArrayList);
        mGridView.setAdapter(mainAdaper);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),"You clicked on "+ mArrayList.get(position).getNom() +" "+mArrayList.get(position).getPrenom() , Toast.LENGTH_LONG).show();
               listener.onClickFreelancer(mArrayList.get(position).getNom() +" "+mArrayList.get(position).getPrenom());
            }
        });
        return  view;
    }
    public interface freelancerInterface{
       public void onClickFreelancer(String name);
    }
}