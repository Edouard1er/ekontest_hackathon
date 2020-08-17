package com.example.ekontest_hackathon;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.ekontest_hackathon.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DocumentAvailableFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {
    ListView mListView;
    CustomDocumentAdapter mAdapter;
    ArrayList mArrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_document_available, container, false);
        mArrayList = new ArrayList<CustomDocumentModel>();
        mListView = (ListView) view.findViewById(R.id.available_list_document);
        mArrayList.add(new CustomDocumentModel("Théorie des physiques.pdf","20:00", "15/08/2020"));
        mArrayList.add(new CustomDocumentModel("Théorie des physiques.pdf","20:00", "15/08/2020"));
        mArrayList.add(new CustomDocumentModel("Théorie des physiques.pdf","20:00", "15/08/2020"));
        mArrayList.add(new CustomDocumentModel("Théorie des physiques.pdf","20:00", "15/08/2020"));
        mArrayList.add(new CustomDocumentModel("Théorie des physiques.pdf","20:00", "15/08/2020"));
        mArrayList.add(new CustomDocumentModel("Théorie des physiques.pdf","20:00", "15/08/2020"));
        mArrayList.add(new CustomDocumentModel("Théorie des physiques.pdf","20:00", "15/08/2020"));
        mArrayList.add(new CustomDocumentModel("Théorie des physiques.pdf","20:00", "15/08/2020"));
        mArrayList.add(new CustomDocumentModel("Théorie des physiques.pdf","20:00", "15/08/2020"));
        mArrayList.add(new CustomDocumentModel("Théorie des physiques.pdf","20:00", "15/08/2020"));
        mArrayList.add(new CustomDocumentModel("Théorie des physiques.pdf","20:00", "15/08/2020"));
        mArrayList.add(new CustomDocumentModel("Théorie des physiques.pdf","20:00", "15/08/2020"));
        mArrayList.add(new CustomDocumentModel("Théorie des physiques.pdf","20:00", "15/08/2020"));
        mArrayList.add(new CustomDocumentModel("Théorie des physiques.pdf","20:00", "15/08/2020"));
        mArrayList.add(new CustomDocumentModel("Théorie des physiques.pdf","20:00", "15/08/2020"));
        mArrayList.add(new CustomDocumentModel("Théorie des physiques.pdf","20:00", "15/08/2020"));
        mArrayList.add(new CustomDocumentModel("Théorie des physiques.pdf","20:00", "15/08/2020"));
        mArrayList.add(new CustomDocumentModel("Théorie des physiques.pdf","20:00", "15/08/2020"));
        mAdapter = new CustomDocumentAdapter (getContext(), R.layout.custom_list_item, mArrayList);
        mListView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {

            default: return false;
        }
    }
}