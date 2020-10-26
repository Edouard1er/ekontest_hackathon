package com.example.ekontest_hackathon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;



import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.google.protobuf.StringValue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReviewActivity extends AppCompatActivity {
    TextView nom, prenom, sexe, email, phone, username, account,
            level, institution, faculty, degree, start, end;
    String mNom, mPrenom, mSexe, mEmail, mPhone, mUsername, mAccount,
            mLevel, mInstitution, mFaculty, mDegree, mStart, mEnd;
    ImageView imagePhoto;
    List<AcademicInformationModel> academicList;
    List<PersonalInformationModel> personelList;
    ProfilModel pofilModel;
    ListView mListView;
    ArrayList mArrayList;
   // InfoAcademicAdapter mAdapter;
    AcademicInformationAdapter adapter;
    RecyclerView recyclerView;


    private Uri mImageUr;
    private StorageReference mStorageRef;
    //private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    private String imageStoragePath;
    private  String filename;
    Uri uri;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);


        academicList = new ArrayList<>();
        personelList= new ArrayList<>();
      //  profilModel= new ProfilModel(0,0);
        mStorageRef= FirebaseStorage.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();


        nom = (TextView) findViewById(R.id.textViewNom);
        prenom = (TextView) findViewById(R.id.textViewPrenom);
        sexe = (TextView) findViewById(R.id.textViewSexe);
        email = (TextView) findViewById(R.id.textViewEmail);
        phone = (TextView) findViewById(R.id.textViewPhone);
        username = (TextView) findViewById(R.id.textViewUsername);
        account = (TextView) findViewById(R.id.textViewAccountType);


       /* mNom=getIntent().getStringExtra("nom");
        mPrenom=getIntent().getStringExtra("prenom");
        mSexe=getIntent().getStringExtra("sexe");
        mEmail=getIntent().getStringExtra("email");
        mPhone=getIntent().getStringExtra("phone");
        mUsername=getIntent().getStringExtra("username");
        mAccount=getIntent().getStringExtra("type");

        mLevel=getIntent().getStringExtra("level");
        mInstitution=getIntent().getStringExtra("institution");
        mFaculty=getIntent().getStringExtra("faculty");
        mDegree=getIntent().getStringExtra("degree");
        mStart=getIntent().getStringExtra("start");
        mEnd=getIntent().getStringExtra("end");*/


        personelList=getIntent().getParcelableArrayListExtra("personnel");



        imagePhoto = (ImageView) findViewById(R.id.imageViewPhoto);

        nom.setText(personelList.get(0).getLastname());
        prenom.setText(personelList.get(0).getFirstname());
        sexe.setText(personelList.get(0).getSexe());
        email.setText(personelList.get(0).getEmail());
        phone.setText(personelList.get(0).getPhone());
        username.setText(personelList.get(0).getUsername());
        account.setText(personelList.get(0).getType());

        //Get array list academic
        if(getIntent().getStringExtra("type").equals("Student")) {
        }
        if(getIntent().getStringExtra("type").equals("Freelancer") ||
                getIntent().getStringExtra("type").equals("Professor") ) {
            academicList=getIntent().getParcelableArrayListExtra("academic");
            recyclerView= findViewById(R.id.list_info_academic);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter=new AcademicInformationAdapter(this, academicList,false);
            recyclerView.setAdapter(adapter);
        }


        //Setting value
        mNom=personelList.get(0).getLastname();
        mPrenom=personelList.get(0).getFirstname();
        mSexe=personelList.get(0).getSexe();
        mEmail=personelList.get(0).getEmail();
        mPhone=personelList.get(0).getPhone();
        mUsername=personelList.get(0).getUsername();
        mAccount=getIntent().getStringExtra("type");

        Toast.makeText(this, "The name of the array personnel :" + personelList.get(0).getLastname(), Toast.LENGTH_SHORT).show();

       /* level = (TextView) findViewById(R.id.textViewLevel);
        institution = (TextView) findViewById(R.id.textViewInstitution);
        faculty = (TextView) findViewById(R.id.textViewFaculty);
        degree = (TextView) findViewById(R.id.textViewDegree);
        start = (TextView) findViewById(R.id.textViewStart);
        end = (TextView) findViewById(R.id.textViewEnd);

       */
        try {

            if(getIntent().hasExtra("photo")) {
                Glide.with(imagePhoto)
                        .load(getIntent().getStringExtra("photo"))
                        .into(imagePhoto);
            }
            if(getIntent().getStringExtra("photo")!= null) {
                uri = Uri.parse(getIntent().getStringExtra("photo"));
            }else{
                if(user.getPhotoUrl()!= null){
                    uri = Uri.parse(user.getPhotoUrl().toString());
                    Glide.with(imagePhoto)
                            .load(getIntent().getStringExtra("photo"))
                            .into(imagePhoto);
                }
            }
        } catch (NullPointerException e) {
            e.getStackTrace();
        }
       /* try {
            if(getIntent().hasExtra("level"))
                level.setText(getIntent().getStringExtra("level"));
            if(getIntent().hasExtra("institution"))
                institution.setText(getIntent().getStringExtra("institution"));
            if(getIntent().hasExtra("faculty"))
                faculty.setText(getIntent().getStringExtra("faculty"));
            if(getIntent().hasExtra("degree"))
                degree.setText(getIntent().getStringExtra("degree"));
            if(getIntent().hasExtra("start"))
                start.setText(getIntent().getStringExtra("start"));
            if(getIntent().hasExtra("end"))
                end.setText(getIntent().getStringExtra("end"));
            if(getIntent().hasExtra("photo")) {
                Glide.with(imagePhoto)
                        .load(getIntent().getStringExtra("photo"))
                        .into(imagePhoto);
            }
            if(getIntent().getStringExtra("photo")!= null) {
                uri = Uri.parse(getIntent().getStringExtra("photo"));
            }else{
                if(user.getPhotoUrl()!= null){
                    uri = Uri.parse(user.getPhotoUrl().toString());
                    Glide.with(imagePhoto)
                            .load(getIntent().getStringExtra("photo"))
                            .into(imagePhoto);
                }
            }
        } catch (NullPointerException e) {
            e.getStackTrace();
        }
*/
    }




    public void onButtonUploadClick(View view) throws FileNotFoundException {
        if(mUploadTask!=null && mUploadTask.isInProgress()){
            Toast.makeText(this, "Upload is in progress", Toast.LENGTH_LONG).show();

        }else{
            uploadData(uri);
        }
    }

    public PersonalInformationModel toSaveUserInformation(String imagelink, String fileIdentity, String firstname,
                                    String lastname, String sexe, String email,
                                    String phone, String username, String type){

        PersonalInformationModel model = new PersonalInformationModel(imagelink, fileIdentity,firstname,
                lastname, sexe, email, phone, username, type);
       // model.insertPersonalInformation(model);

        return model;
    }

    public AcademicInformationModel saveUserAcademicInformation(String level, String institution, String faculte,

                                            String degree, String startDate, String endDate){
        AcademicInformationModel model = new AcademicInformationModel(level, institution, faculte,degree,startDate,endDate);
        //model.insertAcademicInformation(model);
        return model;

    }

    public void goToNavDrawerActivity(){

        Toast.makeText(ReviewActivity.this, "upload succeed", Toast.LENGTH_LONG).show();


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), NavDrawerActivity.class));
                finish();
            }
        }, 3000);


    }


    private void uploadData(Uri uri) throws FileNotFoundException {
        if(uri != null){
            final String fileIdentity=String.valueOf(System.currentTimeMillis());
            Calendar c = Calendar.getInstance();

            final StorageReference fileReference= mStorageRef.child(fileIdentity);

            Toast.makeText(ReviewActivity.this, ""+uri, Toast.LENGTH_SHORT).show();

            if(user.getPhotoUrl() != null){

                PersonalInformationModel pInfo= toSaveUserInformation(user.getPhotoUrl().toString(),fileIdentity,mNom, mPrenom, mSexe, mEmail, mPhone, mUsername, mAccount);
              //  AcademicInformationModel aInfo= saveUserAcademicInformation(mLevel, mInstitution, mFaculty, mDegree, mStart, mEnd);
                UserModel userModel = new UserModel();
                //userModel.InsertUsers(pInfo,aInfo);
                userModel.InsertUsers(pInfo,academicList);
                goToNavDrawerActivity();

            }else{
                InputStream stream = new FileInputStream(new File(String.valueOf(uri)));
                mUploadTask=fileReference.putStream(stream)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Task <Uri> downloadUri=taskSnapshot.getStorage().getDownloadUrl();
                                fileReference.getDownloadUrl()
                                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri2) {
                                                imageStoragePath=uri2.toString();

                                                PersonalInformationModel pInfo= toSaveUserInformation(imageStoragePath,fileIdentity,mNom, mPrenom, mSexe, mEmail, mPhone, mUsername, mAccount);


                                                UserModel userModel = new UserModel();
                                                userModel.InsertUsers(pInfo,academicList);

                                                goToNavDrawerActivity();
                                            }
                                        });

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(ReviewActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

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

           /* if(getIntent().getStringExtra("photo").equals(user.getPhotoUrl().toString())){

            }else{

            }*/

        }else{
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
            PersonalInformationModel pInfo= toSaveUserInformation("","",mNom, mPrenom, mSexe, mEmail, mPhone, mUsername, mAccount);

            UserModel userModel = new UserModel();
            userModel.InsertUsers(pInfo,academicList);

            goToNavDrawerActivity();
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR= getContentResolver();
        MimeTypeMap mime= MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private String getFileName(Uri uri){
        File file = new File(uri.getPath());
        filename= file.getName();
        return filename;
    }

}