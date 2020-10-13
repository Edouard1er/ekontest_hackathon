package com.example.ekontest_hackathon;



import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class UserModel implements Parcelable {
    private String id;
    private PersonalInformationModel personalInformationModel;
    private AcademicInformationModel academicInformationModel;
    private ProfilModel profilModel;
    private AvisModel avisModel;
    private String TAG ="Inside User Class";

    private  int mData;

    private DatabaseReference databaseReference;



   /* public UserModel(String id, String imageUrl, String userName, String status) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.userName = userName;
        this.status=status;
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

    }
*/

    public UserModel(String id, PersonalInformationModel personalInformationModel,
                     AcademicInformationModel academicInformationModel) {
        this.id = id;
        this.personalInformationModel = personalInformationModel;
        this.academicInformationModel = academicInformationModel;
    }
    public UserModel(String id) {
        this.id = id;

    }

    public UserModel(String id, PersonalInformationModel personalInformationModel,
                     AcademicInformationModel academicInformationModel,
                     ProfilModel profilModel, AvisModel avisModel) {
        this.id = id;
        this.personalInformationModel = personalInformationModel;
        this.academicInformationModel = academicInformationModel;
        this.profilModel = profilModel;
        this.avisModel = avisModel;
    }



    public UserModel(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
    }

    public void InsertUsers(final PersonalInformationModel pModel,
                            final AcademicInformationModel aModel){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user.getUid()!= null) {
            UserModel model = new UserModel(user.getUid(), pModel, aModel);
            databaseReference.child(user.getUid()).setValue(model);
        }else{
            //Toast.makeText(, "", Toast.LENGTH_SHORT).show();
        }
    }
    public void InsertUsers(final PersonalInformationModel pModel,
                            final List<AcademicInformationModel> aModel, ProfilModel profilModel){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
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


    public void getUserNameAndImage(String id, final ImageView imageUser, final TextView name){
        DatabaseReference avisRef = FirebaseDatabase.getInstance().getReference("Users")
                .child(id);

        avisRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel model = snapshot.getValue(UserModel.class);
                name.setText(model.getPersonalInformationModel().getFirstname()+" "+model.getPersonalInformationModel().getLastname());
                final String[] url = new String[2];
                url[0] = model.getPersonalInformationModel().getImagelink();
                url[1] = model.getPersonalInformationModel().getImagename();
                UrlImageModel urlImageModel = new UrlImageModel();
                urlImageModel.getUrlImage(url,imageUser);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //Getter and Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PersonalInformationModel getPersonalInformationModel() {
        return personalInformationModel;
    }

    public void setPersonalInformationModel(PersonalInformationModel personalInformationModel) {
        this.personalInformationModel = personalInformationModel;
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
    private UserModel(Parcel in) {
        PersonalInformationModel loaderPersonal= new PersonalInformationModel();
        AcademicInformationModel loaderAcademic = new AcademicInformationModel();
        ProfilModel loaderProfil = new ProfilModel();
        AvisModel loaderAvis = new AvisModel();

        this.id = in.readString();
        this.personalInformationModel = in.readParcelable(getClass().getClassLoader());
        this.academicInformationModel = in.readParcelable(getClass().getClassLoader());
        this.profilModel = in.readParcelable(getClass().getClassLoader());
        this.avisModel = in.readParcelable(getClass().getClassLoader());
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<UserModel> CREATOR = new Parcelable.Creator<UserModel>() {
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };


}

