package com.sengmei.meililian.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sengmei.RetrofitHttps.Adapter.DingDanJiLuAdapter;
import com.sengmei.RetrofitHttps.Beans.DingDanJiLuBean;
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

public class DingDanJiLuShangPu extends BaseActivity implements View.OnClickListener {
    private LinearLayout   you;
    private TextView wu;
    private DingDanJiLuAdapter adapter;
    private List<DingDanJiLuBean.dataBean> list=new ArrayList<>();
    private ListView listview;
    private String Ids;
    private SwipeRefreshLayout refreshLayout;
    private int P=1;

    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.dingdanjilu);
        Ids=getIntent().getStringExtra("ids");
        refreshLayout=(SwipeRefreshLayout)findViewById(R.id.refresh);
        DingDanShow();
    }

    @Override
    public void initViews() {
        listview = (ListView) findViewById(R.id.listview);
        wu = (TextView) findViewById(R.id.wu);
        you = (LinearLayout) findViewById(R.id.you);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final Random random = new Random();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       // P++;
                        DingDanShow();
                        // 加载完数据设置为不刷新状态，将下拉进度收起来
                        refreshLayout.setRefreshing(false);
                    }
                }, 1200);

            }
        });
    }

    @Override
    public void ReinitViews() {
        DingDanShow();
    }

    @Override
    public void initData() {

    }

    public void back(View v) {
        finish();
    }


    private void setList(final List<DingDanJiLuBean.dataBean> news) {
        if (list != null&P==1) {
            list.clear();
        }
        list.addAll(news);
        if (list.size()==0){
            listview.setVisibility(View.GONE);
            wu.setVisibility(View.VISIBLE);
        }
        adapter = new DingDanJiLuAdapter(DingDanJiLuShangPu.this, list);
        listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DingDanJiLuBean.dataBean bean=list.get(position);
                if (bean.getIs_sure().equals("0")){
                    // holder.a2.setText("未完成");
                    if (bean.getType().equals("buy")){
                       startActivity(new Intent(DingDanJiLuShangPu.this,DingDanXiangQingActivity22.class).putExtra("ids",bean.getId()));
                    }else {
                        startActivity(new Intent(DingDanJiLuShangPu.this,DingDanXiangQingActivity2.class)
                                .putExtra("ids",bean.getId()));
                    }
                }else if (bean.getIs_sure().equals("1")){
                    // holder.a2.setText("已完成");
                     startActivity(new Intent(DingDanJiLuShangPu.this,DingDanXiangQingActivity.class).putExtra("ids",bean.getId()));
                }else if (bean.getIs_sure().equals("2")){
                    // holder.a2.setText("已取消");
                     startActivity(new Intent(DingDanJiLuShangPu.this,DingDanXiangQingActivity.class).putExtra("ids",bean.getId()));
                }else if (bean.getIs_sure().equals("3")){
                    // holder.a2.setText("已付款");
                    if (bean.getType().equals("buy")){
                        startActivity(new Intent(DingDanJiLuShangPu.this,DingDanXiangQingActivity.class).putExtra("ids",bean.getId()));
                    }else {
                        startActivity(new Intent(DingDanJiLuShangPu.this,DingDanXiangQingActivity2.class)
                                .putExtra("ids",bean.getId()));
                    }
                }
            }
        });
    }
    //订单记录
    private void DingDanShow() {
        Log.e("DingDanShowDingDanShow", "@@DingDanShow=" + Ids);
        refreshLayout.setRefreshing(true);
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(DingDanJiLuShangPu.this).getService();
        Call<DingDanJiLuBean> indexdata = mFromServerInterface.getlegal(Ids,""+P);
        indexdata.enqueue(new Callback<DingDanJiLuBean>() {

            @Override
            public void onResponse(Call<DingDanJiLuBean> call, Response<DingDanJiLuBean> response) {
                refreshLayout.setRefreshing(false);
                if (response.body() == null) {
                    StringUtil.ToastShow(DingDanJiLuShangPu.this,"请先登录");
                    return;
                }
                Log.e("AAAAA", "@@DingDanShow=" + response.body().getType().equals("ok"));
                if (response.body().getType().equals("ok")) {
                    Log.e("DingDanShowDingDanShow", "@@DingDanShow=" + response.body().getMessage().getData().size());
                   setList(response.body().getMessage().getData());

                }
            }

            @Override
            public void onFailure(Call<DingDanJiLuBean> call, Throwable t) {
                refreshLayout.setRefreshing(false);
                Log.e("AAAAA", "@@DingDanShow=" + t.getMessage());
                listview.setVisibility(View.GONE);
                wu.setVisibility(View.VISIBLE);
                StringUtil.ToastShow(DingDanJiLuShangPu.this,"请先登录");
            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            /*case R.id.next:
                break;*/
            default:
                break;
        }
    }

}
