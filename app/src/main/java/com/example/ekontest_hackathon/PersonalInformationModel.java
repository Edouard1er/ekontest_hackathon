package com.example.ekontest_hackathon;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class PersonalInformationModel  implements Parcelable {

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
    public PersonalInformationModel(String firstname,
                                    String lastname, String sexe, String email,
                                    String phone, String username) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.sexe = sexe;
        this.email = email;
        this.phone = phone;
        this.username = username;
    }
    public PersonalInformationModel( String firstname,
                                    String lastname, String sexe, String email,
                                    String phone, String username, String type) {

        this.firstname = firstname;
        this.lastname = lastname;
        this.sexe = sexe;
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.type = type;

    }
    public  PersonalInformationModel (){

    }


    public void insertPersonalInformation(PersonalInformationModel model){
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(firebaseUser.getUid()).child("personalInformationModel").setValue(model)
        .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            }
        });
    }
    public void updateType(String type){
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(firebaseUser.getUid()).child("personalInformationModel")
                .child(type).setValue(type)
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.firstname);
        dest.writeString(this.lastname);
        dest.writeString(this.sexe);
        dest.writeString(this.email);
        dest.writeString(this.phone);
        dest.writeString(this.username);
        dest.writeString(this.type);
        dest.writeString(this.imagelink);
        dest.writeString(this.imagename);


    }
    public PersonalInformationModel(Parcel in) {
        this.firstname = in.readString();
        this.lastname = in.readString();
        this.sexe = in.readString();
        this.email = in.readString();
        this.phone = in.readString();
        this.username = in.readString();
        this.type = in.readString();
        this.imagelink=in.readString();
        this.imagename=in.readString();
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public PersonalInformationModel createFromParcel(Parcel in) {
            return new PersonalInformationModel(in);
        }

        public PersonalInformationModel[] newArray(int size) {
            return new PersonalInformationModel[size];
        }
    };
}
