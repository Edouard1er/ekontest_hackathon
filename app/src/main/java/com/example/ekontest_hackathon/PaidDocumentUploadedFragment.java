package com.example.ekontest_hackathon;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PaidDocumentUploadedFragment extends Fragment {
    ArrayList mArrayList;
    ListView mListView;
    CustomDocumentAdapter mAdapter;
    private DatabaseReference databaseReference ;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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

        databaseReference = FirebaseDatabase.getInstance().getReference("Documents").child("PaidDoc");
        System.out.println("Getting data...");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("Inside response...");
//                System.out.println(snapshot);
                mArrayList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    PaidDocModel model = dataSnapshot.getValue(PaidDocModel.class);
                    System.out.println(dataSnapshot);
                    System.out.println("Document date: " + model.getDateAdded());

                    String date = model.getDateAdded();
                    String[] parts = date.split(" ");
                    String date_ = parts[0];
                    String time_ = parts[1];
                    System.out.println("Date: " + parts[0]);
                    System.out.println("Time: " + parts[1]);
                    mArrayList.add(new CustomDocumentModel(model.getTitle(),date_, time_, model.getIdDocument(), model.getStatus()));
                }
                mAdapter = new CustomDocumentAdapter (getContext(), R.layout.custom_list_item, mArrayList);
                mListView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}