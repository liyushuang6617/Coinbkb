package com.example.wanandroid2.app;

import android.app.Application;

public class MyApp extends Application {

    private static MyApp app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
//        CrashReport.initCrashReport(getApplicationContext(), "991fe9c95c", false);
//        CrashReport.initCrashReport(getApplicationContext());

    }

    public static MyApp getInstance() {
        return app;
    }
}
