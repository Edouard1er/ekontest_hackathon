package com.example.ekontest_hackathon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

class CustomAllAvisAdapter extends ArrayAdapter {
    ArrayList<CustomAllAvisModel> mArrayList;
    Context mContext;
    public CustomAllAvisAdapter(@NonNull Context context, int resource, ArrayList<CustomAllAvisModel> mArrayList) {
        super(context, resource, mArrayList);
        this.mContext=context;
        this.mArrayList = mArrayList;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater =(LayoutInflater)mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        convertView= layoutInflater.inflate(R.layout.all_avis_custom_listview, parent, false);

        TextView name= (TextView)convertView.findViewById(R.id.nameRaterAllAvis);
        RatingBar ratingBar = convertView.findViewById(R.id.ratingBarAllAvis);
        TextView comment = convertView.findViewById(R.id.avis_info_AllAvis);
        TextView date = convertView.findViewById(R.id.date_AllAvis);
        name.setText(mArrayList.get(position).getNom()+ " "+ mArrayList.get(position).getPrenom());
        ratingBar.setRating(mArrayList.get(position).getNumStar());
        comment.setText(mArrayList.get(position).getCommentaire());
        date.setText(mArrayList.get(position).getDate());
        return convertView;
    }
}
