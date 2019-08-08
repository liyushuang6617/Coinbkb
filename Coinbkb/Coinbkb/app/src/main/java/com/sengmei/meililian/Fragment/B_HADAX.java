package com.sengmei.meililian.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.sengmei.RetrofitHttps.Adapter.FaBiTitleAdapter;
import com.sengmei.RetrofitHttps.Beans.FaBiTiTleBean;
import com.sengmei.RetrofitHttps.Beans.FormBTCBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.RetrofitHttps.NetUtils;
import com.sengmei.kline.KLineActivity;
import com.sengmei.kline.R;
import com.sengmei.meililian.Adapter.B_HADAXAdapter;
import com.sengmei.meililian.Bean.B_HADAXBean;
import com.sengmei.meililian.Bean.DataBean;
import com.sengmei.meililian.UserBean;
import com.sengmei.meililian.Utils.HorizontalListView;
import com.sengmei.meililian.Utils.StringUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class B_HADAX extends Fragment implements View.OnClickListener {
    private View view;
    private TextView tv_zixuan,tv_btc,tv_eth,tv_ht;
    private View vv1,vv2,vv3,vv4;

    private B_HADAXAdapter adapter;
    private ListView listView;
    private TextView wu;
    private List<B_HADAXBean> list=new ArrayList<>();

    private SwipeRefreshLayout refreshLayout;
    private int Page=1,Pages=1,poi=0;
    private HorizontalListView hlistview;
    private FaBiTitleAdapter mAdapter;
    private List<FaBiTiTleBean.ObjectBean> hlist=new ArrayList<>();
    private List<FormBTCBean.dataBean> listD = new ArrayList<>();
    private boolean XiaLa=true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.b_hadax, null);
        initView(view);
        DataView();
        return view;
    }
    private void initView(View view){
        hlistview = (HorizontalListView) view.findViewById(R.id.hlistview);
        tv_zixuan=(TextView)view.findViewById(R.id.tv_zixuan);
        tv_zixuan.setOnClickListener(this);
        tv_btc=(TextView)view.findViewById(R.id.tv_btc);
        tv_btc.setOnClickListener(this);
        tv_eth=(TextView)view.findViewById(R.id.tv_eth);
        tv_eth.setOnClickListener(this);
        tv_ht=(TextView)view.findViewById(R.id.tv_ht);
        tv_ht.setOnClickListener(this);
        vv1=(View)view.findViewById(R.id.vv1);
        vv2=(View)view.findViewById(R.id.vv2);
        vv3=(View)view.findViewById(R.id.vv3);
        vv4=(View)view.findViewById(R.id.vv4);

        listView=(ListView)view.findViewById(R.id.listview);
        wu=(TextView)view.findViewById(R.id.wu);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //  startActivity(new Intent(getActivity(),TuXingActivity.class));
                //                startActivity(new Intent(getActivity(),MainActivity.class));
               // startActivity(new Intent(getActivity(),KLineActivity.class));
            }
        });

        ETHShow("4");
        tv_zixuan.setTextColor(getResources().getColor(R.color.blue));
        vv1.setBackgroundResource(R.color.blue);

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
                        if (XiaLa==true){
                            Page++;
                            //StringUtil.ToastShow(getActivity(),"底部"+Page);
                            ETHShow("" +hlist.get(poi).getId());
                        }

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
    public void onClick(View view) {

        StarColor();
        switch (view.getId()){
            case R.id.tv_btc:
                ETHShow("2");
                tv_btc.setTextColor(getResources().getColor(R.color.blue));
                vv2.setBackgroundResource(R.color.blue);
                break;
            case R.id.tv_eth:
                ETHShow("3");
                tv_eth.setTextColor(getResources().getColor(R.color.blue));
                vv3.setBackgroundResource(R.color.blue);
                break;
            case R.id.tv_zixuan:
                ETHShow("4");
                tv_zixuan.setTextColor(getResources().getColor(R.color.blue));
                vv1.setBackgroundResource(R.color.blue);
                break;
            case R.id.tv_ht:
                ETHShow("14");
                tv_ht.setTextColor(getResources().getColor(R.color.blue));
                vv4.setBackgroundResource(R.color.blue);
                break;
            default:
                break;
        }
    }
    private void StarColor(){
        tv_zixuan.setTextColor(getResources().getColor(R.color.color_text_gray));
        tv_btc.setTextColor(getResources().getColor(R.color.color_text_gray));
        tv_eth.setTextColor(getResources().getColor(R.color.color_text_gray));
        tv_ht.setTextColor(getResources().getColor(R.color.color_text_gray));
        vv1.setBackgroundResource(R.color.white);
        vv2.setBackgroundResource(R.color.white);
        vv3.setBackgroundResource(R.color.white);
        vv4.setBackgroundResource(R.color.white);

    }

    //我的
    /*private void ETHShow(final String curren) {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    String s = msg.obj.toString().trim();
                    Log.e("WoDeShow", "s=" + s);
                    try {
                        JSONObject objectt = new JSONObject(s);
                        String type = objectt.getString("type");
                        String message = objectt.getString("message");
                     //   StringUtil.ToastShow1(getActivity(), message);
                        DataBean.Types=type;
                        if (type.equals("ok")) {
                            JSONObject message1 = new JSONObject(message);

                            LogUtils.e("@@@@@" + message1);
                            String data = message1.getString("data");
                            LogUtils.e("@@@@@" + data);
                            JSONArray array = new JSONArray(data);
                            if (list.size()>0){
                                list.clear();
                            }
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject objec = array.getJSONObject(i);
                                String seller_name = objec.getString("seller_name");
                                String surplus_number = objec.getString("surplus_number");
                                int ids = objec.getInt("id");
                                String price = objec.getString("price");
                                String way_name = objec.getString("way_name");
                                B_HADAXBean bHtBean=new B_HADAXBean();
                                bHtBean.setNames(seller_name);
                                bHtBean.setNums1(surplus_number);
                                bHtBean.setNums2(""+ids);
                                bHtBean.setNums3(price);
                                bHtBean.setNums4(way_name);
                                list.add(bHtBean);
                            }
                            if (list.size()>0){
                                listView.setVisibility(View.VISIBLE);
                                wu.setVisibility(View.GONE);
                                adapter=new B_HADAXAdapter(getActivity(),listD);
                                listView.setAdapter(adapter);
                            }else {
                                wu.setVisibility(View.VISIBLE);
                                listView.setVisibility(View.GONE);
                            }
                        } else {
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        // 启动线程来执行任务
        new Thread() {
            public void run() {
                // 请求网络
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("type","buy");
                map.put("page","1");
                map.put("currency_id",curren);
                String result = HttpUtils.newget(map,UserBean.URLplatformBTC1);
                Message m = new Message();
                m.what = 1;
                m.obj = result;
                // 发送消息到Handler
                handler.sendMessage(m);
            }
        }.start();
    }*/

    private void ETHShow(final String curren) {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(getActivity()).getService();
        Call<FormBTCBean> indexdata = mFromServerInterface.getplatformBTC1("buy","1",curren);
        indexdata.enqueue(new Callback<FormBTCBean>() {

            @Override
            public void onResponse(Call<FormBTCBean> call, Response<FormBTCBean> response) {
                if (response.body() == null) {


                    return;
                }
                if (response.body().getType().equals("ok")) {
                    setDList(response.body().getMessage().getData());
                    if (response.body().getMessage().getData().size()>=9){
                        XiaLa=true;
                    }else {
                        XiaLa=false;
                    }
                }
            }

            @Override
            public void onFailure(Call<FormBTCBean> call, Throwable t) {
                Log.e("AAAAA", "@@11=" + t.getMessage());
            }
        });
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
    //填充list数据
    private void setList(final List<FaBiTiTleBean.ObjectBean> news) {
        if (hlist != null) {
            hlist.clear();
        }
        hlist.addAll(news);
        mAdapter = new FaBiTitleAdapter(getActivity(), hlist);
        hlistview.setAdapter(mAdapter);


        ETHShow("" +hlist.get(poi).getId());
        mAdapter.setSelectedPosition(poi);
        mAdapter.notifyDataSetInvalidated();
        hlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Page=1;
                poi=position;
                mAdapter.setSelectedPosition(position);
                mAdapter.notifyDataSetInvalidated();
                ETHShow("" + hlist.get(poi).getId());
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


                    return;
                }
                List<FaBiTiTleBean.ObjectBean> object = response.body().getMessage();
                setList(object);
                Log.e("AAAAAB", "FaBiTitle=" + response.body().getMessage().size());

            }

            @Override
            public void onFailure(Call<FaBiTiTleBean> call, Throwable t) {
                Log.e("AAAAA", "@@11=" + t.getMessage());

            }
        });
    }
}
