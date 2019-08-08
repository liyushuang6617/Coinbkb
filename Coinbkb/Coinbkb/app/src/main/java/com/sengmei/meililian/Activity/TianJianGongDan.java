package com.sengmei.meililian.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.util.LogUtils;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.RetrofitHttps.IndexData;
import com.sengmei.kline.R;
import com.sengmei.meililian.BaseActivity;
import com.sengmei.meililian.UserBean;
import com.sengmei.meililian.Utils.HttpUtils;
import com.sengmei.meililian.Utils.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TianJianGongDan extends BaseActivity {
    private EditText cont;
    private TextView next;
    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.tianjiagongdan);
    }

    @Override
    public void initViews() {
        cont=(EditText)findViewById(R.id.cont);
          findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 addGongDanShow();
             }
         });

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

    private void addGongDanShow(){
        GetDataFromServerInterface mFromServerInterface= GetDataFromServer.getInstance(this).getService();
        Call<IndexData> indexdata=mFromServerInterface.getaddGongDan(cont.getText().toString().trim());
        Log.e("AAAAA","@@2="+indexdata);
        Log.e("AAAAA","@@22="+indexdata.clone());
        indexdata.enqueue(new Callback<IndexData>() {

            @Override
            public void onResponse(Call<IndexData> call, Response<IndexData> response) {
                if (response.body() == null) {

                    return;
                }
                Log.e("AAAAA","@@="+response.body().getMessage() );
                if (response.body().getType().equals("ok")){
                    StringUtil.ToastShow1(TianJianGongDan.this, response.body().getMessage());
                    finish();
                } else{
                    StringUtil.ToastShow1(TianJianGongDan.this, response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<IndexData> call, Throwable t) {
                Log.e("AAAAA","@@11="+t.getMessage() );
            }
        });
    }
}
