package com.comcare.comcarecustomer.LoginAndRegister;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.comcare.comcarecustomer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EmailRegister extends AppCompatActivity {

    private EditText getName;
    private EditText getEmail;
    private EditText getPassword;
    private EditText getConfirmPassword;
    FirebaseAuth firebaseAuth;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_register);

        bindWidget();
    }

    public void onClickRegister(View obj){

//            String name = getName.getText().toString();
//            String email = getEmail.getText().toString();
//            String password = getPassword.getText().toString();


            if (getName.getText().toString().trim().equals("")
                    &&getEmail.getText().toString().trim().equals("")
                    &&getPassword.getText().toString().trim().equals("")){
                Dialog("\n- ชื่อผู้ใช้งาน\n- อีเมล\n- รหัสผ่าน");
            }else if (getName.getText().toString().trim().equals("")){
                Dialog("\n- ชื่อผู้ใช้งาน");
            }else if (getEmail.getText().toString().trim().equals("")){
                Dialog("\n- อีเมล");
            }else if (getPassword.getText().toString().trim().equals("")){
                Dialog("\n- รหัสผ่าน");
            }else {
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.createUserWithEmailAndPassword(getName.getText().toString(), getPassword.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            Toast.makeText(EmailRegister.this, "Register Failed", Toast.LENGTH_LONG).show();

                        } else {
//                            Toast.makeText(EmailRegister.this, "Register Successful", Toast.LENGTH_LONG).show();

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
                            DatabaseReference childref = dbref.child("user");
                            childref.child("" + user.getUid()).child("Info").child("full_name").setValue(getName.getText().toString());
                            childref.child("" + user.getUid()).child("Info").child("email").setValue(getEmail.getText().toString());

                            intent = new Intent(getApplication(), EmailLogin.class);
                            finish();
                            startActivity(intent);

                        }
                    }

                });
            }
    }

    private void bindWidget() {

        getName = (EditText) findViewById(R.id.edtName);
        getEmail = (EditText) findViewById(R.id.edtMail);
        getPassword = (EditText) findViewById(R.id.edtPass);
        getConfirmPassword = (EditText) findViewById(R.id.edtConfirmPass);

    }
    private void Dialog(String txt) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("คุณยังไม่ได้กรอก: " + txt);
        builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.show();
    }


}
