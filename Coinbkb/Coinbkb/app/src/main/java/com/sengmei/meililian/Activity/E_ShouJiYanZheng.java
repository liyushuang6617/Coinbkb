package com.sengmei.meililian.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sengmei.kline.R;
import com.sengmei.meililian.BaseActivity;

public class E_ShouJiYanZheng extends BaseActivity implements View.OnClickListener{
    private TextView tv_shouji;
    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.e_shoujiyanzheng);
    }

    @SuppressLint("WrongViewCast")
    @Override
    public void initViews() {
        tv_shouji=(TextView)findViewById(R.id.tv_shouji);
        tv_shouji.setText(E_AnQuanZhongXin.shoujis);
    }

    @Override
    public void ReinitViews() {

    }

    @Override
    public void initData() {

    }
    public void back(View v) {
        finish();
    }

    @Override
    public void onClick(View view) {

    }
}
