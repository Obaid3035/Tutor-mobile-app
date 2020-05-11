package com.example.ustaad.Students;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ustaad.R;
import com.example.ustaad.Students.Model.Student;
import com.example.ustaad.Teacher.Edit_profileActivity;
import com.example.ustaad.Teacher.Model.Teacher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Student_ProfileFragment extends Fragment implements View.OnClickListener {

    ImageView imageView,profile;
    TextView name,email,mobile,education,bio,address;
    DatabaseReference reference;

    FirebaseUser firebaseUser;



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UiView();
        imageView.setOnClickListener(this);
        //// PERSONAL DATA RETREIVE FROM FIREBASE ////
        reference = FirebaseDatabase.getInstance().getReference("Students").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (getContext() == null){
                    return;
                }

                Student student = dataSnapshot.getValue(Student.class);

                name.setText(student.getStudentname().toUpperCase());
                email.setText(student.getStudentemail());
                mobile.setText(student.getStudentmobile());
                education.setText(student.getStudentgrade());
                bio.setText(student.getStudentfield());
                Glide.with(getContext()).load(student.getStudentimage()).into(profile);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void UiView(){
        imageView = (ImageView) getView().findViewById(R.id.option_student);
        name = (TextView) getView().findViewById(R.id.name_student);
        email = (TextView) getView().findViewById(R.id.email_student);
        mobile = (TextView) getView().findViewById(R.id.mobile_student);
        education = (TextView) getView().findViewById(R.id.education_student);
        bio = (TextView) getView().findViewById(R.id.bio_student);
        address = (TextView) getView().findViewById(R.id.address_student);
        profile = (ImageView) getView().findViewById(R.id.Profile_Image_student);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    }


    private void Settings(){
        final PopupMenu popupMenu = new PopupMenu(getActivity(),imageView);
        popupMenu.getMenuInflater().inflate(R.menu.option, popupMenu.getMenu());
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.Logout:
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getActivity(), SignIn.class);
                        startActivity(intent);
                        return true;
                    case R.id.edit_profile:
                        Intent intent1 = new Intent(getActivity(), Edit_profileActivity_Students.class);
                        startActivity(intent1);
                        return true;
                    default:
                        return false;
                }
            }
        });

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.option_student:
                Settings();
                break;

        }
    }












    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student__profile, container, false);
    }

}
