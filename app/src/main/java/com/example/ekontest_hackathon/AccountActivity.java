package com.example.ekontest_hackathon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
    ConstraintLayout imagePlus;

    private StorageReference mStorageRef;
    private StorageTask mUploadTask;
    private   Uri uri;

    //Tag part
   AutoCompleteTextView textTag;
    TextView showSpaceTag, saveTag, hideSpaceTag, editPicture;

    LinearLayout tagLayoutFirst;
    LinearLayout tagLayoutSecond;
    ConstraintLayout altUserImage;
    TextView altTxtName;
    ProgressBar progressBar;

    RecyclerView recyclerViewTag;
    List <TagModel> mTag= new ArrayList<>();
    TagListAdapter adapterTag;

    ArrayList<String> autoCompleteListe ;
    ArrayAdapter<String> autoCompleteAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        //tag
        textTag = (AutoCompleteTextView) findViewById(R.id.textTag);
        tagLayoutFirst = (LinearLayout) findViewById(R.id.tag_layout_first);
        tagLayoutSecond = (LinearLayout) findViewById(R.id.tag_layout_second);
        showSpaceTag =(TextView) findViewById(R.id.addTag);
        saveTag =(TextView) findViewById(R.id.saveTag);
        hideSpaceTag =(TextView) findViewById(R.id.cancelTag);
        editPicture =(TextView) findViewById(R.id.editPicture);
        editPicture.setVisibility(View.GONE);

        recyclerViewTag= findViewById(R.id.list_tag);
        autoCompleteListe =new ArrayList<>();




        altUserImage=findViewById(R.id.altUserImage);
        altTxtName=findViewById(R.id.altTxtName);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);




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
        imagePlus =  findViewById(R.id.hideOriginalPicture);

        choosePhotoHelper = ChoosePhotoHelper.with(this)
                .asFilePath()
                .build(new ChoosePhotoCallback<String>() {
                    @Override
                    public void onChoose(String photo) {
                        altUserImage.setVisibility(View.GONE);
                        imageUpload.setVisibility(View.VISIBLE);
                        Glide.with(imageUpload)
                                .load(photo)
                                .into(imageUpload);
                        photoPath = photo;
                        System.out.println("Photo picken: " + photo);
                        editPicture.setVisibility(View.VISIBLE);
                        imagePlus.setVisibility(View.VISIBLE);

                    }
                });
        getAcademicData();
        getPersonalData();
        getTagList();
        try {
            addProfilePhoto();
        }catch (Exception e){
            e.printStackTrace();
        }
        getListTagsAutoComplete();

        autoCompleteAdapter  = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_dropdown_item_1line,autoCompleteListe);
        textTag.setAdapter(autoCompleteAdapter);
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
                            //getUrlImage(url, imageUpload);
                            UrlImageModel urlImageModel = new UrlImageModel();
                            urlImageModel.getUrlImage(url, imageUpload, imagePlus,getApplicationContext(),altUserImage,altTxtName,model);

                            /*UrlImageModel urlImageModel = new UrlImageModel();
                            urlImageModel.getUrlImage(url, imageUpload, getApplicationContext(),altUserImage,altTxtName,model);*/
                           /* UrlImageModel urlImageModel = new UrlImageModel();
                            //urlImageModel.getUrlImage(url,  imageUpload);
                            Toast.makeText(AccountActivity.this, "Link"+url[0], Toast.LENGTH_SHORT).show();*/

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        showSpaceTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagLayoutSecond.setVisibility(View.VISIBLE);
                hideSpaceTag.setVisibility(View.VISIBLE);
                showSpaceTag.setVisibility(View.GONE);

            }
        });
        hideSpaceTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagLayoutSecond.setVisibility(View.GONE);
                showSpaceTag.setVisibility(View.VISIBLE);
                hideSpaceTag.setVisibility(View.GONE);
            }
        });

        textTag.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                if(s.toString().trim().length()!=0){
                    saveTag.setVisibility(View.VISIBLE);
                }else{
                    saveTag.setVisibility(View.GONE);
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().trim().length()!=0){
                    saveTag.setVisibility(View.VISIBLE);
                }else{
                    saveTag.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().length()!=0){
                    saveTag.setVisibility(View.VISIBLE);
                }else{
                    saveTag.setVisibility(View.GONE);
                }

            }
        });
        saveTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TagModel model = new TagModel();
                model.InsertTag(getApplicationContext(),textTag.getText().toString());
                tagLayoutSecond.setVisibility(View.GONE);
                showSpaceTag.setVisibility(View.VISIBLE);
                hideSpaceTag.setVisibility(View.GONE);
                //Toast.makeText(AccountActivity.this, "Enregistre", Toast.LENGTH_SHORT).show();

            }
        });


    }
    public void getTagList(){


        final DatabaseReference databaseReference =  FirebaseDatabase.getInstance().getReference("Tags");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mTag.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                   final String tag = dataSnapshot.getKey();
                    DatabaseReference databaseReference2 =  FirebaseDatabase.getInstance().getReference("Tags")
                            .child(tag).child(user.getUid());
                    databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists() && !tag.equals(nom.getText().toString()+" "+prenom.getText().toString())){
                                TagModel model = new TagModel();
                                model.setTag(tag);
                                mTag.add(model);
                                recyclerViewTag.setHasFixedSize(true);
                                recyclerViewTag.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                                adapterTag= new TagListAdapter(getApplicationContext(), mTag);
                                recyclerViewTag.setAdapter(adapterTag);

                                //Toast.makeText(AccountActivity.this, ""+model.getTag(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }

                    });


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
                try{
                    if(model.getType().equals("Freelancer") || model.getType().equals("Professor")){
                        tagLayoutFirst.setVisibility(View.VISIBLE);
                    }else{
                        tagLayoutFirst.setVisibility(View.GONE);

                    }
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
                adapter =new AcademicInformationAdapter(getApplicationContext(),mAcademic,true);
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
                                        //model.UpdateImageUser(uri2.toString(),"imagelink");
                                        model.UpdateImageUser( uri2.toString(),"imagelink","imagename", imageUpload, getApplicationContext(),altUserImage,altTxtName);
                                        progressBar.setVisibility(View.GONE);
                                        editPicture.setVisibility(View.GONE);
                                        photoPath=uri2.toString();
                                        addProfilePhoto();
                                        Intent intent = new Intent(getApplicationContext(),AccountActivity.class);
                                        startActivity(intent);
                                        finish();

                                        //Toast.makeText(AccountActivity.this, "Succes", Toast.LENGTH_SHORT).show();



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
                         double progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                        progressBar.setProgress((int)progress);
                        if((int) progress==100){
                            progressBar.setVisibility(View.GONE);
                            editPicture.setVisibility(View.GONE);

                        }else{
                            progressBar.setVisibility(View.VISIBLE);
                            editPicture.setVisibility(View.GONE);

                        }

                    }
                });

       /* if(mUploadTask!=null && mUploadTask.isInProgress()){
           // Toast.makeText(this, "Upload is in progress", Toast.LENGTH_LONG).show();

        }else{
            progressBar.setVisibility(View.GONE);
            editPicture.setVisibility(View.GONE);
        }*/



    }
    public String getUrlImage(String[] imageSource, final ImageView imageUser){
        final String[] url = new String[1];
        final StorageReference mStorageRef= FirebaseStorage.getInstance().getReference();

        try{
            if(imageSource[0].contains("firebasestorage.googleapis.com")){
                final StorageReference fileReference= mStorageRef.child("Images").child(imageSource[1]+"_500x175");
                fileReference.getDownloadUrl()
                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri2) {
                                url[0] =String.valueOf(uri2);
                                Glide.with(getApplicationContext())
                                        .load(uri2)
                                        .centerCrop()
                                        .into(imageUser);
                                //     .apply(new RequestOptions().override(200,90))



                            }
                        });
                return url[0];
            }else{
                url[0] =imageSource[0];
                Glide.with(imageUser)
                        .load(url[0])
                        //  .apply(new RequestOptions().override(120,90))
                        .centerCrop()
                        .into(imageUser);

                return url[0];
            }
        }catch (Exception e){
            return url[0];
        }


    }
    public void getListTagsAutoComplete(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tags");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                autoCompleteListe.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    autoCompleteListe.add(dataSnapshot.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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