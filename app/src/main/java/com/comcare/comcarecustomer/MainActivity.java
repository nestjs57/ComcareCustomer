package com.comcare.comcarecustomer;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.comcare.comcarecustomer.Fragments.Fragment1;
import com.comcare.comcarecustomer.Fragments.Fragment2;
import com.comcare.comcarecustomer.Fragments.Fragment3;
import com.comcare.comcarecustomer.LoginAndRegister.Login;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    private FirebaseUser mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentManager frg = getSupportFragmentManager();
        FragmentTransaction transaction = frg.beginTransaction();
        transaction.replace(R.id.content, new Fragment1()).commit();
        debugHashKey();


        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        if (mAuth == null) {
            Intent intent = new Intent(getApplication(), Login.class);
            startActivity(intent);
            finish();
        }



            final DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();


            if (mAuth != null) {
                dbref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final FirebaseUser user = mAuth;

                        String desc_ofPost = (String) dataSnapshot.child("user").child(user.getUid()).child("info").child("profile_image").getValue();
                        String name = (String) dataSnapshot.child("user").child(user.getUid()).child("info").child("full_name").getValue();

                        ImageView profile = (ImageView) findViewById(R.id.imageView);
                        TextView full_name = (TextView) findViewById(R.id.txtEmail);


                        try {
                            full_name.setText(name);
                            Glide.with(getApplication()).load(desc_ofPost).transform(new CircleTransform(getApplication())).into(profile);

                        }catch (Exception e){

                        }

                        //full_name.setText(name);
                        //Glide.with(getApplication()).load(desc_ofPost).transform(new CircleTransform(getApplication())).into(profile);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
    }

//    private void initFont() {
//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("assets/fonts/supermarket.ttf")
//                .setFontAttrId(R.attr.fontPath)
//                .build()
//        );
//    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }

    private void debugHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.comcare.comcarecustomer",
                    PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    private void logout(){
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager  frg = getSupportFragmentManager();
            FragmentTransaction transaction = frg.beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    transaction.replace(R.id.content, new Fragment1()).commit();
                    return true;
                case R.id.navigation_status:
                    transaction.replace(R.id.content, new Fragment2()).commit();
                    return true;
                case R.id.navigation_chat:
                    transaction.replace(R.id.content, new Fragment3()).commit();
                    return true;

            }
            return false;
        }
    };


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        else if (id == R.id.menu_profile) {
            Toast.makeText(MainActivity.this, "nav_slideshow", Toast.LENGTH_LONG).show();

        } else if (id == R.id.menu_option) {
            Toast.makeText(MainActivity.this, "nav_manage", Toast.LENGTH_LONG).show();

        } else if (id == R.id.menu_contact) {
            Toast.makeText(MainActivity.this, "nav_manage", Toast.LENGTH_LONG).show();
        } else if (item.getItemId()==R.id.menu_logout){
            logout();
        }
            return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.menu_profile) {
            Toast.makeText(MainActivity.this, "nav_slideshow", Toast.LENGTH_LONG).show();

        } else if (id == R.id.menu_option) {
            Toast.makeText(MainActivity.this, "nav_manage", Toast.LENGTH_LONG).show();

        } else if (id == R.id.menu_contact) {
           logout();
        } else if (item.getItemId()==R.id.menu_logout){
            logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
