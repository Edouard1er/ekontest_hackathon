package com.example.ekontest_hackathon;



import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserModel {
    private String id;
    private PersonalInformationModel personalInformationModel;
    private AcademicInformationModel academicInformationModel;
    private String TAG ="Inside User Class";

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
}

