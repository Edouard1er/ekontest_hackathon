package com.example.ekontest_hackathon;

import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ekontest_hackathon.ui.NavDrawerActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference freelancerRef= FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("docPurchased");
        // Query query = freelancerRef.equalTo("personalInformationModel").equalTo("Student","type");
        freelancerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mArrayList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    System.out.println(dataSnapshot);
                    String idDoc = (String) dataSnapshot.getValue();
                    System.out.println("id doc: " + dataSnapshot.getValue());
                    System.out.println("id doc__: " + idDoc);
                    DatabaseReference docs = FirebaseDatabase.getInstance().getReference("Documents").child("PaidDoc").child(idDoc);
                    docs.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            PaidDocModel model = snapshot.getValue(PaidDocModel.class);
                            System.out.println(snapshot);

                            String date = model.getDateAdded();
                            String[] parts = date.split(" ");
                            String date_ = parts[0];
                            String time_ = parts[1];

                            mArrayList.add(new CustomDocumentModel(model.getTitle(),time_, time_, model.getIdDocument(), date_, model.getFileName()));
                            mAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
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