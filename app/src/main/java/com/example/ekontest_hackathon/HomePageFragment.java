package com.example.ekontest_hackathon;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class HomePageFragment extends Fragment {
    ListView mListView;
    CustomHomePageAdapter mAdapter;
    ArrayList mArrayList;
    TextView textHome;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_home_page, container, false);
        mListView = view.findViewById(R.id.listview_home);
        textHome = view.findViewById(R.id.textViewHome);
        mArrayList = new ArrayList<CustomHomePageModel>();
        SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault());

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        textHome.setVisibility(View.GONE);
        FirebaseDatabase.getInstance().getReference("Invoice").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                        InvoiceModel inv = dataSnapshot.getValue(InvoiceModel.class);
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
                                    mArrayList.add(new CustomHomePageModel(finalDateToShow,nom,
                                            name,montant +" HTG"  ,"HTG " + rate +" /Day"));
                                    mAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                    if(getActivity() != null) {
                            mAdapter = new CustomHomePageAdapter(getContext(),R.layout.homepage_custom_list, mArrayList);
                            mListView.setAdapter(mAdapter);
                    }
                } else {
                    textHome.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        mArrayList.add(new CustomHomePageModel("09/09/2020 - 10/10/2020","Edouard Chevenslove",
//                "Math","HTG 600.50","HTG 70.24 /hour"));
//        mArrayList.add(new CustomHomePageModel("09/09/2020 - 10/10/2020","Edouard Chevenslove",
//                "Math","HTG 600.50","HTG 70.24 /hour"));
//        mArrayList.add(new CustomHomePageModel("09/09/2020 - 10/10/2020","Edouard Chevenslove",
//                "Math","HTG 600.50","HTG 70.24 /hour"));
//        mArrayList.add(new CustomHomePageModel("09/09/2020 - 10/10/2020","Edouard Chevenslove",
//                "Math","HTG 600.50","HTG 70.24 /hour"));
//        mArrayList.add(new CustomHomePageModel("09/09/2020 - 10/10/2020","Edouard Chevenslove",
//                "Math","HTG 600.50","HTG 70.24 /hour"));
//        mArrayList.add(new CustomHomePageModel("09/09/2020 - 10/10/2020","Edouard Chevenslove",
//                "Math","HTG 600.50","HTG 70.24 /hour"));
//        mArrayList.add(new CustomHomePageModel("09/09/2020 - 10/10/2020","Edouard Chevenslove",
//                "Math","HTG 600.50","HTG 70.24 /hour"));
//        mArrayList.add(new CustomHomePageModel("09/09/2020 - 10/10/2020","Edouard Chevenslove",
//                "Math","HTG 600.50","HTG 70.24 /hour"));
//        mArrayList.add(new CustomHomePageModel("09/09/2020 - 10/10/2020","Edouard Chevenslove",
//                "Math","HTG 600.50","HTG 70.24 /hour"));
//        mArrayList.add(new CustomHomePageModel("09/09/2020 - 10/10/2020","Edouard Chevenslove",
//                "Math","HTG 600.50","HTG 70.24 /hour"));
        return view;
    }
}