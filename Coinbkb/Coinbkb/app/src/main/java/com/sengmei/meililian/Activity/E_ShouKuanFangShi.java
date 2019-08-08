package com.sengmei.meililian.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sengmei.RetrofitHttps.Beans.HuoQuShouKuanBean;
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

public class E_ShouKuanFangShi extends BaseActivity {
    private EditText name,kaihuname,yinhangname,zhifuname,weixinname,weixinname1;
    private String Name,Kaihuname,Yinhangname,Zhifuname,Weixinname,Weixinname1;
    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.e_shoukuanfangshi);
        HuoQuShow();
    }

    @Override
    public void initViews() {
        name=(EditText)findViewById(R.id.name);
        kaihuname=(EditText)findViewById(R.id.kaihuname);
        yinhangname=(EditText)findViewById(R.id.yinhangname);
        zhifuname=(EditText)findViewById(R.id.zhifuname);
        weixinname=(EditText)findViewById(R.id.weixinname);
        weixinname1=(EditText)findViewById(R.id.weixinname1);
        TextView next=(TextView)findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Name=name.getText().toString().trim();
                Kaihuname=kaihuname.getText().toString().trim();
                Yinhangname=yinhangname.getText().toString().trim();
                Zhifuname=zhifuname.getText().toString().trim();
                Weixinname=weixinname.getText().toString().trim();
                Weixinname1=weixinname1.getText().toString().trim();
                ShouKuanShow();
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

    private void HuoQuShow(){
        GetDataFromServerInterface mFromServerInterface= GetDataFromServer.getInstance(this).getService();
        Call<HuoQuShouKuanBean> indexdata=mFromServerInterface.getTBSKFSinfo();
        indexdata.enqueue(new Callback<HuoQuShouKuanBean>() {

            @Override
            public void onResponse(Call<HuoQuShouKuanBean> call, Response<HuoQuShouKuanBean> response) {
                if (response.body() == null) {
                    StringUtil.ToastShow(E_ShouKuanFangShi.this,"请先登录");

                    return;
                }

                Log.e("AAAAA",response+""+response.body() );
                if (response.body().getType().equals("ok")) {
                     name.setText(response.body().getMessage().getReal_name());
                    kaihuname.setText(response.body().getMessage().getBank_name());
                    yinhangname.setText(response.body().getMessage().getBank_account());
                    zhifuname.setText(response.body().getMessage().getAlipay_account());
                    weixinname.setText(response.body().getMessage().getWechat_nickname());
                   weixinname1.setText(response.body().getMessage().getWechat_account());
                }

            }

            @Override
            public void onFailure(Call<HuoQuShouKuanBean> call, Throwable t) {
                StringUtil.ToastShow(E_ShouKuanFangShi.this,"请先登录");
            }
        });
    }
    private void ShouKuanShow(){
        Log.e("AAAAAas",Name+Kaihuname+Yinhangname+Zhifuname+Weixinname+Weixinname1);
        GetDataFromServerInterface mFromServerInterface= GetDataFromServer.getInstance(this).getService();
        Call<IndexData> indexdata=mFromServerInterface.getTBSKFS(Name,Kaihuname,Yinhangname,Zhifuname,Weixinname,Weixinname1);
        indexdata.enqueue(new Callback<IndexData>() {

            @Override
            public void onResponse(Call<IndexData> call, Response<IndexData> response) {
                if (response.body() == null) {

                    return;
                }

                Log.e("AAAAA",response+""+response.body() );
                if (response.body().getType().equals("ok")) {
                    StringUtil.ToastShow1(E_ShouKuanFangShi.this, response.body().getMessage());
                    HuoQuShow();
                } else {
                    StringUtil.ToastShow1(E_ShouKuanFangShi.this,  response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<IndexData> call, Throwable t) {
                Log.e("AAAAA","@@11="+t.getMessage() );
            }
        });
    }
}
