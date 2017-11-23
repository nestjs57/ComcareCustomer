package com.comcare.comcarecustomer;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class AddDetail extends AppCompatActivity {


    private ImageButton btnImage1;
    private ImageButton btnImage2;
    private ImageButton btnImage3;
    private ImageButton btnImage4;
    private Button btnNext;
    private EditText edt1;
    private EditText edt2;

    private Boolean btn1 = false;
    private Boolean btn2 = false;
    private Boolean btn3 = false;
    private Boolean btn4 = false;
    private String btnStr1 = "";
    private String btnStr2 = "";
    private String btnStr3 = "";
    private String btnStr4 = "";


    //
    private static final int GALLERY_REQUEST = 1;
    private Uri mImageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_detail);

        bindWidget();
        setEvent();
    }

    private void setEvent() {

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
                    intent.putExtra("edt1", edt1.getText().toString());
                    intent.putExtra("edt2", edt2.getText().toString());

                    if (btn4 == true) {
                        intent.putExtra("edt1", edt1.getText().toString());
                        intent.putExtra("edt2", edt2.getText().toString());
                        intent.putExtra("img1", btnStr4);
                        intent.putExtra("img2", btnStr3);
                        intent.putExtra("img3", btnStr3);
                        intent.putExtra("img4", btnStr4);
                    } else if (btn3 == true) {
                        intent.putExtra("edt1", edt1.getText().toString());
                        intent.putExtra("edt2", edt2.getText().toString());
                        intent.putExtra("img1", btnStr4);
                        intent.putExtra("img2", btnStr3);
                        intent.putExtra("img3", btnStr3);
                    } else if (btn2 == true) {
                        intent.putExtra("edt1", edt1.getText().toString());
                        intent.putExtra("edt2", edt2.getText().toString());
                        intent.putExtra("img1", btnStr4);
                        intent.putExtra("img2", btnStr3);
                    } else if (btn1 == true) {
                        intent.putExtra("edt1", edt1.getText().toString());
                        intent.putExtra("edt2", edt2.getText().toString());
                        intent.putExtra("img1", btnStr4);
                    } else {
                        intent.putExtra("edt1", edt1.getText().toString());
                        intent.putExtra("edt2", edt2.getText().toString());
                    }
                    startActivity(intent);

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
        btnImage1 = (ImageButton) findViewById(R.id.btnImage1);
        btnImage2 = (ImageButton) findViewById(R.id.btnImage2);
        btnImage3 = (ImageButton) findViewById(R.id.btnImage3);
        btnImage4 = (ImageButton) findViewById(R.id.btnImage4);
        edt1 = (EditText) findViewById(R.id.edt1);
        edt2 = (EditText) findViewById(R.id.edt2);
        btnNext = (Button) findViewById(R.id.btnNext);
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


        }
    }
}
