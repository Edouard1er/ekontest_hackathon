package com.example.ekontest_hackathon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class TestHomeActivity extends AppCompatActivity {
    ListView mListView;
    CustomHomePageAdapter mAdapter;
    ArrayList mArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_home);
        mListView = findViewById(R.id.listview_test);
        mArrayList = new ArrayList<CustomHomePageModel>();
        mArrayList.add(new CustomHomePageModel("09/09/2020 - 10/10/2020","Edouard Chevenslove",
                "Math","HTG 600.50","HTG 70.24 /hour"));
        mArrayList.add(new CustomHomePageModel("09/09/2020 - 10/10/2020","Edouard Chevenslove",
                "Math","HTG 600.50","HTG 70.24 /hour"));
        mArrayList.add(new CustomHomePageModel("09/09/2020 - 10/10/2020","Edouard Chevenslove",
                "Math","HTG 600.50","HTG 70.24 /hour"));
        mArrayList.add(new CustomHomePageModel("09/09/2020 - 10/10/2020","Edouard Chevenslove",
                "Math","HTG 600.50","HTG 70.24 /hour"));
        mArrayList.add(new CustomHomePageModel("09/09/2020 - 10/10/2020","Edouard Chevenslove",
                "Math","HTG 600.50","HTG 70.24 /hour"));
        mArrayList.add(new CustomHomePageModel("09/09/2020 - 10/10/2020","Edouard Chevenslove",
                "Math","HTG 600.50","HTG 70.24 /hour"));
        mArrayList.add(new CustomHomePageModel("09/09/2020 - 10/10/2020","Edouard Chevenslove",
                "Math","HTG 600.50","HTG 70.24 /hour"));
        mArrayList.add(new CustomHomePageModel("09/09/2020 - 10/10/2020","Edouard Chevenslove",
                "Math","HTG 600.50","HTG 70.24 /hour"));
        mArrayList.add(new CustomHomePageModel("09/09/2020 - 10/10/2020","Edouard Chevenslove",
                "Math","HTG 600.50","HTG 70.24 /hour"));
        mArrayList.add(new CustomHomePageModel("09/09/2020 - 10/10/2020","Edouard Chevenslove",
                "Math","HTG 600.50","HTG 70.24 /hour"));
        mAdapter = new CustomHomePageAdapter(this,R.layout.homepage_custom_list, mArrayList);
        mListView.setAdapter(mAdapter);
    }
}