package com.example.ekontest_hackathon;

import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AcademicInformationModel {
    private String level;
    private String institution;
    private String faculte;
    private String degree;
    private String startDate;
    private String endDate;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;

    public AcademicInformationModel(String level, String institution, String faculte,

                                    String degree, String startDate, String endDate) {
        this.level = level;
        this.institution = institution;
        this.faculte = faculte;
        this.degree = degree;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public AcademicInformationModel (){

    }
    public void insertAcademicInformation(AcademicInformationModel model){
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(firebaseUser.getUid()).child("AcademicInformation").push().setValue(model)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                });



    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getFaculte() {
        return faculte;
    }

    public void setFaculte(String faculte) {
        this.faculte = faculte;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
