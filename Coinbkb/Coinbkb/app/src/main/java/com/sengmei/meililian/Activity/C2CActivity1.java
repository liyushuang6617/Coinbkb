package com.sengmei.meililian.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sengmei.RetrofitHttps.Adapter.C2CListAdapter;
import com.sengmei.RetrofitHttps.Adapter.FaBiTitleAdapter;
import com.sengmei.RetrofitHttps.Beans.C2CListBean;
import com.sengmei.RetrofitHttps.Beans.FaBiTiTleBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.RetrofitHttps.NetUtils;
import com.sengmei.kline.R;
import com.sengmei.meililian.BaseActivity;
import com.sengmei.meililian.Fragment.C_ChuShou;
import com.sengmei.meililian.Fragment.C_GouMai;
import com.sengmei.meililian.UserBean;
import com.sengmei.meililian.Utils.HorizontalListView;
import com.sengmei.meililian.Utils.MyListView;
import com.sengmei.meililian.Utils.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class C2CActivity1 extends BaseActivity implements View.OnClickListener {

    private TextView goumai, chushou;
    private ImageView jilu;

    private C2CListAdapter adapter;
    private MyListView listView;
    private TextView wu;

    private HorizontalListView hlistview;
    private FaBiTitleAdapter mAdapter;
    private List<FaBiTiTleBean.ObjectBean> hlist = new ArrayList<>();

    private List<C2CListBean.dataBean> listD = new ArrayList<>();
    private SwipeRefreshLayout refreshLayout;
    private int poi = 0;
    private String TYPES = "sell";
    private int Page=1;

    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.c2c_activity1);
        DataView();
        UserBean.C2CMM = "购买";
    }

    @Override
    public void initViews() {
        goumai = (TextView) findViewById(R.id.goumai);
        goumai.setOnClickListener(this);
        jilu = (ImageView) findViewById(R.id.jilu);
        jilu.setVisibility(View.VISIBLE);
        jilu.setOnClickListener(this);
        chushou = (TextView) findViewById(R.id.chushou);
        chushou.setOnClickListener(this);

        hlistview = (HorizontalListView) findViewById(R.id.hlistview);
        listView = (MyListView) findViewById(R.id.listview);
        wu = (TextView) findViewById(R.id.wu);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final Random random = new Random();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (NetUtils.isConnected(C2CActivity1.this)) {
                            Page=1;
                            mAdapter.setSelectedPosition(poi);
                            mAdapter.notifyDataSetInvalidated();
                            ETHShow("" + hlist.get(poi).getId());
                        } else {
                            StringUtil.ToastShow(C2CActivity1.this, "网络未连接");
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
                      //  Page++;
                        //StringUtil.ToastShow(getActivity(),"底部"+Page);
                       // ETHShow("" + hlist.get(poi).getId());
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

    @Override
    public void ReinitViews() {

    }

    @Override
    public void initData() {

    }

    public void back(View v) {
        finish();
    }

    //填充list数据
    private void setList(final List<FaBiTiTleBean.ObjectBean> news) {
        if (hlist != null) {
            hlist.clear();
        }
        hlist.addAll(news);
        mAdapter = new FaBiTitleAdapter(C2CActivity1.this, hlist);
        hlistview.setAdapter(mAdapter);
        mAdapter.setSelectedPosition(poi);
        mAdapter.notifyDataSetInvalidated();
        ETHShow("" + hlist.get(poi).getId());
        hlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                poi = position;
                mAdapter.setSelectedPosition(poi);
                mAdapter.notifyDataSetInvalidated();
                ETHShow("" + hlist.get(poi).getId());
            }
        });
    }

    //法币币种标题
    private void DataView() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(C2CActivity1.this).getService();
        Call<FaBiTiTleBean> indexdata = mFromServerInterface.getC2CHlist();
        indexdata.enqueue(new Callback<FaBiTiTleBean>() {

            @Override
            public void onResponse(Call<FaBiTiTleBean> call, Response<FaBiTiTleBean> response) {
                if (response.body() == null) {
                    startActivity(new Intent(C2CActivity1.this, LoginActivity.class));
                    return;
                }
                if (response.body().getType().equals("ok")) {

                    List<FaBiTiTleBean.ObjectBean> object = response.body().getMessage();
                    setList(object);
                    Log.e("FBETHShow", "FaBiTitle=" + response.body().getMessage().size());
                }
            }

            @Override
            public void onFailure(Call<FaBiTiTleBean> call, Throwable t) {
                Log.e("FBETHShow", "@@11=" + t.getMessage());
                Toast.makeText(C2CActivity1.this, "请先登录", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(C2CActivity1.this, LoginActivity.class));
            }
        });
    }

    private void setListD(List<C2CListBean.dataBean> news){
        if (listD!=null){
            listD.clear();
        }
        listD.addAll(news);
        if (listD.size() > 0) {
            listView.setVisibility(View.VISIBLE);
            wu.setVisibility(View.GONE);
            adapter = new C2CListAdapter(C2CActivity1.this, listD);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            wu.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
    }
    private void ETHShow(final String curren) { refreshLayout.setRefreshing(true);
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(C2CActivity1.this).getService();
        Call<C2CListBean> indexdata = mFromServerInterface.getC2CGouMaiList(TYPES, curren);
        indexdata.enqueue(new Callback<C2CListBean>() {

            @Override
            public void onResponse(Call<C2CListBean> call, Response<C2CListBean> response) {
                refreshLayout.setRefreshing(false);
                if (response.body() == null) {

                    return;
                }
                Log.e("FBETHShowCqC", "s=" + response.body().getMessage().size());
                if (response.body().getType().equals("ok")) {
                    Log.e("FBETHShowCqC", "s=aaaa");
                    setListD(response.body().getMessage());



                }
            }

            @Override
            public void onFailure(Call<C2CListBean> call, Throwable t) {
                Log.e("FBETHShowCqC", "@@11q=" + t.getMessage());
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void StarColor() {

        goumai.setBackgroundResource(R.color.transparent);
        chushou.setBackgroundResource(R.color.transparent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.goumai:
                StarColor();
                TYPES = "sell";
                UserBean.C2CMM = "购买";
                DataView();
                poi=0;
                goumai.setBackgroundResource(R.drawable.fabi_top1);
                break;
            case R.id.chushou:
                StarColor();
                poi=0;
                TYPES = "buy";
                UserBean.C2CMM = "出售";
                DataView();
                chushou.setBackgroundResource(R.drawable.fabi_top1);
                break;
            case R.id.jilu:
                startActivity(new Intent(C2CActivity1.this, C2CJiaoYiActivity.class));
                break;
            default:
                break;
        }
    }
}
