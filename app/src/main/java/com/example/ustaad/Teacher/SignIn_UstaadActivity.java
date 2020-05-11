package com.example.ustaad.Teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ustaad.R;
import com.example.ustaad.ForgotPasswordActivity;
import com.example.ustaad.Students.SignIn;
import com.example.ustaad.Teacher.Model.Teacher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

public class SignIn_UstaadActivity extends AppCompatActivity implements View.OnClickListener {

    EditText Email,Password;
    Button Btn;
    FirebaseAuth auth;
    TextView info,forgotpassword,goto_shagird;
    private DatabaseReference myref;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in__ustaad);



        UIViews();
        Animation animation = AnimationUtils.loadAnimation(SignIn_UstaadActivity.this,R.anim.fadein);
        view.startAnimation(animation);
        Btn.setOnClickListener(this);
        info.setOnClickListener(this);
        forgotpassword.setOnClickListener(this);
        goto_shagird.setOnClickListener(this);
    }
    private void UIViews(){
        auth = FirebaseAuth.getInstance();
        /*if (auth.getCurrentUser() != null) {
            startActivity(new Intent(SignIn.this, MainActivity.class));
            finish();
        }*/

        Email = (EditText) findViewById(R.id.etext_ustaad);
        Password = (EditText) findViewById(R.id.pwtext_ustaad);
        Btn = (Button) findViewById(R.id.btn_sign_in_ustaad);
        info = (TextView) findViewById(R.id.info_signup_ustaad);
        forgotpassword = (TextView) findViewById(R.id.info_forgotpw_ustaad);
        goto_shagird = (TextView) findViewById(R.id.info_student);
        view = (View) findViewById(R.id.anime1);

    }

    private  void loginuser(){


        String password = Password.getText().toString().trim();
        String email = Email.getText().toString().trim();

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
        /*if (!password.matches("[a-zA-Z0-9]+")){
            Password.setError("must contain number");
            Password.requestFocus();
            return;
        }*/



        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(SignIn_UstaadActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if (!task.isSuccessful()) {
                    // there was an error
                    Toast.makeText(SignIn_UstaadActivity.this, "Wrong Email or Password.", Toast.LENGTH_LONG).show();
                } else {
                    //Toast.makeText(SignIn_UstaadActivity.this, "You Have Successfully Logged In.",
                      //      Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(SignIn_UstaadActivity.this, HomeActivity.class);
                    //startActivity(intent);
                    //finish();
                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Log.e("UStaad id", id);
                    myref = FirebaseDatabase.getInstance().getReference("Teacher");
                    String deviceToken = FirebaseInstanceId.getInstance().getToken();
                    String Current_user_id = auth.getCurrentUser().getUid();
                    myref.child(Current_user_id).child("device_token").setValue(deviceToken).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            myref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.hasChild(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                        myref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                Teacher teacher = dataSnapshot.getValue(Teacher.class);
                                                //Log.e("Studentvalue", ""+teacher.getTeachercheck());
                                                //check = student.getStudentcheck();
                                                if (teacher.getTeachercheck() == 2) {
                                                    Intent intent = new Intent(SignIn_UstaadActivity.this, HomeActivity.class);
                                                    intent.putExtra("Name", FirebaseAuth.getInstance().getCurrentUser().getUid());
                                                    startActivity(intent);
                                                } else {
                                                    Toast.makeText(SignIn_UstaadActivity.this, "Wrong Email or Password.", Toast.LENGTH_LONG).show();

                                                }

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                    }
                                    else {
                                        Toast.makeText(SignIn_UstaadActivity.this, "Wrong Email or Password.", Toast.LENGTH_LONG).show();

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    });


                }
            }
        });






    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_sign_in_ustaad:
                loginuser();
                break;
            case R.id.info_signup_ustaad:
                Intent intent = new Intent(SignIn_UstaadActivity.this, SignUp_UstaadActivity.class);
                startActivity(intent);
                break;

            case R.id.info_forgotpw_ustaad:
                Intent intent1 = new Intent(SignIn_UstaadActivity.this, ForgotPasswordActivity.class);
                startActivity(intent1);
                break;
            case R.id.info_student:
                Intent intent2 = new Intent(SignIn_UstaadActivity.this, SignIn.class);
                startActivity(intent2);
                overridePendingTransition(0,0);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                break;


        }
    }
}
