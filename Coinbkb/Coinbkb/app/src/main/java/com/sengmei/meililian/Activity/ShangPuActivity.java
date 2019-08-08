package com.sengmei.meililian.Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sengmei.RetrofitHttps.Adapter.ShangPuAdapter;
import com.sengmei.RetrofitHttps.Beans.ShangPuBean;
import com.sengmei.RetrofitHttps.Beans.ZhuCeBean;
import com.sengmei.RetrofitHttps.Beans.ZhuanHuanBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.kline.R;
import com.sengmei.meililian.BaseActivity;
import com.sengmei.meililian.Utils.MyListView;
import com.sengmei.meililian.Utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShangPuActivity extends BaseActivity implements View.OnClickListener {
    private String Ids;
    private TextView name, name1, title, times, dan1, dan2, dan3, dan4;
    private List<ShangPuBean.DateBean> hlist = new ArrayList<>();
    private List<ShangPuBean.DateBean> hlistc = new ArrayList<>();//出售
    private List<ShangPuBean.DateBean> hlistg = new ArrayList<>();//购买
    private ListView listviewg;
    private MyListView listviewc;
    private ImageView iv1, iv2, iv3, iv4;

    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shangpuactivity);
        Ids = getIntent().getStringExtra("id");
        Log.e("ShangPuActivity", "Ids= " + Ids);
        GouMaioutShow();
    }

    @Override
    public void initViews() {
        listviewc = (MyListView) findViewById(R.id.listviewc);
        listviewg = (ListView) findViewById(R.id.listviewg);
        name = (TextView) findViewById(R.id.name);
        name1 = (TextView) findViewById(R.id.name1);
        title = (TextView) findViewById(R.id.title);
        times = (TextView) findViewById(R.id.times);
        dan1 = (TextView) findViewById(R.id.dan1);
        dan2 = (TextView) findViewById(R.id.dan2);
        dan3 = (TextView) findViewById(R.id.dan3);
        dan4 = (TextView) findViewById(R.id.dan4);

        iv1 = (ImageView) findViewById(R.id.iv1);
        iv2 = (ImageView) findViewById(R.id.iv2);
        iv3 = (ImageView) findViewById(R.id.iv3);
        iv4 = (ImageView) findViewById(R.id.iv4);
    }

    @Override
    public void ReinitViews() {
        GouMaioutShow();
    }

    @Override
    public void initData() {

    }

    public void back(View v) {
        finish();
    }

    //填充list数据
    private void setList(final List<ShangPuBean.DateBean> news) {
        if (hlist != null) {
            hlist.clear();
        }
        if (hlistc != null) {
            hlistc.clear();
        }
        if (hlistg != null) {
            hlistg.clear();
        }
        hlist.addAll(news);
        for (int i = 0; i < hlist.size(); i++) {
            if (hlist.get(i).getType().equals("sell")) {
                hlistc.add(hlist.get(i));
            } else {
                hlistg.add(hlist.get(i));
            }
        }
        ShangPuAdapter shangPuAdapter = new ShangPuAdapter(ShangPuActivity.this, hlistc);
        ShangPuAdapter shangPuAdapter1 = new ShangPuAdapter(ShangPuActivity.this, hlistg);
        listviewc.setAdapter(shangPuAdapter);
        listviewg.setAdapter(shangPuAdapter1);

    }

    //商铺信息
    private void GouMaioutShow() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(ShangPuActivity.this).getService();
        Call<ShangPuBean> indexdata = mFromServerInterface.getseller(Ids, "1");
        indexdata.enqueue(new Callback<ShangPuBean>() {

            @Override
            public void onResponse(Call<ShangPuBean> call, Response<ShangPuBean> response) {
                if (response.body() == null) {
                    StringUtil.ToastShow(ShangPuActivity.this, "请先登录");
                    return;
                }
                Log.e("AAAAA", "@@11=" + response.body().getType());
                if (response.body().getType().equals("ok")) {
                    Log.e("AAAAA", "@@11=" + response.body().getType());
                    name.setText(response.body().getMessage().getName());
                    String str = response.body().getMessage().getName().substring(0, 1);
                    name1.setText(str);
                    title.setText(response.body().getMessage().getName());
                    times.setText(response.body().getMessage().getCreate_time());
                    dan1.setText(response.body().getMessage().getTotal());
                    dan2.setText(response.body().getMessage().getThirtyDays());
                    dan3.setText(response.body().getMessage().getDone());
                    double a = 0,aa=0;
                    if (StringUtil.isBlank(response.body().getMessage().getDone())) {
                        a = 0;
                    } else {
                        a = Double.parseDouble(response.body().getMessage().getDone());
                    }if (StringUtil.isBlank(response.body().getMessage().getTotal())) {
                        aa = 0;
                    } else {
                        aa =Double.parseDouble(response.body().getMessage().getTotal());
                    }
                    double cc = a / aa * 100;
                    Log.e("AAAAA", a + aa + "@@12=" + cc);
                    String ss = String.format("%.2f", cc);
                    Log.e("AAAAA", "@@222=" + ss);
                    dan4.setText(ss + "%");
                    if (response.body().getMessage().getProve_email().equals("0")) {
                        iv1.setImageResource(R.mipmap.weiren);
                    } else {
                        iv1.setImageResource(R.mipmap.ren);
                    }
                    if (response.body().getMessage().getProve_level().equals("0")) {
                        iv4.setImageResource(R.mipmap.weiren);
                    } else {
                        iv4.setImageResource(R.mipmap.ren);
                    }
                    if (response.body().getMessage().getProve_mobile().equals("0")) {
                        iv2.setImageResource(R.mipmap.weiren);
                    } else {
                        iv2.setImageResource(R.mipmap.ren);
                    }
                    if (!StringUtil.isBlank(response.body().getMessage().getProve_real())) {
                        if (response.body().getMessage().getProve_real().equals("0")) {
                            iv3.setImageResource(R.mipmap.weiren);
                        } else {
                            iv3.setImageResource(R.mipmap.ren);
                        }
                    }
                    setList(response.body().getMessage().getLists().getData());
                }
            }

            @Override
            public void onFailure(Call<ShangPuBean> call, Throwable t) {
                Log.e("AAAAA", "@@11=" + t.getMessage());

                StringUtil.ToastShow(ShangPuActivity.this, "请先登录");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.fabu:
                showDialog1();
                break;*/
            default:
                break;
        }
    }
}
