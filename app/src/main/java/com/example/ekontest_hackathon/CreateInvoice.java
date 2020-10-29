package com.example.ekontest_hackathon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.EventListener;

public class CreateInvoice extends AppCompatActivity {

    EditText studentNameEdit, freelancePriceEdit, durationEdit, freelanceName;
    String receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_invoice);

        studentNameEdit = (EditText) findViewById(R.id.editTextStudentName);
        freelancePriceEdit = (EditText) findViewById(R.id.editTextFreelancePrice);
        durationEdit = (EditText) findViewById(R.id.editTextDuration);
        freelanceName = (EditText) findViewById(R.id.editTextFreelanceName);
        receiver = getIntent().getStringExtra("receiver");

        System.out.println("id receiver: " + receiver);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(receiver).child("personalInformationModel");
        System.out.println("Getting data...");
        UserModel userModel = new UserModel();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println(snapshot);
                PersonalInformationModel receiverInfo = snapshot.getValue(PersonalInformationModel.class);
                studentNameEdit.setText(receiverInfo.getLastname() + " " + receiverInfo.getFirstname());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void createInvoice(View view) {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        InvoiceModel model = new InvoiceModel("", user.getUid(), receiver, Integer.valueOf(freelancePriceEdit.getText().toString()), "New", Integer.valueOf(durationEdit.getText().toString()), freelanceName.getText().toString());
        model.insertInvoice(model);
        Toast.makeText(getApplicationContext(), "Invoice created.", Toast.LENGTH_LONG).show();
        finish();
    }
}