package com.example.mybugly;

import android.content.Context;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static com.tencent.bugly.symtabtool.android.SymtabToolAndroid.isDebug;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Hello World!
     */
    private Button mBt;
    private ArrayList<String> list;
    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initContext();
    }

    private void initContext() {
        Context context = getApplicationContext();
// 获取当前包名
        String packageName = context.getPackageName();
// 获取当前进程名
        String processName = getProcessName(Process.myPid());
// 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
// 初始化Bugly
        CrashReport.initCrashReport(context, "991fe9c95c", isDebug, strategy);
// 如果通过“AndroidManifest.xml”来配置APP信息，初始化方法如下
// CrashReport.initCrashReport(context, strategy);
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    private void initView() {
        mBt = (Button) findViewById(R.id.bt);
        mTv = (TextView) findViewById(R.id.tv);
        mBt.setOnClickListener(this);
        list.add("123");
        list.add("123");
        list.add("123");
        //当用户使用自有账号登录时，可以这样统计：
        MobclickAgent.onProfileSignIn("userID");
//当用户使用第三方账号（如新浪微博）登录时，可以这样统计：
        MobclickAgent.onProfileSignIn("WB", "userID");

//登出
        MobclickAgent.onProfileSignOff();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.bt:
                mTv.setText(list.get(0));
                break;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public static void onProfileSignIn(String ID) {

    }

    public static void onProfileSignIn(String Provider, String ID) {

    }

    public static void onProfileSignOff() {

    }
}
