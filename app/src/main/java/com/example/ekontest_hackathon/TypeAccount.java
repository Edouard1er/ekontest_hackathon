package com.example.ekontest_hackathon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class TypeAccount extends AppCompatActivity {

    Button next;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_account);

        next = (Button) findViewById(R.id.next_button);
        radioGroup = (RadioGroup) findViewById(R.id.typeGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) findViewById(checkedId);
                String type = (String) radioButton.getText();
                Toast.makeText(getApplicationContext(), type, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void nextFormAction(View view) {
        //let's check the selected type
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton)radioGroup.findViewById(selectedId);
        String type = (String) radioButton.getText();
        //open new activity based on the choice
        Intent intent = null;
        if(type.compareTo("Student") == 0) {
            intent = new Intent(getApplicationContext(), ImageUploadActivity.class);
        }
        if(type.compareTo("Freelancer") == 0) {
            intent = new Intent(getApplicationContext(), AcademicActivity.class);
        }
        if(type.compareTo("Professor") == 0) {
            intent = new Intent(getApplicationContext(), AcademicActivity.class);
        }
        intent.putExtra("nom", getIntent().getStringExtra("nom"));
        intent.putExtra("prenom", getIntent().getStringExtra("prenom"));
        intent.putExtra("email", getIntent().getStringExtra("email"));
        intent.putExtra("phone", getIntent().getStringExtra("phone"));
        intent.putExtra("username", getIntent().getStringExtra("username"));
        intent.putExtra("sexe", getIntent().getStringExtra("sexe"));
        intent.putExtra("type", type);
        startActivity(intent);
    }
}