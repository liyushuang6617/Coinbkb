package com.sengmei.meililian.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sengmei.RetrofitHttps.Beans.GengXinBean;
import com.sengmei.RetrofitHttps.Beans.ZhuCeBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.kline.R;
import com.sengmei.meililian.BaseActivity;
import com.sengmei.meililian.MainActivity111;
import com.sengmei.meililian.Utils.StringUtil;

import java.io.File;
import java.util.List;

import kr.co.namee.permissiongen.BuildConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class E_GuanYuWoMen extends BaseActivity implements View.OnClickListener {

    private TextView verName,verNametv;
    private String verNames;
    private String verNametvs;
    //这个是你的包名
    private static final String apkName = "Rodx";
    private static final String firstUrl = "https://jnbdown.mobile369.com/bankex/";
    private static final String[] mPermission = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.e_guanyuwomen);

        verName=(TextView)findViewById(R.id.verName);
        verNametv=(TextView)findViewById(R.id.verNametv);
        verNames= getAppVersionName(this);//AppBanBenShow();
       // AppBanBenShow();
    }

    @Override
    public void initViews() {
        findViewById(R.id.lianxi).setOnClickListener(this);
        findViewById(R.id.yinsi).setOnClickListener(this);
        findViewById(R.id.genxin).setOnClickListener(this);
        verName.setText(verNames);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lianxi:
                startActivity(new Intent(E_GuanYuWoMen.this,WoDeGongDanActivity.class));
                break;
            case R.id.yinsi:
                startActivity(new Intent(E_GuanYuWoMen.this,YinSiXieYiActivity.class).putExtra("titles","yinsi")
                        .putExtra("Ids","103"));
                break;
            case R.id.genxin:
                StringUtil.ToastShow(this,"已是最新版本");
               /* if (verNametvs.equals(verNames)){
                    return;
                }
                Uri uri = Uri.parse(firstUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);*/

                break;
            default:
                break;
        }
    }

    //获取当前版本号
    private String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo("com.Coinbkb.dalao", 0);
            versionName = packageInfo.versionName;
            if (StringUtil.isBlank(versionName)) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    private void AppBanBenShow() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(E_GuanYuWoMen.this).getService();
        Call<GengXinBean> indexdata = mFromServerInterface.URLversion();
        indexdata.enqueue(new Callback<GengXinBean>() {

            @Override
            public void onResponse(Call<GengXinBean> call, Response<GengXinBean> response) {
                if (response.body() == null) {

                    return;
                }
                if (response.body().getType().equals("ok")) {
                    verNametvs=response.body().getMessage().getVersion();
                    verNametv.setText(response.body().getMessage().getVersion());
                }
            }

            @Override
            public void onFailure(Call<GengXinBean> call, Throwable t) {

            }
        });
    }
}
