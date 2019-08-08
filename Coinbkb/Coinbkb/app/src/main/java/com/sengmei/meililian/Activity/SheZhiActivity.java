package com.sengmei.meililian.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sengmei.kline.R;
import com.sengmei.meililian.BaseActivity;

public class SheZhiActivity extends BaseActivity implements View.OnClickListener{
    private TextView shezhi;
    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shezhe_activity);
    }

    @Override
    public void initViews() {
   findViewById(R.id.yuyan).setOnClickListener(this);
        shezhi=(TextView)findViewById(R.id.shezhi);
    }

    @Override
    public void ReinitViews() {

        shezhi.setText(R.string.title);
    }

    @Override
    public void initData() {

    }
    public void back(View view){
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.yuyan:
                startActivity(new Intent(this,LanguageActivity.class));
                break;
                default:
                    break;
        }
    }
}
