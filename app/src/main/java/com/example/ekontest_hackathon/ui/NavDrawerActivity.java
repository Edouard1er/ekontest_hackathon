package com.example.ekontest_hackathon.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.ekontest_hackathon.AvisDisplayFreelancerFragment;
import com.example.ekontest_hackathon.AvisFreelancerFragment;
import com.example.ekontest_hackathon.DocumentFragment;
import com.example.ekontest_hackathon.FreelancerListFragment;
import com.example.ekontest_hackathon.FreelancerListOnClickFragment;
import com.example.ekontest_hackathon.PersonalInformationModel;
import com.example.ekontest_hackathon.R;
import com.example.ekontest_hackathon.UrlImageModel;
import com.example.ekontest_hackathon.UserAdapter;
import com.example.ekontest_hackathon.UserModel;
import com.example.ekontest_hackathon.ui.about_us.AboutUsFragment;
import com.example.ekontest_hackathon.ui.account.AccountFragment;
import com.example.ekontest_hackathon.FavoriteFragment;
import com.example.ekontest_hackathon.ui.help_comment.HelpCommentFragment;
import com.example.ekontest_hackathon.ui.home.HomeFragment;
import com.example.ekontest_hackathon.ui.payment.PaymentMethodFragment;
import com.example.ekontest_hackathon.ui.setting.SettingFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NavDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        HomeFragment.onItemBottomMenuSelected, DocumentFragment.onFragmentBtnSelected, FreelancerListOnClickFragment.onClickInfoFreelancer, FreelancerListFragment.freelancerInterface {
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mActionBarDrawerToggle;
    Toolbar mToolbar;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    View header ;
     public static String bottomMenu;

     //Profile configuration Variable

    ImageView profile_image;
    TextView  profile_name;
    TextView  profile_type;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        mDrawerLayout = findViewById(R.id.nav_drawer_activity);
        mToolbar = findViewById(R.id.toolbar);
        mNavigationView = findViewById(R.id.navigationView);
        mNavigationView.setNavigationItemSelectedListener(this);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        mActionBarDrawerToggle.syncState();
        header = mNavigationView.getHeaderView(0);
        // load default fragment
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.container_fragment, new HomeFragment());
        mFragmentTransaction.commit();
        mToolbar.setTitle("Homepage");
        bottomMenu="HomePage";




        addProfileInformation();

    }

    private void addProfileInformation() {


        profile_image = header.findViewById(R.id.profile_image);
        profile_name = header.findViewById(R.id.profile_name);
        profile_type = header.findViewById(R.id.profile_type);
        logout = header.findViewById(R.id.logout);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users")
                .child(user.getUid());
        final String[] imagename = new String[1];
        databaseReference
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserModel model = snapshot.getValue(UserModel.class);
                        final String[] url = new String[2];
                        url[0] = model.getPersonalInformationModel().getImagelink();
                        url[1] = model.getPersonalInformationModel().getImagename();
                        //getUrlImage(u.getPersonalInformationModel().getImagename(), holder.userImage);
                        UrlImageModel urlImageModel= new UrlImageModel();
                        urlImageModel.getUrlImage(url,profile_image);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




    }

    @Override
    public void onBackPressed() {
        mToolbar.findViewById(R.id.toolbar);
        switch (mToolbar.getTitle().toString()){
            case "Homepage":{
                message();
                break;
            }
            case "Freelancer Information":{
                bottomMenu="My Freelancers";
                setFragmentChange(bottomMenu, new HomeFragment());
                break;
            }
            case "Rediger un avis":{
                setFragmentChange("Freelancer Information", new FreelancerListOnClickFragment());
                break;
            }
            case "Tous les avis":{
                setFragmentChange("Freelancer Information", new FreelancerListOnClickFragment());
                break;
            }

            default:{
                setFragmentChange("Homepage", new HomeFragment());
                bottomMenu="HomePage";
                break;
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        if (menuItem.getItemId() == R.id.nav_home) {
            setFragmentChange("Homepage", new HomeFragment());
        }
        if (menuItem.getItemId() == R.id.nav_favorite) {
            setFragmentChange("Favorite", new FavoriteFragment());
        }
        switch (menuItem.getItemId()) {
            case R.id.nav_compte: {
                setFragmentChange("Account", new AccountFragment());
                break;
            }
            case R.id.nav_about_us: {
                setFragmentChange("About PwòfPam", new AboutUsFragment());
                break;
            }
            case R.id.nav_help_comment: {
                setFragmentChange("Help and Comments", new HelpCommentFragment());
                break;
            }
            case R.id.nav_quit: {
                mToolbar.setTitle("Quit");
                break;
            }
            case R.id.nav_modepaiment: {
                setFragmentChange("Method of Payment", new PaymentMethodFragment());
                break;
            }
            case R.id.nav_setting: {
                setFragmentChange("Setting", new SettingFragment());
                break;
            }
        }
        return true;
    }
    public void setFragmentChange (String name, Fragment fragment){
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.container_fragment, fragment);
        mFragmentTransaction.commit();
        toolBarTitle(name);
    }
    @Override
    public void toolBarTitle(String fragment) {
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle(fragment);
    }

    public  void message(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit");
        // set message
        builder.setMessage("Do you really want to exit PwòfPam ?");
        // set icon
        builder.setIcon(R.drawable.quit);
        // set cancelable
        builder.setCancelable(true);
        // set Yes and No button
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                System.exit(0);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        // create dialog
        AlertDialog alertDialog = builder.create();
        // show dialog
        alertDialog.show();
    }

    @Override
    public void onButtonSelected() {

    }
    @Override
    public void onClickFreelancer(String name) {
    setFragmentChange("Freelancer Information", new FreelancerListOnClickFragment());
    }

    @Override
    public void redigerAvis() {
        setFragmentChange("Rediger un avis", new AvisFreelancerFragment());
    }

    @Override
    public void allAvis() {
        setFragmentChange("Tous les avis", new AvisDisplayFreelancerFragment());
    }

    public void signOut(View view){
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));

                    }
                });
    }
    public Bundle getFreelancerData(){
        String firstname = getIntent().getStringExtra("firstname");
        String lastname = getIntent().getStringExtra("lastname");
        String sexe = getIntent().getStringExtra("sexe");
        String imagelink = getIntent().getStringExtra("imagelink");
        String imagename = getIntent().getStringExtra("imagename");



        //UserModel model = (UserModel) getIntent().getParcelableExtra("model");

        Bundle fd = new Bundle();
        fd.putString("firstname", firstname);
        fd.putString("lastname", lastname);
        fd.putString("sexe", sexe);
        fd.putString("imagelink", imagelink);
        fd.putString("imagename", imagename);


        return fd;
    }

}