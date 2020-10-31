package com.example.ekontest_hackathon;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class FreelancerModel extends StudentModel implements Parcelable {
    private String id;
    private PersonalInformationModel personalInformationModel;
    private AcademicInformationModel academicInformationModel;
    private AvisModel avisModel;
    private DatabaseReference databaseReference ;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public FreelancerModel(AvisModel avisModel,AcademicInformationModel academicInformationModel) {
        super();
        this.academicInformationModel= academicInformationModel;
        this.avisModel = avisModel;
    }

    public FreelancerModel() {
        super();

    }

    public void InsertFreelancer(final PersonalInformationModel pModel,
                            final List<AcademicInformationModel> aModel){
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
           // ProfilModel profilModel1 = new ProfilModel();
            //profilModel1.InsertProfil(profilModel);
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



    public AvisModel getAvisModel() {
        return avisModel;
    }

    public void setAvisModel(AvisModel avisModel) {
        this.avisModel = avisModel;
    }
    public void addFreelancerStudent(String idStudent){
        databaseReference =FirebaseDatabase.getInstance().getReference("Users")
        .child(user.getUid()).child("Students");
        databaseReference.child(idStudent).setValue(idStudent);
    }
    public void getFreelancerStudentQuantity(String idFreelancer, final TextView textView){
        databaseReference =FirebaseDatabase.getInstance().getReference("Users")
                .child(idFreelancer).child("Students");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int countId=0;
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    countId+=1;

                }
                textView.setText(""+countId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void addFreelancerToFavorite(String idFreelancer){
        databaseReference =FirebaseDatabase.getInstance().getReference("Users")
                .child(user.getUid())
                .child("FavoriteFreelancer");
        databaseReference.child(idFreelancer).setValue(idFreelancer).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
    }
    public void removeFreelancerToFavorite(String idFreelancer){
        databaseReference =FirebaseDatabase.getInstance().getReference("Users")
                .child(user.getUid())
                .child("FavoriteFreelancer");
        databaseReference.child(idFreelancer).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
    }
    public void loadFavoritePicture(String idFreelancer, final ImageView add, final ImageView remove, final Context context){
        databaseReference =FirebaseDatabase.getInstance().getReference("Users")
                .child(user.getUid())
                .child("FavoriteFreelancer");
        databaseReference.child(idFreelancer)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            //Toast.makeText(context, "What is wrong", Toast.LENGTH_SHORT).show();
                            add.setVisibility(View.GONE);
                            remove.setVisibility(View.VISIBLE);
                            try{
                                Glide.with(context)
                                        .load(R.drawable.ic_remove_fav)
                                        .centerCrop()
                                        .into(remove);
                            }catch(Exception e){

                            }



                        }else{
                            add.setVisibility(View.VISIBLE);
                            remove.setVisibility(View.GONE);
                            try{
                                Glide.with(context)
                                        .load(R.drawable.ic_add_fav)
                                        .centerCrop()
                                        .into(add);
                            }catch(Exception e){

                            }




                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
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
       // dest.writeParcelable(this.profilModel,flags);
        dest.writeParcelable(this.avisModel,flags);
    }

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private FreelancerModel(Parcel in) {
        this.id= in.readString();
        this.personalInformationModel = in.readParcelable(getClass().getClassLoader());
        this.academicInformationModel = in.readParcelable(getClass().getClassLoader());
        //this.profilModel = in.readParcelable(getClass().getClassLoader());
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
