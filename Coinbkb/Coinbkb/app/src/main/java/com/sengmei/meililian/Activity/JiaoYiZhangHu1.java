package com.sengmei.meililian.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.util.LogUtils;
import com.sengmei.RetrofitHttps.Beans.JYLBBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.kline.R;
import com.sengmei.meililian.Adapter.JiaoYiListAdapter;
import com.sengmei.meililian.BaseActivity;
import com.sengmei.meililian.Bean.JiaoYiListBean;
import com.sengmei.meililian.UserBean;
import com.sengmei.meililian.Utils.HttpUtils;
import com.sengmei.meililian.Utils.StringUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JiaoYiZhangHu1 extends BaseActivity implements View.OnClickListener {
    private String Ids,currencys;
    private TextView name;

    private ListView listview;
    private JiaoYiListAdapter adapter;
    private List<JiaoYiListBean> list=new ArrayList<>();
    private TextView wu;
    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.jiaoyizhanghu1);
        Ids = getIntent().getStringExtra("ids");
        currencys = getIntent().getStringExtra("currencys");
        JiaoYiShow();
    }

    @Override
    public void initViews() {
        wu=findViewById(R.id.wu);
        findViewById(R.id.fabijiaoyi).setOnClickListener(this);
        findViewById(R.id.huazhuan).setOnClickListener(this);
        listview=(ListView)findViewById(R.id.listview);
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
        switch (view.getId()) {
            case R.id.tibi:
                LogUtils.e("获取提币信息=Ids"+Ids);
                startActivity(new Intent(JiaoYiZhangHu1.this, DianKaTiBiActivity.class)
                        .putExtra("currencys", currencys));
                break;
            case R.id.fabijiaoyi:
                startActivity(new Intent(JiaoYiZhangHu1.this, JiaoYiFaBi.class)
                        .putExtra("currencys", currencys));
                break;
            case R.id.huazhuan:
                startActivity(new Intent(JiaoYiZhangHu1.this, HuaZhuanActivity.class)
                        .putExtra("currencys", currencys));
                break;
            default:
                break;
        }
    }

    private void JiaoYiShow(){
        GetDataFromServerInterface mFromServerInterface= GetDataFromServer.getInstance(JiaoYiZhangHu1.this).getService();
        Call<JYLBBean> indexdata=mFromServerInterface.getlegal_log(currencys,"legal");
        indexdata.enqueue(new Callback<JYLBBean>() {

            @Override
            public void onResponse(Call<JYLBBean> call, Response<JYLBBean> response) {
                if (response.body() == null) {
                    StringUtil.ToastShow(JiaoYiZhangHu1.this,"请先登录");
                    return;
                }
                if (response.body().getType().equals("ok")) {
                    wu.setVisibility(View.GONE);
                    if (response.body().getMessage().getList().size()==0){
                        wu.setVisibility(View.VISIBLE);
                    }else {
                        adapter = new JiaoYiListAdapter(JiaoYiZhangHu1.this, response.body().getMessage().getList());
                        listview.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<JYLBBean> call, Throwable t) {
                Log.e("AAAAA","@@11="+t.getMessage() );
                StringUtil.ToastShow(JiaoYiZhangHu1.this,"请先登录");
            }
        });
    }
}
