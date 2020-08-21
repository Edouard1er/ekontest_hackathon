package com.example.ekontest_hackathon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainAdaper extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private String [] number;
    private int [] img;
    private ArrayList<CustomFreelancerModel>mArrayList;


    public  MainAdaper(Context context, ArrayList <CustomFreelancerModel> modelArrayList){
        this.mContext=context;
       // this.number=numberWord;
       // this.img=imgN;
        this.mArrayList=modelArrayList;
    }
    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(mInflater == null){
            mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.row_item_freelancer, null);
        }
        ImageView imageView = convertView.findViewById(R.id.image_freelancer);
        TextView textView = convertView.findViewById(R.id.name_freelancer);

        imageView.setImageResource(mArrayList.get(position).getImage());
        textView.setText(mArrayList.get(position).getNom()+" " + mArrayList.get(position).getPrenom());
        return convertView;
    }
}
