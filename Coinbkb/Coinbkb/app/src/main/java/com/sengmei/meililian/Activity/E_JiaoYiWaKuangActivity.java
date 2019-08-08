package com.sengmei.meililian.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.sengmei.RetrofitHttps.Beans.JYLBBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.kline.R;
import com.sengmei.meililian.Adapter.AddressAdapter;
import com.sengmei.meililian.Adapter.JiaoYiListAdapter;
import com.sengmei.meililian.Adapter.WKListAdapter;
import com.sengmei.meililian.BaseActivity;
import com.sengmei.meililian.UserBean;
import com.sengmei.meililian.Utils.StringUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sengmei.kline.DataRequest.getStringFromAssert;

public class E_JiaoYiWaKuangActivity extends BaseActivity {
    private ListView listview;
    private WKListAdapter adapter;
    private TextView wu;
    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.e_jiaoyiwakuangactivity);
        listview=(ListView)findViewById(R.id.listview);
        wu=findViewById(R.id.wu);
        JiaoYiShow();
    }

    @Override
    public void initViews() {
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

    private void JiaoYiShow(){
        GetDataFromServerInterface mFromServerInterface= GetDataFromServer.getInstance(E_JiaoYiWaKuangActivity.this).getService();
        Call<JYLBBean> indexdata=mFromServerInterface.getlegal_logwk("1","ltc");
        indexdata.enqueue(new Callback<JYLBBean>() {

            @Override
            public void onResponse(Call<JYLBBean> call, Response<JYLBBean> response) {
                if (response.body() == null) {
                    StringUtil.ToastShow(E_JiaoYiWaKuangActivity.this,"请先登录");
                    return;
                }
                if (response.body().getType().equals("ok")) {
                    wu.setVisibility(View.GONE);
                    if (response.body().getMessage().getList().size()==0){
                        wu.setVisibility(View.VISIBLE);
                    }else {
                        adapter=new WKListAdapter(E_JiaoYiWaKuangActivity.this,response.body().getMessage().getList());
                        listview.setAdapter(adapter);
                    }

                }
            }

            @Override
            public void onFailure(Call<JYLBBean> call, Throwable t) {
                Log.e("AAAAA","@@11="+t.getMessage() );
                StringUtil.ToastShow(E_JiaoYiWaKuangActivity.this,"请先登录");
            }
        });
    }
}
