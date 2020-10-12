package com.example.ekontest_hackathon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class FreelancerOnclickActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelancer_onclick);


    }
    public Bundle getFreelancerData(){
        String firstname = getIntent().getStringExtra("firstname");
        String lastname = getIntent().getStringExtra("lastname");
        String sexe = getIntent().getStringExtra("sexe");
        String imagelink = getIntent().getStringExtra("imagelink");
        String imagename = getIntent().getStringExtra("imagename");
        String nCours = getIntent().getStringExtra("nCours");
        String nEtudiant = getIntent().getStringExtra("nEtudiants");
        String idFreelancer = getIntent().getStringExtra("idFreelancer");



        //UserModel model = (UserModel) getIntent().getParcelableExtra("model");

        Bundle fd = new Bundle();
        fd.putString("firstname", firstname);
        fd.putString("lastname", lastname);
        fd.putString("sexe", sexe);
        fd.putString("imagelink", imagelink);
        fd.putString("imagename", imagename);
        fd.putString("nCours", nCours);
        fd.putString("nEtudiants", nEtudiant);
        fd.putString("idFreelancer", idFreelancer);


        return fd;
    }

}