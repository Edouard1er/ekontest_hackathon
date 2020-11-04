package com.example.ekontest_hackathon;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;


import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

class CustomDocumentAdapter extends ArrayAdapter  implements  PopupMenu.OnMenuItemClickListener{
    Context mContext;
    String name;
    ArrayList<CustomDocumentModel> mArrayList;
    int tabPosition;
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public CustomDocumentAdapter(@NonNull Context context, int resource, ArrayList<CustomDocumentModel> mArrayList) {
        super(context, resource, mArrayList);
        this.mContext=context;
        this.mArrayList=mArrayList;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater =(LayoutInflater)mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        convertView= layoutInflater.inflate(R.layout.custom_list_item, parent, false);
        final MyViewHolder myViewHolder = new MyViewHolder(convertView);
        myViewHolder.documentName.setText(mArrayList.get(position).getDocumentName());
        myViewHolder.documentTime.setText(""+mArrayList.get(position).getTimeUploadedDocument());
        myViewHolder.documentStatus.setText(""+mArrayList.get(position).getDocumentStatus());
       // myViewHolder.mImageView.setImageResource(mArrayList.get(position).getIcon());
//        myViewHolder.mDate.setText(mArrayList.get(position).getDateUploadedDocument());
        View view = layoutInflater.inflate(R.layout.fragment_document, parent,false);
        TabLayout tabLayout = view.findViewById(R.id.id_tab_layout);
        tabPosition = tabLayout.getSelectedTabPosition();
        myViewHolder.contextMenuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                available_document(v);
            }
        });

        return convertView;
    }
    public void available_document(View view){
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.menu_purchased_availabledocument);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_purchavala_document: {
//                Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage("Do you really want to remove this document?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                final CustomDocumentModel model = mArrayList.get(tabPosition);
                                if(model.getTab().equals("Paid")) {
                                    System.out.println("Paid tab");
                                    //check if the document exist for another user first
                                }

                                if(model.getTab().equals("Purchased")){
                                    System.out.println("Purchased tab");
                                    System.out.println("id user: " + user.getUid());
                                    System.out.println("id doc: " + model.getId());
                                    FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("docPurchased").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                                                String docKey = (String) dataSnapshot.getValue();
                                                if(docKey.equals(model.getId())) {
                                                    FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("docPurchased").child(dataSnapshot.getKey()).removeValue();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }

                                if(model.getTab().equals("Available")) {
                                    System.out.println("Available tab");
                                    System.out.println("id user: " + user.getUid());
                                    System.out.println("id doc: " + model.getId());
                                    FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("docAvailable").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                                                String docKey = (String) dataSnapshot.getValue();
                                                if(docKey.equals(model.getId())) {
                                                    FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("docAvailable").child(dataSnapshot.getKey()).removeValue();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }

                                if(model.getTab().equals("Uploaded")) {
                                    System.out.println("Uploaded tab");
                                }

                                Toast.makeText(getContext(), "Document deleted successfully.", Toast.LENGTH_LONG).show();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
                return true;
            }
            case R.id.info_document: {
//                Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_LONG).show();
                System.out.println("detail: " + tabPosition);
                CustomDocumentModel model = mArrayList.get(tabPosition);
                if(model.getTab().equals("Paid") || model.getTab().equals("Purchased")) {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Documents").child("PaidDoc").child(model.getId());
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            PaidDocModel mod = snapshot.getValue(PaidDocModel.class);
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Type the document code");

                            builder.setMessage("Document code: " + mod.getSharingCode());

                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            builder.show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Documents").child("NormalDoc").child(model.getId());
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            NormalDocModel mod = snapshot.getValue(NormalDocModel.class);
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Type the document code");

                            builder.setMessage("Document Title: " + mod.getTitle());

                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            builder.show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                return true;
            }

            default: return false;
        }
    }
}
