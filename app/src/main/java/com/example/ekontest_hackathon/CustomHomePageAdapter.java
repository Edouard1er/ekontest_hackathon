package com.example.ekontest_hackathon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

class CustomHomePageAdapter extends ArrayAdapter {
    Context mContext;
    ArrayList<CustomHomePageModel> mArrayList;
    public CustomHomePageAdapter(@NonNull Context context, int resource,  ArrayList<CustomHomePageModel> arrayList) {
        super(context, resource, arrayList);
        this.mContext=context;
        this.mArrayList=arrayList;
    }
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater =(LayoutInflater)mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        convertView= layoutInflater.inflate(R.layout.homepage_custom_list, parent, false);
        final MyHomeViewPage homeViewPage = new MyHomeViewPage(convertView);

        homeViewPage.periodTime.setText(mArrayList.get(position).getPeriodTime());
        homeViewPage.amountTotal.setText(mArrayList.get(position).getAmountTotal());
        homeViewPage.amountPerHour.setText(mArrayList.get(position).getPerHour());
        homeViewPage.relationType.setText(mArrayList.get(position).getRelationType());
        homeViewPage.subject.setText(mArrayList.get(position).getSubject());


        return convertView;
    }
}
