package com.example.ustaad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ustaad.Students.SignIn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText Email1;
    Button btn1;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

         Email1 = (EditText)findViewById(R.id.email_reset);
         btn1 = (Button)findViewById(R.id.btn_reset);
         firebaseAuth = FirebaseAuth.getInstance();





         btn1.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String email1 = Email1.getText().toString();
                 if (email1.equals("")){
                    //Toast.makeText(ForgotPasswordActivity.this,"Please enter email",Toast.LENGTH_SHORT).show();
                     Email1.setError("Enter email");
                     Email1.requestFocus();
                     return;

                 }else {

                     firebaseAuth.sendPasswordResetEmail(email1).addOnCompleteListener(new OnCompleteListener<Void>() {
                         @Override
                         public void onComplete(@NonNull Task<Void> task) {
                             if (task.isSuccessful()){
                                 Toast.makeText(ForgotPasswordActivity.this,"Password send to email"
                                         ,Toast.LENGTH_SHORT).show();
                                 finish();
                                 startActivity(new Intent(ForgotPasswordActivity.this, SignIn.class));
                             }else {
                                 Toast.makeText(ForgotPasswordActivity.this,"Email does not exist"
                                         ,Toast.LENGTH_SHORT).show();
                             }
                         }
                     });

                 }

             }
         });
    }
}
