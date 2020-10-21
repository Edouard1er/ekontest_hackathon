package com.example.ekontest_hackathon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;



import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AcademicActivity extends AppCompatActivity {

    Button next,save;
    EditText faculty, institution;
    Spinner level, degree, start, end;
    ArrayList<AcademicInformationModel> academicList;
    List<PersonalInformationModel> personelList;
    AcademicInformationAdapter adapter;
    RecyclerView recyclerView;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    //button Visibility
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic);

        //Set up list
        academicList= new ArrayList<>();
        personelList= new ArrayList<>();
        recyclerView= findViewById(R.id.list_info_academic_real);



        next = (Button) findViewById(R.id.next_button);
        save = findViewById(R.id.save_button);
        //button visibility
        try {
            if (getIntent().getStringExtra("idUser").equals(user.getUid())) {
                save.setVisibility(View.VISIBLE);
            } else {
                next.setVisibility(View.VISIBLE);
            }
        }catch (Exception e){
            next.setVisibility(View.VISIBLE);
        }
        level = (Spinner) findViewById(R.id.spinnerLevel);
        faculty = (EditText) findViewById(R.id.editTextTextFaculty);
        degree = (Spinner) findViewById(R.id.spinnerDegree);
        institution = (EditText) findViewById(R.id.editTextTextInstitution);
        start = (Spinner) findViewById(R.id.spinnerStart);
        end = (Spinner) findViewById(R.id.spinnerEnd);

        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapterLevel = ArrayAdapter.createFromResource(this, R.array.level_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        staticAdapterLevel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapterDegree = ArrayAdapter.createFromResource(this, R.array.degree_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        staticAdapterDegree.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        level.setAdapter(staticAdapterLevel);
        degree.setAdapter(staticAdapterDegree);

        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1970; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        start.setAdapter(adapter);
        end.setAdapter(adapter);
        System.out.println("Last year: " + start.getItemAtPosition(years.size() - 1));
        start.setSelection(years.size() - 1);
        end.setSelection(years.size() - 1);
    }

    public void addAcademicFormation(View view){
        ArrayList<EditText> collectionEditText = new ArrayList<EditText>();
        collectionEditText.add(institution);
        collectionEditText.add(faculty);
        EmptyField fields = new EmptyField(collectionEditText);

        if(fields.isAllFieldFilled()) {
            String level_ = level.getSelectedItem().toString();
            String faculty_ = String.valueOf(faculty.getText());
            String degree_ = degree.getSelectedItem().toString();
            String institution_ = String.valueOf(institution.getText());
            String start_ = start.getSelectedItem().toString();
            String end_ = end.getSelectedItem().toString();
            AcademicInformationModel model = new AcademicInformationModel(level_,institution_, faculty_, degree_, start_, end_);
            academicList.add(model);
           // Toast.makeText(this, "The size of the academic array is: "+academicList.size(), Toast.LENGTH_SHORT).show();

            recyclerView= findViewById(R.id.list_info_academic_real);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter=new AcademicInformationAdapter(this, academicList);
            recyclerView.setAdapter(adapter);

        }

    }
    public void nextFormAction(View view) {
        ArrayList<EditText> collectionEditText = new ArrayList<EditText>();
        collectionEditText.add(institution);
        collectionEditText.add(faculty);
        EmptyField fields = new EmptyField(collectionEditText);

        if(fields.isAllFieldFilled()) {
            try {
                if (getIntent().getStringExtra("idUser").equals(user.getUid())) {
                    AcademicInformationModel academic = new AcademicInformationModel();
                    if (academicList.size() != 0) {
                        academic.insertAcademicInformation(academicList);
                        Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
                        startActivity(intent);
                        finish();
                    }

                } else {

                }
            }catch (Exception e){
                //sending data to next actitvity
                Intent intent = new Intent(getApplicationContext(), ImageUploadActivity.class);
                intent.putExtra("personnel", getIntent().getParcelableArrayListExtra("personnel"));
                //intent.putExtra("academic", (Serializable) academicList);
                personelList = getIntent().getParcelableArrayListExtra("personnel");
                intent.putParcelableArrayListExtra("personnel", (ArrayList<? extends Parcelable>) personelList);
                intent.putParcelableArrayListExtra("academic", (ArrayList<? extends Parcelable>) academicList);
                intent.putExtra("type", getIntent().getStringExtra("type"));

                startActivity(intent);
            }

        } else {
            Toast.makeText(getApplicationContext(), "Some information are required", Toast.LENGTH_SHORT).show();
        }
    }
}