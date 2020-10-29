package com.example.ekontest_hackathon;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class PaidDocumentUploadedFragment extends Fragment {
    ArrayList mArrayList;
    ListView mListView;
    CustomDocumentAdapter mAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_paid_document_uploaded, container, false);
        mArrayList = new ArrayList<CustomDocumentModel>();
       // model.getTitle(),date_, time_, model.getIdDocument(), "Accepted")
        mListView = (ListView) view.findViewById(R.id.uploaded_list_paid_document);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int position = i;
                CustomDocumentModel value = (CustomDocumentModel) mArrayList.get(position);
                Toast.makeText(getContext(), position + "- Id: " + value.getId(), Toast.LENGTH_LONG).show();
            }
        });
        mArrayList.add(new CustomDocumentModel("math.pdf","12:00", "22/10/2020"
        ,"2","Refused"));
        mArrayList.add(new CustomDocumentModel("math.pdf","12:00", "22/10/2020"
                ,"3","Accepted"));
        mArrayList.add(new CustomDocumentModel("math.pdf","12:00", "22/10/2020"
                ,"4","Refused"));
        mAdapter = new CustomDocumentAdapter (getContext(), R.layout.custom_list_item, mArrayList);
        mListView.setAdapter(mAdapter);
        return view;
    }
}