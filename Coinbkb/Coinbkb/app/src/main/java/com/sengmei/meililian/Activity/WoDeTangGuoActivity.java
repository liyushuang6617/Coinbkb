package com.sengmei.meililian.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.sengmei.RetrofitHttps.Beans.WoDeTangGuoBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.kline.R;
import com.sengmei.meililian.BaseActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WoDeTangGuoActivity extends BaseActivity implements View.OnClickListener{
    private TextView b1,b2;
    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.wodetangguoactivity);
        infoShow();
    }

    @Override
    public void initViews() {
        b1=(TextView)findViewById(R.id.b1);
        b2=(TextView)findViewById(R.id.b2);
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
            case R.id.yuyan:
             //   startActivity(new Intent(this,LanguageActivity.class));
                break;
                default:
                    break;
        }
    }

    private void infoShow() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(WoDeTangGuoActivity.this).getService();
        Call<JSONObject> indexdata = mFromServerInterface.getURLdetail();
        indexdata.enqueue(new Callback<JSONObject>() {

            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if (response.body() == null) {

                    return;
                }
            /*    if (response.body().getType().equals("ok")){

                    b1.setText(response.body().getMessage().getNumber()+"糖果");
                    b2.setText(response.body().getMessage().getInvite()+"糖果");
                }*/

            }


            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {

            }
        });
    }
}
