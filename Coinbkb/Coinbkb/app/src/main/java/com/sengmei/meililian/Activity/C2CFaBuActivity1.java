package com.sengmei.meililian.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sengmei.RetrofitHttps.Adapter.C2CFListAdapter;
import com.sengmei.RetrofitHttps.Adapter.C2CJListAdapter;
import com.sengmei.RetrofitHttps.Adapter.C2C_FaBuAdapter;
import com.sengmei.RetrofitHttps.Adapter.FaBiTitleAdapter;
import com.sengmei.RetrofitHttps.Beans.C2CFListBean;
import com.sengmei.RetrofitHttps.Beans.C2CTiTleBean;
import com.sengmei.RetrofitHttps.Beans.C2C_QuXiaoFaBu_Bean;
import com.sengmei.RetrofitHttps.Beans.FaBiTiTleBean;
import com.sengmei.RetrofitHttps.Beans.ZhuCeBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.RetrofitHttps.Interfaces.FabuBack;
import com.sengmei.RetrofitHttps.NetUtils;
import com.sengmei.kline.R;
import com.sengmei.meililian.BaseActivity;
import com.sengmei.meililian.Fragment.CF_ChuShou;
import com.sengmei.meililian.Fragment.CF_GouMai;
import com.sengmei.meililian.UserBean;
import com.sengmei.meililian.Utils.AddPopWindow;
import com.sengmei.meililian.Utils.CustomDialog;
import com.sengmei.meililian.Utils.HorizontalListView;
import com.sengmei.meililian.Utils.MyListView;
import com.sengmei.meililian.Utils.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class C2CFaBuActivity1 extends BaseActivity implements View.OnClickListener {

    private TextView goumai, chushou;
    private TextView fabu;
    private ImageView jilu;
    private AddPopWindow addPopWindow;
    private Dialog bottomDialog;
    private TextView chushous, qiugou, xuanzhe, names, next;
    private EditText danjia, nums, mins;
    private View va, va1;
    private RelativeLayout fabu_buton;
    private CustomDialog di;
    private String Ids;
    private String currency_name;
    private String price;
    private String number;
    private String create_time;

    private C2CFListAdapter adapter;
    private MyListView listView;
    private TextView wu;

    private HorizontalListView hlistview;
    private FaBiTitleAdapter mAdapter;
    private List<FaBiTiTleBean.ObjectBean> hlist = new ArrayList<>();

    private List<C2CFListBean.dataBean> listD = new ArrayList<>();
    private SwipeRefreshLayout refreshLayout;
    private int poi = 0;
    private String TYPES = "buy";
    private int Page = 1;

    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.c2cf_activity1);
        DataView();
    }

    @Override
    public void initViews() {
        fabu = (TextView) findViewById(R.id.fabu);
        fabu.setVisibility(View.VISIBLE);
        fabu.setOnClickListener(this);
        goumai = (TextView) findViewById(R.id.goumai);
        goumai.setOnClickListener(this);
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
                        if (NetUtils.isConnected(C2CFaBuActivity1.this)) {
                            Page = 1;
                            mAdapter.setSelectedPosition(poi);
                            mAdapter.notifyDataSetInvalidated();
                            ETHShow("" + hlist.get(poi).getId());
                        } else {
                            StringUtil.ToastShow(C2CFaBuActivity1.this, "网络未连接");
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
                if (firstVisibleItem == 0 && (firstView == null || firstView.getTop() == 0)) {
                    /*上滑到listView的顶部时，下拉刷新组件可见*/
                    refreshLayout.setEnabled(true);
                    refreshLayout.setRefreshing(false);
                } else {
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
        mAdapter = new FaBiTitleAdapter(C2CFaBuActivity1.this, hlist);
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
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(C2CFaBuActivity1.this).getService();
        Call<FaBiTiTleBean> indexdata = mFromServerInterface.getC2CHlist();
        indexdata.enqueue(new Callback<FaBiTiTleBean>() {

            @Override
            public void onResponse(Call<FaBiTiTleBean> call, Response<FaBiTiTleBean> response) {
                if (response.body() == null) {
                    startActivity(new Intent(C2CFaBuActivity1.this, LoginActivity.class));
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
                Toast.makeText(C2CFaBuActivity1.this, "请先登录", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(C2CFaBuActivity1.this, LoginActivity.class));
            }
        });
    }

    private void setListD(List<C2CFListBean.dataBean> news) {
        if (listD != null) {
            listD.clear();
        }
        listD.addAll(news);
        if (listD.size() > 0) {
            listView.setVisibility(View.VISIBLE);
            wu.setVisibility(View.GONE);
            adapter = new C2CFListAdapter(C2CFaBuActivity1.this, listD);
            listView.setAdapter(adapter);
            //adapter.setMenuChooseBack(this);
            adapter.notifyDataSetChanged();
        } else {
            wu.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                XiangQing(listD.get(position).getId());
            }
        });
    }

    private void ETHShow(final String curren) {
        refreshLayout.setRefreshing(true);
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(C2CFaBuActivity1.this).getService();
        Call<C2CFListBean> indexdata = mFromServerInterface.getURLC2Cmy_list(TYPES, curren);
        indexdata.enqueue(new Callback<C2CFListBean>() {

            @Override
            public void onResponse(Call<C2CFListBean> call, Response<C2CFListBean> response) {
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
            public void onFailure(Call<C2CFListBean> call, Throwable t) {
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
                TYPES = "buy";
                DataView();
                poi = 0;
                goumai.setBackgroundResource(R.drawable.fabi_top1);
                break;
            case R.id.chushou:
                StarColor();
                poi = 0;
                TYPES = "sell";
                DataView();
                chushou.setBackgroundResource(R.drawable.fabi_top1);
                break;
            case R.id.fabu:
                showDialog1();
                break;
            default:
                break;
        }
    }

    private void showDialog1() {
        bottomDialog = new Dialog(C2CFaBuActivity1.this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(C2CFaBuActivity1.this).inflate(R.layout.c2c_button1, null);
        chushous = (TextView) contentView.findViewById(R.id.chushou);
        qiugou = (TextView) contentView.findViewById(R.id.qiugou);
        chushous.setOnClickListener(onClickListener);
        qiugou.setOnClickListener(onClickListener);
        names = (TextView) contentView.findViewById(R.id.names);
        names.setText(hlist.get(poi).getName());
        va = (View) contentView.findViewById(R.id.va);
        va1 = (View) contentView.findViewById(R.id.va1);
        danjia = (EditText) contentView.findViewById(R.id.danjia);
        nums = (EditText) contentView.findViewById(R.id.nums);
        mins = (EditText) contentView.findViewById(R.id.mins);
        next = (TextView) contentView.findViewById(R.id.next);
        next.setOnClickListener(onClickListener);
        fabu_buton = (RelativeLayout) contentView.findViewById(R.id.fabu_buton);
        fabu_buton.setOnClickListener(onClickListener);
        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = C2CFaBuActivity1.this.getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.next:
                    if (StringUtil.isBlank(nums.getText().toString().trim()) || StringUtil.isBlank(mins.getText().toString().trim())) {
                        StringUtil.ToastShow(C2CFaBuActivity1.this, "最小量或数量不能为空");
                        break;
                    }
                    if (Double.parseDouble(nums.getText().toString().trim()) <= Double.parseDouble(mins.getText().toString().trim())) {
                        StringUtil.ToastShow(C2CFaBuActivity1.this, "最小量不能高于数量");
                    } else if (danjia.getText().toString().trim().equals("请选择币种")) {
                        StringUtil.ToastShow(C2CFaBuActivity1.this, "请选择币种");
                    } else if (StringUtil.isBlank(danjia.getText().toString().trim())) {
                        StringUtil.ToastShow(C2CFaBuActivity1.this, "请填写单价");
                    } else if (StringUtil.isBlank(nums.getText().toString().trim())) {
                        StringUtil.ToastShow(C2CFaBuActivity1.this, "请输入数量");
                    } else if (StringUtil.isBlank(mins.getText().toString().trim())) {
                        StringUtil.ToastShow(C2CFaBuActivity1.this, "请填写最小交易量");
                    } else {
                        ShangPuFaBuShow();
                        bottomDialog.dismiss();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    //C2C发布
    private void ShangPuFaBuShow() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(C2CFaBuActivity1.this).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.getpublish(TYPES, danjia.getText().toString().trim(),
                nums.getText().toString().trim(), mins.getText().toString().trim(), "ali_pay", "" + hlist.get(poi).getId());
        indexdata.enqueue(new Callback<ZhuCeBean>() {
            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                if (response.body() == null) {
                    // Toast.makeText(C2CFaBuActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.e("ShangPuFaBuShow", "@@11=" + response.body().getType());
                if (response.body().getType().equals("ok")) {
                    Toast.makeText(C2CFaBuActivity1.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    mAdapter.setSelectedPosition(poi);
                    mAdapter.notifyDataSetInvalidated();
                    ETHShow("" + hlist.get(poi).getId());
                } else {
                    Toast.makeText(C2CFaBuActivity1.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                Log.e("ShangPuFaBuShow", "@@11=" + t.getMessage());
            }
        });
    }

    private void XiangQing(final String id) {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(C2CFaBuActivity1.this).getService();
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
                    Ids=response.body().getMessage().getId();
                    di=new CustomDialog(C2CFaBuActivity1.this,R.style.customDialog,R.layout.c2cfabudialog_item);
                    di.show();
                    dia();
                }
            }

            @Override
            public void onFailure(Call<C2C_QuXiaoFaBu_Bean> call, Throwable t) {
                Log.e("AAAAA", "@@11=" + t.getMessage());
            }
        });
    }

    public void dia(){
        LayoutInflater in = LayoutInflater.from(C2CFaBuActivity1.this);
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
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(C2CFaBuActivity1.this).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.URLC2CCrevoke(Ids);
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                if (response.body() == null) {
                    return;
                }
                Log.e("AAAAAss", "@@11=" + response.body().getType());
                Toast.makeText(C2CFaBuActivity1.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                ETHShow("" + hlist.get(poi).getId());

            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                Log.e("AAAAAss", "@@11=" + call.toString() + "@@##" + t.getMessage().toString());
            }
        });
    }
}
