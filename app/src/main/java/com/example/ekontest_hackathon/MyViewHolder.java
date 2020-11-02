package com.example.ekontest_hackathon;

import android.content.Context;
import android.view.ContextMenu;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.ekontest_hackathon.ui.NavDrawerActivity;

class MyViewHolder implements  View.OnClickListener{
    ImageView mImageView;
    TextView documentName;
    TextView documentTime;
    TextView mDate;
    TextView documentStatus;
    ClickListener mClickListener;
    Context cc;
    ImageView contextMenuImg;

    public MyViewHolder(View v) {
         mImageView = v.findViewById(R.id.document_icon);
         documentName = v.findViewById(R.id.documentName);
         documentTime = v.findViewById(R.id.documentId);
         documentStatus = v.findViewById(R.id.documentStatus);
//         mDate = v.findViewById(R.id.documentDate);

         contextMenuImg = v.findViewById(R.id.context_image_menu);
        // contextMenuImg.setOnClickListener(this);
       //  contextMenuImg.setOnCreateContextMenuListener(this);
    }
    public void setClickListener(ClickListener lc) {
       this.mClickListener = lc;
    }

    @Override
    public void onClick(View v) {
        this.mClickListener.onItemClick();
    }
}
