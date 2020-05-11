package com.example.ustaad.Teacher.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ustaad.R;
import com.example.ustaad.Teacher.Model.Teacher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class View_Profile_Fragment extends Fragment {

    ImageView imageView,profile;
    TextView name,email,mobile,education;
    Button interact,decline;
    DatabaseReference reference,mInteract,mFriendDatabase,mNotificationDatabase,mRootRef;
    FirebaseUser firebaseUser;
    String profileId1,profileId;
    String mCurrent_state = "not_friends";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view__profile__fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageView = (ImageView) getView().findViewById(R.id.option1);
        interact = (Button) getView().findViewById(R.id.btn_interact);
        decline = (Button) getView().findViewById(R.id.btn_decline);
        name = (TextView) getView().findViewById(R.id.name_ustaad);
        profile = (ImageView) getView().findViewById(R.id.View_image);
        email = (TextView) getView().findViewById(R.id.email_ustaad);
        mobile = (TextView) getView().findViewById(R.id.mobile_ustaad);
        education = (TextView) getView().findViewById(R.id.education_ustaad);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences preferences = getContext().getSharedPreferences("PREFS",Context.MODE_PRIVATE);
        profileId1 = preferences.getString("profileId1","none");
        profileId = getArguments().getString("profileId");
        mRootRef = FirebaseDatabase.getInstance().getReference();
        reference = FirebaseDatabase.getInstance().getReference("Teacher").child(profileId);
        mInteract = FirebaseDatabase.getInstance().getReference("Friend_req ");
        mFriendDatabase = FirebaseDatabase.getInstance().getReference("Friends");
        mNotificationDatabase = FirebaseDatabase.getInstance().getReference("Notification");



        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (getActivity() == null) {
                    return;
                }

                Teacher teacher = dataSnapshot.getValue(Teacher.class);
                String s = teacher.getTeachername();
                String s1 = teacher.getTeacheremail();
                String s2 = teacher.getTeachermobile();
                String s3 = teacher.getTeachergrade();
                name.setText(s.toUpperCase());
                email.setText(s1);
                mobile.setText(s2);
                education.setText(s3);
                Glide.with(getActivity()).load(teacher.getTeacherimage()).into(profile);


                mInteract.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(profileId)) {

                            String req_type = dataSnapshot.child(profileId).child("request_type").getValue().toString();
                            if (req_type.equals("received")) {

                                mCurrent_state = "req_received";
                                interact.setText("Accept Friend Request");
                                decline.setVisibility(View.VISIBLE);
                                decline.setEnabled(true);

                            } else if (req_type.equals("Sent")) {

                                mCurrent_state = "req_sent";
                                interact.setText("Pending...");
                                decline.setVisibility(View.INVISIBLE);
                                decline.setEnabled(false);


                            }


                        } else {
                            mFriendDatabase.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.hasChild(profileId)){

                                        mCurrent_state = "friends";
                                        interact.setText("UnFriend");
                                        decline.setVisibility(View.INVISIBLE);
                                        decline.setEnabled(false);


                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ///--------NOT FRIEND STATE---------//////

        interact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interact.setEnabled(false);

                if (mCurrent_state.equals("not_friends") ){

                    DatabaseReference newNotificationref = mRootRef.child("Notification").child(profileId).push();
                    String newNotificationId = newNotificationref.getKey();

                    HashMap<String, String> notificationData = new HashMap<>();
                    notificationData.put("from",firebaseUser.getUid());
                    notificationData.put("type","request");


                    HashMap requestMap = new HashMap();
                    requestMap.put("Friend_req/" +firebaseUser.getUid() +"/" + profileId +"/request_type","sent");
                    requestMap.put("Friend_req/" + profileId + "/" + firebaseUser.getUid() + "/request_type","received");
                    requestMap.put("Notification/" + profileId + "/" + newNotificationId,notificationData);
                    mRootRef.updateChildren(requestMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if (databaseError != null){

                                Toast.makeText(getActivity(),"There was some error",Toast.LENGTH_SHORT).show();
                            }

                            interact.setEnabled(true);

                            mCurrent_state = "req_sent";
                            interact.setText("Pending...");
                        }
                    });




//                    mInteract.child(firebaseUser.getUid()).child(profileId).child("request_type").setValue("Sent")
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()){
//
//                                        mInteract.child(profileId).child(firebaseUser.getUid()).child("request_type").setValue("received")
//                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                    @Override
//                                                    public void onSuccess(Void aVoid) {
//
//
//
//                                                        mNotificationDatabase.child(profileId).push().setValue(notificationData).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                            @Override
//                                                            public void onSuccess(Void aVoid) {
//
//                                                                mCurrent_state = "req_sent";
//                                                                interact.setText("pending...");
//                                                                decline.setVisibility(View.INVISIBLE);
//                                                                decline.setEnabled(false);
//
//                                                            }
//                                                        });
//
//
//
//
//
//
//                                                        Toast.makeText(getActivity(),"Interact Successfull"+mCurrent_state,Toast.LENGTH_SHORT).show();
//
//
//                                                    }
//                                                });
//
//
//
//                                    }else {
//                                        Toast.makeText(getActivity(),"Interact Failed",Toast.LENGTH_SHORT).show();
//                                    }
//
//                                    interact.setEnabled(true);
//
//                                }
//                            });

                }

            ///-------- Cancel Request_State--------//
                if (mCurrent_state.equals("req_sent")){
                    mInteract.child(firebaseUser.getUid()).child(profileId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mInteract.child(profileId).child(firebaseUser.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    interact.setEnabled(true);
                                    mCurrent_state = "not_friends";
                                    interact.setText("Interact");
                                    decline.setVisibility(View.INVISIBLE);
                                    decline.setEnabled(false);

                                }
                            });
                        }
                    });

                }



                ///----- Request Received _ STATE-----

                if (mCurrent_state.equals("req_received")){
                    final String Current_date = DateFormat.getDateTimeInstance().format(new Date());

                    HashMap friendMap = new HashMap();
                    friendMap.put("Friends/" +firebaseUser.getUid() +"/" + profileId +"/date",Current_date);
                    friendMap.put("Friends/" + profileId + "/" + firebaseUser.getUid() + "/date",Current_date);


                    friendMap.put("Friend_req/" +firebaseUser.getUid() +"/" + profileId ,null);
                    friendMap.put("Friend_req/" + profileId + "/" + firebaseUser.getUid(),null);

                    mRootRef.updateChildren(friendMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if (databaseError == null){
                                interact.setEnabled(true);
                                mCurrent_state = "friends";
                                interact.setText("UnFriend");
                                decline.setVisibility(View.INVISIBLE);
                                decline.setEnabled(false);
                            }else{
                                String error = databaseError.getMessage();
                                Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
//                    mFriendDatabase.child(firebaseUser.getUid()).child(profileId).setValue(Current_date).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            mFriendDatabase.child(profileId).child(firebaseUser.getUid()).setValue(Current_date).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//
//                                    mInteract.child(firebaseUser.getUid()).child(profileId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void aVoid) {
//                                            mInteract.child(profileId).child(firebaseUser.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                @Override
//                                                public void onSuccess(Void aVoid) {
//
//
//
//                                                }
//                                            });
//                                        }
//                                    });
//
//                                }
//                            });
//
//                        }
//                    });

                }

                ///------ UnFriend Request --------//////
                if (mCurrent_state.equals("friends")){
                    mFriendDatabase.child(firebaseUser.getUid()).child(profileId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mFriendDatabase.child(profileId).child(firebaseUser.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    interact.setEnabled(true);
                                    mCurrent_state = "not_friends";
                                    interact.setText("Interact");
                                    decline.setVisibility(View.INVISIBLE);
                                    decline.setEnabled(false);

                                }
                            });
                        }
                    });

                }

            }
        });
















    }

}
