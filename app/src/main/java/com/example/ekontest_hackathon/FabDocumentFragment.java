package com.example.ekontest_hackathon;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FabDocumentFragment extends Fragment implements PopupMenu.OnMenuItemClickListener{
    FloatingActionButton mFloatingActionButton;
    String userType="teacher";
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private String m_Text = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_fab_document, container, false);
        mFloatingActionButton=view.findViewById(R.id.fab_document);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                FirebaseUser user_ = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(user_.getUid()).child("personalInformationModel");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        PersonalInformationModel model = snapshot.getValue(PersonalInformationModel.class);
                        if(model.getType().equals("Student")) {
                            Toast.makeText(getContext(), "Buy a document", Toast.LENGTH_LONG).show();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Type the document code");

                            // Set up the input
                            final EditText input = new EditText(getContext());
                            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
                            builder.setView(input);

                            // Set up the buttons
                            builder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    m_Text = input.getText().toString();
                                    submitDocumentSearch(m_Text);
                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            builder.show();
                        } else {
                            setFab_available_document(v);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        return view;
    }
    public void setFab_available_document(View view){
        final PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.setOnMenuItemClickListener(this);

        FirebaseUser user_ = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(user_.getUid()).child("personalInformationModel");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PersonalInformationModel model = snapshot.getValue(PersonalInformationModel.class);
                if(model.getType().equals("Freelancer")) {
                    popupMenu.inflate(R.menu.menu_fab_document_freelancer);
                }
                if(model.getType().equals("Professor")) {
                    popupMenu.inflate(R.menu.menu_fab_document);
                }
                popupMenu.show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.upload_free_document: {
                Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), UploadNormalDocument.class);
                startActivity(intent);
                return true;
            }
            case R.id.upload_payed_document: {
                Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_LONG).show();
                Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), UploadPaidDocument.class);
                startActivity(intent);
                return true;
            }
            case R.id.buy_document: {
                Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Type the document code");

                // Set up the input
                final EditText input = new EditText(getContext());
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        submitDocumentSearch(m_Text);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
                return true;
            }
            default: return false;
        }
    }

    public void submitDocumentSearch(final String code) {
        Toast.makeText(getContext(), "Code: " + code, Toast.LENGTH_LONG).show();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Documents").child("PaidDoc");
        System.out.println("Getting data...");
        final PaidDocModel[] model_ = new PaidDocModel[1];
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("Inside response...");
//                System.out.println(snapshot);
                boolean found = false;
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    PaidDocModel model = dataSnapshot.getValue(PaidDocModel.class);
                    if(model.getSharingCode().equals(code)) {
                        found = true;
                        model_[0] = model;
                    }
                }
                if(found) {
                    System.out.println("Document found");
                    Intent intent = new Intent(getContext(), MonCash.class);
                    intent.putExtra("receiver", user.getUid());
                    intent.putExtra("sender", model_[0].getIdUser());
                    intent.putExtra("idDocument", model_[0].getIdDocument());
                    intent.putExtra("transaction", "document");
                    startActivity(intent);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Error code");
                    builder.setMessage("Sorry we couldn't find your document.");
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}