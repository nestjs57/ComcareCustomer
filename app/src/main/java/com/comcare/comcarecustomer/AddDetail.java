package com.comcare.comcarecustomer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.comcare.comcarecustomer.Maps.AddLocation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class AddDetail extends AppCompatActivity {


    private ImageButton btnImage1;
    private ImageButton btnImage2;
    private ImageButton btnImage3;
    private ImageButton btnImage4;
    private Button btnNext;
    private EditText edt1;
    private EditText edt2;


    private TextView txtAddress1;
    private EditText editTextAddress2;
    private LinearLayout linearClick;

    private Boolean btn1 = false;
    private Boolean btn2 = false;
    private Boolean btn3 = false;
    private Boolean btn4 = false;
    private String btnStr1 = "";
    private String btnStr2 = "";
    private String btnStr3 = "";
    private String btnStr4 = "";


    private String latCur = "";
    private String lngCur = "";
    //
    private static final int GALLERY_REQUEST = 1;
    private Uri mImageUri = null;
    private String add_choose;
    private String lat_choose;
    private String lng_choose;

    private TextView textView;
    private Spinner spinner2;
    private TextView ttt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_detail);


        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Intent intent = getIntent();
        latCur = intent.getStringExtra("latCur");
        lngCur = intent.getStringExtra("lngCur");
        Toast.makeText(AddDetail.this, latCur, Toast.LENGTH_LONG).show();
        bindWidget();
        setEvent();

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> addresses  = geocoder.getFromLocation(Double.parseDouble(latCur),Double.parseDouble(lngCur), 1);
            String address = addresses.get(0).getAddressLine(0);
            textView.setText(address+"");
        } catch (Exception e) {

        }

        ttt = (TextView) findViewById(R.id.ttt);
        ttt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddDetail.this,String.valueOf(spinner2.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


    private void setEvent() {

        linearClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), AddLocation.class);
                intent.putExtra("latCur",latCur);
                intent.putExtra("lngCur",lngCur);
                startActivityForResult(intent,2);
            }
        });

        btnImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });
        btnImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });
        btnImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });
        btnImage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edt1.getText().toString().equals("") || edt2.getText().toString().equals("")) {
                    if (edt1.getText().toString().equals("") && edt2.getText().toString().equals("")) {
                        Dialog("\n- อาการปัจจุบัน\n- คำอธิบายเพิ่มเติม");
                    } else if (edt1.getText().toString().equals("")) {
                        Dialog("\n- อาการปัจจุบัน");

                    } else if (edt2.getText().toString().equals("")) {
                        Dialog("\n- คำอธิบายเพิ่มเติม");
                    }
                } else {
                    Intent intent = new Intent(getApplication(), SumDetail.class);
                    //intent.putExtra("edt1", edt1.getText().toString());
                    //intent.putExtra("edt2", edt2.getText().toString());

                    if (btn4 == true) {
                        intent.putExtra("edt1", edt1.getText().toString());
                        intent.putExtra("edt2", edt2.getText().toString());
                        intent.putExtra("address1",txtAddress1.getText().toString());
                        intent.putExtra("address2",editTextAddress2.getText().toString());
                        intent.putExtra("type",String.valueOf(spinner2.getSelectedItem()));
                        intent.putExtra("img1", btnStr1);
                        intent.putExtra("img2", btnStr2);
                        intent.putExtra("img3", btnStr3);
                        intent.putExtra("img4", btnStr4);
                        intent.putExtra("chkImg","4");
                    } else if (btn3 == true) {
                        intent.putExtra("edt1", edt1.getText().toString());
                        intent.putExtra("edt2", edt2.getText().toString());
                        intent.putExtra("address1",txtAddress1.getText().toString());
                        intent.putExtra("address2",editTextAddress2.getText().toString());
                        intent.putExtra("type",String.valueOf(spinner2.getSelectedItem()));
                        intent.putExtra("img1", btnStr1);
                        intent.putExtra("img2", btnStr2);
                        intent.putExtra("img3", btnStr3);
                        intent.putExtra("chkImg","3");

                    } else if (btn2 == true) {
                        intent.putExtra("edt1", edt1.getText().toString());
                        intent.putExtra("edt2", edt2.getText().toString());
                        intent.putExtra("address1",txtAddress1.getText().toString());
                        intent.putExtra("address2",editTextAddress2.getText().toString());
                        intent.putExtra("type",String.valueOf(spinner2.getSelectedItem()));
                        intent.putExtra("img1", btnStr1);
                        intent.putExtra("img2", btnStr2);
                        intent.putExtra("chkImg","2");

                    } else if (btn1 == true) {
                        intent.putExtra("edt1", edt1.getText().toString());
                        intent.putExtra("edt2", edt2.getText().toString());
                        intent.putExtra("address1",txtAddress1.getText().toString());
                        intent.putExtra("address2",editTextAddress2.getText().toString());
                        intent.putExtra("type",String.valueOf(spinner2.getSelectedItem()));
                        intent.putExtra("img1", btnStr1);
                        intent.putExtra("chkImg","1");

                    } else {
                        intent.putExtra("edt1", edt1.getText().toString());
                        intent.putExtra("edt2", edt2.getText().toString());
                        intent.putExtra("address1",txtAddress1.getText().toString());
                        intent.putExtra("address2",editTextAddress2.getText().toString());
                        intent.putExtra("type",String.valueOf(spinner2.getSelectedItem()));
                        intent.putExtra("chkImg","0");

                    }
                    startActivity(intent);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }


            }
        });

    }

    private void Dialog(String txt) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("คุณยังไม่ได้กรอก: " + txt);
        builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
//                Toast.makeText(getApplicationContext(),
//                        "ตกลง", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    private void bindWidget() {

        spinner2 = (Spinner) findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        list.add("คอมพิวเตอร์");
        list.add("โน๊ตบุ๊ค");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);




        txtAddress1 = (TextView) findViewById(R.id.txtAddress1);
        editTextAddress2 = (EditText) findViewById(R.id.editTextAddress2);
        btnImage1 = (ImageButton) findViewById(R.id.btnImage1);
        btnImage2 = (ImageButton) findViewById(R.id.btnImage2);
        btnImage3 = (ImageButton) findViewById(R.id.btnImage3);
        btnImage4 = (ImageButton) findViewById(R.id.btnImage4);
        edt1 = (EditText) findViewById(R.id.edt1);
        edt2 = (EditText) findViewById(R.id.edt2);
        linearClick = (LinearLayout) findViewById(R.id.linearClick);
        btnNext = (Button) findViewById(R.id.btnNext);
        textView = (TextView) findViewById(R.id.txtAddress1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {

            if (btn1 == false) {
                mImageUri = data.getData();
                btnImage1.setImageURI(mImageUri);
                Toast.makeText(this, mImageUri.toString(), Toast.LENGTH_LONG).show();
                btn1 = true;
                btnStr1 = mImageUri.toString();
            } else if (btn2 == false) {
                mImageUri = data.getData();
                btnImage2.setImageURI(mImageUri);
                Toast.makeText(this, mImageUri.toString(), Toast.LENGTH_LONG).show();
                btn2 = true;
                btnStr2 = mImageUri.toString();
            } else if (btn3 == false) {
                mImageUri = data.getData();
                btnImage3.setImageURI(mImageUri);
                Toast.makeText(this, mImageUri.toString(), Toast.LENGTH_LONG).show();
                btn3 = true;
                btnStr3 = mImageUri.toString();
            } else if (btn4 == false) {
                mImageUri = data.getData();
                btnImage4.setImageURI(mImageUri);
                Toast.makeText(this, mImageUri.toString(), Toast.LENGTH_LONG).show();
                btn4 = true;
                btnStr4 = mImageUri.toString();
            }


        }else  if (requestCode == 2 && resultCode == RESULT_OK) {

            add_choose = data.getStringExtra("address");
            lat_choose = data.getStringExtra("lat");
            lng_choose = data.getStringExtra("lng");
            textView.setText(add_choose);
        }
    }
}
