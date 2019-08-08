package com.sengmei.meililian.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.sengmei.kline.R;
import com.sengmei.meililian.BaseActivity;
import com.sengmei.meililian.Fragment.B_HADAX;
import com.sengmei.meililian.Fragment.B_HBG;
import com.sengmei.meililian.Fragment.C_ChuShou;
import com.sengmei.meililian.Fragment.C_GouMai;
import com.sengmei.meililian.UserBean;

public class C2CActivity extends BaseActivity implements View.OnClickListener {
    private C_GouMai bHbg;
    private C_ChuShou bHadax;
    private TextView[] ButtonText;
    private Fragment[] fragments;
    private TextView goumai,chushou;
    private int index;
    private int currentTabIndex;
    private ImageView jilu;

    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.c2c_activity);

        goumai = (TextView) findViewById(R.id.goumai);
        goumai.setOnClickListener(this);
        jilu = (ImageView)findViewById(R.id.jilu);
        jilu.setVisibility(View.VISIBLE);
        jilu.setOnClickListener(this);
        chushou = (TextView)findViewById(R.id.chushou);
        chushou.setOnClickListener(this);
        bHbg=new C_GouMai();
        bHadax=new C_ChuShou();
        fragments = new Fragment[]{bHbg, bHadax};

            getSupportFragmentManager().beginTransaction().add(R.id.content, bHbg)
                .add(R.id.content, bHadax).hide(bHadax)
                .show(bHbg)
                .commit();
    }
    @Override
    public void initViews() {
    }

    @Override
    public void ReinitViews() {

    }

    @Override
    public void initData() {

        ButtonText = new TextView[2];
        ButtonText[0] = (TextView)findViewById(R.id.goumai);
        ButtonText[1] = (TextView)findViewById(R.id.chushou);
        ButtonText[0].setSelected(true);
    }

    public void back(View v) {
        finish();
    }

    private void StarColor() {

        goumai.setBackgroundResource(R.color.transparent);
        chushou.setBackgroundResource(R.color.transparent);
    }
    @Override
    public void onClick(View view) {
            switch (view.getId()){
                case R.id.goumai:
                    StarColor();
                    index = 0;
                    //menuChooseBack.Choose("购买");
                    UserBean.C2CMM="购买";
                    break;
                case R.id.chushou:
                    StarColor();
                    index = 1;
                   // menuChooseBack.Choose("出售");
                    UserBean.C2CMM="出售";
                    break;
                case R.id.jilu:
                    startActivity(new Intent(C2CActivity.this, C2CJiaoYiActivity.class));
                    break;
                default:
                    break;
            }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.content, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        ButtonText[currentTabIndex].setSelected(false);
        // set current tab selected
        ButtonText[index].setSelected(true);
        Log.e("index=", index + "");
        ButtonText[index].setBackgroundResource(R.drawable.fabi_top1);
        currentTabIndex = index;
        }
}
