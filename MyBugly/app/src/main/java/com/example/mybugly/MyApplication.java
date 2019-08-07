package com.example.mybugly;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static com.tencent.bugly.symtabtool.android.SymtabToolAndroid.isDebug;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), "991fe9c95c", false);
        CrashReport.initCrashReport(getApplicationContext());

    }
}
