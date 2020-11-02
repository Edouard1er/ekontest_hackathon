package com.example.ekontest_hackathon;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class DocumentAvailableFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {
    ListView mListView;
    CustomDocumentAdapter mAdapter;
    ArrayList mArrayList;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference databaseReference ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_document_available, container, false);
        mArrayList = new ArrayList<CustomDocumentModel>();
        mListView = (ListView) view.findViewById(R.id.available_list_document);

        final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference freelancerRef= FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("docAvailable");
        freelancerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mArrayList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    System.out.println(dataSnapshot);
                    String idDoc = (String) dataSnapshot.getValue();
                    System.out.println("id doc: " + dataSnapshot.getValue());
                    System.out.println("id doc__: " + idDoc);
                    DatabaseReference docs = FirebaseDatabase.getInstance().getReference("Documents").child("NormalDoc").child(idDoc);
                    docs.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            NormalDocModel model = snapshot.getValue(NormalDocModel.class);
                            System.out.println(snapshot);

                            String date = model.getDateAdded();
                            String[] parts = date.split(" ");
                            String date_ = parts[0];
                            String time_ = parts[1];

                            mArrayList.add(new CustomDocumentModel(model.getTitle(),time_, time_, model.getIdDocument(), date_));
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

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {

            default: return false;
        }
    }
}