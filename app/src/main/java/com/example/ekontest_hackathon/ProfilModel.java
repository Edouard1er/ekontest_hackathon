package com.example.ekontest_hackathon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfilModel extends ClassLoader implements Parcelable {
    private int nCours=0;
    private int nEtudiant=0;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;


    public ProfilModel(int nCours, int nEtudiant) {
        this.nCours = nCours;
        this.nEtudiant = nEtudiant;
    }
    public ProfilModel(){}

    public void InsertProfil(ProfilModel model){
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(firebaseUser.getUid()).child("profilModel").setValue(model)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                });

    }

    public int getnCours() {
        return nCours;
    }

    public void setnCours(int nCours) {
        this.nCours = nCours;
    }

    public int getnEtudiant() {
        return nEtudiant;
    }

    public void setnEtudiant(int nEtudiant) {
        this.nEtudiant = nEtudiant;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.nCours);
        dest.writeInt(this.nEtudiant);

    }
    public ProfilModel(Parcel in) {
        this.nCours = in.readInt();
        this.nEtudiant = in.readInt();

    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ProfilModel createFromParcel(Parcel in) {
            return new ProfilModel(in);
        }

        public ProfilModel[] newArray(int size) {
            return new ProfilModel[size];
        }
    };
}
