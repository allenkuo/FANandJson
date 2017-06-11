package com.example.allen.fanandjson;

import android.app.Application;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;

/**
 *
 * Created by Allen on 2017/6/10.
 */

public class MyApplication extends Application {

    public static final String TAG = MyApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.enableLogging();
    }
}
