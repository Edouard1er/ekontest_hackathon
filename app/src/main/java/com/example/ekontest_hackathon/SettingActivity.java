package com.example.ekontest_hackathon;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ekontest_hackathon.ui.NavDrawerActivity;

import java.util.Locale;

import static com.example.ekontest_hackathon.R.string.app_name;

public class SettingActivity extends AppCompatActivity {
    TextView language, termUse, userDataPolicy, copyright, privacy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

       // ActionBar actionBar = getSupportActionBar();
       // actionBar.setTitle(getResources().getString(app_name));

        language = findViewById(R.id.id_langue_setting);
        termUse = findViewById(R.id.id_conditionUtilisation_setting);
        userDataPolicy =findViewById(R.id.id_politique_setting);
        copyright = findViewById(R.id.id_copyright_setting);
        privacy = findViewById(R.id.id_confidentialite_setting);



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

        // change language listener

        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangelanguageDialog();
            }
        });



    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, NavDrawerActivity.class);
        startActivity(intent);
        finish();
    }


    private void showChangelanguageDialog(){

        final String[] listItems = {"French","English","Creole"};
        final AlertDialog.Builder nBuilder = new AlertDialog.Builder(SettingActivity.this);
        nBuilder.setTitle("Choose language");
        nBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                if(i==0){
                    setLocate("fr");
                    recreate();

                }
                else if(i==1){
                    setLocate("en");
                    recreate();
                }
                else if(i==2){
                    setLocate("crp");
                    recreate();
                }

                dialog.dismiss();
            }
        });
        AlertDialog nDialog = nBuilder.create();
        nDialog.show();
    }

    private void setLocate(String lang) {
        Configuration config = getBaseContext().getResources().getConfiguration();
        if (!"".equals(lang) && !config.locale.getLanguage().equals(lang)) {

            Locale locale = new Locale(lang);
            Locale.setDefault(locale);
            Configuration conf = new Configuration(config);
            conf.locale = locale;
            getBaseContext().getResources().updateConfiguration(conf, getBaseContext().getResources().getDisplayMetrics());
        }
    }


    public  void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("setting", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_lang","");
        setLocate(language);
    }
}