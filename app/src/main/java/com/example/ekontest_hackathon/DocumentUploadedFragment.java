package com.example.ekontest_hackathon;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class DocumentUploadedFragment extends Fragment {
    ListView mListView;
    CustomDocumentAdapter mAdapter;
    ArrayList mArrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_document_uploaded, container, false);
        mArrayList = new ArrayList<CustomDocumentModel>();
        mListView = (ListView) view.findViewById(R.id.uploaded_list_document);
        mArrayList.add(new CustomDocumentModel("Théorie de la Chimie.pdf","15:00", "15/08/2020"));
        mArrayList.add(new CustomDocumentModel("Théorie de la Chimie.pdf","15:00", "15/08/2020"));
        mArrayList.add(new CustomDocumentModel("Théorie de la Chimie.pdf","15:00", "15/08/2020"));
        mArrayList.add(new CustomDocumentModel("Théorie de la Chimie.pdf","15:00", "15/08/2020"));
        mArrayList.add(new CustomDocumentModel("Théorie de la Chimie.pdf","15:00", "15/08/2020"));
        mArrayList.add(new CustomDocumentModel("Théorie de la Chimie.pdf","15:00", "15/08/2020"));
        mArrayList.add(new CustomDocumentModel("Théorie de la Chimie.pdf","15:00", "15/08/2020"));
        mArrayList.add(new CustomDocumentModel("Théorie de la Chimie.pdf","15:00", "15/08/2020"));
        mArrayList.add(new CustomDocumentModel("Théorie de la Chimie.pdf","15:00", "15/08/2020"));
        mArrayList.add(new CustomDocumentModel("Théorie de la Chimie.pdf","15:00", "15/08/2020"));
        mArrayList.add(new CustomDocumentModel("Théorie de la Chimie.pdf","15:00", "15/08/2020"));
        mArrayList.add(new CustomDocumentModel("Théorie de la Chimie.pdf","15:00", "15/08/2020"));
        mArrayList.add(new CustomDocumentModel("Théorie de la Chimie.pdf","15:00", "15/08/2020"));
        mArrayList.add(new CustomDocumentModel("Théorie de la Chimie.pdf","15:00", "15/08/2020"));
        mArrayList.add(new CustomDocumentModel("Théorie de la Chimie.pdf","15:00", "15/08/2020"));
        mArrayList.add(new CustomDocumentModel("Théorie de la Chimie.pdf","15:00", "15/08/2020"));
        mArrayList.add(new CustomDocumentModel("Théorie de la Chimie.pdf","15:00", "15/08/2020"));
        mAdapter = new CustomDocumentAdapter (getContext(), R.layout.custom_list_item, mArrayList);
        mListView.setAdapter(mAdapter);
        return view;
    }
}