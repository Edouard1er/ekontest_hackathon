package com.example.ekontest_hackathon.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.ekontest_hackathon.DocumentFragment;
import com.example.ekontest_hackathon.FreelancerListFragment;
import com.example.ekontest_hackathon.HomePageFragment;
import com.example.ekontest_hackathon.MessageFragment;
import com.example.ekontest_hackathon.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavBottomStudentFragment extends Fragment {
    BottomNavigationView mBottomNavigationView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_nav_bottom_student, container, false);
        mBottomNavigationView= view.findViewById(R.id.bottom_navigation);



        return view;
    }
 }