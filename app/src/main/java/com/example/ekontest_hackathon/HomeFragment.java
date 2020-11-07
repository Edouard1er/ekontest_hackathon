package com.example.ekontest_hackathon;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ekontest_hackathon.ui.NavDrawerActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements FreelancerListFragment.freelancerInterface {
    BottomNavigationView mBottomNavigationView;
    onItemBottomMenuSelected listener;
    ListView homePageListView;
    ArrayList<CustomHomePageModel> mArrayList;

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
        switch (NavDrawerActivity.bottomMenu){
            case "My Relations":{
                menuItemClick(new FreelancerListFragment(), "My Relations");
                mBottomNavigationView.setSelectedItemId(R.id.bottom_freelancer);
                break;
            }
        }
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bottom_freelancer:{
                        listener.toolBarTitle("My Relations");
                        item.setChecked(true);
                        getChildFragmentManager().beginTransaction().replace(R.id.home_container_layout, new RelationFragment()).commit();
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
                        getChildFragmentManager().beginTransaction().replace(R.id.home_container_layout, new SearchFragment()).commit();
                        break;
                    }
                    case R.id.bottom_home:{
                        listener.toolBarTitle("Homepage");
                        item.setChecked(true);
                        getChildFragmentManager().beginTransaction().replace(R.id.home_container_layout, new HomePageFragment()).commit();
                        break;
                    }
                }


                return true;
            }
        });
        return view;
    }

    @Override
    public void onClickFreelancer(String name) {
        getChildFragmentManager().beginTransaction().replace(R.id.home_container_layout, new FreelancerListOnClickFragment()).commit();

    }

    public  interface onItemBottomMenuSelected{
        void toolBarTitle(String fragment);
    }
    public void menuItemClick(Fragment fragment, String toolBar){
        listener.toolBarTitle(toolBar);
        getChildFragmentManager().beginTransaction().replace(R.id.home_container_layout, fragment).commit();
    }
}