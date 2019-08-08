package com.sengmei.meililian;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import com.sengmei.kline.R;
import com.sengmei.meililian.Bean.DataBean;
import com.sengmei.meililian.Utils.SharedPreferencesUtil;
import com.sengmei.meililian.Utils.StringUtil;

import java.util.Locale;

public class FirstActivity extends BaseActivity{
    private Handler handler;
    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_first);

        String str=  (String)SharedPreferencesUtil.get(FirstActivity.this, DataBean.Language, "cc");
        Log.e("AAAASADAD","str="+str );
        if (str.equals("US")){
            changeAppLanguage(Locale.US);
        }else {
            changeAppLanguage(Locale.CHINA);
        }
    }

    @Override
    public void initViews() {
        SharedPreferences share=getSharedPreferences("DATA", 0);
        String Tokens=share.getString(DataBean.Authori, "");
        Log.e("AAAASADAD","str="+Tokens );
        if (!StringUtil.isBlank(Tokens)&&!Tokens.equals("0")){
            MyApplication.Authori=Tokens;
            Log.e("AAAASADAD","str="+Tokens );
        }
    }

    @Override
    public void ReinitViews() {

    }

    @Override
    public void initData() {

    }

    public void changeAppLanguage(Locale locale) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        Configuration configuration = getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        getResources().updateConfiguration(configuration, metrics);
        //  recreate();//刷新界面
        //重新启动Activity
        new Thread(){
            public void run(){
                try {
                    sleep(1500);
                    Intent intent = new Intent(FirstActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}
