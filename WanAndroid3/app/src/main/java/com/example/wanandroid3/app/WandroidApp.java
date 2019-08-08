package com.example.wanandroid3.app;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;

public class WandroidApp extends Application{


    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {//1
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        CrashReport.initCrashReport(getApplicationContext(), "991fe9c95c", true);
        CrashReport.initCrashReport(getApplicationContext());
    }
}
