package com.sengmei.meililian.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class DingDanXiangQingActivity22 extends BaseActivity {

    private LinearLayout   llb;
    private TextView m1, m2, m3, m4, m5, all, titles, title1,panduan,danjia,nums,name;
    private TextView kaihum1,mm3,wxm5;
    private String Ids, phoneNum;
    private RelativeLayout shou;
    private String ids;
    private CustomDialog di;
    private EditText et_dialogContent;

    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.dingdanxiangqingactivity22);
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
        title1 = (TextView) findViewById(R.id.title1);
        m1 = (TextView) findViewById(R.id.m1);
        m2 = (TextView) findViewById(R.id.m2);
        m3 = (TextView) findViewById(R.id.m3);
        m4 = (TextView) findViewById(R.id.m4);
        m5 = (TextView) findViewById(R.id.m5);
        panduan = (TextView) findViewById(R.id.panduan);
        findViewById(R.id.quxiao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quxiaoShow();
            }
        });
        findViewById(R.id.queren).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                querenShow();
               /* di=new CustomDialog(DingDanXiangQingActivity22.this,R.style.customDialog,R.layout.c2cfabudialog_item);
                di.show();
                dia();*/
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
                Intent intent = new Intent(DingDanXiangQingActivity22.this, ShangPuActivity.class);
                intent.putExtra("id",ids);
                startActivity(intent);
            }
        });
    }

    private void TiBiListShow() {Log.e("AAIdsAAA", "@@TiBiListShow=" +Ids);
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(DingDanXiangQingActivity22.this).getService();
        Call<DingDanXiangQingBean> indexdata = mFromServerInterface.getlegal_deal(Ids);
        indexdata.enqueue(new Callback<DingDanXiangQingBean>() {

            @Override
            public void onResponse(Call<DingDanXiangQingBean> call, Response<DingDanXiangQingBean> response) {
                if (response.body() == null) {
                    StringUtil.ToastShow(DingDanXiangQingActivity22.this,"请先登录");
                    return;
                }
                if (response.body().getType().equals("ok")) {
                    ids=response.body().getMessage().getId();
                    danjia.setText("￥"+response.body().getMessage().getPrice());
                    nums.setText(""+response.body().getMessage().getNumber()+response.body().getMessage().getCurrency_name());
                    name.setText(response.body().getMessage().getUser_cash_info().getReal_name());
                    m1.setText(response.body().getMessage().getUser_cash_info().getBank_name()+" "+response.body().getMessage().getUser_cash_info().getBank_account());
                    m2.setText(response.body().getMessage().getPrice());
                    m3.setText(response.body().getMessage().getUser_cash_info().getReal_name());
                    m4.setText(StringUtil.date(response.body().getMessage().getUser_cash_info().getCreate_time()));
                    m5.setText(response.body().getMessage().getAli_account());
                    all.setText(response.body().getMessage().getDeal_money());
                    phoneNum = response.body().getMessage().getUser_cash_info().getAccount_number();
                    kaihum1.setText("￥"+response.body().getMessage().getDeal_money());
                    mm3.setText(response.body().getMessage().getUser_cash_info().getAccount_number());
                    wxm5.setText(response.body().getMessage().getUser_cash_info().getWechat_account());
                }
            }

            @Override
            public void onFailure(Call<DingDanXiangQingBean> call, Throwable t) {
                Log.e("AAAAA", "@@11=" + t.getMessage());
                StringUtil.ToastShow(DingDanXiangQingActivity22.this,"请先登录");
            }
        });
    }
    private void quxiaoShow() {Log.e("AAIdsAAA", "@@quxiaoShow=" +Ids);
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(DingDanXiangQingActivity22.this).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.getlegal_dealquxiao(Ids);
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                if (response.body() == null) {
                    Toast.makeText(DingDanXiangQingActivity22.this, "暂无数据", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (response.body().getType().equals("ok")) {
                    Toast.makeText(DingDanXiangQingActivity22.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                Log.e("AAAAA", "@@11=" + t.getMessage());
            }
        });
    }

    public void dia(){
        LayoutInflater in = LayoutInflater.from(DingDanXiangQingActivity22.this);
        View view = in.inflate(R.layout.c2cfabudialog_item, null);
        di.setContentView(view);
        et_dialogContent=(EditText)view.findViewById(R.id.et_dialogContent);
        et_dialogContent.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        TextView querens=(TextView)view.findViewById(R.id.queren);
        querens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                querenShow();

                di.dismiss();
            }
        });
    }
    private void querenShow() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(DingDanXiangQingActivity22.this).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.getlegal_dealqueren(Ids);
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                if (response.body() == null) {

                    return;
                }
                Log.e("AAIdsAAA", "@@querenShow=" +Ids);
                Log.e("AAIdsAAA", response.body().getType()+"=@@querenShow=" +response.body().getMessage());
                Toast.makeText(DingDanXiangQingActivity22.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                Log.e("AAAAA", "@@11=" + t.getMessage());
            }
        });
    }

    public void back(View view) {
        finish();
    }
}
