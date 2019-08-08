package com.sengmei.meililian.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sengmei.RetrofitHttps.Adapter.C2CFListAdapter;
import com.sengmei.RetrofitHttps.Adapter.C2CListAdapter;
import com.sengmei.RetrofitHttps.Adapter.FaBiTitleAdapter;
import com.sengmei.RetrofitHttps.Beans.C2CFListBean;
import com.sengmei.RetrofitHttps.Beans.C2CListBean;
import com.sengmei.RetrofitHttps.Beans.C2C_QuXiaoFaBu_Bean;
import com.sengmei.RetrofitHttps.Beans.C2C_XiangQingBean;
import com.sengmei.RetrofitHttps.Beans.FaBiTiTleBean;
import com.sengmei.RetrofitHttps.Beans.ZhuCeBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.RetrofitHttps.NetUtils;
import com.sengmei.kline.R;
import com.sengmei.meililian.Activity.C2C_XiangQing;
import com.sengmei.meililian.Activity.LoginActivity;
import com.sengmei.meililian.Bean.B_HADAXBean;
import com.sengmei.meililian.Utils.CustomDialog;
import com.sengmei.meililian.Utils.HorizontalListView;
import com.sengmei.meililian.Utils.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CF_GouMai extends Fragment implements View.OnClickListener {
    private View view;

    private C2CFListAdapter adapter;
    private ListView listView;
    private TextView wu;
    private List<B_HADAXBean> list = new ArrayList<>();
    private TextView zixuan_tv, usdt_tv, btc_tv, eth_tv, cny_tv;
    private View v1, v2, v3, v4, v5;
    private String currencys;
    private HorizontalListView hlistview;
    private FaBiTitleAdapter mAdapter;
    private List<FaBiTiTleBean.ObjectBean> hlist = new ArrayList<>();
    private List<C2CFListBean.dataBean> listD = new ArrayList<>();

    private String currency_name;
    private String price;
    private String number;
    private String create_time,ids;
    private CustomDialog di;
    private int jobId;
    private SwipeRefreshLayout refreshLayout;
    private int poi=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.b_hbg, null);
        initView(view);
       if (NetUtils.isConnected(getContext())){
           DataView();
       }else {
          StringUtil.ToastShow(getActivity(),"网络未连接");
       };

        return view;
    }

    private void initView(View view) {
        hlistview = (HorizontalListView) view.findViewById(R.id.hlistview);
        listView = (ListView) view.findViewById(R.id.listview);
        wu = (TextView) view.findViewById(R.id.wu);
        zixuan_tv = (TextView) view.findViewById(R.id.zixuan_tv);
        zixuan_tv.setOnClickListener(this);
        usdt_tv = (TextView) view.findViewById(R.id.usdt_tv);
        usdt_tv.setOnClickListener(this);
        btc_tv = (TextView) view.findViewById(R.id.btc_tv);
        btc_tv.setOnClickListener(this);
        eth_tv = (TextView) view.findViewById(R.id.eth_tv);
        eth_tv.setOnClickListener(this);
        cny_tv = (TextView) view.findViewById(R.id.cny_tv);
        cny_tv.setOnClickListener(this);
        v1 = (View) view.findViewById(R.id.v1);
        v2 = (View) view.findViewById(R.id.v2);
        v3 = (View) view.findViewById(R.id.v3);
        v4 = (View) view.findViewById(R.id.v4);
        v5 = (View) view.findViewById(R.id.v5);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              /*  Intent intent = new Intent(getActivity(), ShangPuActivity.class);
                intent.putExtra("id",""+listD.get(position).getSeller_id());
                startActivity(intent);*/
               /*  startActivity(new Intent(getActivity(),C2C_XiangQing.class).putExtra("Ids",listD.get(position).getId())
                        .putExtra("typs","buy"));*/
                XiangQing(listD.get(poi).getId());
            }
        });

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final Random random = new Random();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (NetUtils.isConnected(getContext())) {
                            ETHShow(""+hlist.get(poi).getId());
                        } else {
                            StringUtil.ToastShow(getActivity(), "网络未连接");
                        }
                        // 加载完数据设置为不刷新状态，将下拉进度收起来
                        refreshLayout.setRefreshing(false);
                    }
                }, 1200);

            }
        });
    }

    //填充list数据
    private void setList(final List<FaBiTiTleBean.ObjectBean> news) {
        if (hlist != null) {
            hlist.clear();
        }
        hlist.addAll(news);
        mAdapter = new FaBiTitleAdapter(getActivity(), hlist);
        hlistview.setAdapter(mAdapter);
        ETHShow("" + hlist.get(poi).getId());
        hlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                poi=position;
                jobId = hlist.get(poi).getId();
                /*Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
                intent.putExtra("id",jobId+"");
                startActivity(intent);*/
                Log.e(jobId+"FBETHShow", "s=" +poi);
                mAdapter.setSelectedPosition(poi);
                mAdapter.notifyDataSetInvalidated();
                ETHShow("" + jobId);
            }
        });
    }

    @Override
    public void onClick(View view) {
        StarColor();
        switch (view.getId()) {

            default:
                break;
        }
    }

    private void XiangQing(final String id) {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(getActivity()).getService();
        Call<C2C_QuXiaoFaBu_Bean> indexdata = mFromServerInterface.URLC2Ccanceldetail(id);
        indexdata.enqueue(new Callback<C2C_QuXiaoFaBu_Bean>() {

            @Override
            public void onResponse(Call<C2C_QuXiaoFaBu_Bean> call, Response<C2C_QuXiaoFaBu_Bean> response) {
                if (response.body() == null) {


                    return;
                }
                if (response.body().getType().equals("ok")) {

                    currency_name=response.body().getMessage().getCurrency_name();
                    price=response.body().getMessage().getPrice();
                    number=response.body().getMessage().getNumber();
                    create_time=response.body().getMessage().getCreate_time();
                    ids=response.body().getMessage().getId();
                    di=new CustomDialog(getActivity(),R.style.customDialog,R.layout.c2cfabudialog_item);
                    di.show();
                    dia(view);
                }
            }

            @Override
            public void onFailure(Call<C2C_QuXiaoFaBu_Bean> call, Throwable t) {
                Log.e("AAAAA", "@@11=" + t.getMessage());
            }
        });
    }

    public void dia(View v){
        LayoutInflater in = LayoutInflater.from(getActivity());
        View view = in.inflate(R.layout.c2c_quxiao_dialog_item, null);
        di.setContentView(view);
        TextView title=(TextView)view.findViewById(R.id.title);
        TextView pri=(TextView)view.findViewById(R.id.pri);
        TextView nums=(TextView)view.findViewById(R.id.nums);
        TextView times=(TextView)view.findViewById(R.id.times);
        title.setText(currency_name);
        pri.setText(price);
        nums.setText(number);
        times.setText(create_time);
        TextView queren=(TextView)view.findViewById(R.id.queren);
        TextView quxiao=(TextView)view.findViewById(R.id.quxiao);
        queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuXiaoShow();
                di.dismiss();
            }
        });
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                di.dismiss();
            }
        });
    }

    //取消发布订单
    private void QuXiaoShow() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(getActivity()).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.URLC2CCrevoke(ids);
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                if (response.body() == null) {
                    return;
                }
                Log.e("AAAAAss", "@@11=" + response.body().getType());
                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                ETHShow("" + jobId);

            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                Log.e("AAAAAss", "@@11=" + call.toString() + "@@##" + t.getMessage().toString());
            }
        });
    }
    private void StarColor() {
        zixuan_tv.setTextColor(getResources().getColor(R.color.color_text_gray));
        usdt_tv.setTextColor(getResources().getColor(R.color.color_text_gray));
        btc_tv.setTextColor(getResources().getColor(R.color.color_text_gray));
        eth_tv.setTextColor(getResources().getColor(R.color.color_text_gray));
        cny_tv.setTextColor(getResources().getColor(R.color.color_text_gray));
        v1.setBackgroundResource(R.color.white);
        v2.setBackgroundResource(R.color.white);
        v3.setBackgroundResource(R.color.white);
        v4.setBackgroundResource(R.color.white);
        v5.setBackgroundResource(R.color.white);

    }

    private void ETHShow(final String curren) {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(getActivity()).getService();
        Call<C2CFListBean> indexdata = mFromServerInterface.getURLC2Cmy_list("buy",curren);
        indexdata.enqueue(new Callback<C2CFListBean>() {

            @Override
            public void onResponse(Call<C2CFListBean> call, Response<C2CFListBean> response) {
                if (response.body() == null) {

                    return;
                }
                Log.e("FBETHShowCqC", "s=" +response.body().getMessage().size());
                    if (response.body().getType().equals("ok")) {
                        Log.e("FBETHShowCqC", "s=aaaa" );
                        listD=response.body().getMessage();
                        if (response.body().getMessage().size() > 0) {
                            listView.setVisibility(View.VISIBLE);
                            wu.setVisibility(View.GONE);
                            adapter = new C2CFListAdapter(getActivity(), listD);
                            listView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } else {
                            wu.setVisibility(View.VISIBLE);
                            listView.setVisibility(View.GONE);
                        }


                }
            }

            @Override
            public void onFailure(Call<C2CFListBean> call, Throwable t) {
                Log.e("FBETHShowCqC", "@@11q=" + t.getMessage());
            }
        });
    }
    //法币币种标题
    private void DataView() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(getActivity()).getService();
        Call<FaBiTiTleBean> indexdata = mFromServerInterface.getC2CHlist();
        indexdata.enqueue(new Callback<FaBiTiTleBean>() {

            @Override
            public void onResponse(Call<FaBiTiTleBean> call, Response<FaBiTiTleBean> response) {
                if (response.body() == null) {


                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                if (response.body().getType().equals("ok")){

                    List<FaBiTiTleBean.ObjectBean> object = response.body().getMessage();
                    setList(object);
                    Log.e("FBETHShow", "FaBiTitle=" + response.body().getMessage().size());
                }
            }

            @Override
            public void onFailure(Call<FaBiTiTleBean> call, Throwable t) {
                Log.e("FBETHShow", "@@11=" + t.getMessage());
                Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
    }
}
