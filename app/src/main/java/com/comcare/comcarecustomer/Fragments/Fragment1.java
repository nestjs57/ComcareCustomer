package com.comcare.comcarecustomer.Fragments;


import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.comcare.comcarecustomer.AddDetail;
import com.comcare.comcarecustomer.Maps.AddLocation;
import com.comcare.comcarecustomer.Models.MarkerModel;
import com.comcare.comcarecustomer.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;


/**
 * A simple {@link Fragment} subclass.
 */


public class Fragment1 extends Fragment implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback {

    private GoogleMap mMapView;
    private LatLng defaultLocation;
    private GoogleApiClient mApiClient;
    private LocationRequest mRequest;
    private static final long UPDATE_INTERVAL = 5000;
    private static final long FASTEST_INTERVAL = 1000;
    private double latCur;
    private double lngCur;

    private ArrayList<MarkerModel> dataset;
    private Button btnRequese;
    private String lat = "";
    private String lng = "";

    public Fragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment1, container, false);
        dataset = new ArrayList<MarkerModel>();

        bindWidgetMap(v);
        setEvent(v);
        return v;
    }

    private void setEvent(View v) {
        btnRequese = (Button) v.findViewById(R.id.btnRequese);
        btnRequese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddDetail.class);
                intent.putExtra("latCur", lat);
                intent.putExtra("lngCur", lng);
                startActivity(intent);
            }
        });
    }

    private void bindWidgetMap(View v) {
        FragmentManager fragmentMgr = getFragmentManager();
        SupportMapFragment mMapViewFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.mMap);
        mMapViewFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMapView = googleMap;
        requestRealTimePermission();

        //12.651319, 99.859887
        //12.651933, 99.863304
        //12.653897, 99.864269

        LatLng latLng = new LatLng(12.651319, 99.859887);
        LatLng latLng2 = new LatLng(12.651933, 99.863304);
        LatLng latLng3 = new LatLng(12.653897, 99.864269);

        /*MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.title("พรภวิษย์ บัวบาน\\หอ 2 ศิลปากรเพชรบุรี");
        markerOption.icon(BitmapDescriptorFactory.fromResource(R.mipmap.wrench));
        mMapView.addMarker(markerOption);*/

        addDunmyData();
        for (int i = 0; i < dataset.size(); i++) {
            addMarker(dataset.get(i).getLat(), dataset.get(i).getLng(), dataset.get(i).getTxt());
        }


    }

    private void addMarker(double lat, double lng, String text) {

        LatLng latLng = new LatLng(lat, lng);
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.title(text);
        markerOption.icon(BitmapDescriptorFactory.fromResource(R.mipmap.wrench));
        mMapView.addMarker(markerOption);
    }

    private void addDunmyData() {
        dataset.add(new MarkerModel(12.651319, 99.859887, "พรภวิษย์ บัวบาน"));
        dataset.add(new MarkerModel(12.651933, 99.863304, "อรรถชัยว์ สว่าง"));
        dataset.add(new MarkerModel(12.653897, 99.864269, "สักสิท จันทรา"));
        dataset.add(new MarkerModel(12.652023, 99.860417, "ศุทธิพงศ์ เสวนา"));
    }


    public void requestRealTimePermission() {
        Nammu.askForPermission(this, Manifest.permission.ACCESS_FINE_LOCATION, new PermissionCallback() {
            @Override
            public void permissionGranted() {
                startLocationTracking();
            }

            @Override
            public void permissionRefused() {

            }
        });


    }

    // End of Utility
    @SuppressWarnings({"MissingPermission"})
    public void startLocationTracking() {

        mApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        mMapView.setMyLocationEnabled(true);
                        // get last location
                        try {
                            getLastLocation();
                        } catch (Exception e) {
                        }

                        // set request
                        mRequest = LocationRequest.create();
                        mRequest.setInterval(UPDATE_INTERVAL);
                        mRequest.setFastestInterval(FASTEST_INTERVAL);
                        mRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                        LocationServices.FusedLocationApi.requestLocationUpdates(mApiClient, mRequest, new LocationListener() {
                            @Override
                            public void onLocationChanged(Location location) {
                                animateToDefaultLocation();
                            }
                        });
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        Toast.makeText(getActivity(), "Connection is susppended!", Toast.LENGTH_LONG).show();
                    }
                }).build();

        mApiClient.connect();


    }

    public void animateToDefaultLocation() {
        if (defaultLocation == null) {
            defaultLocation = convertToLatLng(getLastLocation());
            mMapView.animateCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 15));
        }


    }

    public LatLng convertToLatLng(Location location) {

        latCur = location.getLatitude();
        lngCur = location.getLongitude();
        lat = String.valueOf(latCur);
        lng = String.valueOf(lngCur);

        return new LatLng(location.getLatitude(), location.getLongitude());

    }

    @SuppressWarnings({"MissingPermission"})
    public Location getLastLocation() {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mApiClient);
        radiusCenter(location);

        if (location != null) {
        }
        return location;
    }


    private void radiusCenter(Location location) {
//        Circle circle = mMapView.addCircle(new CircleOptions()
//                .center(new LatLng(location.getLatitude(), location.getLongitude()))
//                .radius(500)
//                .strokeColor(Color.parseColor("#3f51b5"))
//                .strokeWidth(2)
//                .fillColor(Color.argb(20, 50, 10, 255)));

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.icon(BitmapDescriptorFactory.fromResource(R.mipmap.pin));
        mMapView.addMarker(markerOption);
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }

    private class MarkerData {
    }
}
