package com.sengmei.meililian.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.sengmei.RetrofitHttps.Adapter.BangZhuAdapter;
import com.sengmei.RetrofitHttps.Beans.LunBoBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.kline.R;
import com.sengmei.meililian.BaseActivity;
import com.sengmei.meililian.MyApplication;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class E_BanZhuZhongXin extends BaseActivity {
    private List<LunBoBean.Bean> hlist = new ArrayList<>();
    private ListView listView;

    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.e_bangzhuguanli);
        NewListShow();
    }

    @Override
    public void initViews() {
        listView = findViewById(R.id.listview);
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

    //填充list数据
    private void setList(final List<LunBoBean.Bean> news) {
        if (hlist != null) {
            hlist.clear();
        }
        hlist.addAll(news);
        Log.e("AAAAA", "广告id=2=" + hlist.size());
        BangZhuAdapter zhuAdapter = new BangZhuAdapter(E_BanZhuZhongXin.this, hlist);
        listView.setAdapter(zhuAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(E_BanZhuZhongXin.this, GuangGaoActivity.class).putExtra("titles", "gonggao")
                        .putExtra("Ids", hlist.get(position).getId()));
            }
        });
    }

    /*广告*/
    private void NewListShow() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(E_BanZhuZhongXin.this).getService();
        Call<LunBoBean> indexdata = mFromServerInterface.getnews("32");
        indexdata.enqueue(new Callback<LunBoBean>() {

            @Override
            public void onResponse(Call<LunBoBean> call, Response<LunBoBean> response) {
                if (response.body() == null) {

                    return;
                }
                Log.e("糖果糖果AAAAA", "广告" + response.body());
                Log.e("糖果糖果AAAAA", "广告" + response);
                if (response.body().getType().equals("ok")) {
                    Log.e("AAAAA", "广告id=2=" + response.body().getMessage().toString());
                    setList(response.body().getMessage().getList());
                }
            }

            @Override
            public void onFailure(Call<LunBoBean> call, Throwable t) {
                Log.e("AAAAA", "@@11=" + t.getMessage());


            }
        });
    }


}
