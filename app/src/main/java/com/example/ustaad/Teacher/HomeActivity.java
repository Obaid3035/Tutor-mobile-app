package com.example.ustaad.Teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.ustaad.Students.HomeActivity_Student;
import com.example.ustaad.Teacher.Fragment.HomeFragment;
import com.example.ustaad.Teacher.Fragment.NotificationFragment;
import com.example.ustaad.Teacher.Fragment.Profile_Fragment;
import com.example.ustaad.Students.SignIn;
import com.example.ustaad.R;
import com.example.ustaad.Teacher.Fragment.View_Profile_Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    Fragment selectedfragment = null;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mContext = HomeActivity.this;

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        setupBottomNavigationView();

        if (getIntent().hasExtra("hey")) {


            Intent intent = getIntent();
            String id = intent.getStringExtra("hey");
            Log.e("jan_id", id);

            Bundle bundle = new Bundle();

            bundle.putString("profileId",id);
            View_Profile_Fragment fragment = new View_Profile_Fragment();
            fragment.setArguments(bundle);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

        }















    }

    private void setupBottomNavigationView() {


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        selectedfragment = new HomeFragment();
                        break;
                    case R.id.action_post:
                        Intent intent = new Intent ( HomeActivity.this, PostActivity.class );
                        startActivity(intent);
                        break;
                    case R.id.action_notification:
                        selectedfragment = new NotificationFragment();
                        break;
                    case R.id.action_profile:
                        selectedfragment = new Profile_Fragment();
                        break;
                }
                if (selectedfragment != null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, selectedfragment)
                            .commit();

                }
                return true;
            }
        });


    }

    //Check Current user





}

