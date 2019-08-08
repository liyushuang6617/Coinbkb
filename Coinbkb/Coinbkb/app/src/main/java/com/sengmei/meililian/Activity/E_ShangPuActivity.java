package com.sengmei.meililian.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sengmei.RetrofitHttps.Adapter.WodeShangPuAdapter;
import com.sengmei.RetrofitHttps.Beans.WoDeShangPuBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.kline.R;
import com.sengmei.meililian.BaseActivity;
import com.sengmei.meililian.Utils.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class E_ShangPuActivity extends BaseActivity {
    private List<WoDeShangPuBean.DateBean> list=new ArrayList<>();
    private ListView listview;
    private TextView fresh;
    private SwipeRefreshLayout refreshLayout;
    private WodeShangPuAdapter shangPuAdapter;
    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.e_shangpuactivity);
    }

    @Override
    public void initViews() {
        fresh= findViewById(R.id.fresh);
        fresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShangPuListShow();
            }
        });
        listview=(ListView)findViewById(R.id.listview);

        refreshLayout=(SwipeRefreshLayout)findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final Random random = new Random();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        ShangPuListShow();
                        // 加载完数据设置为不刷新状态，将下拉进度收起来
                        refreshLayout.setRefreshing(false);
                    }
                }, 1200);

            }
        });
    }

    @Override
    public void ReinitViews() {
        ShangPuListShow();

    }

    @Override
    public void initData() {

    }

    public void back(View v) {
        finish();
    }

    /**填入数据*/
    private void setHList(final List<WoDeShangPuBean.DateBean> news) {
        if (list != null) {
            list.clear();
        }
        list.addAll(news);
        if (list.size()==0){
            return;
        }

         shangPuAdapter=new WodeShangPuAdapter(E_ShangPuActivity.this,list);
        listview.setAdapter(shangPuAdapter);
    }
    private void ShangPuListShow() {
        refreshLayout.setRefreshing(true);
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(E_ShangPuActivity.this).getService();
        Call<WoDeShangPuBean> indexdata = mFromServerInterface.getseller();
        indexdata.enqueue(new Callback<WoDeShangPuBean>() {

            @Override
            public void onResponse(Call<WoDeShangPuBean> call, Response<WoDeShangPuBean> response) {
                refreshLayout.setRefreshing(false);
                if (response.body() == null) {
                    StringUtil.ToastShow(E_ShangPuActivity.this,"请先登录");
                    fresh.setVisibility(View.VISIBLE);
                    return;
                }
                if (response.body().getType().equals("ok")) {
                    fresh.setVisibility(View.GONE);
                    setHList(response.body().getMessage().getData());
                }else {

                    fresh.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<WoDeShangPuBean> call, Throwable t) {
                refreshLayout.setRefreshing(false);

                fresh.setVisibility(View.VISIBLE);
                StringUtil.ToastShow(E_ShangPuActivity.this,"请先登录");
                Log.e("AAAAA", "@@11=" + t.getMessage());
            }
        });
    }
}
