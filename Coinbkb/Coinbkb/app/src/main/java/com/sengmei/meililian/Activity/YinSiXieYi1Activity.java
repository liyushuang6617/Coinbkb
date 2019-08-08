package com.sengmei.meililian.Activity;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.sengmei.RetrofitHttps.Beans.YinSiBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.kline.R;
import com.sengmei.meililian.BaseActivity;
import com.sengmei.meililian.Utils.StringUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YinSiXieYi1Activity extends BaseActivity implements View.OnClickListener {
    private TextView title, name, cont;
    private WebView webview;

    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.yinsixieyiactivity);
        String str = getIntent().getStringExtra("titles");
        String Ids = getIntent().getStringExtra("Ids");
        YinSiShow();
    }

    @Override
    public void initViews() {
        title = (TextView) findViewById(R.id.title);
        name = (TextView) findViewById(R.id.name);
        cont = (TextView) findViewById(R.id.cont);
        webview = (WebView) findViewById(R.id.webview);
    }

    @Override
    public void ReinitViews() {

    }

    @Override
    public void initData() {

    }

    public void back(View view) {
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                break;
        }
    }

    //隐私协议
    private void YinSiShow() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(YinSiXieYi1Activity.this).getService();
        Call<YinSiBean> indexdata = mFromServerInterface.getprivate1();
        indexdata.enqueue(new Callback<YinSiBean>() {

            @Override
            public void onResponse(Call<YinSiBean> call, Response<YinSiBean> response) {
                if (response.body() == null) {
                    StringUtil.ToastShow(YinSiXieYi1Activity.this, "请先登录");
                    return;
                }
                if (response.body().getType().equals("ok")) {
                    if (!StringUtil.isBlank(response.body().getMessage().getTitle())){
                        title.setText(response.body().getMessage().getTitle());
                        name.setText(response.body().getMessage().getTitle());
                    }
                    if (!StringUtil.isBlank(response.body().getMessage().getContent())){
                        webview.loadData(getHtmlData(response.body().getMessage().getContent()), "text/html; charset=UTF-8", null);

                    } }
            }

            @Override
            public void onFailure(Call<YinSiBean> call, Throwable t) {
                StringUtil.ToastShow(YinSiXieYi1Activity.this, "请先登录");
            }
        });
    }

    private String getHtmlData(String bodyHTML) {
        String head = "<head><style>img{max-width: 100%; width:auto; height: auto;}body{background-color:#501C1734!important}</style></head>";
        return "<html>" + head + "<body style='background:#501C1734 !important'>" + bodyHTML + "</body></html>";
    }
}
