package com.sengmei.meililian.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sengmei.RetrofitHttps.Adapter.ShangPuListAdapter;
import com.sengmei.RetrofitHttps.Beans.ShangPuBean;
import com.sengmei.RetrofitHttps.Beans.WoDeShangPuListBean;
import com.sengmei.RetrofitHttps.Beans.ZhuCeBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.RetrofitHttps.Interfaces.FabuBack;
import com.sengmei.kline.R;
import com.sengmei.meililian.BaseActivity;
import com.sengmei.meililian.InterFaces.ShangPuYiChangBack;
import com.sengmei.meililian.Utils.AddPopWindow;
import com.sengmei.meililian.Utils.MyListView;
import com.sengmei.meililian.Utils.StringUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class E_ShangPuListActivity extends BaseActivity implements View.OnClickListener,FabuBack,ShangPuYiChangBack {
    private TextView chushou,qiugou,xuanzhe,names,next;
    private EditText danjia,nums,mins;
    private View va,va1;

    private String Ids,Was="false",types="sell",titles;
    private TextView name,name1,title,times,dan1,dan2,dan3,dan4,wodechushou,wodeqiugou;
    private TextView weiwancheng,yiwancheng,wu;
    private View v1,v2;
    private ImageView iv1, iv2, iv3, iv4;
    private MyListView listview;
    private SwipeRefreshLayout refreshLayout;
    private List<WoDeShangPuListBean.DateBean> list=new ArrayList<>();
    private String Ways,Types="sell",currency_id;
    private AddPopWindow addPopWindow;
    private Dialog bottomDialog;
    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.e_shangpulistactivity);
        Ids=getIntent().getStringExtra("Ids");
        refreshLayout=(SwipeRefreshLayout)findViewById(R.id.refresh);
        wu=(TextView)findViewById(R.id.wu);
        name=(TextView)findViewById(R.id.name);
        name1=(TextView)findViewById(R.id.name1);
        title=(TextView)findViewById(R.id.title);
        times=(TextView)findViewById(R.id.times);
        dan1=(TextView)findViewById(R.id.dan1);
        dan2=(TextView)findViewById(R.id.dan2);
        dan3=(TextView)findViewById(R.id.dan3);
        dan4=(TextView)findViewById(R.id.dan4);
        iv1 = (ImageView) findViewById(R.id.iv1);
        iv2 = (ImageView) findViewById(R.id.iv2);
        iv3 = (ImageView) findViewById(R.id.iv3);
        iv4 = (ImageView) findViewById(R.id.iv4);
        addPopWindow = new AddPopWindow(E_ShangPuListActivity.this);
        ShangPuTopShow();
        ShangPuListShow();
        addPopWindow.setFabuBack(this);
    }

    @Override
    public void initViews() {
        listview=(MyListView)findViewById(R.id.listview);
        v1=(View)findViewById(R.id.v1);
        v2=(View)findViewById(R.id.v2);
        findViewById(R.id.fabu).setOnClickListener(this);
        weiwancheng=(TextView)findViewById(R.id.weiwancheng);
        weiwancheng.setOnClickListener(this);
        yiwancheng=(TextView)findViewById(R.id.yiwancheng);
        yiwancheng.setOnClickListener(this);
        wodechushou=(TextView)findViewById(R.id.wodechushou);
        wodechushou.setOnClickListener(this);
        wodeqiugou=(TextView)findViewById(R.id.wodeqiugou);
        wodeqiugou.setOnClickListener(this);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final Random random = new Random();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        ShangPuTopShow();
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
        ShangPuTopShow();
        ShangPuListShow();
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fabu:
                showDialog1();
                break;
            case R.id.wodechushou:
                wodechushou.setBackgroundResource(R.color.blue);
                wodeqiugou.setBackgroundResource(R.color.text_blue);
                types="sell";
                ShangPuListShow();
                break;
            case R.id.wodeqiugou:
                wodeqiugou.setBackgroundResource(R.color.blue);
                wodechushou.setBackgroundResource(R.color.text_blue);
                types="buy";
                ShangPuListShow();
                break;
            case R.id.weiwancheng:
                yiwancheng.setTextColor(getResources().getColor(R.color.black));
                weiwancheng.setTextColor(getResources().getColor(R.color.blue));
                v1.setBackgroundResource(R.color.blue);
                v2.setBackgroundResource(R.color.transparent);
                Was="false";
                ShangPuListShow();
                break;
            case R.id.yiwancheng:
                yiwancheng.setTextColor(getResources().getColor(R.color.blue));
                weiwancheng.setTextColor(getResources().getColor(R.color.black));
                v2.setBackgroundResource(R.color.blue);
                v1.setBackgroundResource(R.color.transparent);
                Was="true";
                ShangPuListShow();
                break;
                default:
                    break;
        }

    }
    public void back(View v) {
        finish();
    }

    //填充list数据
    private void setList(final List<WoDeShangPuListBean.DateBean> news) {
        if (list != null) {
            list.clear();
        }Log.e("AAAAA", "@@1111=" + list.size());
        if (news.size()==0){
            wu.setVisibility(View.VISIBLE);
        }else {
            wu.setVisibility(View.GONE);
        }
        list.addAll(news);
        ShangPuListAdapter shangPuListAdapter=new ShangPuListAdapter(E_ShangPuListActivity.this,list);
        listview.setAdapter(shangPuListAdapter);
        shangPuListAdapter.setYiChangBack(this);
        if (Was.equals("true")){
            shangPuListAdapter.setZhangTai(false);
        }else {
            shangPuListAdapter.setZhangTai(true);
        }
        shangPuListAdapter.notifyDataSetChanged();
        shangPuListAdapter.notifyDataSetChanged();
    }
    //商铺信息
    private void ShangPuTopShow() {
        refreshLayout.setRefreshing(true);
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(E_ShangPuListActivity.this).getService();
        Call<ShangPuBean> indexdata = mFromServerInterface.getseller1(Ids,Was);
        indexdata.enqueue(new Callback<ShangPuBean>() {

            @Override
            public void onResponse(Call<ShangPuBean> call, Response<ShangPuBean> response) {
                refreshLayout.setRefreshing(false);
                if (response.body() == null) {
                    StringUtil.ToastShow(E_ShangPuListActivity.this,"请先登录");
                    return;
                }Log.e("AAAAA", "@@11=" + response.body().getType());
                if (response.body().getType().equals("ok")) {
                    Log.e("AAAAA", "@@11=" + response.body().getType());
                    name.setText(response.body().getMessage().getName());
                    String str=response.body().getMessage().getName().substring(0,1);
                    name1.setText(str);
                    title.setText(response.body().getMessage().getName());
                    times.setText(response.body().getMessage().getCreate_time());
                    dan1.setText(response.body().getMessage().getTotal());
                    dan2.setText(response.body().getMessage().getThirtyDays());
                    dan3.setText(response.body().getMessage().getDone());
                    titles=response.body().getMessage().getCurrency_name();
                    currency_id=response.body().getMessage().getCurrency_id();

                    double a =0;
                    if (StringUtil.isBlank(response.body().getMessage().getDone())){
                        a = 0;
                    }else {
                        a =  Double.parseDouble(response.body().getMessage().getDone());
                    }
                    double aa =0;
                    if (StringUtil.isBlank(response.body().getMessage().getTotal())){
                        aa = 0;
                    }else {
                        aa =  Double.parseDouble(response.body().getMessage().getTotal());
                    }
                    double cc=(a/aa)*100;
                    Log.e("AAAAA", a+aa+"@@12=" + cc);
                    String ss=String.format("%.2f", cc);
                    Log.e("AAAAA", "@@222=" + ss);
                    if (ss.equals("NAN")){
                        dan4.setText("0%");
                    }else {
                        dan4.setText(ss+"%");
                    }

                    if (response.body().getMessage().getProve_email().equals("0")) {
                        iv1.setImageResource(R.mipmap.weiren);
                    } else {
                        iv1.setImageResource(R.mipmap.ren);
                    }
                    if (response.body().getMessage().getProve_level().equals("0")) {
                        iv4.setImageResource(R.mipmap.weiren);
                    } else {
                        iv4.setImageResource(R.mipmap.ren);
                    }
                    if (response.body().getMessage().getProve_mobile().equals("0")) {
                        iv2.setImageResource(R.mipmap.weiren);
                    } else {
                        iv2.setImageResource(R.mipmap.ren);
                    }
                    if (!StringUtil.isBlank(response.body().getMessage().getProve_real())) {
                        if (response.body().getMessage().getProve_real().equals("0")) {
                            iv3.setImageResource(R.mipmap.weiren);
                        } else {
                            iv3.setImageResource(R.mipmap.ren);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ShangPuBean> call, Throwable t) {
                refreshLayout.setRefreshing(false);
                Log.e("AAAAA" +
                        "", "@@11=" + t.getMessage());
                StringUtil.ToastShow(E_ShangPuListActivity.this,"请先登录");
            }
        });
    }
    //商铺信息列表
    private void ShangPuListShow() {
        refreshLayout.setRefreshing(true);
        wu.setVisibility(View.GONE);
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(E_ShangPuListActivity.this).getService();
        Call<WoDeShangPuListBean> indexdata = mFromServerInterface.gettrade(Ids,types,Was);
        indexdata.enqueue(new Callback<WoDeShangPuListBean>() {

            @Override
            public void onResponse(Call<WoDeShangPuListBean> call, Response<WoDeShangPuListBean> response) {
                refreshLayout.setRefreshing(false);
                if (response.body() == null) {
                    Toast.makeText(E_ShangPuListActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
                    return;
                }Log.e("AAAAA", "@@11=" + response.body().getType());
                if (response.body().getType().equals("ok")) {
                    Log.e("AAAAA", "@@11=" + response.body().getMessage().getData().size());

                    setList(response.body().getMessage().getData());

                }else {
                    wu.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<WoDeShangPuListBean> call, Throwable t) {
                refreshLayout.setRefreshing(false);
                Log.e("AAAAA", "@@11=" + t.getMessage());
                Toast.makeText(E_ShangPuListActivity.this, "请求网络失败", Toast.LENGTH_SHORT).show();
                wu.setVisibility(View.VISIBLE);
            }
        });
    }


    private void showDialog1() {
        bottomDialog = new Dialog(E_ShangPuListActivity.this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(E_ShangPuListActivity.this).inflate(R.layout.fabu_button, null);
        chushou = (TextView) contentView.findViewById(R.id.chushou);
        qiugou = (TextView) contentView.findViewById(R.id.qiugou);
        chushou.setOnClickListener(onClickListener);
        qiugou.setOnClickListener(onClickListener);
        xuanzhe = (TextView) contentView.findViewById(R.id.xuanzhe);
        xuanzhe.setOnClickListener(onClickListener);
        names = (TextView) contentView.findViewById(R.id.names);
        names.setText(titles);
        va = (View) contentView.findViewById(R.id.va);
        va1 = (View) contentView.findViewById(R.id.va1);
        danjia = (EditText) contentView.findViewById(R.id.danjia);
        nums = (EditText) contentView.findViewById(R.id.nums);
        mins = (EditText) contentView.findViewById(R.id.mins);
        next=(TextView) contentView.findViewById(R.id.next);
        next.setOnClickListener(onClickListener);
        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = E_ShangPuListActivity.this.getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();

    }
    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
      switch (v.getId()){
          case R.id.next:
              if (StringUtil.isBlank(danjia.getText().toString().trim())){
                  StringUtil.ToastShow(E_ShangPuListActivity.this,"请填写单价");
              }else if (StringUtil.isBlank(nums.getText().toString().trim())){
                  StringUtil.ToastShow(E_ShangPuListActivity.this,"请输入数量");
              }else if (StringUtil.isBlank(mins.getText().toString().trim())){
                  StringUtil.ToastShow(E_ShangPuListActivity.this,"请填写最小交易量");
              }else {
                  ShangPuFaBuShow();
                  bottomDialog.dismiss();
              }
              break;
          case R.id.xuanzhe:
              addPopWindow.showPopupWindow(xuanzhe);
              break;
          case R.id.chushou:
              chushou.setTextColor(getResources().getColor(R.color.blue));
              qiugou.setTextColor(getResources().getColor(R.color.black));
              va.setBackgroundResource(R.color.blue);
              va1.setBackgroundResource(R.color.transparent);
              Types="sell";
              break;
          case R.id.qiugou:
              chushou.setTextColor(getResources().getColor(R.color.black));
              qiugou.setTextColor(getResources().getColor(R.color.blue));
              va.setBackgroundResource(R.color.transparent);
              va1.setBackgroundResource(R.color.blue);
              Types="buy";

              break;
              default:
                  break;
      }
        }
    };

    @Override
    public void Ways(String ways) {
      /*   Ways=ways;
       if (ways.equals("ali_pay")){
            xuanzhe.setText("支付宝");
        }else if (ways.equals("we_chat")){
            xuanzhe.setText("微信");
        }else if (ways.equals("bank")){
            xuanzhe.setText("银行卡");
        }*/

        Log.e("AAAAA", "@@ways=" +ways);
    }

    //商铺信息列表 发布
    private void ShangPuFaBuShow() {
        refreshLayout.setRefreshing(true);
        wu.setVisibility(View.GONE);
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(E_ShangPuListActivity.this).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.getlegal_send(Types,"-1",danjia.getText().toString().trim(),
                nums.getText().toString().trim(),mins.getText().toString().trim(),currency_id);
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                refreshLayout.setRefreshing(false);
                if (response.body() == null) {
                    Toast.makeText(E_ShangPuListActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.e("AAAAA", "@@11=" + response.body().getType());
                if (response.body().getType().equals("ok")) {
                    Toast.makeText(E_ShangPuListActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    ShangPuListShow();
                }else {
                    Toast.makeText(E_ShangPuListActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                refreshLayout.setRefreshing(false);
                Log.e("AAAAA", "@@11=" + t.getMessage());
            }
        });
    }

    @Override
    public void YiChangBack(String s) {
        ShangPuListShow();
        Log.e("YiChangBack", "@@YiChangBack=" +s);
    }
}
