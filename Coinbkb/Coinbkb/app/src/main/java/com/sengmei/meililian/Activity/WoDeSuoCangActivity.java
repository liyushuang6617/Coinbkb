package com.sengmei.meililian.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sengmei.RetrofitHttps.Beans.SuoCBean;
import com.sengmei.RetrofitHttps.Beans.WoDeTangGuoBean;
import com.sengmei.RetrofitHttps.Beans.ZhuCeBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.kline.R;
import com.sengmei.meililian.BaseActivity;
import com.sengmei.meililian.Utils.StringUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WoDeSuoCangActivity extends BaseActivity implements View.OnClickListener{
    private TextView a1,b1,b2,b3;
    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.wodesuocangactivity);
        infoShowtop();infoShowb();
    }

    @Override
    public void initViews() {
        b1=(TextView)findViewById(R.id.b1);
        b2=(TextView)findViewById(R.id.b2);
        b3=(TextView)findViewById(R.id.b3);
        a1=(TextView)findViewById(R.id.a1);
        findViewById(R.id.suochang).setOnClickListener(this);
    }

    @Override
    public void ReinitViews() {

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
            case R.id.suochang:
                infoShow();
                break;
                default:
                    break;
        }
    }

    private void infoShowtop() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(WoDeSuoCangActivity.this).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.getURLbalance();
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                if (response.body() == null) {

                    return;
                }
                if (response.body().getType().equals("ok")){
                    a1.setText("可用余额："+response.body().getMessage()+" BQB");
                }
            }


            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {

            }
        });
    }
    private void infoShowb() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(WoDeSuoCangActivity.this).getService();
        Call<SuoCBean> indexdata = mFromServerInterface.getURLmy_lock();
        indexdata.enqueue(new Callback<SuoCBean>() {

            @Override
            public void onResponse(Call<SuoCBean> call, Response<SuoCBean> response) {
                if (response.body() == null) {

                    return;
                }
                if (response.body().getType().equals("ok")){
                   b1.setText( response.body().getMessage().getTotal());
                    b2.setText( response.body().getMessage().getNumber());
                    b3.setText( response.body().getMessage().getTime());
                }
            }


            @Override
            public void onFailure(Call<SuoCBean> call, Throwable t) {

            }
        });
    }
    private void infoShow() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(WoDeSuoCangActivity.this).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.getURLlock("20000");
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                if (response.body() == null) {

                    return;
                }
                StringUtil.ToastShow(WoDeSuoCangActivity.this,response.body().getMessage());
            }


            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {

            }
        });
    }
}
