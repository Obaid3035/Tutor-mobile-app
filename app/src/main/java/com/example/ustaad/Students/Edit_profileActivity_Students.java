package com.example.ustaad.Students;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ustaad.R;
import com.example.ustaad.Students.Model.Student;
import com.example.ustaad.Teacher.Edit_profileActivity;
import com.example.ustaad.Teacher.Model.Teacher;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.HashMap;

public class Edit_profileActivity_Students extends AppCompatActivity {

    TextView choose_image,save;
    private final int PICK_IMAGE_REQUEST = 1;
    private Uri filePath;
    EditText name,mobile,address;
    ImageView close;
    ImageView circleImageView;
    StorageReference storageReference;
    FirebaseStorage storage;
    private StorageTask uploadTask;
    DatabaseReference reference,reference2;
    FirebaseUser firebaseUser;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile__students);

        name = (EditText) findViewById(R.id.name_ustaad_edit_student);
        mobile = (EditText) findViewById(R.id.mobile_ustaad_edit_student);
        address = (EditText) findViewById(R.id.address_ustaad_edit_student);
        choose_image = (TextView) findViewById(R.id.profile_picture_student);
        circleImageView = (ImageView) findViewById(R.id.edit_image_student);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar_student);
        close = (ImageView) findViewById(R.id.close_student);
        save = (TextView) findViewById(R.id.save_student);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("Student");
        reference = FirebaseDatabase.getInstance().getReference("Students").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Student student = dataSnapshot.getValue(Student.class);
                name.setText(student.getStudentname());
                mobile.setText(student.getStudentmobile());
                Glide.with(getApplicationContext()).load(student.getStudentimage()).into(circleImageView);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadImage();
                updateProfile(name.getText().toString(),mobile.getText().toString(),address.getText().toString());
                finish();
            }
        });

        choose_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenfileChooser();
            }
        });

    }

    private void OpenfileChooser(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            Glide.with(this).load(filePath).into(circleImageView);
        }

    }

    private String getFileExtension(Uri uri){

        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));


    }

    private void uploadImage(){

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress(0);
            }
        },500);

        if (filePath != null){

            final StorageReference fileReference = storageReference.child(firebaseUser.getUid()
                    + "." + getFileExtension(filePath));

            uploadTask = fileReference.putFile(filePath);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();

                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()){
                        Uri downloadUri = (Uri) task.getResult();
                        String myUri = downloadUri.toString();
                        HashMap<String,Object> hashMap = new HashMap<>();
                        hashMap.put("studentimage",""+myUri);
                        reference.updateChildren(hashMap);

                    }else {
                        Toast.makeText(Edit_profileActivity_Students.this,"Failed",Toast.LENGTH_SHORT).show();}
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(Edit_profileActivity_Students.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    });
        }else {
            Toast.makeText(this,"No File Selected",Toast.LENGTH_SHORT).show();
        }


    }

    private void updateProfile(String name,String mobile,String address){
        DatabaseReference reference =FirebaseDatabase.getInstance().getReference("Students").child(firebaseUser.getUid());

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("studentname",name);
        hashMap.put("studentmobile",mobile);
        reference.updateChildren(hashMap);
    }



}
