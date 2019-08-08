package com.sengmei.meililian.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.util.LogUtils;
import com.sengmei.RetrofitHttps.Beans.DanShowBean;
import com.sengmei.RetrofitHttps.Beans.JYLBBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.kline.R;
import com.sengmei.meililian.Adapter.GongDanAdapter;
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

public class JiaoYiZhangHu extends BaseActivity implements View.OnClickListener {
    private String Ids,names,currencys,pick_ups,recharges;
    private ListView listview;
    private JiaoYiListAdapter adapter;
    private List<JiaoYiListBean> list=new ArrayList<>();
    private LinearLayout chongbi;
    private TextView wu;

                    /*    .putExtra("pick_up",Alllist.get(i).getIs_pick_up())
            .putExtra("recharge",Alllist.get(i).getIs_recharge()));*/
    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.jiaoyizhanghu);
        Ids = getIntent().getStringExtra("ids");
        names = getIntent().getStringExtra("names");
        currencys = getIntent().getStringExtra("currencys");
        pick_ups = getIntent().getStringExtra("pick_up");
        recharges = getIntent().getStringExtra("recharge");
        JiaoYiShow();
    }

    @Override
    public void initViews() {
        wu=findViewById(R.id.wu);
        findViewById(R.id.tibi).setOnClickListener(this);
        findViewById(R.id.fabijiaoyi).setOnClickListener(this);
        findViewById(R.id.chongbi).setOnClickListener(this);
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
            case R.id.tibi://提币
                if (pick_ups.equals("0")){
                    StringUtil.ToastShow(JiaoYiZhangHu.this,"此币种暂未开通,暂不支持提币功能");
                    return;
                }
                LogUtils.e("获取提币信息=112"+currencys);
                startActivity(new Intent(JiaoYiZhangHu.this, DianKaTiBiActivity.class)
                        .putExtra("currencys", currencys));
                break;
            case R.id.fabijiaoyi://法币交易

                LogUtils.e("@@@@@=" + currencys);
                startActivity(new Intent(JiaoYiZhangHu.this, JiaoYiFaBi.class)
                        .putExtra("currencys", currencys));
                break;
             case R.id.chongbi:
                 if (recharges.equals("0")){
                     StringUtil.ToastShow(JiaoYiZhangHu.this,"此币种暂未开通,暂不支持充币功能");
                     return;
                 }
                LogUtils.e("namesnames"+names);
                startActivity(new Intent(JiaoYiZhangHu.this, ChongZhiGuanLi.class)
                        .putExtra("currencys", currencys).putExtra("names",names));
                break;

            default:
                break;
        }
    }

    private void JiaoYiShow(){
        LogUtils.e("currencys"+currencys);
        GetDataFromServerInterface mFromServerInterface= GetDataFromServer.getInstance(JiaoYiZhangHu.this).getService();
        Call<JYLBBean> indexdata=mFromServerInterface.getlegal_log(currencys,"change");
        indexdata.enqueue(new Callback<JYLBBean>() {

            @Override
            public void onResponse(Call<JYLBBean> call, Response<JYLBBean> response) {
                if (response.body() == null) {
                    StringUtil.ToastShow(JiaoYiZhangHu.this,"请先登录");
                    return;
                }
                if (response.body().getType().equals("ok")) {
                    wu.setVisibility(View.GONE);
                    if (response.body().getMessage().getList().size()==0){
                        wu.setVisibility(View.VISIBLE);
                    }else {
                        adapter=new JiaoYiListAdapter(JiaoYiZhangHu.this,response.body().getMessage().getList());
                        listview.setAdapter(adapter);
                    }

                }
            }

            @Override
            public void onFailure(Call<JYLBBean> call, Throwable t) {
                Log.e("AAAAA","@@11="+t.getMessage() );
                StringUtil.ToastShow(JiaoYiZhangHu.this,"请先登录");
            }
        });
    }
}
