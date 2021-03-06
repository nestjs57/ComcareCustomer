package com.comcare.comcarecustomer.LoginAndRegister;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.comcare.comcarecustomer.MainActivity;
import com.comcare.comcarecustomer.R;
import com.comcare.comcarecustomer.firstLoginDetail;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

public class Login extends Activity {

    private CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;
    private CallbackManager callbackManager;
    private ProgressDialog mProgressDialog;
    private TextView txtLogin;
    private Button btnMailRegister;
    private ValueEventListener valueEventListener;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setEvent();

        mAuth = FirebaseAuth.getInstance();
        mProgressDialog = new ProgressDialog(this);


        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        handleFacebookAccessToken(loginResult.getAccessToken());
                        Profile profile = Profile.getCurrentProfile();
                        if (profile != null) {
                            String facebook_id = profile.getId();
                            String f_name = profile.getFirstName();
                            String m_name = profile.getMiddleName();
                            String l_name = profile.getLastName();
                            String full_name = profile.getName();
                            String profile_image = profile.getProfilePictureUri(1000, 1000).toString();
                            //Toast.makeText(getApplication(),"ยินดีต้อนรับ facebook: "+full_name, Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        mProgressDialog.setMessage("กำลังเข้าสู่ระบบ ..");
        mProgressDialog.show();
    }

    private void handleFacebookAccessToken(AccessToken token) {
        // [START_EXCLUDE silent]
        // [END_EXCLUDE]

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mProgressDialog.dismiss();
                            // Sign in success, update UI with the signed-in user's information
                            //build child
                            //childrf.child(user.getUid()).child("info").setValue(new DataUer(Email,Password,user.getUid(),"null",DisplayName));
                            //String a = ""+user;
                            //Toast.makeText(getApplication(),"ยินดีต้อนรับ : "+user.getUid().toString(), Toast.LENGTH_LONG).show();


                            DatabaseReference dbreff = FirebaseDatabase.getInstance().getReference();
                            DatabaseReference childref = dbreff.child("user").child(mAuth.getUid().toString()).child("info");

                            valueEventListener = new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    //FirebaseUser user = mAuth.getCurrentUser();

                                    Profile profile = Profile.getCurrentProfile();
                                    if (profile != null) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
                                        //DatabaseReference childrf = dbref.child("user");
                                        if (!dataSnapshot.hasChildren()) {
                                            // db has no children
                                            String facebook_id = profile.getId();
                                            String f_name = profile.getFirstName();
                                            String m_name = profile.getMiddleName();
                                            String l_name = profile.getLastName();
                                            String full_name = profile.getName();
                                            String token = FirebaseInstanceId.getInstance().getToken();

                                            String profile_image = profile.getProfilePictureUri(300, 300).toString();
                                            dbref.child("user").child(mAuth.getUid()).child("info").child("full_name").setValue(full_name);
                                            dbref.child("user").child(mAuth.getUid()).child("info").child("profile_image").setValue(profile_image);
                                            dbref.child("user").child(mAuth.getUid()).child("info").child("tel").setValue("null");
                                            dbref.child("user").child(mAuth.getUid()).child("info").child("token").setValue(token);

                                            intent = new Intent(getApplication(), firstLoginDetail.class);
                                            finish();
                                            startActivity(intent);
                                        } else {
                                            checkTel();

                                        }


                                        //Toast.makeText(getApplication(),"ยินดีต้อนรับ : "+full_name, Toast.LENGTH_LONG).show();


//                                        dbref.child("user").child(user.getUid()).child("info").child("name").setValue("null");
//                                        dbref.child("user").child(user.getUid()).child("info").child("lastname").setValue("null");


                                    }


                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            };
                            childref.addValueEventListener(valueEventListener);


                            //Toast.makeText(login.this, "username & password Fail", Toast.LENGTH_SHORT).show();

                            //checkTel();
                            //overridePendingTransition(R.anim.right_in, R.anim.left_out); //ใหม่ , เก่า

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        // [END_EXCLUDE]
                    }
                });
    }

    private void setEvent() {

        txtLogin = (TextView) findViewById(R.id.txtLogin);
        btnMailRegister = (Button) findViewById(R.id.button2);

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), EmailLogin.class);
                startActivity(intent);
            }
        });

        btnMailRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), EmailRegister.class);
                startActivity(intent);
            }
        });

    }

    private void checkTel() {

        mAuth = FirebaseAuth.getInstance();
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference childref = dbref.child("user").child(mAuth.getUid().toString()).child("info");

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if (dataSnapshot.child("tel").getValue().toString().equals("null")) {
                    intent = new Intent(getApplication(), firstLoginDetail.class);

                } else {
                    intent = new Intent(getApplication(), MainActivity.class);

                }
                finish();
                startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        childref.addValueEventListener(valueEventListener);


        Intent intent = new Intent(getApplication(), MainActivity.class);
    }

}
