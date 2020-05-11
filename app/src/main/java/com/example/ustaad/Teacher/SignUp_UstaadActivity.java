package com.example.ustaad.Teacher;

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
import com.example.ustaad.Students.SignUp;
import com.example.ustaad.Teacher.Model.Teacher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class SignUp_UstaadActivity extends AppCompatActivity implements View.OnClickListener {

    EditText Email,Password,Name,Mobile,Interest,Address;
    Button btn;
    private FirebaseAuth auth;
    DatabaseReference ref;
    FirebaseDatabase firebaseDatabase;
    RadioGroup mgender;
    RadioButton mgenderoption;
    String strgender, grade;
    TextView info_signin,info_forgot,goto_student;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up__ustaad);

        UIViews();
        Animation animation = AnimationUtils.loadAnimation(SignUp_UstaadActivity.this,R.anim.fadein);
        view.startAnimation(animation);
        btn.setOnClickListener(this);
        info_signin.setOnClickListener(this);
        goto_student.setOnClickListener(this);
    }

    private void UIViews(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("Teacher");
        auth = FirebaseAuth.getInstance();

        //info_forgot = (TextView) findViewById(R.id.info_forgotpw);
        Mobile = (EditText) findViewById(R.id.mbtext_ustaad);
        Interest = (EditText) findViewById(R.id.interest_text_ustaad);
        view = (View) findViewById(R.id.anim1);
        Email = (EditText) findViewById(R.id.emtext_ustaad);
        Password = (EditText) findViewById(R.id.pwtext_ustaad);
        goto_student = (TextView) findViewById(R.id.goto_student);
        btn = (Button) findViewById(R.id.btn_sign_up_ustaad);
        Name = (EditText) findViewById(R.id.nmtext_ustaad);
        mgender = (RadioGroup) findViewById(R.id.radioGroup_ustaad);
        info_signin = (TextView) findViewById(R.id.info_sign_in_ustaad);
        spinner = (Spinner) findViewById(R.id.spin_ustaad);
        Address = (EditText) findViewById(R.id.address_ustaad);
        adapter = ArrayAdapter.createFromResource
                (this,R.array.education_level_ustaad, android.R.layout.simple_spinner_item);

        mgender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                mgenderoption = mgender.findViewById(i);
                switch (i){
                    case R.id.male_radio_ustaad:
                        strgender = mgenderoption.getText().toString();
                        break;
                    case R.id.female_radio_ustaad:
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
        final String address = Address.getText().toString().trim();
//        final String id = FirebaseAuth.getInstance().getCurrentUser().getUid();


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
//        if (!password.matches("[a-zA-Z0-9]+")){
//            Password.setError("must contain number");
//            Password.requestFocus();
//            return;
//        }
        if (password.length() < 7){
            Password.setError("Password Length must be 7 or greater");
            Password.requestFocus();
            return;

        }
//        if (name.isEmpty()){
//            Name.setError("Name Required");
//            Name.requestFocus();
//            return;
//        }
//        if (!name.matches("[a-zA-Z]+")){
//            Name.setError("only Aplhabets");
//            Name.requestFocus();
//            return;
//        }
        if (mobile.isEmpty()){
            Mobile.setError("Mobile no. Required");
            Mobile.requestFocus();
            return;
        }
//        if (!mobile.matches("[0-9]+")){
//            Mobile.setError("only number");
//            Mobile.requestFocus();
//            return;
//        }
        if (interest.isEmpty()){
            Interest.setError("Field of Interest Required");
            Interest.requestFocus();
            return;
        }
//        if (!interest.matches("[a-zA-z]+")){
//            Interest.setError("Alphabets only");
//            Interest.requestFocus();
//            return;
//        }
        if (address.isEmpty()){
            Address.setError("Address Required");
            Address.requestFocus();
            return;
        }


        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUp_UstaadActivity.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUp_UstaadActivity.this, "Email Already Exisit.",
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            String deviceToken = FirebaseInstanceId.getInstance().getToken();
                            Teacher teacher = new Teacher(2, FirebaseAuth.getInstance().getCurrentUser().getUid(), name,email,password, mobile, interest, strgender, grade,address,"",deviceToken);
                            ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(teacher);

                            Toast.makeText(SignUp_UstaadActivity.this, "You Have Successfully Registered",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUp_UstaadActivity.this, HomeActivity.class));
                            finish();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_sign_up_ustaad:
                registeruser();
                break;
            case R.id.info_sign_in_ustaad:
                Intent intent = new Intent(SignUp_UstaadActivity.this, SignIn_UstaadActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                break;
            case R.id.goto_student:
                Intent intent2 = new Intent(SignUp_UstaadActivity.this, SignUp.class);
                startActivity(intent2);
                overridePendingTransition(0,0);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                break;


        }
    }
}
