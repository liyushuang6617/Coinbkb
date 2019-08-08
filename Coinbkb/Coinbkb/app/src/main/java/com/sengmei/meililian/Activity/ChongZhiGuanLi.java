package com.sengmei.meililian.Activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.util.LogUtils;
import com.sengmei.RetrofitHttps.Beans.TiBiBean;
import com.sengmei.RetrofitHttps.Beans.TiBidiZhiBean;
import com.sengmei.RetrofitHttps.Beans.ZhuCeBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.kline.R;
import com.sengmei.meililian.BaseActivity;
import com.sengmei.meililian.UserBean;
import com.sengmei.meililian.Utils.HttpUtils;
import com.sengmei.meililian.Utils.QRCodeUtil;
import com.sengmei.meililian.Utils.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChongZhiGuanLi extends BaseActivity {
    private TextView titles,ma_tv,fuzhi,conts_tv;
    private ImageView iv;
    private String currencys,names,minnum;
    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.chongzhicuanli);
        currencys = getIntent().getStringExtra("currencys");
        names = getIntent().getStringExtra("names");
        WoDeShow();infoShow();


    }

    //获取提币信息
    private void infoShow() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(ChongZhiGuanLi.this).getService();
        Call<TiBiBean> indexdata = mFromServerInterface.get_info(currencys);
        indexdata.enqueue(new Callback<TiBiBean>() {

            @Override
            public void onResponse(Call<TiBiBean> call, Response<TiBiBean> response) {
                if (response.body() == null) {

                    return;
                }
                Log.e("AAAAA", "@@1wqe1=" + response.body().getType());
                if (response.body().getType().equals("ok")) {
                    conts_tv=(TextView)findViewById(R.id.conts_tv);
                    minnum=response.body().getMessage().getMin_number();
                    String str="   请勿向上述地址充值任何非"+names+"资产，否则资产将不可找回。\n" +
                            names+ "充币仅支持simple send的方法，使用其他方法的充币暂时无法上账，请您谅解。\n" +
                            "您充值至上述地址后，需要整个网络节点的确认，1次网络确认后到账，6次网络确认可提币。\n" +
                            "最小充值金额："+minnum+" "+names+",小于最小金额的充值将不会上账且无法退回。 您的充值地址不会经常改变，可以重复充值;如有更改，我们会尽量通过网络公告或邮件通知您。\n" +
                            "请务必确认电脑及浏览器安全，防止信息被篡改或泄露";
                LogUtils.e("strstrstrstr"+str);
                conts_tv.setText(str);
                }
            }


            @Override
            public void onFailure(Call<TiBiBean> call, Throwable t) {

            }
        });
    }
    @Override
    public void initViews() {
        titles=(TextView)findViewById(R.id.titles);
        titles.setText(names+"充币");
        ma_tv=(TextView)findViewById(R.id.ma_tv);
        iv=(ImageView)findViewById(R.id.iv);
        findViewById(R.id.fuzhi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(ma_tv.getText());
                Toast.makeText(ChongZhiGuanLi.this, "复制成功", Toast.LENGTH_LONG).show();
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


    private void WoDeShow() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(ChongZhiGuanLi.this).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.getaddress(currencys);
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {

                if (response.body() == null) {
                    StringUtil.ToastShow(ChongZhiGuanLi.this,"请先登录");
                    return;
                }
                Log.e("KeYongMaichuShow卖出=", "@@11=" + response.body().getType());
                if (response.body().getType().equals("ok")) {

                        ma_tv.setText(response.body().getMessage());
                        Bitmap mBitmap = QRCodeUtil.createQRCodeBitmap(response.body().getMessage(), 500, 500);
                        iv.setImageBitmap(mBitmap);

                }else {
                    StringUtil.ToastShow(ChongZhiGuanLi.this,response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                Log.e("KeYongMaichuShow卖出=", "@@11=" + t.getMessage());
                StringUtil.ToastShow(ChongZhiGuanLi.this,"请先登录");
            }
        });
    }
}
