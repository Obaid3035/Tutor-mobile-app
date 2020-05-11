package com.example.ustaad.Students;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ustaad.R;
import com.example.ustaad.Students.Model.Product_Student;
import com.example.ustaad.Teacher.HomeActivity;
import com.example.ustaad.Teacher.Model.Product;
import com.example.ustaad.Teacher.PostActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostActivity_Student extends AppCompatActivity {



    EditText description;
    Button btn;
    ImageView btn1;
    DatabaseReference ref,ref2,ref3;
    TextView address;
    Spinner spinner,spinner2;
    String name,subject,Address,timing,image;
    ArrayAdapter<CharSequence> adapter,adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post__student);


        ref = FirebaseDatabase.getInstance().getReference("Post_Student");
        description = (EditText) findViewById(R.id.descrption_student);
        btn = (Button) findViewById(R.id.btn_post_student);
        btn1 = (ImageView) findViewById(R.id.back_student);
        spinner = (Spinner) findViewById(R.id.spinner_subject_student);
        spinner2 = (Spinner) findViewById(R.id.spinner_timing_student);

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
                Intent intent = new Intent(PostActivity_Student.this, HomeActivity_Student.class);
                startActivity(intent);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addPost();
                Intent intent2 = new Intent(PostActivity_Student.this, HomeActivity_Student.class);
                startActivity(intent2);

            }
        });




    }
    public void addPost(){
        final String post_description = description.getText().toString().trim();
        String unique = ref.push().getKey();
        Product_Student product_student = new Product_Student( FirebaseAuth.getInstance().getCurrentUser().getUid() , post_description, subject,timing);
        ref.child(unique).setValue(product_student);
        description.setText("");




    }

}
