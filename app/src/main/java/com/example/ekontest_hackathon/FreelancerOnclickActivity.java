package com.example.ekontest_hackathon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class FreelancerOnclickActivity extends AppCompatActivity {
    String nCours, nEtudiant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelancer_onclick);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AvisModel avisModel = new AvisModel();
        //avisModel.InsertAvis(user.getUid(),"C'est un bon professeur extraordinaire",3);


    }
    public Bundle getFreelancerData(){
        Bundle fd=new Bundle();
        ArrayList <FreelancerModel> userModels;
        try {
            userModels = getIntent().getParcelableArrayListExtra("freelancer");
            fd.putParcelableArrayList("freelancer", userModels);
        }catch (Exception e){
            e.printStackTrace();
        }
       // Toast.makeText(this, "Trying something :"+userModels.get(0).getPersonalInformationModel().getLastname(), Toast.LENGTH_SHORT).show();



        return fd;
    }

}