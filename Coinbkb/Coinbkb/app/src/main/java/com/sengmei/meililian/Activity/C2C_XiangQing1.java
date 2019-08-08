package com.sengmei.meililian.Activity;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sengmei.RetrofitHttps.Beans.C2C_XiangQingBean;
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

public class C2C_XiangQing1 extends BaseActivity implements View.OnClickListener {
    private TextView a1, a2, a3, a4,a44, a5, a6, a7, a8, a9, a10, a11, bt1, bt2;
    private String Ids,Typs;
    private String status;

    private CustomDialog di,d2;
    private  EditText et_dialogContent;
    private String Str;
    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.c2c_xiangqing);
        Ids = getIntent().getStringExtra("Ids");
        Typs = getIntent().getStringExtra("typs");
        XiangQingShow();
    }

    @Override
    public void initViews() {

        a1 = (TextView) findViewById(R.id.a1);
        a2 = (TextView) findViewById(R.id.a2);
        a3 = (TextView) findViewById(R.id.a3);
        a4 = (TextView) findViewById(R.id.a4);
        a44 = (TextView) findViewById(R.id.a44);
        a5 = (TextView) findViewById(R.id.a5);
        a6 = (TextView) findViewById(R.id.a6);
        a7 = (TextView) findViewById(R.id.a7);
        a8 = (TextView) findViewById(R.id.a8);
        a9 = (TextView) findViewById(R.id.a9);
        a10 = (TextView) findViewById(R.id.a10);
        a11 = (TextView) findViewById(R.id.a11);
        bt1 = (TextView) findViewById(R.id.bt1);
        bt2 = (TextView) findViewById(R.id.bt2);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
    }

    @Override
    public void ReinitViews() {
        XiangQingShow();
    }

    @Override
    public void initData() {

    }

    public void back(View v) {
        finish();
    }


    //筛选
    private void XiangQingShow() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(C2C_XiangQing1.this).getService();
        Call<C2C_XiangQingBean> indexdata = mFromServerInterface.URLC2Corder_detail(Ids);
        indexdata.enqueue(new Callback<C2C_XiangQingBean>() {

            @Override
            public void onResponse(Call<C2C_XiangQingBean> call, Response<C2C_XiangQingBean> response) {
                if (response.body() == null) {

                    return;
                }
                if (response.body().getType().equals("ok")) {
                    a1.setText(response.body().getMessage().getCurrency_name());
                    a2.setText(response.body().getMessage().getCreate_time());
                    a3.setText(response.body().getMessage().getPrice());
                    a4.setText(response.body().getMessage().getNumber());
                    a44.setText(response.body().getMessage().getTotal_price());
                    a5.setText(response.body().getMessage().getCny_uci().getReal_name());
                    a6.setText(response.body().getMessage().getOther_phone());
                    a7.setText(response.body().getMessage().getCny_uci().getAli_pay_account());
                    a8.setText(response.body().getMessage().getCny_uci().getWe_chat_account_name());
                    a9.setText(response.body().getMessage().getCny_uci().getWe_chat_account());
                    a10.setText(response.body().getMessage().getCny_uci().getBank_account_name());
                    a11.setText(response.body().getMessage().getCny_uci().getBank_account());
                    status = response.body().getMessage().getStatus();
                    if (status.equals("1")) {
                        bt1.setText("取消订单");
                        bt2.setText("确认付款");
                        bt1.setVisibility(View.GONE);
                        bt2.setVisibility(View.GONE);

                    } else if (status.equals("2")) {
                        bt1.setText("取消订单");
                        bt1.setVisibility(View.GONE);
                        if (Typs.equals("buy")) {
                            bt2.setText("已付款");
                        } else {
                            bt2.setText("确认收款");
                        }
                    } else if (status.equals("3")) {
                        bt1.setText("已完成");
                        bt2.setVisibility(View.GONE);
                    } else {
                        bt2.setVisibility(View.GONE);
                        bt1.setText("已取消");
                    }
                }
            }

            @Override
            public void onFailure(Call<C2C_XiangQingBean> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt1:
                if (status.equals("1")){
                    Str="quxiao";
                    di=new CustomDialog(C2C_XiangQing1.this,R.style.customDialog,R.layout.c2cfabudialog_item);
                    di.show();
                    dia(view);
                }
                break;
            case R.id.bt2:
                Log.e("","");
                if (status.equals("1")) {
                    C2CPayShow(Ids);
                }
                if (Typs.equals("sell")&status.equals("2")){
                    Str="shoukuan";
                    di=new CustomDialog(C2C_XiangQing1.this,R.style.customDialog,R.layout.c2cfabudialog_item);
                    di.show();
                    dia(view);
                }
                break;
            default:
                break;
        }
    }

    public void dia(View v){
        LayoutInflater in = LayoutInflater.from(C2C_XiangQing1.this);
        View view = in.inflate(R.layout.c2cfabudialog_item, null);
        di.setContentView(view);
        et_dialogContent=(EditText)view.findViewById(R.id.et_dialogContent);
        et_dialogContent.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        TextView queren=(TextView)view.findViewById(R.id.queren);
        queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtil.isBlank(et_dialogContent.getText().toString().trim())){
                    StringUtil.ToastShow1(C2C_XiangQing1.this,"密码不能为空");
                    return;
                }
                if (Str.equals("quxiao")){
                    QuXiaoShow(Ids,et_dialogContent.getText().toString().trim());
                }if (Str.equals("shoukuan")){
                    ShouKuanShow(Ids,et_dialogContent.getText().toString().trim());
                }
                di.dismiss();
            }
        });
    }
    //取消订单
    private void QuXiaoShow(final String ids,final String ps) {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(C2C_XiangQing1.this).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.URLC2Ccancel(ids,ps);
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                if (response.body() == null) {
                    return;
                }
                Log.e("AAAAAss", "@@11=" + response.body().getType());
                Toast.makeText(C2C_XiangQing1.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                finish();

            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                Log.e("AAAAAss", "@@11=" + call.toString() + "@@##" + t.getMessage().toString());
            }
        });
    }
    //确认收款
    private void ShouKuanShow(final String ids,final String ps) {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(C2C_XiangQing1.this).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.URLC2Cconfirm(ids,ps);
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                if (response.body() == null) {
                    return;
                }
                Log.e("AAAAAss", "@@11=" + response.body().getType());
                Toast.makeText(C2C_XiangQing1.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                finish();

            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                Log.e("AAAAAss", "@@11=" + call.toString() + "@@##" + t.getMessage().toString());
            }
        });
    }
    //确认付款
    private void C2CPayShow(final String ss) {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(C2C_XiangQing1.this).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.URLC2Cpay(ss);
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                if (response.body() == null) {
                    return;
                }
                Log.e("AAAAAss", "@@11=" + response.body().getType());
                Toast.makeText(C2C_XiangQing1.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                finish();

            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                Log.e("AAAAAss", "@@11=" + call.toString() + "@@##" + t.getMessage().toString());
            }
        });
    }
}
