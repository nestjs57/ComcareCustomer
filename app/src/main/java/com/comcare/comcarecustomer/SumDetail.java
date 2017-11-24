package com.comcare.comcarecustomer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SumDetail extends AppCompatActivity {

    private TextView txt1;
    private TextView txt2;
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sum_detail);

        bindWidget();
        getStringIntent();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }


    private void getStringIntent() {
        Intent intent = getIntent();
        String chk = intent.getStringExtra("chkImg").toString();
        if (chk.equals("4")) {
            txt1.setText(intent.getStringExtra("edt1"));
            txt2.setText(intent.getStringExtra("edt2"));
            img1.setImageURI(Uri.parse(intent.getStringExtra("img1")));
            img2.setImageURI(Uri.parse(intent.getStringExtra("img2")));
            img3.setImageURI(Uri.parse(intent.getStringExtra("img3")));
            img4.setImageURI(Uri.parse(intent.getStringExtra("img4")));
        } else if (chk.equals("3")) {
            txt1.setText(intent.getStringExtra("edt1"));
            txt2.setText(intent.getStringExtra("edt2"));
            img1.setImageURI(Uri.parse(intent.getStringExtra("img1")));
            img2.setImageURI(Uri.parse(intent.getStringExtra("img2")));
            img3.setImageURI(Uri.parse(intent.getStringExtra("img3")));
            img4.setVisibility(View.INVISIBLE);
        } else if (chk.equals("2")) {
            txt1.setText(intent.getStringExtra("edt1"));
            txt2.setText(intent.getStringExtra("edt2"));
            img1.setImageURI(Uri.parse(intent.getStringExtra("img1")));
            img2.setImageURI(Uri.parse(intent.getStringExtra("img2")));
            img3.setVisibility(View.INVISIBLE);
            img4.setVisibility(View.INVISIBLE);
        } else if (chk.equals("1")) {
            txt1.setText(intent.getStringExtra("edt1"));
            txt2.setText(intent.getStringExtra("edt2"));
            img1.setImageURI(Uri.parse(intent.getStringExtra("img1")));
            img2.setVisibility(View.INVISIBLE);
            img3.setVisibility(View.INVISIBLE);
            img4.setVisibility(View.INVISIBLE);
        } else {
            txt1.setText(intent.getStringExtra("edt1"));
            txt2.setText(intent.getStringExtra("edt2"));
            img1.setVisibility(View.INVISIBLE);
            img2.setVisibility(View.INVISIBLE);
            img3.setVisibility(View.INVISIBLE);
            img4.setVisibility(View.INVISIBLE);
        }
    }

    private void bindWidget() {
        txt1 = (TextView) findViewById(R.id.txt1);
        txt2 = (TextView) findViewById(R.id.txt2);
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        img4 = (ImageView) findViewById(R.id.img4);
        btnNext = (Button) findViewById(R.id.btnNext);
    }
}
