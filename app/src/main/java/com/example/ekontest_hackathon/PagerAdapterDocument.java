package com.example.ekontest_hackathon;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class PagerAdapterDocument extends FragmentPagerAdapter {
    private int tabsNumber;
    public PagerAdapterDocument(@NonNull FragmentManager fm, int behavior, int tabs) {
        super(fm, behavior);
        this.tabsNumber = tabs;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:{
                return  new DocumentPurchasedFragment();
            }
            case 1:{
                return new DocumentAvailableFragment();
            }
            case 2:{
                return new FreeDocumentUploadedFragment();
            }
            case 3:{
                return new PaidDocumentUploadedFragment();
            }
            default:{
                return new DocumentPurchasedFragment();
            }
        }
    }
    @Override
    public int getCount() {
        return tabsNumber;
    }

}
