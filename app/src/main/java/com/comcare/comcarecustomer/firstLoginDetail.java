package com.comcare.comcarecustomer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.comcare.comcarecustomer.LoginAndRegister.Login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class firstLoginDetail extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ValueEventListener valueEventListener;
    private Intent intent;
    private Button okButton;
    private TextView edtTel;
    private DatabaseReference childref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_login_detail);


        okButton = (Button) findViewById(R.id.okButton);
        edtTel = (TextView) findViewById(R.id.edtTel);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkTel(edtTel.getText().toString());
            }
        });
    }


    private void checkTel(final String s) {

        mAuth = FirebaseAuth.getInstance();
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
        childref = dbref.child("user").child(mAuth.getUid().toString()).child("info");

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                childref.child("tel").setValue(s);
//                Toast.makeText(getApplication(), "ได้แล้วเย่ๆ",
//                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        childref.addValueEventListener(valueEventListener);



    }
}
