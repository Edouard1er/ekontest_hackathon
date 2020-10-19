package com.example.ekontest_hackathon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ekontest_hackathon.ui.NavDrawerActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AccountActivity extends AppCompatActivity {
    private AcademicInformationAdapter adapter;
    List<AcademicInformationModel> mAcademic=new ArrayList<>();
    ArrayList<PersonalInformationModel> personelList;

    AcademicInformationModel academicInformationModel;
    RecyclerView recyclerView;
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    TextView nom, prenom, sexe, email, phone, username,account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        nom = (TextView) findViewById(R.id.textViewNom);
        prenom = (TextView) findViewById(R.id.textViewPrenom);
        sexe = (TextView) findViewById(R.id.textViewSexe);
        email = (TextView) findViewById(R.id.textViewEmail);
        phone = (TextView) findViewById(R.id.textViewPhone);
        username = (TextView) findViewById(R.id.textViewUsername);
        account = (TextView) findViewById(R.id.textViewAccountType);
        personelList=new ArrayList<>();
        getAcademicData();
        getPersonalData();
    }
    public void getPersonalData(){
        DatabaseReference academicRef = FirebaseDatabase.getInstance().getReference("Users")
                .child(user.getUid()).child("personalInformationModel");


        academicRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PersonalInformationModel model = snapshot.getValue(PersonalInformationModel.class);
                personelList.add(model);
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
    public void getAcademicData(){
       /* List <AcademicInformationModel> list = new ArrayList<>();
        AcademicInformationModel m = new AcademicInformationModel("Universite", "UEH", "FDS",

                "Master", "2020", "2022");
        list.add(m);
        m.insertAcademicInformation(list);*/


        recyclerView = findViewById(R.id.list_info_academic);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
                adapter =new AcademicInformationAdapter(getApplicationContext(),mAcademic);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void addAcademicFormation(View view){
        Intent intent = new Intent(getApplicationContext(), AcademicActivity.class);
        intent.putExtra("idUser",user.getUid());
        // intent.putExtra("model",(Parcelable) model);
         startActivity(intent);
    }
    public void editType(View view){
        Intent intent = new Intent(getApplicationContext(), TypeAccount.class);

        intent.putExtra("idUser",user.getUid());
        // intent.putExtra("model",(Parcelable) model);
        startActivity(intent);
    }
    public void updatePersonalData(View view){
        Intent intent = new Intent(getApplicationContext(), PersonalInformationActivity.class);

        intent.putParcelableArrayListExtra("personnel",personelList);
        intent.putExtra("idUser",user.getUid());
        // intent.putExtra("model",(Parcelable) model);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, NavDrawerActivity.class);
        startActivity(intent);
        finish();
    }
}