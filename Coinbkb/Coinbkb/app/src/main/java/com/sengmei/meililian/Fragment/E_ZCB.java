package com.sengmei.meililian.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sengmei.RetrofitHttps.Beans.ZhuCeBean;
import com.sengmei.RetrofitHttps.Beans.ZiChanBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.kline.R;
import com.sengmei.meililian.Activity.JiaoYiZhangHu1;
import com.sengmei.meililian.Activity.LoginActivity;
import com.sengmei.meililian.Adapter.E_ZCAAdapter;
import com.sengmei.meililian.Adapter.E_ZCBAdapter;
import com.sengmei.meililian.Bean.E_ZCBBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class E_ZCB extends Fragment {
    private View view;
    private E_ZCBAdapter adapter;
    private ListView listView;
    private List<E_ZCBBean> list=new ArrayList<>();
    private List<ZiChanBean.legalbalanceBean>  Alllist=new ArrayList<>();

    private TextView titles,yue;
    private ImageView yan;
    private ImageView yan1;
    private boolean Yans = false;
    private boolean Yans1 = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.e_zcb, null);
        initView(view);
        FaBiioutShow();
        refreshShow();
        return view;
    }
    private void initView(View view){
        titles=(TextView)view.findViewById(R.id.titles);
        yue = (TextView) view.findViewById(R.id.yue);
        yan = view.findViewById(R.id.yan);
        yan1 = view.findViewById(R.id.yan1);
        listView=(ListView)view.findViewById(R.id.listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //  startActivity(new Intent(getActivity(),TuXingActivity.class));
                //                startActivity(new Intent(getActivity(),MainActivity.class));
                startActivity(new Intent(getActivity(),JiaoYiZhangHu1.class)
                        .putExtra("ids",Alllist.get(i).getId()).putExtra("currencys",Alllist.get(i).getCurrency()));
            }
        });
        yan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Yans) {
                    Yans = false;
                    //选择状态 显示明文--设置为可见的密码
                    titles.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    yue.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    yan.setImageResource(R.mipmap.cb_checked);
                } else {
                    Yans = true;
                    //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    titles.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    yue.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    yan.setImageResource(R.mipmap.cb_normaled);
                }
            }
        });
        yan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Yans1) {
                    Yans1 = false;
                    //选择状态 显示明文--设置为可见的密码
                    yan1.setImageResource(R.mipmap.cb_checked);
                    adapter.setSelectedClick(false);
                    adapter.notifyDataSetChanged();
                } else {
                    Yans1 = true;
                    //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    yan1.setImageResource(R.mipmap.cb_normaled);
                    adapter.setSelectedClick(true);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }


    private void FaBiioutShow() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(getActivity()).getService();
        Call<ZiChanBean> indexdata = mFromServerInterface.getZiChan();
        indexdata.enqueue(new Callback<ZiChanBean>() {

            @Override
            public void onResponse(Call<ZiChanBean> call, Response<ZiChanBean> response) {
                if (response.body() == null) {

                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }Log.e("AAAAA", "@@11=" + response.body().getType());
                if (response.body().getType().equals("ok")) {
                    Log.e("AAAAA", "@@11=" + response.body().getType());
                    titles.setText(response.body().getMessage().getLegal_wallet().getTotal()+" USDT");
                    yue.setText(response.body().getMessage().getLegal_wallet().getTotalCNY()+" CNY");
                    setList(response.body().getMessage().getLegal_wallet().getBalance());
                }
            }

            @Override
            public void onFailure(Call<ZiChanBean> call, Throwable t) {
                Log.e("AAAAA", "@@11=" + t.getMessage());

            }
        });
    }

    //填充list数据
    private void setList(final List<ZiChanBean.legalbalanceBean> news) {
        if (Alllist != null) {
            Alllist.clear();
        }
        Alllist.addAll(news);
        Log.e("Alllist", "setList= "+Alllist );
        adapter=new E_ZCBAdapter(getActivity(),Alllist);
        listView.setAdapter(adapter);

    }


    private void  refreshShow() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(getActivity()).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.URLURLrefresh();
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                if (response.body() == null) {

                    return;
                }
                Log.e("dsfdsf","dsfdfs"+response.body().getMessage() );

                FaBiioutShow();
            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                Log.e("dsfdsf","dsfdfs"+t.toString() );
            }
        });
    }
}
