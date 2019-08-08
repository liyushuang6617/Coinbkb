package com.sengmei.meililian.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.sengmei.RetrofitHttps.Beans.ErWeiMaBean;
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

public class TangGuoDuiHuanActivity extends BaseActivity implements View.OnClickListener{
    private TextView numss;
    private double nums;
    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.tangguoduihuanactivity);
        infoShow1();
    }

    @Override
    public void initViews() {
        numss=(TextView)findViewById(R.id.numss) ;
   findViewById(R.id.next).setOnClickListener(this);
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
            case R.id.next:
             //   startActivity(new Intent(this,LanguageActivity.class));
                infoShow();
                break;
                default:
                    break;
        }
    }

    private void infoShow() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(TangGuoDuiHuanActivity.this).getService();
        Call<JSONObject> indexdata = mFromServerInterface.getURLexchange();
        indexdata.enqueue(new Callback<JSONObject>() {

            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if (response.body() == null) {

                    return;
                }
                StringUtil.ToastShow(TangGuoDuiHuanActivity.this,response.body().getString("message"));

            }


            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {

            }
        });
    }

    private void infoShow1() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(TangGuoDuiHuanActivity.this).getService();
        Call<JSONObject> indexdata = mFromServerInterface.getURLdetail();
        indexdata.enqueue(new Callback<JSONObject>() {

            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                Log.e("糖果糖果","response="+response.body());
                if (response.body() == null) {

                    return;
                }
             /*   if (response.body().getType().equals("ok")){
                    nums=StringUtil.strToDouble(response.body().getMessage().getNumber())+StringUtil.strToDouble(response.body().getMessage().getInvite());
                    numss.setText(nums+" 糖果");
                }*/

            }


            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {

            }
        });
    }
}
