package com.example.ekontest_hackathon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

public class CoursModel implements Parcelable {
    private String idCours;
    private String cours;
    private int nEtudiant;
    private AvisModel avisModel;
    DatabaseReference databaseReference;
    FirebaseUser cUser= FirebaseAuth.getInstance().getCurrentUser();


    public CoursModel(String idCours, String cours,int nEtudiant, AvisModel avisModel) {
        this.idCours = idCours;
        this.cours = cours;
        this.avisModel = avisModel;
        this.nEtudiant=nEtudiant;
    }
    public CoursModel(){}
    //insert Cours
    public void InsertCours(String id,String cours, int nEtudiant){
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
                .child(cUser.getUid()).child("coursModel");
        //databaseReference.push().setValue(model);

        String key = databaseReference.push().getKey();
        Map<String,Object> c= new HashMap<>();
        c.put("idCours", key);
        c.put("cours", cours);
        c.put("nEtudiant", nEtudiant);
        databaseReference.child(key).setValue(c);
    }
    public String getIdCours() {
        return idCours;
    }

    public void setIdCours(String idCours) {
        this.idCours = idCours;
    }

    public String getCours() {
        return cours;
    }

    public void setCours(String cours) {
        this.cours = cours;
    }

    public int getnEtudiant() {
        return nEtudiant;
    }

    public void setnEtudiant(int nEtudiant) {
        this.nEtudiant = nEtudiant;
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
        dest.writeString(this.idCours);
        dest.writeString(this.cours);
        dest.writeInt(this.nEtudiant);
        dest.writeParcelable(this.avisModel,flags);
    }
    public CoursModel(Parcel in) {
        this.idCours=in.readString();
        this.cours=in.readString();
        this.nEtudiant=in.readInt();
      //  this.avisModel=in.readParcelable(this.avisModel);
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public CoursModel createFromParcel(Parcel in) {
            return new CoursModel(in);
        }

        public CoursModel[] newArray(int size) {
            return new CoursModel[size];
        }
    };

}
