package com.example.ekontest_hackathon;

import android.graphics.drawable.Icon;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ekontest_hackathon.ui.NavDrawerActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DocumentPurchasedFragment extends Fragment {
    ListView mListView;
    CustomDocumentAdapter mAdapter;
    ArrayList mArrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_document_purchased, container,false);
        mArrayList = new ArrayList<CustomDocumentModel>();
        mListView = (ListView) view.findViewById(R.id.purshased_list_document);
        mArrayList.add(new CustomDocumentModel("Théorie des mathématiques.pdf","14:00", "15/08/2020", "", "Accepted"));

        mAdapter = new CustomDocumentAdapter (getContext(), R.layout.custom_list_item, mArrayList);
        mListView.setAdapter(mAdapter);
        return view;
    }
  }