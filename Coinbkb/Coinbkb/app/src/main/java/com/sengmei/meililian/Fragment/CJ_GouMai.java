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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sengmei.RetrofitHttps.Adapter.C2CJListAdapter;
import com.sengmei.RetrofitHttps.Adapter.C2CListAdapter;
import com.sengmei.RetrofitHttps.Adapter.FaBiTitleAdapter;
import com.sengmei.RetrofitHttps.Beans.C2CJListBean;
import com.sengmei.RetrofitHttps.Beans.C2CListBean;
import com.sengmei.RetrofitHttps.Beans.FaBiTiTleBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.RetrofitHttps.NetUtils;
import com.sengmei.kline.R;
import com.sengmei.meililian.Activity.LoginActivity;
import com.sengmei.meililian.Bean.B_HADAXBean;
import com.sengmei.meililian.InterFaces.MenuChooseBack;
import com.sengmei.meililian.Utils.HorizontalListView;
import com.sengmei.meililian.Utils.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CJ_GouMai extends Fragment implements View.OnClickListener,MenuChooseBack {
    private View view;

    private C2CJListAdapter adapter;
    private ListView listView;
    private TextView wu;
    private List<B_HADAXBean> list = new ArrayList<>();
    private TextView zixuan_tv, usdt_tv, btc_tv, eth_tv, cny_tv;
    private View v1, v2, v3, v4, v5;
    private String currencys;
    private HorizontalListView hlistview;
    private FaBiTitleAdapter mAdapter;
    private List<FaBiTiTleBean.ObjectBean> hlist = new ArrayList<>();
    private List<C2CJListBean.dataBean> listD = new ArrayList<>();
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

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // 当不滚动时
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    // 判断是否滚动到底部
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
                        //加载更多功能的代码

                        //StringUtil.ToastShow(getActivity(),"底部"+Page);
                     //   ETHShow(""+hlist.get(poi).getId());
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                View firstView = view.getChildAt(firstVisibleItem);
                if(firstVisibleItem ==0 && (firstView == null || firstView.getTop() == 0))
                {
                    /*上滑到listView的顶部时，下拉刷新组件可见*/
                    refreshLayout.setEnabled(true);
                    refreshLayout.setRefreshing(false);
                }
                else
                {
                    /*不是listView的顶部时，下拉刷新组件不可见*/
                    refreshLayout.setEnabled(false);
                    refreshLayout.setRefreshing(true);
                }

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
                int jobId = hlist.get(poi).getId();
                /*Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
                intent.putExtra("id",jobId+"");
                startActivity(intent);*/
                Log.e(jobId+"FBETHShow", "s=" +position);
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

          /*  case R.id.btc_tv:
                FaBiTiTleBean bean = new FaBiTiTleBean();
                bean.getMessage().get(1).getId();
                ETHShow("" + bean.getMessage().get(1).getId());
                btc_tv.setTextColor(getResources().getColor(R.color.blue));
                v3.setBackgroundResource(R.color.blue);
                break;
            case R.id.eth_tv:
                ETHShow("3");
                eth_tv.setTextColor(getResources().getColor(R.color.blue));
                v4.setBackgroundResource(R.color.blue);
                break;
            case R.id.usdt_tv:
                ETHShow("4");
                usdt_tv.setTextColor(getResources().getColor(R.color.blue));
                v2.setBackgroundResource(R.color.blue);
                break;
            *//*case R.id.zixuan_tv:
                viewpager.setCurrentItem(3);
                zixuan_tv.setTextColor(getResources().getColor(R.color.blue));
                v1.setBackgroundResource(R.color.blue);
                break;*//*
            case R.id.cny_tv:
                ETHShow("19");
                cny_tv.setTextColor(getResources().getColor(R.color.blue));
                v5.setBackgroundResource(R.color.blue);
                break;*/
            default:
                break;
        }
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

    //填充list数据
    private void setListD( List<C2CJListBean.dataBean> news) {
        if (listD != null) {
            listD.clear();
        }
        listD.addAll(news);
        if (listD.size() > 0) {
            listView.setVisibility(View.VISIBLE);
            wu.setVisibility(View.GONE);
            adapter = new C2CJListAdapter(getActivity(), listD);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            adapter.setMenuChooseBack(this);
        } else {
            wu.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }

    }
    private void ETHShow(final String curren) {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(getActivity()).getService();
        Call<C2CJListBean> indexdata = mFromServerInterface.getURLC2Cmy_ctoc("buy",curren);
        indexdata.enqueue(new Callback<C2CJListBean>() {

            @Override
            public void onResponse(Call<C2CJListBean> call, Response<C2CJListBean> response) {
                if (response.body() == null) {

                    return;
                }
                Log.e("FBETHShowCqC", "s=" +response.body().getMessage().size());
                    if (response.body().getType().equals("ok")) {
                        Log.e("FBETHShowCqC", "s=aaaa" );
                        setListD(response.body().getMessage());

                }
            }

            @Override
            public void onFailure(Call<C2CJListBean> call, Throwable t) {
                Log.e("FBETHShowCqC", "@@11q=" + t.getMessage());
            }
        });
    }
    //法币币种标题
    private void DataView() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(getActivity()).getService();
        Call<FaBiTiTleBean> indexdata = mFromServerInterface.getFaBiTiTle();
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

    @Override
    public void Choose(String s) {
        if (NetUtils.isConnected(getContext())){
            DataView();
        }else {
            StringUtil.ToastShow(getActivity(),"网络未连接");
        }
    }
}
