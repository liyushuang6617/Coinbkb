package com.sengmei.meililian.Activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.util.LogUtils;
import com.sengmei.RetrofitHttps.Beans.ErWeiMaBean;
import com.sengmei.RetrofitHttps.Beans.TiBiBean;
import com.sengmei.RetrofitHttps.Beans.ZhuCeBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.kline.R;
import com.sengmei.meililian.BaseActivity;
import com.sengmei.meililian.Utils.QRCodeUtil;
import com.sengmei.meililian.Utils.StringUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TuiGuangMaActivity extends BaseActivity {
    private TextView titles,ma_tv,fuzhi,conts_tv,titles4;
    private ImageView iv;
    private String Yaoqings;
    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.tuiguangmaactivity);
        Yaoqings=getIntent().getStringExtra("Yaoqings");

    }

    @Override
    public void initViews() {
        titles=(TextView)findViewById(R.id.titles);
        titles4=(TextView)findViewById(R.id.titles4);
        titles4.setText(Yaoqings);
        conts_tv=(TextView)findViewById(R.id.conts_tv);
        conts_tv.setText("交易所+多能能生活服务钱包两个强大应用\n" +
                "Coinbkb— 平台币百倍升值空间留给用户");
        ma_tv=(TextView)findViewById(R.id.ma_tv);
        String ss="http://www.coinbkb.net/#/components/register?code="+Yaoqings;
        ma_tv.setText("http://www.coinbkb.net/#/components/register?code="+Yaoqings);
        iv=(ImageView)findViewById(R.id.iv);
        Bitmap mBitmap = QRCodeUtil.createQRCodeBitmap(ss, 500, 500);
        iv.setImageBitmap(mBitmap);
        findViewById(R.id.fuzhi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(ma_tv.getText());
                Toast.makeText(TuiGuangMaActivity.this, "复制成功", Toast.LENGTH_LONG).show();
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

}
