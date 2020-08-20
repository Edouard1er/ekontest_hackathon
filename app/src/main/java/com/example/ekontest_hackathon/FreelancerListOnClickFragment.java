package com.example.ekontest_hackathon;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RatingBar;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.util.ArrayList;


public class FreelancerListOnClickFragment extends Fragment {
    SimpleRatingBar mRatingBar;
    ListView mListView;
    ArrayList mArrayList;
    InfoAcademicAdapter mAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_freelancer_list_on_click, container, false);
        mRatingBar = view.findViewById(R.id.id_rating_bar_freelancer);
        mRatingBar.setStarsSeparation(100,30);
        mRatingBar.setFillColor(R.color.bleu_fonce);
        mArrayList = new ArrayList<InfoAcademicModel>();
        mListView= view.findViewById(R.id.list_info_academic_freelancer);
        mArrayList.add(new InfoAcademicModel(1, "ESIH", "Sces Info", "Licence", "2016", "2020"));
        mAdapter = new InfoAcademicAdapter(getContext(), R.layout.info_academic_custom_listview,mArrayList);
        mListView.setAdapter(mAdapter);
        return view;
    }
}