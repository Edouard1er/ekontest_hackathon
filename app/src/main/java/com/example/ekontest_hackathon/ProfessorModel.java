package com.example.ekontest_hackathon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class ProfessorModel extends FreelancerModel implements Parcelable {
    private DatabaseReference databaseReference;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public ProfessorModel() {
        super();
    }

    public void InsertProfessor(final PersonalInformationModel pModel,
                                 final List<AcademicInformationModel> aModel, ProfilModel profilModel){
        if(user.getUid()!= null) {
            UserModel model = new UserModel(user.getUid());
            databaseReference.child(user.getUid()).setValue(model);
            //Insert personnel data
            PersonalInformationModel personal= new PersonalInformationModel();
            personal.insertPersonalInformation(pModel);
            //Insert academic data
            AcademicInformationModel academic= new AcademicInformationModel();
            academic.insertAcademicInformation(aModel);

            //Insert profil
            ProfilModel profilModel1 = new ProfilModel();
            profilModel1.InsertProfil(profilModel);
        }else{
            //Toast.makeText(, "", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private ProfessorModel(Parcel in) {


    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<ProfessorModel> CREATOR = new Parcelable.Creator<ProfessorModel>() {
        public ProfessorModel createFromParcel(Parcel in) {
            return new ProfessorModel(in);
        }

        public ProfessorModel[] newArray(int size) {
            return new ProfessorModel[size];
        }
    };

}
