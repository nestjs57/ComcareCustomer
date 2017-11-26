package com.comcare.comcarecustomer;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SumDetail extends AppCompatActivity {

    private TextView txtName;
    private TextView txtTel;
    private TextView txt1;
    private TextView txt2;
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;
    private TextView address1;
    private TextView address2;
    private Button btnConfirm;

    private String chk;
    private String Simg1;
    private String Simg2;
    private String Simg3;
    private String Simg4;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private String name = "";
    private String tel;
    private StorageReference mStorage;
    private ProgressDialog progressDialog;

    private StorageReference filepath = null;
    private StorageReference filepath2 = null;
    private StorageReference filepath3 = null;
    private StorageReference filepath4 = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sum_detail);

        mStorage = FirebaseStorage.getInstance().getReference();
        progressDialog = new ProgressDialog(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        connectFirebase();
        bindWidget();
        getStringIntent();
        setEvent();
        Intent intent = getIntent();
        Toast.makeText(getApplicationContext(), intent.getStringExtra("img1"), Toast.LENGTH_SHORT).show();

    }

    private void setEvent() {

        txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(SumDetail.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.edit_popup);
                dialog.setCancelable(true);

                EditText editText = (EditText) dialog.findViewById(R.id.edit);

                editText.setHint("กรอกชื่อ นามสกุล ที่ต้องการแก้ไข");

                Button buttonE = (Button) dialog.findViewById(R.id.btncancel);
                buttonE.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                Button buttonC = (Button) dialog.findViewById(R.id.btnenter);
                buttonC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText editText = (EditText) dialog.findViewById(R.id.edit);
                        //editText.getText().toString();
                        txtName.setText(editText.getText().toString());
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });

        txtTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(SumDetail.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.edit_popup);
                dialog.setCancelable(true);
                EditText editText = (EditText) dialog.findViewById(R.id.edit);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                editText.setHint("กรอกเบอร์โทรศัพท์ที่ต้องการแก้ไข");

                Button buttonE = (Button) dialog.findViewById(R.id.btncancel);
                buttonE.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                Button buttonC = (Button) dialog.findViewById(R.id.btnenter);
                buttonC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText editText = (EditText) dialog.findViewById(R.id.edit);
                        //editText.getText().toString();

                        txtTel.setText(editText.getText().toString());
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });
        //404
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(SumDetail.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.activity_confirm_popup);
                dialog.setCancelable(true);
                Button buttonE = (Button) dialog.findViewById(R.id.btncel);
                buttonE.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                Button buttonC = (Button) dialog.findViewById(R.id.btnenter);
                buttonC.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View view) {

                        progressDialog.setMessage("รอสักครู่ กำลังทำรายการ ...");
                        progressDialog.show();
                        Intent intent1 = getIntent();

                        if (chk.equals("1")) {
                            Intent intent = getIntent();
                            final StorageReference filepath = mStorage.child("photo").child(auth.getCurrentUser().getUid().toString()).child((Uri.parse(intent.getStringExtra("img1"))).getLastPathSegment());
                            filepath.putFile(Uri.parse(Simg1)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    progressDialog.dismiss();
                                    String path1 = filepath.getPath();

                                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference ref = database.getReference();
                                    final DatabaseReference x = ref.child("order").push();

                                    Intent intentFilepath = getIntent();

                                    Calendar c = Calendar.getInstance();
                                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                                    SimpleDateFormat dff = new SimpleDateFormat("HH:mm:");
                                    String formattedDate = df.format(c.getTime());
                                    String formattedDatef = dff.format(c.getTime());

                                    x.child("user_id").setValue(auth.getCurrentUser().getUid().toString());
                                    x.child("name").setValue(txtName.getText().toString());
                                    x.child("tel").setValue(txtTel.getText().toString());
                                    x.child("time").setValue(formattedDatef);
                                    x.child("date").setValue(formattedDate);
                                    x.child("address1").setValue(address1.getText().toString());
                                    x.child("address2").setValue(address2.getText().toString());
                                    x.child("problem1").setValue(txt1.getText().toString());
                                    x.child("problem2").setValue(txt2.getText().toString());
                                    x.child("Path_img1").setValue(path1);
                                    x.child("Path_img2").setValue("null");
                                    x.child("Path_img3").setValue("null");
                                    x.child("Path_img4").setValue("null");

                                    Intent intent = new Intent(SumDetail.this, MainActivity.class);
                                    finishAffinity();
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            });
                        } else if (chk.equals("2")) {
                            Intent intent = getIntent();
                            filepath = mStorage.child("photo").child(auth.getCurrentUser().getUid().toString()).child((Uri.parse(intent.getStringExtra("img1"))).getLastPathSegment());
                            filepath.putFile(Uri.parse(Simg1)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    Intent intent = getIntent();
                                    filepath2 = mStorage.child("photo").child(auth.getCurrentUser().getUid().toString()).child((Uri.parse(intent.getStringExtra("img2"))).getLastPathSegment());
                                    filepath2.putFile(Uri.parse(Simg2)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                            progressDialog.dismiss();
                                            String path1 = filepath.getPath();
                                            String path2 = filepath2.getPath();

                                            final FirebaseDatabase database = FirebaseDatabase.getInstance();
                                            DatabaseReference ref = database.getReference();
                                            final DatabaseReference x = ref.child("order").push();

                                            Intent intentFilepath = getIntent();

                                            Calendar c = Calendar.getInstance();
                                            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                                            SimpleDateFormat dff = new SimpleDateFormat("HH:mm:");
                                            String formattedDate = df.format(c.getTime());
                                            String formattedDatef = dff.format(c.getTime());

                                            x.child("user_id").setValue(auth.getCurrentUser().getUid().toString());
                                            x.child("name").setValue(txtName.getText().toString());
                                            x.child("tel").setValue(txtTel.getText().toString());
                                            x.child("time").setValue(formattedDatef);
                                            x.child("date").setValue(formattedDate);
                                            x.child("address1").setValue(address1.getText().toString());
                                            x.child("address2").setValue(address2.getText().toString());
                                            x.child("problem1").setValue(txt1.getText().toString());
                                            x.child("problem2").setValue(txt2.getText().toString());
                                            x.child("Path_img1").setValue(path1);
                                            x.child("Path_img2").setValue(path2);
                                            x.child("Path_img3").setValue("null");
                                            x.child("Path_img4").setValue("null");

                                            Intent intent = new Intent(SumDetail.this, MainActivity.class);
                                            finishAffinity();
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                        }
                                    });
                                }
                            });
                        } else if (chk.equals("3")) {
                            Intent intent = getIntent();
                            filepath = mStorage.child("photo").child(auth.getCurrentUser().getUid().toString()).child((Uri.parse(intent.getStringExtra("img1"))).getLastPathSegment());
                            filepath.putFile(Uri.parse(Simg1)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    Intent intent = getIntent();
                                    filepath2 = mStorage.child("photo").child(auth.getCurrentUser().getUid().toString()).child((Uri.parse(intent.getStringExtra("img2"))).getLastPathSegment());
                                    filepath2.putFile(Uri.parse(Simg2)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                            Intent intent = getIntent();
                                            filepath3 = mStorage.child("photo").child(auth.getCurrentUser().getUid().toString()).child((Uri.parse(intent.getStringExtra("img3"))).getLastPathSegment());
                                            filepath3.putFile(Uri.parse(Simg3)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                                    progressDialog.dismiss();
                                                    String path1 = filepath.getPath();
                                                    String path2 = filepath2.getPath();
                                                    String path3 = filepath3.getPath();

                                                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                    DatabaseReference ref = database.getReference();
                                                    final DatabaseReference x = ref.child("order").push();

                                                    Intent intentFilepath = getIntent();

                                                    Calendar c = Calendar.getInstance();
                                                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                                                    SimpleDateFormat dff = new SimpleDateFormat("HH:mm:");
                                                    String formattedDate = df.format(c.getTime());
                                                    String formattedDatef = dff.format(c.getTime());

                                                    x.child("user_id").setValue(auth.getCurrentUser().getUid().toString());
                                                    x.child("name").setValue(txtName.getText().toString());
                                                    x.child("tel").setValue(txtTel.getText().toString());
                                                    x.child("time").setValue(formattedDatef);
                                                    x.child("date").setValue(formattedDate);
                                                    x.child("address1").setValue(address1.getText().toString());
                                                    x.child("address2").setValue(address2.getText().toString());
                                                    x.child("problem1").setValue(txt1.getText().toString());
                                                    x.child("problem2").setValue(txt2.getText().toString());
                                                    x.child("Path_img1").setValue(path1);
                                                    x.child("Path_img2").setValue(path2);
                                                    x.child("Path_img3").setValue(path3);
                                                    x.child("Path_img4").setValue("null");

                                                    Intent intent = new Intent(SumDetail.this, MainActivity.class);
                                                    finishAffinity();
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    startActivity(intent);
                                                    // Toast.makeText(SumDetail.this, path+"", Toast.LENGTH_LONG).show();
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        } else if (chk.equals("4")) {
                            Intent intent = getIntent();
                            filepath = mStorage.child("photo").child(auth.getCurrentUser().getUid().toString()).child((Uri.parse(intent.getStringExtra("img1"))).getLastPathSegment());
                            filepath.putFile(Uri.parse(Simg1)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    Intent intent = getIntent();
                                    filepath2 = mStorage.child("photo").child(auth.getCurrentUser().getUid().toString()).child((Uri.parse(intent.getStringExtra("img2"))).getLastPathSegment());
                                    filepath2.putFile(Uri.parse(Simg2)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                            Intent intent = getIntent();
                                            filepath3 = mStorage.child("photo").child(auth.getCurrentUser().getUid().toString()).child((Uri.parse(intent.getStringExtra("img3"))).getLastPathSegment());
                                            filepath3.putFile(Uri.parse(Simg3)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                                    Intent intent = getIntent();
                                                    filepath4 = mStorage.child("photo").child(auth.getCurrentUser().getUid().toString()).child((Uri.parse(intent.getStringExtra("img4"))).getLastPathSegment());
                                                    filepath4.putFile(Uri.parse(Simg4)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                                            progressDialog.dismiss();
                                                            String path1 = filepath.getPath();
                                                            String path2 = filepath2.getPath();
                                                            String path3 = filepath3.getPath();
                                                            String path4 = filepath4.getPath();

                                                            final FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                            DatabaseReference ref = database.getReference();
                                                            final DatabaseReference x = ref.child("order").push();

                                                            Intent intentFilepath = getIntent();

                                                            Calendar c = Calendar.getInstance();
                                                            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                                                            SimpleDateFormat dff = new SimpleDateFormat("HH:mm:");
                                                            String formattedDate = df.format(c.getTime());
                                                            String formattedDatef = dff.format(c.getTime());

                                                            x.child("user_id").setValue(auth.getCurrentUser().getUid().toString());
                                                            x.child("name").setValue(txtName.getText().toString());
                                                            x.child("tel").setValue(txtTel.getText().toString());
                                                            x.child("time").setValue(formattedDatef);
                                                            x.child("date").setValue(formattedDate);
                                                            x.child("address1").setValue(address1.getText().toString());
                                                            x.child("address2").setValue(address2.getText().toString());
                                                            x.child("problem1").setValue(txt1.getText().toString());
                                                            x.child("problem2").setValue(txt2.getText().toString());
                                                            x.child("Path_img1").setValue(path1);
                                                            x.child("Path_img2").setValue(path2);
                                                            x.child("Path_img3").setValue(path3);
                                                            x.child("Path_img4").setValue(path4);

                                                            Intent intent = new Intent(SumDetail.this, MainActivity.class);
                                                            finishAffinity();
                                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                            startActivity(intent);
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        }


//                                    x.child("name").setValue(txtName.getText().toString());
//                                    x.child("tel").setValue(txtTel.getText().toString());
//                                    x.child("time").setValue(formattedDatef);
//                                    x.child("date").setValue(formattedDate);
//                                    x.child("user").setValue(auth.getCurrentUser().getUid().toString());
//
//                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                    finishAffinity();

//                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                startActivity(intent);
//                                sendWithOtherThread("token");
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });

    }

    private void connectFirebase() {

        database.child("user").child(auth.getCurrentUser().getUid().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name = (String) dataSnapshot.child("info").child("name").getValue();
                tel = (String) dataSnapshot.child("info").child("tel").getValue();
                Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
                txtName.setText(name);
                txtTel.setText(tel);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }


    private void getStringIntent() {
        Intent intent = getIntent();
        chk = intent.getStringExtra("chkImg").toString();
        if (chk.equals("4")) {
            txt1.setText(intent.getStringExtra("edt1"));
            txt2.setText(intent.getStringExtra("edt2"));
            address1.setText(intent.getStringExtra("address1"));
            address2.setText(intent.getStringExtra("address2"));
            img1.setImageURI(Uri.parse(intent.getStringExtra("img1")));
            img2.setImageURI(Uri.parse(intent.getStringExtra("img2")));
            img3.setImageURI(Uri.parse(intent.getStringExtra("img3")));
            img4.setImageURI(Uri.parse(intent.getStringExtra("img4")));
            Simg1 = intent.getStringExtra("img1");
            Simg2 = intent.getStringExtra("img2");
            Simg3 = intent.getStringExtra("img3");
            Simg4 = intent.getStringExtra("img4");

        } else if (chk.equals("3")) {
            txt1.setText(intent.getStringExtra("edt1"));
            txt2.setText(intent.getStringExtra("edt2"));
            address1.setText(intent.getStringExtra("address1"));
            address2.setText(intent.getStringExtra("address2"));
            img1.setImageURI(Uri.parse(intent.getStringExtra("img1")));
            img2.setImageURI(Uri.parse(intent.getStringExtra("img2")));
            img3.setImageURI(Uri.parse(intent.getStringExtra("img3")));
            Simg1 = intent.getStringExtra("img1");
            Simg2 = intent.getStringExtra("img2");
            Simg3 = intent.getStringExtra("img3");
            img4.setVisibility(View.INVISIBLE);
        } else if (chk.equals("2")) {
            txt1.setText(intent.getStringExtra("edt1"));
            txt2.setText(intent.getStringExtra("edt2"));
            address1.setText(intent.getStringExtra("address1"));
            address2.setText(intent.getStringExtra("address2"));
            img1.setImageURI(Uri.parse(intent.getStringExtra("img1")));
            img2.setImageURI(Uri.parse(intent.getStringExtra("img2")));
            Simg1 = intent.getStringExtra("img1");
            Simg2 = intent.getStringExtra("img2");
            img3.setVisibility(View.INVISIBLE);
            img4.setVisibility(View.INVISIBLE);
        } else if (chk.equals("1")) {
            txt1.setText(intent.getStringExtra("edt1"));
            txt2.setText(intent.getStringExtra("edt2"));
            address1.setText(intent.getStringExtra("address1"));
            address2.setText(intent.getStringExtra("address2"));
            img1.setImageURI(Uri.parse(intent.getStringExtra("img1")));
            Simg1 = intent.getStringExtra("img1");
            img2.setVisibility(View.INVISIBLE);
            img3.setVisibility(View.INVISIBLE);
            img4.setVisibility(View.INVISIBLE);
        } else {
            txt1.setText(intent.getStringExtra("edt1"));
            txt2.setText(intent.getStringExtra("edt2"));
            address1.setText(intent.getStringExtra("address1"));
            address2.setText(intent.getStringExtra("address2"));
            img1.setVisibility(View.INVISIBLE);
            img2.setVisibility(View.INVISIBLE);
            img3.setVisibility(View.INVISIBLE);
            img4.setVisibility(View.INVISIBLE);
        }
    }

    private void bindWidget() {
        txtName = (TextView) findViewById(R.id.txtName);
        txtTel = (TextView) findViewById(R.id.txtTel);
        txt1 = (TextView) findViewById(R.id.txt1);
        txt2 = (TextView) findViewById(R.id.txt2);
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        img4 = (ImageView) findViewById(R.id.img4);
        address1 = (TextView) findViewById(R.id.txtAddress);
        address2 = (TextView) findViewById(R.id.txtAddress2);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
    }
}
