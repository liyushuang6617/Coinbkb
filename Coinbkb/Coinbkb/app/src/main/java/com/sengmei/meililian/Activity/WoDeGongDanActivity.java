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
import com.sengmei.RetrofitHttps.Beans.DanShowBean;
import com.sengmei.RetrofitHttps.Beans.GeRenZhongXinBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.kline.R;
import com.sengmei.meililian.Adapter.GongDanAdapter;
import com.sengmei.meililian.BaseActivity;
import com.sengmei.meililian.Bean.DataBean;
import com.sengmei.meililian.Bean.GongDanBean;
import com.sengmei.meililian.UserBean;
import com.sengmei.meililian.Utils.HttpUtils;
import com.sengmei.meililian.Utils.StringUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WoDeGongDanActivity extends BaseActivity implements View.OnClickListener{
    private ListView listview;
    private TextView wu;
    private GongDanAdapter gongDanAdapter;
    private List<GongDanBean> list=new ArrayList<>();
    private GongDanBean gongDanBean;
    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.wodegongdanactivity);
        GongDanShow();
    }

    @Override
    public void initViews() {
        wu=findViewById(R.id.wu);
        listview=(ListView)findViewById(R.id.listview);
        findViewById(R.id.tianjia).setOnClickListener(this);
    }

    @Override
    public void ReinitViews() {
        GongDanShow();
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
            case R.id.tianjia:
                startActivity(new Intent(this,TianJianGongDan.class));
                break;
            default:
                break;
        }
    }

    private void GongDanShow(){
        GetDataFromServerInterface mFromServerInterface= GetDataFromServer.getInstance(WoDeGongDanActivity.this).getService();
        Call<DanShowBean> indexdata=mFromServerInterface.GongDan();
        indexdata.enqueue(new Callback<DanShowBean>() {

            @Override
            public void onResponse(Call<DanShowBean> call, Response<DanShowBean> response) {
                if (response.body() == null) {
                    return;
                }
                if (response.body().getType().equals("ok")) {
                    gongDanAdapter=new GongDanAdapter(WoDeGongDanActivity.this,response.body().getMessage().getData());
                    listview.setAdapter(gongDanAdapter);
                    if (response.body().getMessage().getData().size()==0){
                        wu.setVisibility(View.VISIBLE);
                        listview.setVisibility(View.GONE);
                    }else {
                        listview.setVisibility(View.VISIBLE);
                        wu.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<DanShowBean> call, Throwable t) {
                Log.e("AAAAA","@@11="+t.getMessage() );
            }
        });
    }
}
