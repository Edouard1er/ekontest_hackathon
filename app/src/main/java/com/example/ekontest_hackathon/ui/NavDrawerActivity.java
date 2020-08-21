package com.example.ekontest_hackathon.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.ekontest_hackathon.AvisDisplayFreelancerFragment;
import com.example.ekontest_hackathon.AvisFreelancerFragment;
import com.example.ekontest_hackathon.DocumentFragment;
import com.example.ekontest_hackathon.FreelancerListFragment;
import com.example.ekontest_hackathon.FreelancerListOnClickFragment;
import com.example.ekontest_hackathon.R;
import com.example.ekontest_hackathon.ui.about_us.AboutUsFragment;
import com.example.ekontest_hackathon.ui.account.AccountFragment;
import com.example.ekontest_hackathon.ui.favorite.FavoriteFragment;
import com.example.ekontest_hackathon.ui.help_comment.HelpCommentFragment;
import com.example.ekontest_hackathon.ui.home.HomeFragment;
import com.example.ekontest_hackathon.ui.payment.PaymentMethodFragment;
import com.example.ekontest_hackathon.ui.setting.SettingFragment;
import com.google.android.material.navigation.NavigationView;

public class NavDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        HomeFragment.onItemBottomMenuSelected, DocumentFragment.onFragmentBtnSelected, FreelancerListOnClickFragment.onClickInfoFreelancer, FreelancerListFragment.freelancerInterface {
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mActionBarDrawerToggle;
    Toolbar mToolbar;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
     public static String bottomMenu;


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
        // load default fragment
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.container_fragment, new HomeFragment());
        mFragmentTransaction.commit();
        mToolbar.setTitle("Homepage");
        bottomMenu="HomePage";

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
}