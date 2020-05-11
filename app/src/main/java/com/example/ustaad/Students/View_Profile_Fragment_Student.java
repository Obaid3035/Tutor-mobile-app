package com.example.ustaad.Students;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ustaad.R;
import com.example.ustaad.Students.Model.Student;
import com.example.ustaad.Teacher.Model.Teacher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class View_Profile_Fragment_Student extends Fragment {


    ImageView imageView,profile;
    TextView name,email,mobile,education;
    Button interact;
    DatabaseReference reference,reference1;
    FirebaseUser firebaseUser;
    String profileId;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        name = (TextView) getView().findViewById(R.id.name_student);
        profile = (ImageView) getView().findViewById(R.id.View_image_student);
        email = (TextView) getView().findViewById(R.id.email_student);
        mobile = (TextView) getView().findViewById(R.id.mobile_student);
        education = (TextView) getView().findViewById(R.id.education_student);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences preferences = getContext().getSharedPreferences("PREFS",Context.MODE_PRIVATE);
        profileId = preferences.getString("profileId_student","none");
        reference = FirebaseDatabase.getInstance().getReference("Students").child(profileId);



        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Student student = dataSnapshot.getValue(Student.class);
                String s = student.getStudentname();
                String s1 = student.getStudentemail();
                String s2 = student.getStudentmobile();
                String s3 = student.getStudentgrade();
                name.setText(s.toUpperCase());
                email.setText(s1);
                mobile.setText(s2);
                education.setText(s3);
                Glide.with(getActivity()).load(student.getStudentimage()).into(profile);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });









    }







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view__profile__fragment__student, container, false);
    }




}
