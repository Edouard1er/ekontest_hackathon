package com.example.ekontest_hackathon;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AcademicInformationModel  implements Parcelable {
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

    public void insertAcademicInformation(List<AcademicInformationModel> model){
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        for(int i=0; i<model.size(); i++) {
            databaseReference.child(firebaseUser.getUid()).child("academicInformationModel").push().setValue(model.get(i))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                        }
                    });
        }
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.level);
        dest.writeString(this.institution);
        dest.writeString(this.faculte);
        dest.writeString(this.degree);
        dest.writeString(this.startDate);
        dest.writeString(this.endDate);
    }
    public AcademicInformationModel(Parcel in) {
        this.level = in.readString();
        this.institution = in.readString();
        this.faculte = in.readString();
        this.degree = in.readString();
        this.startDate = in.readString();
        this.endDate = in.readString();

    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public AcademicInformationModel createFromParcel(Parcel in) {
            return new AcademicInformationModel(in);
        }

        public AcademicInformationModel[] newArray(int size) {
            return new AcademicInformationModel[size];
        }
    };
}
