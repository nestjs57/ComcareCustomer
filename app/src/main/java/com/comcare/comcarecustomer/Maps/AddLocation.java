package com.comcare.comcarecustomer.Maps;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.comcare.comcarecustomer.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AddLocation extends AppCompatActivity implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback {

    private LinearLayout linearLayout;
    private static final int REQUEST_LOCATION = 1;
    private GoogleMap mMapView;
    private static final long UPDATE_INTERVAL = 5000;
    private static final long FASTEST_INTERVAL = 1000;
    private LocationRequest mRequest;
    private GoogleApiClient mApiClient;
    private MarkerOptions mapMarker;
    private int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private static final int REQUEST_CC = 100;
    private TextView txt;

    private String latCur = "";
    private String lngCur = "";

    //Maps



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);


        Intent intent = getIntent();
        latCur = intent.getStringExtra("latCur");
        lngCur = intent.getStringExtra("lngCur");
        Toast.makeText(AddLocation.this, latCur, Toast.LENGTH_LONG).show();

        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        android.app.FragmentManager fragmentMgr = getFragmentManager();
        SupportMapFragment mMapViewFragment = (SupportMapFragment) this.getSupportFragmentManager().findFragmentById(R.id.mMap);
        mMapViewFragment.getMapAsync(this);


        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    AutocompleteFilter typeFilter = new AutocompleteFilter.Builder().
                            setTypeFilter(AutocompleteFilter.TYPE_FILTER_NONE).build();

                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .setFilter(typeFilter)
                                    .build(AddLocation.this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);

                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        txt = (TextView) findViewById(R.id.textView10);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                String address_choose = String.valueOf(place.getAddress());
                String lat_choose = String.valueOf(place.getLatLng().latitude);
                String lng_choose = String.valueOf(place.getLatLng().longitude);

                LatLng latLng = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);

                mMapView.clear();
                mMapView.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(lat_choose), Double.parseDouble(lng_choose)))
                );

                mMapView.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(lat_choose), Double.parseDouble(lng_choose)), 15.0f));

//                MarkerOptions markerOption = new MarkerOptions();
//                markerOption.position(latLng);
//                markerOption.title(address_choose);
//                markerOption.icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker));
//                mMapView.addMarker(markerOption);
//
//                mMapView.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
//                //Toast.makeText(getApplicationContext(), address_choose, Toast.LENGTH_SHORT).show();
                txt.setText(address_choose);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                //Toast.makeText(getApplicationContext(), status.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMapView = googleMap;
        mMapView.addMarker(new MarkerOptions()
                .position(new LatLng(Double.parseDouble(latCur), Double.parseDouble(lngCur)))
        );

        mMapView.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(latCur), Double.parseDouble(lngCur)), 15.0f));

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    //zoomAllMarkers();


}
