package com.comcare.comcarecustomer.LoginAndRegister;

import android.content.Intent;
import android.support.annotation.NonNull;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);

        bindWidget();

    }

    public void onClickLogin(View obj) {

        String email = getMail.getText().toString();
        String password = getPass.getText().toString();

            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()) {
                        Toast.makeText(EmailLogin.this, "Login Failed Please Try Agian", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(EmailLogin.this, "Login Successful", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(EmailLogin.this, MainActivity.class);
                        finish();
                        startActivity(intent);
                    }

                }
            });

    }

    private void bindWidget() {

        getMail = (EditText) findViewById(R.id.edtMail);
        getPass = (EditText) findViewById(R.id.edtPass);

    }


}
