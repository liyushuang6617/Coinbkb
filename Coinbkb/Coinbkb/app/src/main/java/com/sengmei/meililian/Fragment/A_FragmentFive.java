package com.sengmei.meililian.Fragment;

import android.content.ClipboardManager;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sengmei.RetrofitHttps.Beans.GeRenZhongXinBean;
import com.sengmei.RetrofitHttps.Beans.ZhuCeBean;
import com.sengmei.RetrofitHttps.Beans.ZiChanBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.RetrofitHttps.NetUtils;
import com.sengmei.kline.R;
import com.sengmei.meililian.Activity.C2CActivity;
import com.sengmei.meililian.Activity.C2CActivity1;
import com.sengmei.meililian.Activity.C2CFaBuActivity;
import com.sengmei.meililian.Activity.C2CFaBuActivity1;
import com.sengmei.meililian.Activity.C2CJiaoYiActivity;
import com.sengmei.meililian.Activity.C2CJiaoYiActivity1;
import com.sengmei.meililian.Activity.E_AnQuanZhongXin;
import com.sengmei.meililian.Activity.E_BanZhuZhongXin;
import com.sengmei.meililian.Activity.E_FaBiGuanLi;
import com.sengmei.meililian.Activity.E_GeRenZhongXin;
import com.sengmei.meililian.Activity.E_GuanYuWoMen;
import com.sengmei.meililian.Activity.E_JiaoYiWaKuangActivity;
import com.sengmei.meililian.Activity.E_ShangPuActivity;
import com.sengmei.meililian.Activity.E_ShouKuanFangShi;
import com.sengmei.meililian.Activity.E_TiBiDiZhi;
import com.sengmei.meililian.Activity.E_WoDeJiaoYi;
import com.sengmei.meililian.Activity.E_WodeZiChan;
import com.sengmei.meililian.Activity.FinancialActivity;
import com.sengmei.meililian.Activity.LoginActivity;
import com.sengmei.meililian.Activity.SXZhiFuActivity;
import com.sengmei.meililian.Activity.ShangPuActivity;
import com.sengmei.meililian.Activity.SheZhiActivity;
import com.sengmei.meililian.Activity.TangGuoDuiHuanActivity;
import com.sengmei.meililian.Activity.TuiGuangMaActivity;
import com.sengmei.meililian.Activity.WoDeSuoCangActivity;
import com.sengmei.meililian.Activity.WoDeTangGuoActivity;
import com.sengmei.meililian.Activity.YinSiXieYiActivity;
import com.sengmei.meililian.Adapter.E_ZCAAdapter;
import com.sengmei.meililian.Bean.DataBean;
import com.sengmei.meililian.MainActivity;
import com.sengmei.meililian.MyApplication;
import com.sengmei.meililian.UserBean;
import com.sengmei.meililian.Utils.HttpUtils;
import com.sengmei.meililian.Utils.MoShiZhuanHuan;
import com.sengmei.meililian.Utils.SharedPreferencesUtil;
import com.sengmei.meililian.Utils.StringUtil;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;

public class A_FragmentFive extends Fragment implements View.OnClickListener {
    private View view;
    private ImageView conversion;
    private boolean isNight;
    public static MoShiZhuanHuan MoShiZhuanHuan;
    private TextView tvdenglu, tvid, login_no, shenhe, yaoqing;
    private TextView a1, a2, a3, a4;
    private String Yaoqings;
    private LinearLayout shenfen, dianpu;
    private SwipeRefreshLayout refreshLayout;
    private String Accounts, Names, Ids, Card_ids;
    private String review_status;
    public static List<ZiChanBean.legalbalanceBean> Alllist = new ArrayList<>();

    public static void setMoShiZhuanHuan(MoShiZhuanHuan moShiZhuanHuan) {
        MoShiZhuanHuan = moShiZhuanHuan;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.a_fragmentfive, null);
        initView(view);
        if (NetUtils.isConnected(getContext())) {
            WoDeShow();
        } else {
            StringUtil.ToastShow(getActivity(), "网络未连接");
        }
        return view;
    }

    private void initView(View view) {
        view.findViewById(R.id.login).setOnClickListener(this);
        view.findViewById(R.id.shezhi_iv).setOnClickListener(this);
        view.findViewById(R.id.wozichan).setOnClickListener(this);
        view.findViewById(R.id.fabidindan).setOnClickListener(this);
        view.findViewById(R.id.wodejiaoyi).setOnClickListener(this);
        view.findViewById(R.id.anquan).setOnClickListener(this);
        view.findViewById(R.id.fangshi).setOnClickListener(this);
        view.findViewById(R.id.bangzhu).setOnClickListener(this);
        view.findViewById(R.id.tibi).setOnClickListener(this);
        view.findViewById(R.id.guanyu).setOnClickListener(this);
        view.findViewById(R.id.c2c).setOnClickListener(this);
        view.findViewById(R.id.licai).setOnClickListener(this);
        view.findViewById(R.id.fabuc).setOnClickListener(this);
        view.findViewById(R.id.jiaoyic).setOnClickListener(this);
        view.findViewById(R.id.a11).setOnClickListener(this);
        view.findViewById(R.id.a12).setOnClickListener(this);
        view.findViewById(R.id.a1).setOnClickListener(this);
        view.findViewById(R.id.a2).setOnClickListener(this);
        view.findViewById(R.id.a3).setOnClickListener(this);
        view.findViewById(R.id.a4).setOnClickListener(this);
        yaoqing = (TextView) view.findViewById(R.id.yaoqing);
        yaoqing.setOnClickListener(this);
        dianpu = (LinearLayout) view.findViewById(R.id.dianpu);
        dianpu.setOnClickListener(this);
        login_no = (TextView) view.findViewById(R.id.login_no);
        login_no.setOnClickListener(this);
        shenfen = (LinearLayout) view.findViewById(R.id.shenfen);
        shenfen.setOnClickListener(this);
        tvdenglu = (TextView) view.findViewById(R.id.tvdenglu);
        shenhe = (TextView) view.findViewById(R.id.shenhe);
        tvid = (TextView) view.findViewById(R.id.tvid);
        conversion = (ImageView) view.findViewById(R.id.conversion);
        conversion.setOnClickListener(this);
        isNight = (boolean) SharedPreferencesUtil.get(getActivity(), DataBean.isNight, true);

        Log.e("isNight", "A_FragmentFive" + isNight);
        if (isNight) {
            conversion.setImageResource(R.mipmap.sun);
        } else {
            conversion.setImageResource(R.mipmap.moon);
        }

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final Random random = new Random();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (NetUtils.isConnected(getContext())) {
                            WoDeShow();
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

    @Override
    public void onStart() {
        super.onStart();
        if (NetUtils.isConnected(getContext())) {
            WoDeShow();
        } else {
            StringUtil.ToastShow(getActivity(), "网络未连接");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                if (tvdenglu.getText().toString().equals("点击登录")) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {

                }
                break;
            case R.id.login_no:
                DataBean.Types = "00";
                login_no.setClickable(false);
                SharedPreferencesUtil.put(getActivity(), DataBean.Authori, "");
                startActivity(new Intent(getActivity(), LoginActivity.class));
                MyApplication.Authori = "";
                LoginOut();
                break;
            case R.id.shenfen://个人中心
                if (!review_status.equals("1")) {
                    startActivity(new Intent(getActivity(), E_GeRenZhongXin.class)
                            .putExtra("review_status", review_status).putExtra("Accounts", Accounts)
                            .putExtra("Names", Names).putExtra("Ids", Ids).putExtra("Card_ids", Card_ids));
                }
                // startActivity(new Intent(getActivity(), NightActivity.class));
                break;
            case R.id.wozichan://我的资产
                startActivity(new Intent(getActivity(), E_WodeZiChan.class));
                break;
            case R.id.fabidindan://法币订单
                startActivity(new Intent(getActivity(), E_FaBiGuanLi.class));
                break;
            case R.id.dianpu://我的店铺
                startActivity(new Intent(getActivity(), E_ShangPuActivity.class));
                break;
            case R.id.wodejiaoyi://我的交易
                startActivity(new Intent(getActivity(), E_WoDeJiaoYi.class));
                break;
            case R.id.licai://理财
                startActivity(new Intent(getActivity(), FinancialActivity.class));
                break;
            case R.id.anquan://安全中心
                startActivity(new Intent(getActivity(), E_AnQuanZhongXin.class));
                break;
            case R.id.fangshi:
                startActivity(new Intent(getActivity(), E_ShouKuanFangShi.class));
                break;
            case R.id.tibi://提币地址
                startActivity(new Intent(getActivity(), E_TiBiDiZhi.class));
                break;
            case R.id.bangzhu://帮助中心
                startActivity(new Intent(getActivity(), E_BanZhuZhongXin.class));
                break;
            case R.id.a11://wk
                startActivity(new Intent(getActivity(), E_JiaoYiWaKuangActivity.class));
                break;
            case R.id.a12://sc
                startActivity(new Intent(getActivity(), WoDeSuoCangActivity.class));
                break;
            case R.id.a1://兑换
                startActivity(new Intent(getActivity(), TangGuoDuiHuanActivity.class));
                break;
            case R.id.a2://我的tg
                startActivity(new Intent(getActivity(), WoDeTangGuoActivity.class));
                break;
            case R.id.a3://手续费
                startActivity(new Intent(getActivity(), SXZhiFuActivity.class));
                break;
            case R.id.a4://推广码
                startActivity(new Intent(getActivity(), TuiGuangMaActivity.class).putExtra("Yaoqings", Yaoqings));
                break;
            case R.id.guanyu://关于我们
                startActivity(new Intent(getActivity(), E_GuanYuWoMen.class));
                break;
            case R.id.shezhi_iv:
                startActivity(new Intent(getActivity(), SheZhiActivity.class));
                // startActivity(new Intent(getActivity(), NightActivity.class));
                break;
            case R.id.conversion:
                if (isNight) {
                    if (MoShiZhuanHuan != null) {
                        isNight = false;
                        SharedPreferencesUtil.put(getActivity(), DataBean.isNight, false);
                        conversion.setImageResource(R.mipmap.moon);
                        MoShiZhuanHuan.HeiBai(true);
                    }
                    // startActivity(new Intent(getActivity(), NightActivity.class));
                } else {
                    if (MoShiZhuanHuan != null) {
                        MoShiZhuanHuan.HeiBai(false);
                        isNight = true;
                        SharedPreferencesUtil.put(getActivity(), DataBean.isNight, true);
                        conversion.setImageResource(R.mipmap.sun);
                    }
                    //startActivity(new Intent(getActivity(), NightActivity.class));
                }
                break;

            case R.id.c2c://C2C
                startActivity(new Intent(getActivity(), C2CActivity1.class));
                break;
            case R.id.fabuc://我发布的C2C
                startActivity(new Intent(getActivity(), C2CFaBuActivity1.class));
                break;
            case R.id.jiaoyic://我交易的C2C
                startActivity(new Intent(getActivity(), C2CJiaoYiActivity1.class));
                break;
            case R.id.yaoqing://邀请码
                ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(getActivity().CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(Yaoqings);
                Toast.makeText(getActivity(), "复制成功", Toast.LENGTH_LONG).show();
                break;
            default:
                break;

        }
    }

    //我的
    private void LoginOut() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(getActivity()).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.logout();
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {

                if (response.body() == null) {
                    return;
                }
                if (response.body().getType().equals("ok")) {
                    DataBean.Types = response.body().getType();
                    SharedPreferencesUtil.put(getActivity(), DataBean.Authori, "0");

                    login_no.setClickable(true);
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                Log.e("AAAAA", "@@11??=" + t.getMessage().toString());
                //Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                login_no.setClickable(true);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        WoDeShow1();
    }

    private void WoDeShow1() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(getActivity()).getService();
        Call<JSONObject> indexdata = mFromServerInterface.gernzx1();
        indexdata.enqueue(new Callback<JSONObject>() {

            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                refreshLayout.setRefreshing(false);
                if (response.body() == null) {
                    // StringUtil.ToastShow(getActivity(),"请先登录1");
                    Log.e("测试", "请先登录1");
                    return;
                }
                if (response.body().getString("type").equals("999")) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                GeRenZhongXinBean geRenZhongXinBean = JSON.parseObject(response.body().toString(), GeRenZhongXinBean.class);
                if (geRenZhongXinBean.getType().equals("ok")) {
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }

            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                refreshLayout.setRefreshing(false);


            }
        });
    }

    private void WoDeShow() {
        refreshLayout.setRefreshing(true);
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(getActivity()).getService();
        Call<GeRenZhongXinBean> indexdata = mFromServerInterface.gernzx();
        indexdata.enqueue(new Callback<GeRenZhongXinBean>() {

            @Override
            public void onResponse(Call<GeRenZhongXinBean> call, Response<GeRenZhongXinBean> response) {
                refreshLayout.setRefreshing(false);
                if (response.body() == null) {
                    // StringUtil.ToastShow(getActivity(),"请先登录1");
                    Log.e("测试", "请先登录1");
                    return;
                }
                if (response.body().getType().equals("ok")) {
                    Log.e("测试", "请先登录0");
                    DataBean.Types = response.body().getType();
                    Yaoqings = response.body().getMessage().getExtension_code();
                    yaoqing.setText("邀请码：" + Yaoqings);
                    review_status = response.body().getMessage().getReview_status();
                    String sellers = response.body().getMessage().getIs_seller();
                    Accounts = response.body().getMessage().getAccount();
                    Ids = response.body().getMessage().getId();
                    tvdenglu.setText(Accounts);
                    tvid.setText("Uid:" + Ids);//0未认证 1审核 2已通过 3未通过
                    if (review_status.equals("2")) {
                        Names = response.body().getMessage().getName();
                        Card_ids = response.body().getMessage().getCard_id();

                    } else {
                        Names = "--";
                        Card_ids = "--";
                    }
                    if (review_status.equals("1")) {
                        shenhe.setVisibility(View.VISIBLE);
                    } else {
                        shenhe.setVisibility(View.GONE);
                    }
                    if (sellers.equals("0")) {
                        dianpu.setVisibility(View.GONE);
                    } else {
                        dianpu.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<GeRenZhongXinBean> call, Throwable t) {
                refreshLayout.setRefreshing(false);
                tvdenglu.setText("点击登录");
                tvid.setText("欢迎");
                shenfen.setVisibility(View.VISIBLE);
                Log.e("测试", "请先登录2");
                // StringUtil.ToastShow(getActivity(), "请先登录");
              /*  if (t instanceof HttpException) {

                    ResponseBody responseBody=((HttpException)t).response().errorBody();
                    try {
                        if (responseBody!=null){
                            String s= responseBody.string();
                            Log.e("AAAAA","@@999="+s );
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }*/


            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        onCreate(null);
        WoDeShow();

    }
}