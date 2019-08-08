package com.sengmei.meililian.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.sengmei.kline.R;
import com.sengmei.meililian.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class E_SuoBiListActivity extends BaseActivity {
    private List<String> list=new ArrayList<>();
    private ListView listview;
    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.e_suobilistactivity);
        listview=(ListView)findViewById(R.id.listview);

    }

    @Override
    public void initViews() {

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
}
