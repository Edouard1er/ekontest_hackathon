package com.example.ekontest_hackathon;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class RelationFragment extends Fragment {
    ViewPager mViewPager;
    TabLayout mTabLayout;
    PagerAdapterRelation mPagerAdapterRelation;
    // declaration of the listener
    DocumentFragment.onFragmentBtnSelected listener;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_relation, container,false);
        mViewPager = view.findViewById(R.id.id_viewpager_relation);
        mTabLayout = view.findViewById(R.id.id_tab_layout_relation);
        mPagerAdapterRelation = new PagerAdapterRelation(getChildFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                mTabLayout.getTabCount());
        mViewPager.setAdapter(mPagerAdapterRelation);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        return  view;
    }
}