package com.sengmei.meililian.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sengmei.RetrofitHttps.Beans.DingDanXiangQingBean;
import com.sengmei.RetrofitHttps.Beans.ZhuCeBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.kline.R;
import com.sengmei.meililian.BaseActivity;
import com.sengmei.meililian.Utils.CustomDialog;
import com.sengmei.meililian.Utils.StringUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DingDanXiangQingActivity1 extends BaseActivity {

    private LinearLayout   llb;
    private TextView m1, m2, m3, m4, m5, all, titles, title1,panduan,danjia,nums,name;
    private TextView kaihum1,mm3,wxm5,biaoti,tv1,tv2,queren,quxiao;
    private String Ids, phoneNum;
    private RelativeLayout shou;
    private String ids;
    private String getType;
    private CustomDialog di,d2;
    private  EditText et_dialogContent;

    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.dingdanxiangqingactivity1);
        Ids = getIntent().getStringExtra("ids");
        TiBiListShow();
    }

    @Override
    public void initViews() {
        kaihum1 = (TextView) findViewById(R.id.kaihum1);
        mm3 = (TextView) findViewById(R.id.mm3);
        wxm5 = (TextView) findViewById(R.id.wxm5);
        shou = (RelativeLayout) findViewById(R.id.shou);
        all = (TextView) findViewById(R.id.all);
        danjia = (TextView) findViewById(R.id.danjia);
        nums = (TextView) findViewById(R.id.nums);
        name = (TextView) findViewById(R.id.name);

        llb = (LinearLayout) findViewById(R.id.llb);
        titles = (TextView) findViewById(R.id.titles);
        titles = (TextView) findViewById(R.id.titles);
        biaoti = (TextView) findViewById(R.id.biaoti);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        m1 = (TextView) findViewById(R.id.m1);
        m2 = (TextView) findViewById(R.id.m2);
        m3 = (TextView) findViewById(R.id.m3);
        m4 = (TextView) findViewById(R.id.m4);
        m5 = (TextView) findViewById(R.id.m5);
        panduan = (TextView) findViewById(R.id.panduan);
        quxiao= findViewById(R.id.quxiao);
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quxiaoShow();
            }
        });
        queren= findViewById(R.id.queren);
        queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (queren.equals("确认收款")){
                    d2=new CustomDialog(DingDanXiangQingActivity1.this,R.style.customDialog,R.layout.c2cfabudialog_item);
                    d2.show();
                    dia();
                }else {
                    querenShow();
                }
            }
        });
    }

    @Override
    public void ReinitViews() {

    }

    @Override
    public void initData() {
        shou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DingDanXiangQingActivity1.this, ShangPuActivity.class);
                intent.putExtra("id",ids);
                startActivity(intent);
            }
        });
    }

    private void TiBiListShow() {Log.e("AAIdsAAA", "@@TiBiListShow=" +Ids);
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(DingDanXiangQingActivity1.this).getService();
        Call<DingDanXiangQingBean> indexdata = mFromServerInterface.getlegal_deal(Ids);
        indexdata.enqueue(new Callback<DingDanXiangQingBean>() {

            @Override
            public void onResponse(Call<DingDanXiangQingBean> call, Response<DingDanXiangQingBean> response) {
                if (response.body() == null) {
                    StringUtil.ToastShow(DingDanXiangQingActivity1.this,"请先登录");
                    return;
                }
                if (response.body().getType().equals("ok")) {
                    ids=response.body().getMessage().getId();
                    danjia.setText("交易单价   ￥"+response.body().getMessage().getPrice());
                    nums.setText("交易数量 "+response.body().getMessage().getNumber()+response.body().getMessage().getCurrency_name());
                    name.setText(response.body().getMessage().getUser_cash_info().getReal_name());
                    m1.setText(response.body().getMessage().getUser_cash_info().getBank_name()+" "+response.body().getMessage().getUser_cash_info().getBank_account());
                    m2.setText(response.body().getMessage().getPrice());
                    m3.setText(response.body().getMessage().getUser_cash_info().getReal_name());
                    m4.setText(StringUtil.date(response.body().getMessage().getUser_cash_info().getCreate_time()));
                    m5.setText(response.body().getMessage().getUser_cash_info().getAlipay_account());
                    all.setText(response.body().getMessage().getDeal_money());
                    phoneNum = response.body().getMessage().getUser_cash_info().getAccount_number();
                    kaihum1.setText(response.body().getMessage().getBank_address());
                     mm3.setText(response.body().getMessage().getUser_cash_info().getAccount_number());
                     wxm5.setText(response.body().getMessage().getUser_cash_info().getWechat_account());
                     if(response.body().getMessage().getType().equals("sell")){
                         queren.setText(response.body().getMessage().getType()+"确认收款"+response.body().getMessage().getIs_sure());
                         biaoti.setText("待收款");
                         tv1.setText("买家账户");
                         tv2.setText("买家电话");
                         if (response.body().getMessage().getIs_sure().equals("0")){
                             queren.setVisibility(View.GONE);
                         }else if (response.body().getMessage().getIs_sure().equals("3")){
                             getType=response.body().getMessage().getType();
                             queren.setVisibility(View.VISIBLE);
                         }
                     }

                }
            }

            @Override
            public void onFailure(Call<DingDanXiangQingBean> call, Throwable t) {
                Log.e("AAAAA", "@@11=" + t.getMessage());
                StringUtil.ToastShow(DingDanXiangQingActivity1.this,"请先登录");
            }
        });
    }
    private void quxiaoShow() {Log.e("AAIdsAAA", "@@quxiaoShow=" +Ids);
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(DingDanXiangQingActivity1.this).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.getlegal_dealquxiao(Ids);
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                if (response.body() == null) {
                    Toast.makeText(DingDanXiangQingActivity1.this, "暂无数据", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (response.body().getType().equals("ok")) {
                    Toast.makeText(DingDanXiangQingActivity1.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                Log.e("AAAAA", "@@11=" + t.getMessage());
            }
        });
    }
    private void querenShow() {Log.e("AAIdsAAA", "@@querenShow=" +Ids);
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(DingDanXiangQingActivity1.this).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.getlegal_dealqueren(Ids);
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                if (response.body() == null) {

                    return;
                }
                Toast.makeText(DingDanXiangQingActivity1.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                Log.e("AAAAA", "@@11=" + t.getMessage());
            }
        });
    }

    public void dia(){
        LayoutInflater in = LayoutInflater.from(DingDanXiangQingActivity1.this);
        View view = in.inflate(R.layout.c2cfabudialog_item, null);
        di.setContentView(view);
        et_dialogContent=(EditText)view.findViewById(R.id.et_dialogContent);
        et_dialogContent.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        TextView querens=(TextView)view.findViewById(R.id.queren);
        querens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                QueRenShow(Ids,et_dialogContent.getText().toString().trim());
                di.dismiss();
            }
        });
    }
    public void back(View view) {
        finish();
    }

    //商家确认
    private void QueRenShow(final String ids,final String ps) {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(DingDanXiangQingActivity1.this).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.URLC2Ccancel(ids,ps);
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                if (response.body() == null) {
                    return;
                }
                Log.e("AAAAAss", "@@11=" + response.body().getType());
                Toast.makeText(DingDanXiangQingActivity1.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                finish();

            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                Log.e("AAAAAss", "@@11=" + call.toString() + "@@##" + t.getMessage().toString());
            }
        });
    }

}
