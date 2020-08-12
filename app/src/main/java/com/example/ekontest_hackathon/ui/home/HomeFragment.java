package com.example.ekontest_hackathon.ui.home;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

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


public class HomeFragment extends Fragment {
    BottomNavigationView mBottomNavigationView;
    onItemBottomMenuSelected listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if( context instanceof onItemBottomMenuSelected){
            listener = (onItemBottomMenuSelected) context;

        }else {
            throw new ClassCastException(context.toString()+ "must implement listener");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        // Initialize and assign variable
        mBottomNavigationView= view.findViewById(R.id.bottom_navigation);
        // set Home selected

        // perfom ItemSelectedListener
        getChildFragmentManager().beginTransaction().replace(R.id.home_container_layout, new HomePageFragment()).commit();
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()){
                    case R.id.bottom_freelancer:{
                        listener.toolBarTitle("Freelancer List");
                        item.setChecked(true);
                        getChildFragmentManager().beginTransaction().replace(R.id.home_container_layout, new FreelancerListFragment()).commit();
                        break;
                    }
                    case R.id.bottom_document:{
                        listener.toolBarTitle("Documents");
                        item.setChecked(true);
                        getChildFragmentManager().beginTransaction().replace(R.id.home_container_layout, new DocumentFragment()).commit();
                        break;
                    }
                    case R.id.bottom_message:{
                        listener.toolBarTitle("Message");
                        item.setChecked(true);
                        getChildFragmentManager().beginTransaction().replace(R.id.home_container_layout, new MessageFragment()).commit();
                        break;
                    }
                    case R.id.bottom_search:{
                        listener.toolBarTitle("Search");
                        item.setChecked(true);
                        break;
                    }
                    case R.id.bottom_home:{
                        listener.toolBarTitle("Homepage");
                        item.setChecked(true);
                        getChildFragmentManager().beginTransaction().replace(R.id.home_container_layout, new HomePageFragment()).commit();
                        break;
                    }
                }
                return false;
            }
        });
        return view;
    }
    public  interface onItemBottomMenuSelected{
        public void toolBarTitle(String fragment);
    }
}