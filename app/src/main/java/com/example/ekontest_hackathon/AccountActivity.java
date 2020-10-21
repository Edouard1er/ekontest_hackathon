package com.example.ekontest_hackathon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aminography.choosephotohelper.ChoosePhotoHelper;
import com.aminography.choosephotohelper.callback.ChoosePhotoCallback;
import com.bumptech.glide.Glide;
import com.example.ekontest_hackathon.ui.NavDrawerActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AccountActivity extends AppCompatActivity {
    private AcademicInformationAdapter adapter;
    List<AcademicInformationModel> mAcademic=new ArrayList<>();
    ArrayList<PersonalInformationModel> personelList;

    AcademicInformationModel academicInformationModel;
    RecyclerView recyclerView;
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    TextView nom, prenom, sexe, email, phone, username,account;

    //IMage
    ChoosePhotoHelper choosePhotoHelper;
    ImageView imageUpload;
    String photoPath;

    private StorageReference mStorageRef;
    private StorageTask mUploadTask;
    private   Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        nom = (TextView) findViewById(R.id.textViewNom);
        prenom = (TextView) findViewById(R.id.textViewPrenom);
        sexe = (TextView) findViewById(R.id.textViewSexe);
        email = (TextView) findViewById(R.id.textViewEmail);
        phone = (TextView) findViewById(R.id.textViewPhone);
        username = (TextView) findViewById(R.id.textViewUsername);
        account = (TextView) findViewById(R.id.textViewAccountType);
        personelList=new ArrayList<>();

        //Image Upload
        imageUpload = (ImageView) findViewById(R.id.imageViewPhoto);
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
        getAcademicData();
        getPersonalData();
        try {
            addProfilePhoto();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void addProfilePhoto() {


        imageUpload = (ImageView) findViewById(R.id.imageViewPhoto);


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users")
                .child(user.getUid());
        final String[] imagename = new String[1];
        databaseReference
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        try {
                            UserModel model = snapshot.getValue(UserModel.class);
                            final String[] url = new String[2];
                            url[0] = model.getPersonalInformationModel().getImagelink();
                            url[1] = model.getPersonalInformationModel().getImagename();
                            //getUrlImage(u.getPersonalInformationModel().getImagename(), holder.userImage);
                            UrlImageModel urlImageModel = new UrlImageModel();
                            urlImageModel.getUrlImage(url,  imageUpload);

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




    }
    public void getPersonalData(){
        DatabaseReference academicRef = FirebaseDatabase.getInstance().getReference("Users")
                .child(user.getUid()).child("personalInformationModel");


        academicRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PersonalInformationModel model = snapshot.getValue(PersonalInformationModel.class);
                personelList.add(model);
                try {
                    nom.setText(model.getFirstname());
                    prenom.setText(model.getLastname());
                    sexe.setText(model.getSexe());
                    email.setText(model.getEmail());
                    phone.setText(model.getPhone());
                    username.setText(model.getUsername());
                    account.setText(model.getType());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void getAcademicData(){
       /* List <AcademicInformationModel> list = new ArrayList<>();
        AcademicInformationModel m = new AcademicInformationModel("Universite", "UEH", "FDS",

                "Master", "2020", "2022");
        list.add(m);
        m.insertAcademicInformation(list);*/


        recyclerView = findViewById(R.id.list_info_academic);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DatabaseReference academicRef = FirebaseDatabase.getInstance().getReference("Users")
                .child(user.getUid()).child("academicInformationModel");


        academicRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mAcademic.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    academicInformationModel = dataSnapshot.getValue(AcademicInformationModel.class);

                    mAcademic.add(academicInformationModel);

                }
                adapter =new AcademicInformationAdapter(getApplicationContext(),mAcademic);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void addAcademicFormation(View view){
        Intent intent = new Intent(getApplicationContext(), AcademicActivity.class);
        intent.putExtra("idUser",user.getUid());
        // intent.putExtra("model",(Parcelable) model);
         startActivity(intent);
    }
    public void editType(View view){
        Intent intent = new Intent(getApplicationContext(), TypeAccount.class);

        intent.putExtra("idUser",user.getUid());
        // intent.putExtra("model",(Parcelable) model);
        startActivity(intent);
    }
    public void updatePersonalData(View view){
        Intent intent = new Intent(getApplicationContext(), PersonalInformationActivity.class);

        intent.putParcelableArrayListExtra("personnel",personelList);
        intent.putExtra("idUser",user.getUid());
        // intent.putExtra("model",(Parcelable) model);
        startActivity(intent);
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
    public void onButtonSaveClick(View view) throws FileNotFoundException {
        if(mUploadTask!=null && mUploadTask.isInProgress()){
            Toast.makeText(this, "Upload is in progress", Toast.LENGTH_LONG).show();

        }else{
            updateImageUser(photoPath);
        }
    }
    public void updateImageUser(String path) throws FileNotFoundException {
        uri = Uri.parse(path);
        mStorageRef= FirebaseStorage.getInstance().getReference();
        final String fileIdentity=String.valueOf(System.currentTimeMillis());
        Calendar c = Calendar.getInstance();

        final StorageReference fileReference= mStorageRef.child(user.getUid());
        InputStream stream = new FileInputStream(new File(String.valueOf(uri)));
        mUploadTask=fileReference.putStream(stream)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> downloadUri=taskSnapshot.getStorage().getDownloadUrl();
                        fileReference.getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri2) {
                                        uri2.toString();
                                        UserModel model= new UserModel();
                                        model.UpdateImageUser(uri2.toString(),"imagelink");
                                        model.UpdateImageUser(user.getUid(),"imagename");



                                    }
                                });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        //  Toast.makeText(ReviewActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        // double progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                        //mProgressBar.setProgress((int)progress);

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