package com.example.mydemo1.app;

import android.app.Application;
import android.content.Context;


import com.example.mydemo1.utils.DataManager;

import javax.inject.Inject;

public class MyApp extends Application {

    public static boolean isNaightMode;
    public static boolean isLogin;
    private static MyApp app;
    public static String username;
    public static String userpwd;
    @Inject
    private DataManager mDataManager;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static MyApp getInstance() {
        return app;
    }

}
