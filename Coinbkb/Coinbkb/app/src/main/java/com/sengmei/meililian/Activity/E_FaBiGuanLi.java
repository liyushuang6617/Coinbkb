package com.sengmei.meililian.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.sengmei.RetrofitHttps.Beans.FaBiGuanLiBean;
import com.sengmei.RetrofitHttps.Beans.FormBTCBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.RetrofitHttps.NetUtils;
import com.sengmei.kline.R;
import com.sengmei.meililian.Adapter.B_HADAXAdapter;
import com.sengmei.meililian.Adapter.GuanLiListAdapter;
import com.sengmei.meililian.BaseActivity;
import com.sengmei.meililian.Bean.DataBean;
import com.sengmei.meililian.Bean.GuanLiListBean;
import com.sengmei.meililian.UserBean;
import com.sengmei.meililian.Utils.HttpUtils;
import com.sengmei.meililian.Utils.MyListView;
import com.sengmei.meililian.Utils.SharedPreferencesUtil;
import com.sengmei.meililian.Utils.StringUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class E_FaBiGuanLi extends BaseActivity implements View.OnClickListener {
    private LinearLayout you, saixuan_dialog;
    private TextView wu;
    private EditText name, fabipassword, surepassword;
    private String Name, Fabipassword, Surepassword;
    private boolean saixuan_d = true;
    private GuanLiListAdapter adapter;
    private GuanLiListBean bean;
    private List<GuanLiListBean> list = new ArrayList<>();
    private MyListView listview;
    private TextView a1, a2, a3, a4, a5, a6, b1, b2;
    private String Types = "", is_sures = "";
    private String Types1 = "", is_sures1 = "";
    private SwipeRefreshLayout refreshLayout;

    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.e_fabiguanli);
        FaBiGetShow();
    }

    @Override
    public void initViews() {
        listview = (MyListView) findViewById(R.id.listview);
        wu = (TextView) findViewById(R.id.wu);
        you = (LinearLayout) findViewById(R.id.you);
        saixuan_dialog = (LinearLayout) findViewById(R.id.saixuan_dialog);
        saixuan_dialog.setOnClickListener(this);
        name = (EditText) findViewById(R.id.name);
        fabipassword = (EditText) findViewById(R.id.fabipassword);
        surepassword = (EditText) findViewById(R.id.surepassword);
        findViewById(R.id.saixuan).setOnClickListener(this);
        findViewById(R.id.next).setOnClickListener(this);

        a1 = (TextView) findViewById(R.id.a1);
        a1.setOnClickListener(this);
        a2 = (TextView) findViewById(R.id.a2);
        a2.setOnClickListener(this);
        a3 = (TextView) findViewById(R.id.a3);
        a3.setOnClickListener(this);
        a4 = (TextView) findViewById(R.id.a4);
        a4.setOnClickListener(this);
        a5 = (TextView) findViewById(R.id.a5);
        a5.setOnClickListener(this);
        a6 = (TextView) findViewById(R.id.a6);
        a6.setOnClickListener(this);
        b1 = (TextView) findViewById(R.id.b1);
        b1.setOnClickListener(this);
        b2 = (TextView) findViewById(R.id.b2);
        b2.setOnClickListener(this);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final Random random = new Random();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (NetUtils.isConnected(E_FaBiGuanLi.this)) {

                            SaiXuanShow();
                        } else {
                            StringUtil.ToastShow(E_FaBiGuanLi.this, "网络未连接");
                        }
                        // 加载完数据设置为不刷新状态，将下拉进度收起来
                        refreshLayout.setRefreshing(false);
                    }
                }, 1200);

            }
        });
    }

    @Override
    public void ReinitViews() {
        FaBiGetShow();
    }

    @Override
    public void initData() {

    }

    public void back(View v) {
        finish();
    }


    //筛选
    private void SaiXuanShow() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(E_FaBiGuanLi.this).getService();
        Call<FaBiGuanLiBean> indexdata = mFromServerInterface.getuser_deal1(is_sures, Types);
        indexdata.enqueue(new Callback<FaBiGuanLiBean>() {

            @Override
            public void onResponse(Call<FaBiGuanLiBean> call, Response<FaBiGuanLiBean> response) {
                if (response.body() == null) {

                    return;
                }
                if (response.body().getType().equals("ok")) {
                    adapter = new GuanLiListAdapter(E_FaBiGuanLi.this, response.body().getMessage().getData());
                    listview.setAdapter(adapter);
                    if (response.body().getMessage().getData().size() == 0) {

                        listview.setVisibility(View.GONE);
                        wu.setVisibility(View.VISIBLE);
                    } else {
                        listview.setVisibility(View.VISIBLE);
                        wu.setVisibility(View.GONE);
                    }


                }
            }

            @Override
            public void onFailure(Call<FaBiGuanLiBean> call, Throwable t) {
                Log.e("AAAAA", "@@11=" + t.getMessage());
                listview.setVisibility(View.GONE);
                wu.setVisibility(View.VISIBLE);
            }
        });
    }

    //获取数据
    private void FaBiGetShow() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(E_FaBiGuanLi.this).getService();
        Call<FaBiGuanLiBean> indexdata = mFromServerInterface.getuser_deal();
        indexdata.enqueue(new Callback<FaBiGuanLiBean>() {

            @Override
            public void onResponse(Call<FaBiGuanLiBean> call, Response<FaBiGuanLiBean> response) {
                if (response.body() == null) {
                    StringUtil.ToastShow(E_FaBiGuanLi.this, "请先登录");

                    return;
                }
                if (response.body().getType().equals("ok")) {
                    adapter = new GuanLiListAdapter(E_FaBiGuanLi.this, response.body().getMessage().getData());
                    listview.setAdapter(adapter);
                    if (response.body().getMessage().getData().size() == 0) {

                        listview.setVisibility(View.GONE);
                        wu.setVisibility(View.VISIBLE);
                    } else {

                        listview.setVisibility(View.VISIBLE);
                        wu.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<FaBiGuanLiBean> call, Throwable t) {
                Log.e("AAAAA", "@@11=" + t.getMessage());
                you.setVisibility(View.GONE);
                wu.setVisibility(View.VISIBLE);
                StringUtil.ToastShow(E_FaBiGuanLi.this, "请先登录");
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.saixuan:
                saixuan_dialog.setVisibility(View.VISIBLE);
                ABColor1();
                ABColor();
                if (!StringUtil.isBlank(Types)&Types.equals("sell")){
                    a1.setBackgroundResource(R.color.blue);
                }else if (!StringUtil.isBlank(Types)&Types.equals("buy")){
                    a2.setBackgroundResource(R.color.blue);
                }
                if (!StringUtil.isBlank(is_sures)&is_sures.equals("0")){
                    a3.setBackgroundResource(R.color.blue);

                }else if (!StringUtil.isBlank(is_sures)&is_sures.equals("1")){
                    a4.setBackgroundResource(R.color.blue);

                }else if (!StringUtil.isBlank(is_sures)&is_sures.equals("2")){
                    a5.setBackgroundResource(R.color.blue);

                }else if (!StringUtil.isBlank(is_sures)&is_sures.equals("3")){
                    a6.setBackgroundResource(R.color.blue);
                }
                break;
            case R.id.saixuan_dialog:
                saixuan_d = true;
                saixuan_dialog.setVisibility(View.GONE);
                break;
            case R.id.next:
                break;
            case R.id.a1:
                ABColor();
                a1.setBackgroundResource(R.color.blue);
                Types1 = "sell";
                break;
            case R.id.a2:
                ABColor();
                a2.setBackgroundResource(R.color.blue);
                Types1 = "buy";
                break;
            case R.id.a3:
                ABColor1();
                a3.setBackgroundResource(R.color.blue);
                is_sures1 = "0";
                break;
            case R.id.a4:
                ABColor1();
                a4.setBackgroundResource(R.color.blue);
                is_sures1 = "1";
                break;
            case R.id.a5:
                ABColor1();
                a5.setBackgroundResource(R.color.blue);
                is_sures1 = "2";
                break;
            case R.id.a6:
                ABColor1();
                a6.setBackgroundResource(R.color.blue);
                is_sures1 = "3";
                break;
            case R.id.b1:
                ABColor();
                ABColor1();
                Types1 = "";
                is_sures1 = "";
                break;
            case R.id.b2:
                ABColor();
                ABColor1();
                Types=Types1;
                is_sures = is_sures1;
                saixuan_dialog.setVisibility(View.GONE);
                SaiXuanShow();
                break;
            default:
                break;
        }
    }

    private void ABColor() {
        a1.setBackgroundResource(R.color.transparentblack);
        a2.setBackgroundResource(R.color.transparentblack);
    }

    private void ABColor1() {
        a3.setBackgroundResource(R.color.transparentblack);
        a4.setBackgroundResource(R.color.transparentblack);
        a5.setBackgroundResource(R.color.transparentblack);
        a6.setBackgroundResource(R.color.transparentblack);
    }
}
