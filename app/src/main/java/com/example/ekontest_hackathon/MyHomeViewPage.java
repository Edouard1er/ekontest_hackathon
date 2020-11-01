package com.example.ekontest_hackathon;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

class MyHomeViewPage implements  View.OnClickListener {
    TextView periodTime ;
    TextView amountTotal;
    TextView amountPerHour ;
    TextView relationType ;
    TextView subject;
    ClickListener mClickListener;

    public MyHomeViewPage(View v) {
       periodTime = v.findViewById(R.id.id_period_time_homepage);
       amountTotal =v.findViewById(R.id.id_amount_total_homepage);
        amountPerHour = v.findViewById(R.id.id_amount_per_hour_homepage);
        relationType =v.findViewById(R.id.id_relation_type_homepage);
         subject = v.findViewById(R.id.id_subject_homepage);


    }
    public void setClickListener(ClickListener lc) {
        this.mClickListener = lc;
    }

    @Override
    public void onClick(View v) {
        this.mClickListener.onItemClick();
    }

}
