package com.example.ekontest_hackathon;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class PagerAdapterRelation extends FragmentPagerAdapter {
    private int tabsNumber;
    public PagerAdapterRelation(@NonNull FragmentManager fm, int behavior, int tabs) {
        super(fm, behavior);
        this.tabsNumber = tabs;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:{
                return  new FreelancerListFragment();
            }
            case 1: {
                return new StudentListFragment();
            }
            default:{
                return new FreelancerListFragment();
            }
        }
    }
    @Override
    public int getCount() {
        return tabsNumber;
    }

}
