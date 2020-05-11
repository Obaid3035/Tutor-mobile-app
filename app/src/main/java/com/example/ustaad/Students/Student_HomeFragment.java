package com.example.ustaad.Students;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ustaad.R;
import com.example.ustaad.Students.Model.Product_Student;
import com.example.ustaad.Students.Model.Student;
import com.example.ustaad.Teacher.Adapter.ProductAdapter;
import com.example.ustaad.Teacher.Model.Product;
import com.example.ustaad.Teacher.Model.Teacher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Student_HomeFragment extends Fragment {


    RecyclerView recyclerView;
    ProductAdapter_Student adapter;
    List<Product_Student> productList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,reference;
    ImageView img;
    FirebaseUser firebaseUser;
    TextView textView;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        productList = new ArrayList<>();
        recyclerView = (RecyclerView)getView().findViewById(R.id.recycler_view_student);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        img = (ImageView)getView().findViewById(R.id.home_image_student);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Students").child(firebaseUser.getUid());



        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (getActivity() == null) {
                    return;
                }
                Student student = dataSnapshot.getValue(Student.class);
                Glide.with(getActivity()).load(student.getStudentimage()).into(img);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Post_Student");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Product_Student p = dataSnapshot1.getValue(Product_Student.class);
                    productList.add(p);
                }
                adapter = new ProductAdapter_Student(getActivity(), productList);
                recyclerView.setAdapter(adapter);

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
        return inflater.inflate(R.layout.fragment_student__home, container, false);
    }

}
