package com.example.ekontest_hackathon;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class DocumentFragment extends Fragment {
    ViewPager mViewPager;
    TabLayout mTabLayout;
    TabItem mUpload;
    TabItem mAvailable;
    TabItem mPurshase;
    PagerAdapter mPagerAdapter;
    onFragmentBtnSelected listener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if( context instanceof onFragmentBtnSelected){
            listener = (onFragmentBtnSelected) context;

        }else {
            throw new ClassCastException(context.toString()+ "must implement listener");
        }
    }
    public  interface onFragmentBtnSelected{
        public void onButtonSelected();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_document, container,false);
        mViewPager = view.findViewById(R.id.id_viewpager_tab);
        mTabLayout = view.findViewById(R.id.id_tab_layout);
        mUpload = view.findViewById(R.id.id_upload_tab);
        mPurshase = view.findViewById(R.id.id_purshased_tab);
        mAvailable = view.findViewById(R.id.id_available_tab);
        mPagerAdapter = new PagerAdapter(getChildFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                mTabLayout.getTabCount());
        mViewPager.setAdapter(mPagerAdapter);

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