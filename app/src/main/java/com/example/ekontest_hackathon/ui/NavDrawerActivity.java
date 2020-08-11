package com.example.ekontest_hackathon.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.ekontest_hackathon.R;
import com.example.ekontest_hackathon.ui.about_us.AboutUsFragment;
import com.example.ekontest_hackathon.ui.account.AccountFragment;
import com.example.ekontest_hackathon.ui.favorite.FavoriteFragment;
import com.example.ekontest_hackathon.ui.help_comment.HelpCommentFragment;
import com.example.ekontest_hackathon.ui.home.HomeFragment;
import com.example.ekontest_hackathon.ui.payment.PaymentMethodFragment;
import com.example.ekontest_hackathon.ui.setting.SettingFragment;
import com.google.android.material.navigation.NavigationView;

public class NavDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mActionBarDrawerToggle;
    Toolbar mToolbar;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        mDrawerLayout = findViewById(R.id.nav_drawer_activity);
        mToolbar = findViewById(R.id.toolbar);
      //  setSupportActionBar(mToolbar);
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
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        if (menuItem.getItemId() == R.id.nav_home) {
            // load home fragment
            mFragmentManager = getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.container_fragment, new HomeFragment());
            mFragmentTransaction.commit();
        }

        if (menuItem.getItemId() == R.id.nav_favorite) {
            // load dashboard fragment
            mFragmentManager = getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.container_fragment, new FavoriteFragment());
            mFragmentTransaction.commit();
        }

        switch (menuItem.getItemId()) {
            case R.id.nav_compte: {
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.container_fragment, new AccountFragment());
                mFragmentTransaction.commit();
                break;
            }

            case R.id.nav_about_us: {
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.container_fragment, new AboutUsFragment());
                mFragmentTransaction.commit();
                break;
            }

            case R.id.nav_help_comment: {
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.container_fragment, new HelpCommentFragment());
                mFragmentTransaction.commit();
                break;
            }
            case R.id.nav_quit: {
                break;
            }

            case R.id.nav_modepaiment: {
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.container_fragment, new PaymentMethodFragment());
                mFragmentTransaction.commit();
                break;
            }

            case R.id.nav_setting: {
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.container_fragment, new SettingFragment());
                mFragmentTransaction.commit();
                break;
            }
        }
        return true;
    }
}