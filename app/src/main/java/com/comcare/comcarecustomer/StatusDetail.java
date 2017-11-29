package com.comcare.comcarecustomer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.comcare.comcarecustomer.Models.StatusModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Collections;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class StatusDetail extends AppCompatActivity {

    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;
    private Intent intent;
    private TextView txtName, txtTel, txtMail, txtType, txtDate, txtAddress1, txtAddress2, txtProblem1, txtProblem2;
    private ImageView txtPath_img1, txtPath_img2, txtPath_img3, txtPath_img4, popupImg;

    StorageReference storageReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status_detail);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        txtName = (TextView) findViewById(R.id.txtName);
        txtTel = (TextView) findViewById(R.id.txtTelNo);
        txtMail = (TextView) findViewById(R.id.txtMail);
        txtType = (TextView) findViewById(R.id.txtType);
        txtDate = (TextView) findViewById(R.id.txtTime);
        txtAddress2 = (TextView) findViewById(R.id.txtLoca2);
        txtAddress1 = (TextView) findViewById(R.id.txtLoca1);
        txtProblem1 = (TextView) findViewById(R.id.txtProblem);
        txtProblem2 = (TextView) findViewById(R.id.txtDetail);
        txtPath_img1 = (ImageView) findViewById(R.id.imageView15);
        txtPath_img2 = (ImageView) findViewById(R.id.imageView16);
        txtPath_img3 = (ImageView) findViewById(R.id.imageView17);
        txtPath_img4 = (ImageView) findViewById(R.id.imageView18);


//        Toast.makeText(StatusDetail.this, intent.getStringExtra("key"), Toast.LENGTH_LONG).show();


        connectToFirebase();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }


    private void connectToFirebase() {
        intent = getIntent();
        storageReference = FirebaseStorage.getInstance().getReference();



        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("technician_Name").getValue().toString().equals("null")) {
                    txtName.setText("กำลังค้นหาช่าง");
                    txtTel.setText("-");
                    txtMail.setText("-");
                    txtName.setTextColor(Color.parseColor("#ffff8800"));
                }
                if (dataSnapshot.child("type").getValue().toString().equals("1")) {
                    txtType.setText("คอมพิวเตอร์");
                } else {
                    txtType.setText("โน๊ตบุ๊ค");
                }

                txtDate.setText(dataSnapshot.child("date").getValue().toString()+"\n"+dataSnapshot.child("time").getValue().toString());
                txtAddress2.setText(dataSnapshot.child("address1").getValue().toString());
                txtAddress1.setText(dataSnapshot.child("address2").getValue().toString());
                txtProblem1.setText(dataSnapshot.child("problem1").getValue().toString());
                txtProblem2.setText(dataSnapshot.child("problem2").getValue().toString());


                Toast.makeText(getApplication(), dataSnapshot.child("Path_img1").getValue().toString(), Toast.LENGTH_LONG).show();

                Glide.with(getApplication()).load(dataSnapshot.child("Path_img1").getValue().toString()).into(txtPath_img1);
                Glide.with(getApplication()).load(dataSnapshot.child("Path_img2").getValue().toString()).into(txtPath_img2);
                Glide.with(getApplication()).load(dataSnapshot.child("Path_img3").getValue().toString()).into(txtPath_img3);
                Glide.with(getApplication()).load(dataSnapshot.child("Path_img4").getValue().toString()).into(txtPath_img4);

                setEvent(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference childref = dbref.child("order").child(intent.getStringExtra("key"));
        childref.addValueEventListener(valueEventListener);
    }

    public void setEvent(final DataSnapshot dataSnapshot){

        txtPath_img1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                LayoutInflater inflater = LayoutInflater.from(StatusDetail.this);
                view = inflater.inflate(R.layout.image_popup, null);
                ImageView imageView = (ImageView)view.findViewById(R.id.img);

                GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
                Glide.with(getApplication()).load(dataSnapshot.child("Path_img1").getValue().toString()).into(imageViewTarget);

                AlertDialog.Builder share_dialog = new AlertDialog.Builder(StatusDetail.this);
                share_dialog.setView(view);
                share_dialog.show();

            }
        });

        txtPath_img2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                LayoutInflater inflater = LayoutInflater.from(StatusDetail.this);
                view = inflater.inflate(R.layout.image_popup, null);
                ImageView imageView = (ImageView)view.findViewById(R.id.img);

                GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
                Glide.with(getApplication()).load(dataSnapshot.child("Path_img2").getValue().toString()).into(imageViewTarget);

                AlertDialog.Builder share_dialog = new AlertDialog.Builder(StatusDetail.this);
                share_dialog.setView(view);
                share_dialog.show();

            }
        });

        txtPath_img3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                LayoutInflater inflater = LayoutInflater.from(StatusDetail.this);
                view = inflater.inflate(R.layout.image_popup, null);
                ImageView imageView = (ImageView)view.findViewById(R.id.img);

                GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
                Glide.with(getApplication()).load(dataSnapshot.child("Path_img3").getValue().toString()).into(imageViewTarget);

                AlertDialog.Builder share_dialog = new AlertDialog.Builder(StatusDetail.this);
                share_dialog.setView(view);
                share_dialog.show();

            }
        });

        txtPath_img4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                LayoutInflater inflater = LayoutInflater.from(StatusDetail.this);
                view = inflater.inflate(R.layout.image_popup, null);
                ImageView imageView = (ImageView)view.findViewById(R.id.img);

                GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
                Glide.with(getApplication()).load(dataSnapshot.child("Path_img4").getValue().toString()).into(imageViewTarget);

                AlertDialog.Builder share_dialog = new AlertDialog.Builder(StatusDetail.this);
                share_dialog.setView(view);
                share_dialog.show();

            }
        });



    }
}
