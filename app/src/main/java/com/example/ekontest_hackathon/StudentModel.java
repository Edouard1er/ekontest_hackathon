package com.example.ekontest_hackathon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class StudentModel extends UserModel implements Parcelable {



    public StudentModel() {
        super();
    }



    public void InsertStudent(final PersonalInformationModel pModel){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        ;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user.getUid()!= null) {
            UserModel model = new UserModel(user.getUid());
            databaseReference.child(user.getUid()).setValue(model);
            //Insert personnel data
            PersonalInformationModel personal= new PersonalInformationModel();
            personal.insertPersonalInformation(pModel);
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
    private StudentModel(Parcel in) {


    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<StudentModel> CREATOR = new Parcelable.Creator<StudentModel>() {
        public StudentModel createFromParcel(Parcel in) {
            return new StudentModel(in);
        }

        public StudentModel[] newArray(int size) {
            return new StudentModel[size];
        }
    };

}
