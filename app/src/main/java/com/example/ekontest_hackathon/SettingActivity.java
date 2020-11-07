package com.example.ekontest_hackathon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ekontest_hackathon.ui.NavDrawerActivity;

public class SettingActivity extends AppCompatActivity {
    TextView language, termUse, userDataPolicy, copyright, privacy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        language = findViewById(R.id.id_langue_setting);
        termUse = findViewById(R.id.id_conditionUtilisation_setting);
        userDataPolicy =findViewById(R.id.id_politique_setting);
        copyright = findViewById(R.id.id_copyright_setting);
        privacy = findViewById(R.id.id_confidentialite_setting);
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, LanguageSettingActivity.class);
                startActivity(intent);
                finish();
            }
        });
        termUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),termUse.getText(), Toast.LENGTH_LONG).show();
            }
        });
        copyright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),copyright.getText(), Toast.LENGTH_LONG).show();
            }
        });
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),privacy.getText(), Toast.LENGTH_LONG).show();
            }
        });
        userDataPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),userDataPolicy.getText(), Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, NavDrawerActivity.class);
        startActivity(intent);
        finish();
    }
}