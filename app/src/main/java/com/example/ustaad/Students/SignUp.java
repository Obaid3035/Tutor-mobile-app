package com.example.ustaad.Students;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ustaad.R;
import com.example.ustaad.Students.Model.Student;
import com.example.ustaad.Teacher.HomeActivity;
import com.example.ustaad.Teacher.SignUp_UstaadActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    EditText Email,Password,Name,Mobile,Interest;
    Button btn;
    private FirebaseAuth auth;
    DatabaseReference ref;
    FirebaseDatabase firebaseDatabase;
    RadioGroup mgender;
    RadioButton mgenderoption;
    String strgender, grade;
    TextView info_signin,info_forgot,goto_ustaad;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    TextView gender;
    View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        UIViews();
        Animation animation = AnimationUtils.loadAnimation(SignUp.this,R.anim.fadein);
        view.startAnimation(animation);
        btn.setOnClickListener(this);
        info_signin.setOnClickListener(this);
        goto_ustaad.setOnClickListener(this);
    }

        private void UIViews(){

            firebaseDatabase = FirebaseDatabase.getInstance();
            ref = FirebaseDatabase.getInstance().getReference("Students");
            auth = FirebaseAuth.getInstance();
            //info_forgot = (TextView) findViewById(R.id.info_forgotpw);
            Mobile = (EditText) findViewById(R.id.mbtext);
            view = (View) findViewById(R.id.anime2);
            Interest = (EditText) findViewById(R.id.interest_text);
            Email = (EditText) findViewById(R.id.emtext);
            Password = (EditText) findViewById(R.id.pwtext);
            btn = (Button) findViewById(R.id.btn_sign_up);
            goto_ustaad = (TextView) findViewById(R.id.goto_ustaad);
            Name = (EditText) findViewById(R.id.nmtext);
            mgender = (RadioGroup) findViewById(R.id.radioGroup);
            info_signin = (TextView) findViewById(R.id.info_sign_in);
            spinner = (Spinner) findViewById(R.id.spin);
            adapter = ArrayAdapter.createFromResource
                    (this,R.array.education_level, android.R.layout.simple_spinner_item);


            mgender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    mgenderoption = mgender.findViewById(i);
                    switch (i){
                        case R.id.male_radio:
                            strgender = mgenderoption.getText().toString();
                            break;
                        case R.id.female_radio:
                            strgender = mgenderoption.getText().toString();
                            break;
                        default:

                    }
                }
            });
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    grade = adapterView.getItemAtPosition(i).toString();


                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {


                }
            });



        }

        private void registeruser(){
            final String name = Name.getText().toString().trim();
            final String email = Email.getText().toString().trim();
            final String password = Password.getText().toString().trim();
            final String mobile = Mobile.getText().toString().trim();
            final String interest = Interest.getText().toString().trim();
            //final String id = FirebaseAuth.getInstance().getCurrentUser().getUid();


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
            if (!password.matches("[a-zA-Z0-9]+")){
                Password.setError("must contain number");
                Password.requestFocus();
                return;
            }
            if (password.length() < 7){
                Password.setError("Password Length must be 7 or greater");
                Password.requestFocus();
                return;

            }
            if (name.isEmpty()){
                Name.setError("Name Required");
                Name.requestFocus();
                return;
            }
            if (!name.matches("[a-zA-Z]+")){
                Name.setError("only Aplhabets");
                Name.requestFocus();
                return;
            }
            if (mobile.isEmpty()){
                Mobile.setError("Mobile no. Required");
                Mobile.requestFocus();
                return;
            }
            if (!mobile.matches("[0-9]+")){
                Mobile.setError("only number");
                Mobile.requestFocus();
                return;
            }
            if (interest.isEmpty()){
                Interest.setError("Field of Interest Required");
                Interest.requestFocus();
                return;
            }
            if (!interest.matches("[a-zA-z]+")){
                Interest.setError("Alphabets only");
                Interest.requestFocus();
                return;
            }

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUp.this,
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()) {
                                Toast.makeText(SignUp.this, "Email Already Exisit.",
                                        Toast.LENGTH_SHORT).show();
                            }else {
                                Student student = new Student(1, FirebaseAuth.getInstance().getCurrentUser().getUid(), name,email,password, mobile, interest, strgender, grade,"");
                                ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(student);

                                Toast.makeText(SignUp.this, "You Have Successfully Registered",
                                        Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUp.this, HomeActivity_Student.class));
                                finish();
                            }
                        }
                    });



        }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_sign_up:
                registeruser();
                break;
            case R.id.info_sign_in:
                Intent intent = new Intent(SignUp.this, SignIn.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                break;
            case R.id.goto_ustaad:
                Intent intent2 = new Intent(SignUp.this, SignUp_UstaadActivity.class);
                startActivity(intent2);
                overridePendingTransition(0,0);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                break;


        }
    }
}
