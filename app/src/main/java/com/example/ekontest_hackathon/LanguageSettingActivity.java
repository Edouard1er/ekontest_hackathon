package com.example.ekontest_hackathon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class LanguageSettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_setting);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LanguageSettingActivity.this, SettingActivity.class);
        startActivity(intent);
        finish();
    }
}