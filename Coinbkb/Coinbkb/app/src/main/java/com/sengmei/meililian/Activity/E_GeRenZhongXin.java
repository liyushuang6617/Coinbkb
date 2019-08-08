package com.sengmei.meililian.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sengmei.kline.R;
import com.sengmei.meililian.BaseActivity;

public class E_GeRenZhongXin extends BaseActivity {
    private LinearLayout ll, lll;
    private TextView renzhen, names, zhanghu, ids, zhengjianhao;
    private String Namas, ZhangHus, Ids, ZhengJianHaos;

    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.e_gerenzhongxin);
        String s = getIntent().getStringExtra("review_status");
        Namas = getIntent().getStringExtra("Names");
        ZhangHus = getIntent().getStringExtra("Accounts");
        Ids = getIntent().getStringExtra("Ids");
        ZhengJianHaos = getIntent().getStringExtra("Card_ids");
        renzhen = (TextView) findViewById(R.id.renzhen);
        ll = (LinearLayout) findViewById(R.id.ll);
        lll = (LinearLayout) findViewById(R.id.lll);
        if (s.equals("0")) {
            renzhen.setText("未认证");
            lll.setVisibility(View.GONE);
            ll.setVisibility(View.VISIBLE);
            renzhen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    startActivity(new Intent(E_GeRenZhongXin.this, ShenFen_Activity2.class));
                }
            });
        } else if (s.equals("1")) {
            renzhen.setText("正在审核");
        } else if (s.equals("2")) {
            renzhen.setText("已审核");
            ll.setVisibility(View.GONE);
            lll.setVisibility(View.VISIBLE);
        } else if (s.equals("3")) {
            renzhen.setText("已拒绝");
            lll.setVisibility(View.GONE);
            ll.setVisibility(View.VISIBLE);
            renzhen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    startActivity(new Intent(E_GeRenZhongXin.this, ShenFen_Activity2.class));
                }
            });
        }
    }
    @Override
    public void initViews() {
        names = (TextView) findViewById(R.id.names);
        zhanghu = (TextView) findViewById(R.id.zhanghu);
        ids = (TextView) findViewById(R.id.ids);
        zhengjianhao = (TextView) findViewById(R.id.zhengjianhao);
        names.setText(Namas);
        zhanghu.setText(ZhangHus);
        ids.setText(Ids);
        zhengjianhao.setText(ZhengJianHaos);
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
