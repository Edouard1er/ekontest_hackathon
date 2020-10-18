package com.example.ekontest_hackathon.ui.account;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ekontest_hackathon.AcademicInformationAdapter;
import com.example.ekontest_hackathon.AcademicInformationModel;
import com.example.ekontest_hackathon.PersonalInformationModel;
import com.example.ekontest_hackathon.R;
import com.example.ekontest_hackathon.UserAdapter;
import com.example.ekontest_hackathon.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private AcademicInformationAdapter adapter;

    List<AcademicInformationModel> mAcademic=new ArrayList<>();
    AcademicInformationModel academicInformationModel;
    RecyclerView recyclerView;
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    TextView nom, prenom, sexe, email, phone, username,account;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_account, container, false);
        nom = (TextView) view.findViewById(R.id.textViewNom);
        prenom = (TextView) view.findViewById(R.id.textViewPrenom);
        sexe = (TextView) view.findViewById(R.id.textViewSexe);
        email = (TextView) view.findViewById(R.id.textViewEmail);
        phone = (TextView) view.findViewById(R.id.textViewPhone);
        username = (TextView) view.findViewById(R.id.textViewUsername);
        account = (TextView) view.findViewById(R.id.textViewAccountType);
        getAcademicData(view);
        getPersonalData();
        return view;
    }
    public void getPersonalData(){
        DatabaseReference academicRef = FirebaseDatabase.getInstance().getReference("Users")
                .child(user.getUid()).child("personalInformationModel");


        academicRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PersonalInformationModel model = snapshot.getValue(PersonalInformationModel.class);
                try {
                    nom.setText(model.getFirstname());
                    prenom.setText(model.getLastname());
                    sexe.setText(model.getSexe());
                    email.setText(model.getEmail());
                    phone.setText(model.getPhone());
                    username.setText(model.getUsername());
                    account.setText(model.getType());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void getAcademicData(View v){
       /* List <AcademicInformationModel> list = new ArrayList<>();
        AcademicInformationModel m = new AcademicInformationModel("Universite", "UEH", "FDS",

                "Master", "2020", "2022");
        list.add(m);
        m.insertAcademicInformation(list);*/


        recyclerView = v.findViewById(R.id.list_info_academic);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DatabaseReference academicRef = FirebaseDatabase.getInstance().getReference("Users")
                .child(user.getUid()).child("academicInformationModel");


        academicRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mAcademic.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    academicInformationModel = dataSnapshot.getValue(AcademicInformationModel.class);

                    mAcademic.add(academicInformationModel);

                }
                adapter =new AcademicInformationAdapter(getContext(),mAcademic);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}