package com.comcare.comcarecustomer.Models;

/**
 * Created by AdvicePT on 19/11/2560.
 */

public class MarkerModel {
    private double lat;
    private double lng;
    private String txt;

    public MarkerModel(double lat, double lng, String txt) {
        this.lat = lat;
        this.lng = lng;
        this.txt = txt;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
