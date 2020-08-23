package com.example.ekontest_hackathon;

import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PersonalInformationModel {

        private String imagelink;
        private String firstname;
        private String lastname;
        private String sexe;
        private String email;
        private String phone;
        private String username;
        private String type;
        private String imagename;
        private DatabaseReference databaseReference;
        private FirebaseUser firebaseUser;




    public PersonalInformationModel(String imagelink, String imagename, String firstname,
                                    String lastname, String sexe, String email,
                                    String phone, String username, String type) {
        this.imagelink = imagelink;
        this.firstname = firstname;
        this.lastname = lastname;
        this.sexe = sexe;
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.type = type;
        this.imagename=imagename;
    }
    public  PersonalInformationModel (){

    }

    public void insertPersonalInformation(PersonalInformationModel model){
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(firebaseUser.getUid()).child("PersonalInformation").child(firebaseUser.getUid()).setValue(model)
        .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            }
        });



    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public String getImagelink() {
        return imagelink;
    }

    public void setImagelink(String imagelink) {
        this.imagelink = imagelink;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
