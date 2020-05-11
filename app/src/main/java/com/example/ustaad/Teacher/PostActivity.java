package com.example.ustaad.Teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ustaad.R;
import com.example.ustaad.Teacher.Fragment.View_Profile_Fragment;
import com.example.ustaad.Teacher.Model.Product;
import com.example.ustaad.Teacher.Model.Teacher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PostActivity extends AppCompatActivity {

    EditText description;
    Button btn;
    ImageView btn1;
    DatabaseReference ref,ref2,ref3;
    TextView address;
    Spinner spinner,spinner2;
    String name,subject,Address,timing,image,id;
    ArrayAdapter<CharSequence> adapter,adapter2;
    Fragment selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);








//
//
//
//                 getSupportFragmentManager()
//                        .beginTransaction()
//                        .add(R.id.fragment_container, new View_Profile_Fragment())
//                        .commit();
//                 selected = new View_Profile_Fragment();
//
//
////                Fragment fragment = new View_Profile_Fragment();//Get Fragment Instance
////                Bundle data = new Bundle();//Use bundle to pass data
////                data.putString("hey", id);//put string, int, etc in bundle with a key value
////                fragment.setArguments(data);//Finally set argument bundle to fragment
////
////                ((FragmentActivity)getApplicationContext()).getSupportFragmentManager()
////                        .beginTransaction()
////                        .replace(R.id.fragment_container,new View_Profile_Fragment())
////                        .commit();
//
//
//            }







        ref = FirebaseDatabase.getInstance().getReference("Post_Ustaad");
        ref3 = FirebaseDatabase.getInstance().getReference("Teacher").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//        ref2 = FirebaseDatabase.getInstance().getReference("Students/hw8ykrag6zZeMX4INSAXzSKDhtg2");
//        ref2.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Student student = dataSnapshot.getValue(Student.class);
//                String string = student.getStudentname();
//                textView.setText(string);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

         //*/

        description = (EditText) findViewById(R.id.descrp);
        btn = (Button) findViewById(R.id.btn_post);
        btn1 = (ImageView) findViewById(R.id.back);
        address = (TextView) findViewById(R.id.address);
        spinner = (Spinner) findViewById(R.id.spinner_subject);
        spinner2 = (Spinner) findViewById(R.id.spinner_timing);

        adapter = ArrayAdapter.createFromResource(this,R.array.Subject,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        adapter2 = ArrayAdapter.createFromResource(this,R.array.Timing,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 timing= adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                subject = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addPost();
                Intent intent2 = new Intent(PostActivity.this, HomeActivity.class);
                startActivity(intent2);

            }
        });

        ref3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Teacher teacher = dataSnapshot.getValue(Teacher.class);
                address.setText(teacher.getTeacheraddress());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
    public void addPost(){
        final String post_description = description.getText().toString().trim();
        String unique = ref.push().getKey();
        Product product = new Product( FirebaseAuth.getInstance().getCurrentUser().getUid() , post_description, subject,timing);
        ref.child(unique).setValue(product);
        description.setText("");




    }
}
