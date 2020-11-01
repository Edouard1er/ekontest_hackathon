package com.example.ekontest_hackathon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.ekontest_hackathon.ui.MainActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class PersonalInformationActivity extends AppCompatActivity {

    Button next,save;
    RadioGroup radioGroup;
    EditText nom, prenom, email, phone, username;

    FirebaseUser user ;
    ArrayList<PersonalInformationModel> infoPersoList;
    ArrayList <PersonalInformationModel> models =new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);

        user= FirebaseAuth.getInstance().getCurrentUser();


        radioGroup = (RadioGroup) findViewById(R.id.sexeGroup);
        nom = (EditText) findViewById(R.id.editTextNom);
        prenom = (EditText) findViewById(R.id.editTextPrenom);
        email = (EditText) findViewById(R.id.editTextTextEmail);
        phone = (EditText) findViewById(R.id.editTextPhone);
        username = (EditText) findViewById(R.id.editTextTextUsername);
        infoPersoList = new ArrayList<>();
       // signOut();

        next = (Button) findViewById(R.id.next_button);
        save = (Button) findViewById(R.id.save_button);


            next.setVisibility(View.VISIBLE);
            if(user.getDisplayName()!= null){
                try {
                    String[] fullname = user.getDisplayName().split(" ");
                    nom.setText(fullname[0]);
                    prenom.setText(fullname[1]);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            if(user.getPhoneNumber() != null){
                phone.setText(user.getPhoneNumber());
            }
            if(user.getEmail() != null){
                email.setText(user.getEmail());
            }



            try{
               setData();
            }catch (Exception e){
                e.printStackTrace();
                next.setVisibility(View.VISIBLE);

            }

    }

    public void nextFormAction(View view) {
        //let's check if all the field are filled
        ArrayList<EditText> collectionEditText = new ArrayList<EditText>();
        collectionEditText.add(nom);
        collectionEditText.add(prenom);
        collectionEditText.add(email);
        collectionEditText.add(phone);
        collectionEditText.add(username);
        EmptyField fields = new EmptyField(collectionEditText);

        if(fields.isAllFieldFilled()) {
            //Let's get the text in all the field
            String nom_ = String.valueOf(nom.getText());
            String prenom_ = String.valueOf(prenom.getText());
            String email_ = String.valueOf(email.getText());
            String phone_ = String.valueOf(phone.getText());
            String username_ = String.valueOf(username.getText());
            //for radio button
            int selectedId = radioGroup.getCheckedRadioButtonId();
            RadioButton radioButton = (RadioButton)radioGroup.findViewById(selectedId);
            String sexe_ = (String) radioButton.getText();
            //testing output
            System.out.println("Nom: " + nom_);
            System.out.println("Prenom: " + prenom_);
            System.out.println("email: " + email_);
            System.out.println("phone: " + phone_);
            System.out.println("username: " + username_);
            System.out.println("sexe: " + sexe_);
            //let's save all the information, we need to send them to the next intent
            //check some information against firebase here
            Intent intent = new Intent(getApplicationContext(), TypeAccount.class);
            PersonalInformationModel personalInformationModel = new PersonalInformationModel(
                    nom_,
                   prenom_,
                    sexe_,
                   email_,
                    phone_,
                    username_
            );
            infoPersoList.add(personalInformationModel);
            intent.putParcelableArrayListExtra("personnel", (ArrayList<? extends Parcelable>) infoPersoList);
            startActivity(intent);
        } else {
            Toast.makeText(PersonalInformationActivity.this, "All the information are required", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateData(View view) {
        //let's check if all the field are filled
        ArrayList<EditText> collectionEditText = new ArrayList<EditText>();
        collectionEditText.add(nom);
        collectionEditText.add(prenom);
        collectionEditText.add(email);
        collectionEditText.add(phone);
        collectionEditText.add(username);
        EmptyField fields = new EmptyField(collectionEditText);

        if (fields.isAllFieldFilled()) {
            //Let's get the text in all the field
            String nom_ = String.valueOf(nom.getText());
            String prenom_ = String.valueOf(prenom.getText());
            String email_ = String.valueOf(email.getText());
            String phone_ = String.valueOf(phone.getText());
            String username_ = String.valueOf(username.getText());
            //for radio button
            int selectedId = radioGroup.getCheckedRadioButtonId();
            RadioButton radioButton = (RadioButton) radioGroup.findViewById(selectedId);
            String sexe_ = (String) radioButton.getText();
           /* //testing output
            System.out.println("Nom: " + nom_);
            System.out.println("Prenom: " + prenom_);
            System.out.println("email: " + email_);
            System.out.println("phone: " + phone_);
            System.out.println("username: " + username_);
            System.out.println("sexe: " + sexe_);*/
            //let's save all the information, we need to send them to the next intent
            //check some information against firebase here
            PersonalInformationModel personalInformationModel = new PersonalInformationModel(
                    models.get(0).getImagelink(),
                    models.get(0).getImagename(),
                    nom_,
                    prenom_,
                    sexe_,
                    email_,
                    phone_,
                    username_,
                    models.get(0).getType()
            );
            personalInformationModel.insertPersonalInformation(personalInformationModel);
            Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
            startActivity(intent);
            finish();


        }
    }
    public void setData(){
        try {
            models=getIntent().getParcelableArrayListExtra("personnel");
            nom.setText(models.get(0).getFirstname());
            prenom.setText(models.get(0).getLastname());
            phone.setText(models.get(0).getPhone());
            email.setText(models.get(0).getEmail());
            username.setText(models.get(0).getUsername());
           /* RadioButton radioButton = (RadioButton)radioGroup.findViewById(1);
            radioButton.setSaveEnabled(true);            */
            save.setVisibility(View.VISIBLE);
        }catch (Exception e){

        }




    }

    public void signOut(){
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(getApplicationContext(), Authentication.class));

                    }
                });
    }

}
