package com.example.ekontest_hackathon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.*;

import com.aminography.choosephotohelper.ChoosePhotoHelper;
import com.aminography.choosephotohelper.callback.ChoosePhotoCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.time.Instant;

public class ImageUploadActivity extends AppCompatActivity {

    Button next;
    ChoosePhotoHelper choosePhotoHelper;
    ImageButton imageUpload;
    String photoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);

        next = (Button) findViewById(R.id.next_button);
        imageUpload = (ImageButton) findViewById(R.id.imageViewButton);
        choosePhotoHelper = ChoosePhotoHelper.with(this)
                .asFilePath()
                .build(new ChoosePhotoCallback<String>() {
                    @Override
                    public void onChoose(String photo) {
                        Glide.with(imageUpload)
                                .load(photo)
                                .into(imageUpload);
                        photoPath = photo;
                        System.out.println("Photo picken: " + photo);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        choosePhotoHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        choosePhotoHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        choosePhotoHelper.onSaveInstanceState(outState);
    }

    public void uploadImage(View view) {
        choosePhotoHelper.showChooser();
    }

    public void nextFormAction(View view) {
        Intent intent = new Intent(getApplicationContext(), ReviewActivity.class);
       /* intent.putExtra("nom", getIntent().getStringExtra("nom"));
        intent.putExtra("prenom", getIntent().getStringExtra("prenom"));
        intent.putExtra("email", getIntent().getStringExtra("email"));
        intent.putExtra("phone", getIntent().getStringExtra("phone"));
        intent.putExtra("username", getIntent().getStringExtra("username"));
        intent.putExtra("sexe", getIntent().getStringExtra("sexe"));
        intent.putExtra("type", getIntent().getStringExtra("type"));*/


        intent.putExtra("personnel",getIntent().getParcelableArrayListExtra("personnel"));
        intent.putExtra("academic",getIntent().getParcelableArrayListExtra("academic"));
        intent.putExtra("type", getIntent().getStringExtra("type"));

        //let's add the photo path
        if(this.photoPath != null){
            intent.putExtra("photo", this.photoPath);
            Toast.makeText(this, "Get image path", Toast.LENGTH_SHORT).show();
        }else{
            FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
            if(firebaseUser.getPhotoUrl() != null) {
                intent.putExtra("photo", firebaseUser.getPhotoUrl().toString());
                Glide.with(imageUpload)
                        .load(firebaseUser.getPhotoUrl())
                        .into(imageUpload);
            }
            Toast.makeText(this, "Get image link", Toast.LENGTH_SHORT).show();

        }


        //from freelancer or professor
        if(getIntent().hasExtra("level"))
            intent.putExtra("level", getIntent().getStringExtra("level"));
        if(getIntent().hasExtra("faculty"))
            intent.putExtra("faculty", getIntent().getStringExtra("faculty"));
        if(getIntent().hasExtra("degree"))
            intent.putExtra("degree", getIntent().getStringExtra("degree"));
        if(getIntent().hasExtra("institution"))
            intent.putExtra("institution", getIntent().getStringExtra("institution"));
        if(getIntent().hasExtra("start"))
            intent.putExtra("start", getIntent().getStringExtra("start"));
        if(getIntent().hasExtra("end"))
            intent.putExtra("end", getIntent().getStringExtra("end"));


        System.out.println("nom: " + getIntent().getStringExtra("nom"));
        System.out.println("prenom: " + getIntent().getStringExtra("prenom"));
        System.out.println("email: " + getIntent().getStringExtra("email"));
        System.out.println("phone: " + getIntent().getStringExtra("phone"));
        System.out.println("username: " + getIntent().getStringExtra("username"));
        System.out.println("sexe: " + getIntent().getStringExtra("sexe"));
        System.out.println("type: " + getIntent().getStringExtra("type"));

        System.out.println("level: " + getIntent().getStringExtra("level"));
        System.out.println("faculty: " + getIntent().getStringExtra("faculty"));
        System.out.println("degree: " + getIntent().getStringExtra("degree"));
        System.out.println("institution: " + getIntent().getStringExtra("institution"));
        System.out.println("start: " + getIntent().getStringExtra("start"));
        System.out.println("end: " + getIntent().getStringExtra("end"));
        startActivity(intent);
    }
}