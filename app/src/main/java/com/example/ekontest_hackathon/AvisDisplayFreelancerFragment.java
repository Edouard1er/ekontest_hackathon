package com.example.ekontest_hackathon;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class AvisDisplayFreelancerFragment extends Fragment {
    ArrayList mArrayList;
    CustomAllAvisAdapter mAdapter;
    ListView mListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_avis_display_freelancer, container, false);
        mListView = view.findViewById(R.id.id_listview_all_avis);
        mArrayList = new ArrayList<CustomAllAvisModel>();

        mArrayList.add(new CustomAllAvisModel("Pierre", "Sylvain",
                "C'est un grand freelancer," +
                        " il est toujour  à lecoute. Il soutient toujours ses eleves et cherche a leur comprendre malgré" +
                        " leurs lacunes. Je vous recommance ce professeur.", "22/08/2020", 4));
        mArrayList.add(new CustomAllAvisModel("Jean", "Robert",
                "C'est un grand freelancer," +
                        " il est toujour  à lecoute. Il soutient toujours ses eleves et cherche a leur comprendre malgré" +
                        " leurs lacunes. Je vous recommance ce professeur.", "21/08/2020", 5));
              mAdapter = new CustomAllAvisAdapter(getContext(), R.layout.all_avis_custom_listview, mArrayList);
        mListView.setAdapter(mAdapter);
        return view;
    }
}