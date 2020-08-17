package com.example.ekontest_hackathon;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;


import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

class CustomDocumentAdapter extends ArrayAdapter  implements  PopupMenu.OnMenuItemClickListener{
    Context mContext;
    String name;
    ArrayList<CustomDocumentModel> mArrayList;
    int tabPosition;

    public CustomDocumentAdapter(@NonNull Context context, int resource, ArrayList<CustomDocumentModel> mArrayList) {
        super(context, resource, mArrayList);
        this.mContext=context;
        this.mArrayList=mArrayList;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater =(LayoutInflater)mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        convertView= layoutInflater.inflate(R.layout.custom_list_item, parent, false);
        final MyViewHolder myViewHolder = new MyViewHolder(convertView);
        myViewHolder.documentName.setText(mArrayList.get(position).getDocumentName());
        myViewHolder.documentTime.setText(""+mArrayList.get(position).getTimeUploadedDocument());
       // myViewHolder.mImageView.setImageResource(mArrayList.get(position).getIcon());
        myViewHolder.mDate.setText(mArrayList.get(position).getDateUploadedDocument());
        View view = layoutInflater.inflate(R.layout.fragment_document, parent,false);
        TabLayout tabLayout = view.findViewById(R.id.id_tab_layout);
        tabPosition = tabLayout.getSelectedTabPosition();
        myViewHolder.contextMenuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                available_document(v);
            }
        });

        return convertView;
    }
    public void available_document(View view){
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.menu_purchased_availabledocument);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.delete_purchavala_document: {
                Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_LONG).show();
                return true;
            }
            case R.id.info_document: {
                Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_LONG).show();
                return true;
            }

            default: return false;
        }
    }
}
