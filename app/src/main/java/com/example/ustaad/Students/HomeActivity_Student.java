package com.example.ustaad.Students;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.ustaad.R;
import com.example.ustaad.Teacher.Fragment.HomeFragment;
import com.example.ustaad.Teacher.Fragment.NotificationFragment;
import com.example.ustaad.Teacher.Fragment.Profile_Fragment;
import com.example.ustaad.Teacher.HomeActivity;
import com.example.ustaad.Teacher.PostActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity_Student extends AppCompatActivity {

    Fragment selectedfragment = null;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__student);


        setContentView(R.layout.activity_home__student);
        mContext = HomeActivity_Student.this;
        //setupFirebaseAuth();
        setupBottomNavigationView();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        checkCurrentUser(user);

    }

    private void setupBottomNavigationView() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation1);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container1, new Student_HomeFragment())
                .commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home_student:
                        selectedfragment = new Student_HomeFragment();
                        break;
                    case R.id.action_post_student:
                        Intent intent1 = new Intent(HomeActivity_Student.this, PostActivity_Student.class);
                        startActivity(intent1);
                        break;
                    case R.id.action_notification_student:
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(HomeActivity_Student.this, SignIn.class);
                        startActivity(intent);
                        break;
                    case R.id.action_profile_student:
                        selectedfragment = new Student_ProfileFragment();
                        break;
                }
                if (selectedfragment != null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container1, selectedfragment)
                            .commit();

                }
                return true;
            }
        });


    }

    private void checkCurrentUser(FirebaseUser user) {
        if (user == null) {
            Intent intent = new Intent(HomeActivity_Student.this, SignIn.class);
            startActivity(intent);
            finish();
        }
    }

}
