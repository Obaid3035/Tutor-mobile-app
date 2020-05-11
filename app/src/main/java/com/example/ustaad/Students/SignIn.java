package com.example.ustaad.Students;

import android.content.Intent;
import android.os.Bundle;

import com.example.ustaad.ForgotPasswordActivity;
import com.example.ustaad.R;
import com.example.ustaad.Students.Model.Student;
import com.example.ustaad.Teacher.HomeActivity;
import com.example.ustaad.Teacher.SignIn_UstaadActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignIn extends AppCompatActivity implements View.OnClickListener {

    private EditText Email,Password;
    private Button Btn;
    private FirebaseAuth mAuth;
    private TextView info,forgotpassword,goto_shagird;
    private DatabaseReference myref;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        UIViews();
        Animation animation = AnimationUtils.loadAnimation(SignIn.this,R.anim.fadein);
        view.startAnimation(animation);
        // Buttons Clicked
        Btn.setOnClickListener(this);
        info.setOnClickListener(this);
        forgotpassword.setOnClickListener(this);
        goto_shagird.setOnClickListener(this);
    }

    private void UIViews(){
        mAuth = FirebaseAuth.getInstance();
        Email = (EditText) findViewById(R.id.etext);
        Password = (EditText) findViewById(R.id.pwtext);
        Btn = (Button) findViewById(R.id.btn_sign_in);
        info = (TextView) findViewById(R.id.info_signup);
        forgotpassword = (TextView) findViewById(R.id.info_forgotpw);
        goto_shagird = (TextView) findViewById(R.id.info_ustaad);
        view = (View) findViewById(R.id.anime2);
    }


    private  void loginuser(){
        String password = Password.getText().toString().trim();
        final String email = Email.getText().toString().trim();

        if (email.isEmpty()){
            Email.setError("Email Required");
            Email.requestFocus();
            return;
        }
        if (!email.matches("[a-zA-Z0-9_-]+@[a-z]+\\.+[a-z]+")){
            Email.setError("Invalid Email");
            Email.requestFocus();
            return;
        }

        if (password.isEmpty()){
            Password.setError("Password Required");
            Password.requestFocus();
            return;
        }

        if (password.length() < 7){
            Password.setError("Password Length must be 7 or greater");
            Password.requestFocus();
            return;
        }


        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                //Student student = new Student();


                if (!task.isSuccessful()) {
                    // there was an error
                        Toast.makeText(SignIn.this, "Wrong Email or .", Toast.LENGTH_LONG).show();
                } else {

                    myref = FirebaseDatabase.getInstance().getReference("Students");

                    myref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                                myref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Student student = dataSnapshot.getValue(Student.class);

                                        Log.e("Studentvalue", ""+student.getStudentcheck());
                                        //check = student.getStudentcheck();
                                        if(student.getStudentcheck() == 1) {
                                            Intent intent = new Intent( SignIn.this, HomeActivity_Student.class );
                                            startActivity(intent);
                                            finish();
                                        }
                                        else {
                                            Toast.makeText(SignIn.this, "Wrong Email.", Toast.LENGTH_LONG).show();

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }

                            else {
                                Toast.makeText(SignIn.this, "Wrong Email and .", Toast.LENGTH_LONG).show();

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                   // Toast.makeText(SignIn.this, "You Have Successfully Logged In.",
                     //       Toast.LENGTH_SHORT).show();

                }
            }
        });

      /*  if (mAuth.getCurrentUser() != null){

            Intent intent = new Intent(SignIn.this, HomeActivity.class);
            intent.putExtra ( "TextBox", email);
            startActivity(intent);
            finish();

        }*/



    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_sign_in:
                loginuser();
                break;
            case R.id.info_signup:
                Intent intent = new Intent(SignIn.this, SignUp.class);
                startActivity(intent);
                break;

            case R.id.info_forgotpw:
                Intent intent1 = new Intent(SignIn.this, ForgotPasswordActivity.class);
                startActivity(intent1);
                break;
            case R.id.info_ustaad:
                Intent intent2 = new Intent(SignIn.this, SignIn_UstaadActivity.class);
                startActivity(intent2);
                overridePendingTransition(0,0);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                break;


        }
    }



}
