package com.example.bugly;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), "991fe9c95c", false);
        CrashReport.initCrashReport(getApplicationContext());

    }
}
