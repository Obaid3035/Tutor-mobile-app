package com.example.ustaad.Teacher.Fragment;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ustaad.R;
import com.example.ustaad.Students.ProfileActivity;
import com.example.ustaad.Students.SignIn;
import com.example.ustaad.Teacher.Edit_profileActivity;
import com.example.ustaad.Teacher.Model.Teacher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile_Fragment extends Fragment implements View.OnClickListener {

    ImageView imageView,profile;
    TextView name,email,mobile,education,bio,address;
    DatabaseReference reference;

    FirebaseUser firebaseUser;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile__fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        UiView();
        imageView.setOnClickListener(this);
        //// PERSONAL DATA RETREIVE FROM FIREBASE ////
        reference = FirebaseDatabase.getInstance().getReference("Teacher").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (getContext() == null){
                    return;
                }

                Teacher teacher = dataSnapshot.getValue(Teacher.class);

                name.setText(teacher.getTeachername().toUpperCase());
                email.setText(teacher.getTeacheremail());
                mobile.setText(teacher.getTeachermobile());
                education.setText(teacher.getTeachergrade());
                bio.setText(teacher.getTeacherfield());
                address.setText(teacher.getTeacheraddress());
                Glide.with(getContext()).load(teacher.getTeacherimage()).into(profile);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }



    private void UiView(){
        imageView = (ImageView) getView().findViewById(R.id.option1);
        name = (TextView) getView().findViewById(R.id.name_ustaad);
        email = (TextView) getView().findViewById(R.id.email_ustaad);
        mobile = (TextView) getView().findViewById(R.id.mobile_ustaad);
        education = (TextView) getView().findViewById(R.id.education_ustaad);
        bio = (TextView) getView().findViewById(R.id.bio_ustaad);
        address = (TextView) getView().findViewById(R.id.address_ustaad);
        profile = (ImageView) getView().findViewById(R.id.Profile_Image);
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
                        Intent intent1 = new Intent(getActivity(), Edit_profileActivity.class);
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

            case R.id.option1:
                Settings();
                break;

        }
    }
}


