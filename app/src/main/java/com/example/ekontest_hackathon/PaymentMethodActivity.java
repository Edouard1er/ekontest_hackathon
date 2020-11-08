package com.example.ekontest_hackathon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.ekontest_hackathon.ui.NavDrawerActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class PaymentMethodActivity extends AppCompatActivity {
    ListView mListView;
    CustomHomePageAdapter mAdapter;
    ArrayList mArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        mListView = (ListView) findViewById(R.id.listview_home);
        mArrayList = new ArrayList<CustomHomePageModel>();
        SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault());

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance().getReference("Invoice").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                        final InvoiceModel inv = dataSnapshot.getValue(InvoiceModel.class);
                        if(inv.getSenderId().equals(user.getUid())) {
                            final int montant = inv.getAmount();
                            final String name = inv.getFreelanceName();
                            String date = inv.getDate();
                            int duree = inv.getDuration();
                            final int rate = montant / duree;
                            String dateToShow = "";

                            Calendar cal = Calendar.getInstance();
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy", Locale.ENGLISH);
                            try {
                                cal.setTime(sdf.parse(date));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dateToShow += sdf.format(cal.getTime());
                            cal.add(Calendar.DAY_OF_YEAR, duree);
                            dateToShow += " - " + sdf.format(cal.getTime());

                            System.out.println("Current time => " + sdf.format(cal.getTime()));
                            String idReceiver = inv.getReceiverId();
                            final String finalDateToShow = dateToShow;
                            FirebaseDatabase.getInstance().getReference("Users").child(idReceiver).child("personalInformationModel").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    PersonalInformationModel infMod = snapshot.getValue(PersonalInformationModel.class);
                                    String nom = infMod.getFirstname() + " " + infMod.getLastname();
                                    if(inv.getStatus().equals("Paid")) {
                                        mArrayList.add(new CustomHomePageModel(finalDateToShow,nom,
                                                name,montant +" HTG"  ,"HTG " + rate +" /Day"));
                                    } else {
                                        mArrayList.add(new CustomHomePageModel("-",nom,
                                                name,montant +" HTG"  ,"HTG " + "-" +" /Day"));
                                    }

                                    mAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                    if(getApplicationContext() != null) {
                        mAdapter = new CustomHomePageAdapter(getApplicationContext(),R.layout.homepage_custom_list, mArrayList);
                        mListView.setAdapter(mAdapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, NavDrawerActivity.class);
        startActivity(intent);
        finish();
    }
}