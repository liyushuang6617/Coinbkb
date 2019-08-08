package com.sengmei.meililian.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sengmei.RetrofitHttps.Adapter.C2CFListAdapter;
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
import com.sengmei.meililian.Bean.B_HADAXBean;
import com.sengmei.meililian.Utils.CustomDialog;
import com.sengmei.meililian.Utils.HorizontalListView;
import com.sengmei.meililian.Utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CF_ChuShou extends Fragment implements View.OnClickListener {
    private View view;
    private TextView tv_zixuan, tv_btc, tv_eth, tv_ht;
    private View vv1, vv2, vv3, vv4;

    private C2CFListAdapter adapter;
    private ListView listView;
    private TextView wu;
    private List<C2C_QuXiaoFaBu_Bean.MessageBean> list = new ArrayList<>();

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
               /* startActivity(new Intent(getActivity(),C2C_XiangQing.class).putExtra("Ids",listD.get(i).getId())
                        .putExtra("typs","sell"));*/
                XiangQing(listD.get(i).getId());
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
    private void ETHShow(final String curren) {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(getActivity()).getService();
        Call<C2CFListBean> indexdata = mFromServerInterface.getURLC2Cmy_list("sell", curren);
        indexdata.enqueue(new Callback<C2CFListBean>() {

            @Override
            public void onResponse(Call<C2CFListBean> call, Response<C2CFListBean> response) {
                if (response.body() == null) {


                    return;
                }
                if (response.body().getType().equals("ok")) {
                    listD = response.body().getMessage();
                    if (response.body().getMessage().size() > 0) {
                        listView.setVisibility(View.VISIBLE);
                        wu.setVisibility(View.GONE);
                        adapter = new C2CFListAdapter(getActivity(), listD);
                        listView.setAdapter(adapter);
                    } else {
                        wu.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.GONE);
                    }


                }
            }

            @Override
            public void onFailure(Call<C2CFListBean> call, Throwable t) {
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
                 jobId = hlist.get(position).getId();
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
        Call<FaBiTiTleBean> indexdata = mFromServerInterface.getC2CHlist();
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
