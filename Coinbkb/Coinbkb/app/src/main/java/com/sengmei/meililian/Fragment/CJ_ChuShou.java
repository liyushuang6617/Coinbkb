package com.sengmei.meililian.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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
import com.sengmei.meililian.Bean.B_HADAXBean;
import com.sengmei.meililian.Utils.HorizontalListView;
import com.sengmei.meililian.Utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CJ_ChuShou extends Fragment implements View.OnClickListener {
    private View view;
    private TextView tv_zixuan, tv_btc, tv_eth, tv_ht;
    private View vv1, vv2, vv3, vv4;

    private C2CJListAdapter adapter;
    private ListView listView;
    private TextView wu;
    private List<B_HADAXBean> list = new ArrayList<>();

    private HorizontalListView hlistview;
    private FaBiTitleAdapter mAdapter;
    private List<FaBiTiTleBean.ObjectBean> hlist = new ArrayList<>();
    private List<C2CJListBean.dataBean> listD = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.b_hadax, null);
        initView(view);
        if (NetUtils.isConnected(getContext())) {
            DataView();
        } else {
          StringUtil.ToastShow(getActivity(), "网络未连接");
        }
        return view;
    }

    private void initView(View view) {
        hlistview = (HorizontalListView) view.findViewById(R.id.hlistview);
        tv_zixuan = (TextView) view.findViewById(R.id.tv_zixuan);
        tv_zixuan.setOnClickListener(this);
        tv_btc = (TextView) view.findViewById(R.id.tv_btc);
        tv_btc.setOnClickListener(this);
        tv_eth = (TextView) view.findViewById(R.id.tv_eth);
        tv_eth.setOnClickListener(this);
        tv_ht = (TextView) view.findViewById(R.id.tv_ht);
        tv_ht.setOnClickListener(this);
        vv1 = (View) view.findViewById(R.id.vv1);
        vv2 = (View) view.findViewById(R.id.vv2);
        vv3 = (View) view.findViewById(R.id.vv3);
        vv4 = (View) view.findViewById(R.id.vv4);

        listView = (ListView) view.findViewById(R.id.listview);
        wu = (TextView) view.findViewById(R.id.wu);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //  startActivity(new Intent(getActivity(),TuXingActivity.class));
                //                startActivity(new Intent(getActivity(),MainActivity.class));
            }
        });

        ETHShow("4");
        tv_zixuan.setTextColor(getResources().getColor(R.color.blue));
        vv1.setBackgroundResource(R.color.blue);
    }

    @Override
    public void onClick(View view) {

        StarColor();
        switch (view.getId()) {
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

    private void StarColor() {
        tv_zixuan.setTextColor(getResources().getColor(R.color.color_text_gray));
        tv_btc.setTextColor(getResources().getColor(R.color.color_text_gray));
        tv_eth.setTextColor(getResources().getColor(R.color.color_text_gray));
        tv_ht.setTextColor(getResources().getColor(R.color.color_text_gray));
        vv1.setBackgroundResource(R.color.white);
        vv2.setBackgroundResource(R.color.white);
        vv3.setBackgroundResource(R.color.white);
        vv4.setBackgroundResource(R.color.white);

    }
    private void ETHShow(final String curren) {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(getActivity()).getService();
        Call<C2CJListBean> indexdata = mFromServerInterface.getURLC2Cmy_ctoc("sell", curren);
        indexdata.enqueue(new Callback<C2CJListBean>() {

            @Override
            public void onResponse(Call<C2CJListBean> call, Response<C2CJListBean> response) {
                if (response.body() == null) {


                    return;
                }
                if (response.body().getType().equals("ok")) {
                    listD = response.body().getMessage();
                    if (response.body().getMessage().size() > 0) {
                        listView.setVisibility(View.VISIBLE);
                        wu.setVisibility(View.GONE);
                        adapter = new C2CJListAdapter(getActivity(), listD);
                        listView.setAdapter(adapter);
                    } else {
                        wu.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.GONE);
                    }


                }
            }

            @Override
            public void onFailure(Call<C2CJListBean> call, Throwable t) {
                Log.e("AAAAA", "@@11=" + t.getMessage());
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

        ETHShow("" + hlist.get(0).getId());
        hlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int jobId = hlist.get(position).getId();
                /*Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
                intent.putExtra("id",jobId+"");
                startActivity(intent);*/
                mAdapter.setSelectedPosition(position);
                mAdapter.notifyDataSetInvalidated();
                ETHShow("" + jobId);
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
