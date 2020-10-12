package com.example.ekontest_hackathon;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfilModel {
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
}
