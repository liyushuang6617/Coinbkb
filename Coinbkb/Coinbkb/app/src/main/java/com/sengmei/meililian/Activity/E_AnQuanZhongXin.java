package com.sengmei.meililian.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.sengmei.RetrofitHttps.Beans.AnQuanBean;
import com.sengmei.RetrofitHttps.Beans.WoDeShangPuBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.kline.R;
import com.sengmei.meililian.BaseActivity;
import com.sengmei.meililian.Bean.DataBean;
import com.sengmei.meililian.UserBean;
import com.sengmei.meililian.Utils.HttpUtils;
import com.sengmei.meililian.Utils.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class E_AnQuanZhongXin extends BaseActivity implements View.OnClickListener{
    private LinearLayout shouji,youxiang,fabimima,shoushi;
    private TextView tv_shouji,tv_youxiang,verName;
    public static String shoujis,youxiangs,fabimimas,shoushis;
    private String mobile;
    private String email;
    private String titles;
    private String pass;
    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.e_anquanzhongxin);
        ZhongXinShow();
    }

    @Override
    public void initViews() {
        shouji=(LinearLayout)findViewById(R.id.shouji);
        youxiang=(LinearLayout)findViewById(R.id.youxiang);
        fabimima=(LinearLayout)findViewById(R.id.fabimima);
        fabimima.setOnClickListener(this);
        shoushi=(LinearLayout)findViewById(R.id.shoushi);
        shouji.setOnClickListener(this);
        youxiang.setOnClickListener(this);
        tv_shouji=(TextView)findViewById(R.id.tv_shouji);
        tv_youxiang=(TextView)findViewById(R.id.tv_youxiang);
        verName=(TextView)findViewById(R.id.verName);
    }

    @Override
    public void ReinitViews() {
        ZhongXinShow();
    }

    @Override
    public void initData() {

    }
    public void back(View v) {
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.shouji:
                if (!StringUtil.isMobile(mobile)){

                    startActivity(new Intent(E_AnQuanZhongXin.this,ShouJiYanZhengActivity.class));
                }else {
                    startActivity(new Intent(E_AnQuanZhongXin.this,E_ShouJiYanZheng.class));
                }
                break;
            case R.id.youxiang:
                if (StringUtil.isBlank(email)||!StringUtil.isEmail(email)){
                    startActivity(new Intent(E_AnQuanZhongXin.this,YouXiangYanZhengActivity.class));
                }else {
                    startActivity(new Intent(E_AnQuanZhongXin.this,E_YouXiangZheng.class));
                }
                break;
            case R.id.fabimima:
                if (StringUtil.isBlank(pass)||pass.equals("0")){
                   startActivity(new Intent(E_AnQuanZhongXin.this, FaBiMiMaActivity.class).putExtra("titles", "0"));
                }else {
                    startActivity(new Intent(E_AnQuanZhongXin.this,FaBiMiMaActivity.class).putExtra("titles","1"));
                }
                break;
                default:
                    break;
        }

    }

    //应用中心
    private void ZhongXinShow() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(E_AnQuanZhongXin.this).getService();
        Call<AnQuanBean> indexdata = mFromServerInterface.getcenter();
        indexdata.enqueue(new Callback<AnQuanBean>() {

            @Override
            public void onResponse(Call<AnQuanBean> call, Response<AnQuanBean> response) {

                if (response.body() == null) {
                    StringUtil.ToastShow(E_AnQuanZhongXin.this,"请先登录");
                    return;
                }
                if (response.body().getType().equals("ok")) {
                    mobile =response.body().getMessage().getPhone();
                    email = response.body().getMessage().getEmail();
                    pass = response.body().getMessage().getPaypassword();
                    String gesture_password = response.body().getMessage().getGesture_password();
                    if (!StringUtil.isMobile(mobile)){
                        tv_shouji.setText("未绑定");
                        shoujis="未绑定";
                    }else {
                        shoujis=mobile;
                        tv_shouji.setText(mobile);
                    }

                    LogUtils.e("@@@@@@email=" + email);
                    if (StringUtil.isBlank(email)){
                        LogUtils.e("@@@@@@email0=" + email);
                        tv_youxiang.setText("未绑定");
                        youxiangs="未绑定";
                    }else {
                        tv_youxiang.setText(email);
                        LogUtils.e("@@@@@@emai0l=" + email);
                        youxiangs=email;
                    } if (StringUtil.isBlank(gesture_password)){
                       // verName.setText("未绑定");
                        fabimimas="未绑定";
                        titles="0";
                    }else {
                        titles="1";
                        fabimimas=gesture_password;
                        tv_youxiang.setText(gesture_password);
                    }
                    Log.e("AAAAA", "@@11pass=" + pass);
                    if (StringUtil.isBlank(pass)||pass.equals("0")){
                        pass="0";
                        verName.setText("去设置");
                    }else if (pass.equals("1")){
                        verName.setText("修改");

                    }

                }

            }

            @Override
            public void onFailure(Call<AnQuanBean> call, Throwable t) {
                Log.e("AAAAA", "@@11=" + t.getMessage());
                StringUtil.ToastShow(E_AnQuanZhongXin.this,"请先登录");
            }
        });
    }
}
