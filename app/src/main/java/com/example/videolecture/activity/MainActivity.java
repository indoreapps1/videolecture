package com.example.videolecture.activity;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.videolecture.R;
import com.example.videolecture.fragment.ConditionsFragment;
import com.example.videolecture.fragment.DashboardFragment;
import com.example.videolecture.fragment.HomeFragment;
import com.example.videolecture.fragment.AboutUsFragment;
import com.example.videolecture.fragment.PolicyFragment;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        Fragment fragment;

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new HomeFragment();
//                    mTextMessage.setText(R.string.title_home);
                    openFragment(fragment);
                    return true;
//                case R.id.navigation_dashboard:
//                    fragment = new DashboardFragment();
//                    mTextMessage.setText(R.string.title_dashboard);
//                    openFragment(fragment);
//                    return true;
                case R.id.navigation_about:
                    fragment = new AboutUsFragment();
                    openFragment(fragment);
                    return true;
                case R.id.navigation_privacy:
                    fragment = new PolicyFragment();
//                    mTextMessage.setText(R.string.title_policy);
                    openFragment(fragment);
                    return true;
                case R.id.navigation_conditions:
                    fragment = new ConditionsFragment();
                    openFragment(fragment);
//                    mTextMessage.setText(R.string.title_conditions);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        HomeFragment homeFragment=new HomeFragment();
        openFragment(homeFragment);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void openFragment(Fragment fragment){
        FragmentManager manager=getSupportFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.container, fragment)
                .commit();
    }

    public void setActionBartitle(String title){
        getSupportActionBar().setTitle(title);
    }
}
