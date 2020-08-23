package com.example.ekontest_hackathon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class AcademicActivity extends AppCompatActivity {

    Button next;
    EditText faculty, institution;
    Spinner level, degree, start, end;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic);

        next = (Button) findViewById(R.id.next_button);
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
    public void nextFormAction(View view) {
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
            //testing output
            //sending data to next actitvity
            Intent intent = new Intent(getApplicationContext(), ImageUploadActivity.class);
            intent.putExtra("nom", getIntent().getStringExtra("nom"));
            intent.putExtra("prenom", getIntent().getStringExtra("prenom"));
            intent.putExtra("email", getIntent().getStringExtra("email"));
            intent.putExtra("phone", getIntent().getStringExtra("phone"));
            intent.putExtra("username", getIntent().getStringExtra("username"));
            intent.putExtra("sexe", getIntent().getStringExtra("sexe"));
            intent.putExtra("type", getIntent().getStringExtra("type"));

            intent.putExtra("level", level_);
            intent.putExtra("faculty", faculty_);
            intent.putExtra("degree", degree_);
            intent.putExtra("institution", institution_);
            intent.putExtra("start", start_);
            intent.putExtra("end", end_);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Some information are required", Toast.LENGTH_SHORT).show();
        }
    }
}