package com.comcare.comcarecustomer;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class CustomFont extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initFont();
    }
    private void initFont() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/supermarket.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
