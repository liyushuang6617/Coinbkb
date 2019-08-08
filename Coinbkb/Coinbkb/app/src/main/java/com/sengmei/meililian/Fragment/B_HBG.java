package com.sengmei.meililian.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.sengmei.RetrofitHttps.Adapter.FaBiTitleAdapter;
import com.sengmei.RetrofitHttps.Beans.FaBiTiTleBean;
import com.sengmei.RetrofitHttps.Beans.FormBTCBean;
import com.sengmei.RetrofitHttps.Beans.XiangQingBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.RetrofitHttps.NetUtils;
import com.sengmei.kline.R;
import com.sengmei.meililian.Activity.GuangGaoActivity;
import com.sengmei.meililian.Activity.LoginActivity;
import com.sengmei.meililian.Activity.ShangPuActivity;
import com.sengmei.meililian.Adapter.B_HADAXAdapter;
import com.sengmei.meililian.Bean.B_HADAXBean;
import com.sengmei.meililian.Utils.HorizontalListView;
import com.sengmei.meililian.Utils.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class B_HBG extends Fragment implements View.OnClickListener {
    private View view;

    private B_HADAXAdapter adapter;
    private ListView listView;
    private TextView wu;
    private List<B_HADAXBean> list = new ArrayList<>();
    private TextView zixuan_tv, usdt_tv, btc_tv, eth_tv, cny_tv;
    private View v1, v2, v3, v4, v5;
    private HorizontalListView hlistview;
    private FaBiTitleAdapter mAdapter;
    private List<FaBiTiTleBean.ObjectBean> hlist = new ArrayList<>();
    private List<FormBTCBean.dataBean> listD = new ArrayList<>();

    private SwipeRefreshLayout refreshLayout;
    private int Page=1,Pages=1,jobId=0,poi=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.b_hbg, null);
        initView(view);
        DataView();
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
                Intent intent = new Intent(getActivity(), ShangPuActivity.class);
                intent.putExtra("id",""+listD.get(position).getSeller_id());
                startActivity(intent);
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

                            DataView();Page=1;
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
                        Page++;
                        //StringUtil.ToastShow(getActivity(),"底部"+Page);
                         ETHShow("" +jobId);
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


        jobId = hlist.get(poi).getId();
        ETHShow("" +jobId);
        mAdapter.setSelectedPosition(poi);
        mAdapter.notifyDataSetInvalidated();
        hlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 jobId = hlist.get(position).getId();
                Page=1;
                poi=position;
                mAdapter.setSelectedPosition(position);
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
    private void setDList(final List<FormBTCBean.dataBean> news) {
        if (listD!=null&Page==1){
            Pages=1;
            listD.clear();
            listD.addAll(news);

            wu.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            if (listD.size()==0){
                wu.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            }else {
                Log.e("FBETHShow2323", listD.size()+"=s="+ Page);
                adapter = new B_HADAXAdapter(getActivity(), listD);
                listView.setAdapter(adapter);
            }
        }else if (news.size()==0&Page!=1){
            if (Pages==1){
                Pages++;
            }
        }else {
            Pages=1;
            listD.addAll(news);
            Log.e("FBETHShow2323", listD.size()+"=s="+ Page);
            adapter = new B_HADAXAdapter(getActivity(), listD);
            listView.setAdapter(adapter);
            listView.setSelection(9*(Page-1)) ;
            adapter.notifyDataSetChanged();
        }

    }
    private void ETHShow(final String curren) {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(getActivity()).getService();
        Call<FormBTCBean> indexdata = mFromServerInterface.getplatformBTC1("sell",""+Page,curren);
        indexdata.enqueue(new Callback<FormBTCBean>() {

            @Override
            public void onResponse(Call<FormBTCBean> call, Response<FormBTCBean> response) {
                if (response.body() == null) {

                    return;
                }
                Log.e("FBETHShow", "s=" +response.body().getMessage().getData().size());
                    if (response.body().getType().equals("ok")) {

                        setDList(response.body().getMessage().getData());


                }
            }

            @Override
            public void onFailure(Call<FormBTCBean> call, Throwable t) {
                Log.e("FBETHShow", "@@11=" + t.getMessage());
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
}
