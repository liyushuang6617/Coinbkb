package com.sengmei.meililian.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.sengmei.kline.R;
import com.sengmei.meililian.BaseActivity;
import com.sengmei.meililian.Bean.DataBean;
import com.sengmei.meililian.MainActivity;
import com.sengmei.meililian.Utils.SharedPreferencesUtil;

import java.util.Locale;

public class LanguageActivity extends BaseActivity implements View.OnClickListener{
    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_language);
    }

    @Override
    public void initViews() {
        findViewById(R.id.yingyu).setOnClickListener(this);
        findViewById(R.id.zhongwen).setOnClickListener(this);
    }

    @Override
    public void ReinitViews() {

    }

    @Override
    public void initData() {

    }
    /**
     * 更改应用语言
     *
     * @param locale
     */
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
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.yingyu:
                changeAppLanguage(Locale.US);
                SharedPreferencesUtil.put(LanguageActivity.this, DataBean.Language, "US");
                break;
            case R.id.zhongwen:
                changeAppLanguage(Locale.CANADA);
                SharedPreferencesUtil.put(LanguageActivity.this, DataBean.Language, "CA");
                break;
                default:
                    break;
        }
    }
    public void back(View view){
        finish();
    }
}
