package com.example.cursovayaapp;

import android.app.Application;

import android.app.Application;
import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StdApp extends Application {
    public static final String LOG_TAG = "student_app_tag";
    private static Api api;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(LOG_TAG, "StudentApplication created!");
        Retrofit rt = new Retrofit.Builder()
                .baseUrl("https://android-for-students.ru")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = rt.create(Api.class);


    }
    public static Api getServerApi(){
        return api;
    }
}
