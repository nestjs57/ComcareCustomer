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

import com.comcare.comcarecustomer.MainActivity;
import com.comcare.comcarecustomer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class EmailLogin extends AppCompatActivity {

    EditText getMail,getPass;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);

        bindWidget();

    }

    public void onClickLogin(View obj) {

        if (getMail.getText().toString().trim().equals("")&&getPass.getText().toString().trim().equals("")){
            Dialog("\n- อีเมล\n- รหัสผ่าน");
        }else if (getMail.getText().toString().trim().equals("")){
            Dialog("\n- อีเมล");
        }else if (getPass.getText().toString().trim().equals("")){
            Dialog("\n- รหัสผ่าน");
        }else {

            firebaseAuth.signInWithEmailAndPassword(getMail.getText().toString(), getPass.getText().toString())
                    .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()) {
                                Toast.makeText(EmailLogin.this, "Login Failed Please Try Agian", Toast.LENGTH_LONG).show();

                            } else {
//                        Toast.makeText(EmailLogin.this, "Login Successful", Toast.LENGTH_LONG).show();

                                intent = new Intent(EmailLogin.this, MainActivity.class);
                                finish();
                                startActivity(intent);
                            }
                        }
                    });
        }
    }
    private void bindWidget() {

        getMail = (EditText) findViewById(R.id.edtMail);
        getPass = (EditText) findViewById(R.id.edtPass);

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
