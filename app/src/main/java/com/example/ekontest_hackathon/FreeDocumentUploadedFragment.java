package com.example.ekontest_hackathon;

import android.content.Intent;
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

public class FreeDocumentUploadedFragment extends Fragment {
    ListView mListView;
    CustomDocumentAdapter mAdapter;
    ArrayList mArrayList;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference databaseReference ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_free_document_uploaded, container, false);
        mArrayList = new ArrayList<CustomDocumentModel>();
        mListView = (ListView) view.findViewById(R.id.uploaded_list_free_document);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int position = i;
                CustomDocumentModel value = (CustomDocumentModel) mArrayList.get(position);
                Toast.makeText(getContext(), position + "- Id: " + value.getId(), Toast.LENGTH_LONG).show();
                System.out.println("About to open document: " + value.getId());
                Intent intent = new Intent(view.getContext(), PdfView.class);
                intent.putExtra("fileName", value.getFileName());
                startActivity(intent);
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("Documents").child("NormalDoc");
        System.out.println("Getting data...");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("Inside response...");
                mArrayList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    NormalDocModel model = dataSnapshot.getValue(NormalDocModel.class);
                    System.out.println(dataSnapshot);
                    System.out.println("Document date: " + model.getDateAdded());

                    String date = model.getDateAdded();
                    String[] parts = date.split(" ");
                    String date_ = parts[0];
                    String time_ = parts[1];
                    System.out.println("Date: " + parts[0]);
                    System.out.println("Time: " + parts[1]);
                    if(model.getIdUser().equals(user.getUid())) {
                        mArrayList.add(new CustomDocumentModel(model.getTitle(),date_, time_, model.getIdDocument(), "Accepted", model.getFileName()));
                    }
                }
                if(getActivity()!=null) {
                    mAdapter = new CustomDocumentAdapter (getContext(), R.layout.custom_list_item, mArrayList);
                    mListView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}