package com.example.ekontest_hackathon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class FreelancerModel extends StudentModel implements Parcelable {
    private String id;
    private PersonalInformationModel personalInformationModel;
    private AcademicInformationModel academicInformationModel;
    private ProfilModel profilModel;
    private AvisModel avisModel;
    private DatabaseReference databaseReference =FirebaseDatabase.getInstance().getReference("Users");;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public FreelancerModel(ProfilModel profilModel, AvisModel avisModel,AcademicInformationModel academicInformationModel) {
        super();
        this.academicInformationModel= academicInformationModel;
        this.profilModel = profilModel;
        this.avisModel = avisModel;
    }

    public FreelancerModel() {
        super();

    }

    public void InsertFreelancer(final PersonalInformationModel pModel,
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

    public AcademicInformationModel getAcademicInformationModel() {
        return academicInformationModel;
    }

    public void setAcademicInformationModel(AcademicInformationModel academicInformationModel) {
        this.academicInformationModel = academicInformationModel;
    }

    public ProfilModel getProfilModel() {
        return profilModel;
    }

    public void setProfilModel(ProfilModel profilModel) {
        this.profilModel = profilModel;
    }

    public AvisModel getAvisModel() {
        return avisModel;
    }

    public void setAvisModel(AvisModel avisModel) {
        this.avisModel = avisModel;
    }



    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public PersonalInformationModel getPersonalInformationModel() {
        return personalInformationModel;
    }

    @Override
    public void setPersonalInformationModel(PersonalInformationModel personalInformationModel) {
        this.personalInformationModel = personalInformationModel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeParcelable(this.personalInformationModel,flags);
        dest.writeParcelable(this.academicInformationModel,flags);
        dest.writeParcelable(this.profilModel,flags);
        dest.writeParcelable(this.avisModel,flags);
    }

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private FreelancerModel(Parcel in) {
        this.id= in.readString();
        this.personalInformationModel = in.readParcelable(getClass().getClassLoader());
        this.academicInformationModel = in.readParcelable(getClass().getClassLoader());
        this.profilModel = in.readParcelable(getClass().getClassLoader());
        this.avisModel = in.readParcelable(getClass().getClassLoader());


    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<FreelancerModel> CREATOR = new Parcelable.Creator<FreelancerModel>() {
        public FreelancerModel createFromParcel(Parcel in) {
            return new FreelancerModel(in);
        }

        public FreelancerModel[] newArray(int size) {
            return new FreelancerModel[size];
        }
    };

}
