package com.example.ekontest_hackathon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainAdaper extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private String [] number;
    private int [] img;

    public  MainAdaper(Context context, String [] numberWord, int[] imgN){
        this.mContext=context;
        this.number=numberWord;
        this.img=imgN;
    }
    @Override
    public int getCount() {
        return number.length;
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

        imageView.setImageResource(img[position]);
        textView.setText(number[position]);
        return convertView;
    }
}
