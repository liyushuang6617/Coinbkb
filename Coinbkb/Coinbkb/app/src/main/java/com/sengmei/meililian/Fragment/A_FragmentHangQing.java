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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sengmei.RetrofitHttps.Adapter.HangQingTitleAdapter;
import com.sengmei.RetrofitHttps.Beans.HangQingBean;
import com.sengmei.RetrofitHttps.Beans.HangQingZiXuanBean;
import com.sengmei.RetrofitHttps.Beans.ZhuCeBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.RetrofitHttps.NetUtils;
import com.sengmei.kline.KLineActivity;
import com.sengmei.kline.KLineManager;
import com.sengmei.kline.R;
import com.sengmei.meililian.Activity.LoginActivity;
import com.sengmei.meililian.Adapter.FragmentTwoAdapter;
import com.sengmei.meililian.Bean.FragmentTwoListBean;
import com.sengmei.meililian.MyApplication;
import com.sengmei.meililian.UserBean;
import com.sengmei.meililian.Utils.CustomDialog;
import com.sengmei.meililian.Utils.HorizontalListView;
import com.sengmei.meililian.Utils.KTititlesBean;
import com.sengmei.meililian.Utils.RefreshDialog;
import com.sengmei.meililian.Utils.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class A_FragmentHangQing extends Fragment implements View.OnClickListener {
    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 1500;
    private static long lastClickTime;
    private CustomDialog di, d2;
    private View view;
    private ListView listView;
    private FragmentTwoAdapter adapter;
    private LinearLayout paibi;

    private HorizontalListView hlistview;
    private HangQingTitleAdapter hListAdapter;
    private SwipeRefreshLayout refreshLayout;
    private int poi = 0;

    private List<FragmentTwoListBean> Twolist = new ArrayList<>();
    private Map<Integer, FragmentTwoListBean> map = new HashMap<>();
    private Map<Integer, Map<Integer, FragmentTwoListBean>> mapMap = new HashMap<>();
    private List<HangQingBean.MessageBean> Hlist = new ArrayList<>();//头部标题
    private List<HangQingBean.quotationBean> hlist = new ArrayList<>();//底部列表
    private List<HangQingBean.quotationBean> hlist1 = new ArrayList<>();
    private TextView all, zixuan;
    Map<Integer, HangQingBean.quotationBean> beanMap = new HashMap<>();
    Map<Integer, Map<Integer, HangQingBean.quotationBean>> titlemap = new HashMap<>();
    private RefreshDialog dialog;
    private TextView wu;
    private String AZ = "all";//判斷自選和
    private LinearLayout liangs, prices, baifens;
    private ImageView v1, v2, v3;
    private String Leix = "leixing";
    private int Addid, Addid1;
    private int deleid, deleid1;
    private String PAIXU = "PAIXUPING", PAIXULEIXING = "1";
    private Handler handler = new Handler();
    private List<HangQingBean.quotationBean> plist = new ArrayList<>();

    private Map<Integer, HangQingBean.quotationBean> beanMap1 = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.a_fragmenttwo, null);

        initView(view);
        refreshLayout.setRefreshing(true);
        if (NetUtils.isConnected(getContext())) {
            HlistTitleShow();
        } else {
            StringUtil.ToastShow(getActivity(), "网络未连接");
        }

        try {
            Socket mSocket = IO.socket(UserBean.Urlio);//正式
            // Socket mSocket = IO.socket("http://47.75.200.255:8080");//测试
            mSocket.connect();
            mSocket.emit("login", "10");
            //mSocket.on("update_online_count", onNewMessage);
            mSocket.on("daymarket", onNewMessage);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        //KLineShow();
        return view;
    }

    private void initView(View view) {
        view.findViewById(R.id.liangs).setOnClickListener(this);
        view.findViewById(R.id.prices).setOnClickListener(this);
        view.findViewById(R.id.baifens).setOnClickListener(this);
        v1 = (ImageView) view.findViewById(R.id.v1);
        v2 = (ImageView) view.findViewById(R.id.v2);
        v3 = (ImageView) view.findViewById(R.id.v3);
        wu = (TextView) view.findViewById(R.id.wu);
        all = (TextView) view.findViewById(R.id.all);
        all.setOnClickListener(this);
        zixuan = (TextView) view.findViewById(R.id.zixuan);
        zixuan.setOnClickListener(this);
        listView = (ListView) view.findViewById(R.id.listview);
        paibi = (LinearLayout) view.findViewById(R.id.paibi);
        hlistview = (HorizontalListView) view.findViewById(R.id.hlistview);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final Random random = new Random();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        HlistTitleShow();
                        // 加载完数据设置为不刷新状态，将下拉进度收起来
                        refreshLayout.setRefreshing(false);
                    }
                }, 1200);

            }
        });
        //  handler.postDelayed(runnable, 5000);//每两秒执行一次runnable.
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("SASA", "onPause: ");
        dialog.hideLoading();
        // handler.removeCallbacks(runnable);
    }


    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if (getActivity() == null) {
                return;
            }
            //主线程调用
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e("A_FragmentHangQing", "Kdata=" + args[0]);
                    String Str = "" + args[0];
                    try {
                        JSONObject objectt = new JSONObject(Str);
                        int legal_ids = objectt.getInt("legal_id");//
                        int currency_ids = objectt.getInt("currency_id");//
                        String changes = objectt.getString("change");//
                        String volumes = objectt.getString("volume");//
                        String legal_names = objectt.getString("legal_name");//
                        String now_prices = objectt.getString("now_price");//
                        String currency_names = objectt.getString("currency_name");//

                        for (Map.Entry<Integer, Map<Integer, HangQingBean.quotationBean>> vo : titlemap.entrySet()) {
                            if (vo.getKey().equals(legal_ids)) {
                                Map<Integer, HangQingBean.quotationBean> beanMap11 = new HashMap<>();
                                for (Map.Entry<Integer, HangQingBean.quotationBean> vo1 : titlemap.get(legal_ids).entrySet()) {
                                    if (vo1.getKey().equals(currency_ids)) {
                                        HangQingBean.quotationBean quotationBean = new HangQingBean.quotationBean();
                                        //Map<Integer, HangQingBean.quotationBean> beanMap11 = new HashMap<>();
                                        quotationBean.setChange(changes);
                                        quotationBean.setVolume(volumes);
                                        quotationBean.setLegal_name(legal_names);
                                        //   quotationBean.setNow_cny_price(now_cny_prices);
                                        quotationBean.setNow_price(now_prices);
                                        quotationBean.setCurrency_name(currency_names);
                                        quotationBean.setLegal_id("" + legal_ids);
                                        quotationBean.setCurrency_id("" + currency_ids);
                                        beanMap11.put(currency_ids, quotationBean);

                                        Log.e("ASSSAS", "vo1.getKey()0=" + vo1.getKey());
                                    } else {
                                        Log.e("ASSSAS", "vo1.getKey()=" + vo1.getKey());
                                        beanMap11.put(vo1.getKey(), vo1.getValue());
                                    }
                                }
                                titlemap.put(legal_ids, beanMap11);

                            } else {

                                titlemap.put(vo.getKey(), vo.getValue());
                            }
                            Log.e("DDDDD", "SASS1=" + Hlist.get(poi).getLegal_id());
                        }

                        if (AZ.equals("zixuan")) {
                            return;
                        }
                        if(Hlist.size()==0){
                            return;
                        }
                        PAIXU(Hlist.get(poi).getLegal_id());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        Log.e("SASA", "onResume: ");
        dialog = new RefreshDialog(getActivity());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.all:
                HlistTitleShow();
                wu.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                hlistview.setVisibility(View.VISIBLE);
                AZ = "all";
                all.setBackgroundResource(R.color.blue);
                zixuan.setBackgroundResource(R.drawable.button_bj1);
                paibi.setVisibility(View.VISIBLE);
                break;
            case R.id.zixuan:
                wu.setVisibility(View.GONE);
                AZ = "zixuan";
                hlistview.setVisibility(View.GONE);
                paibi.setVisibility(View.GONE);
                zixuan.setBackgroundResource(R.color.blue);
                all.setBackgroundResource(R.drawable.button_bj1);
                if (MyApplication.Authori.equals("")) {
                    wu.setVisibility(View.VISIBLE);
                    wu.setText("请先登录");
                    listView.setVisibility(View.GONE);
                    return;
                } else {
                    HlistTitleZXShow();
                }
                break;
            case R.id.liangs:
                starImg();
                if (PAIXU.equals("SHULIANGSHANG")) {
                    PAIXU = "SHULIANGXIA";
                    v1.setImageResource(R.mipmap.down);
                    PAIXULEIXING = "";
                } else if (PAIXU.equals("SHULIANGXIA")) {
                    PAIXU = "PAIXUPING";
                    v1.setImageResource(R.mipmap.line1);
                } else {
                    PAIXU = "SHULIANGSHANG";
                    v1.setImageResource(R.mipmap.top1);
                }
                PAIXU(Hlist.get(poi).getLegal_id());
                break;
            case R.id.prices:
                starImg();
                if (PAIXU.equals("JIAGESHANG")) {
                    PAIXU = "JIAGEXIA";
                    v2.setImageResource(R.mipmap.down);
                } else if (PAIXU.equals("JIAGEXIA")) {
                    PAIXU = "PAIXUPING";
                    v2.setImageResource(R.mipmap.line1);
                } else {
                    PAIXU = "JIAGESHANG";
                    v2.setImageResource(R.mipmap.top1);
                }
                PAIXU(Hlist.get(poi).getLegal_id());
                break;
            case R.id.baifens:
                starImg();
                if (PAIXU.equals("BAIFENSHANG")) {
                    PAIXU = "BAIFENXIA";
                    v3.setImageResource(R.mipmap.down);
                } else if (PAIXU.equals("BAIFENXIA")) {
                    PAIXU = "PAIXUPING";
                    v3.setImageResource(R.mipmap.line1);
                } else {
                    PAIXU = "BAIFENSHANG";
                    v3.setImageResource(R.mipmap.top1);
                }
                PAIXU(Hlist.get(poi).getLegal_id());
                break;
            default:
                break;
        }
    }

    private void starImg() {
        v1.setImageResource(R.mipmap.line1);
        v2.setImageResource(R.mipmap.line1);
        v3.setImageResource(R.mipmap.line1);
    }

    private void KShow(String title) {

        UserBean.KLineShow5(getActivity(), title);
        UserBean.KLineShow0(getActivity(), title);
        // UserBean.KLineShow(getActivity(), title);
        UserBean.KLineShow30(getActivity(), title);
        UserBean.KLineShow1h(getActivity(), title);
        UserBean.KLineShow1d(getActivity(), title);
        UserBean.KLineShow1w(getActivity(), title);
        UserBean.KLineShow1m(getActivity(), title);
    }
    //填充Hlist数据 顶部横向标题


    private void PAIXU(int id) {
        Log.e("PAIXUPAIXU", PAIXU + id + "=PAIXUPAIXU=" + titlemap.get(id).values().size());
        if (plist.size() > 0) {
            plist.clear();
        }
        for (HangQingBean.quotationBean quotationBean : titlemap.get(id).values()) {
            plist.add(quotationBean);
        }
        Log.e("排序后的结果", "排序后的结果：" + plist.size());
        switch (PAIXU) {
            case "SHULIANGSHANG"://降序
                Collections.sort(plist, new Comparator<HangQingBean.quotationBean>() {
                    BigDecimal bg1, bg2;

                    public int compare(HangQingBean.quotationBean p1, HangQingBean.quotationBean p2) {
                        bg1 = new BigDecimal(p1.getVolume());
                        bg2 = new BigDecimal(p2.getVolume());
                        //按照Person的年龄进行升序排列
                        if (bg2.compareTo(bg1) == 1) {
                            return 1;
                        }
                        if (bg2.compareTo(bg1) == 0) {
                            return 0;
                        }

                        return -1;
                    }
                });
                break;
            case "SHULIANGXIA":
                Collections.sort(plist, new Comparator<HangQingBean.quotationBean>() {
                    BigDecimal bg1, bg2;

                    public int compare(HangQingBean.quotationBean p1, HangQingBean.quotationBean p2) {
                        bg1 = new BigDecimal(p2.getVolume());
                        bg2 = new BigDecimal(p1.getVolume());
                        //按照Person的年龄进行升序排列
                        if (bg2.compareTo(bg1) == 1) {
                            return 1;
                        }
                        if (bg2.compareTo(bg1) == 0) {
                            return 0;
                        }

                        return -1;
                    }
                });
                break;
            case "JIAGESHANG":
                Collections.sort(plist, new Comparator<HangQingBean.quotationBean>() {
                    BigDecimal bg1, bg2;

                    public int compare(HangQingBean.quotationBean p1, HangQingBean.quotationBean p2) {
                        bg1 = new BigDecimal(p1.getNow_price());
                        bg2 = new BigDecimal(p2.getNow_price());
                        //按照Person的年龄进行升序排列
                        if (bg2.compareTo(bg1) == 1) {
                            return 1;
                        }
                        if (bg2.compareTo(bg1) == 0) {
                            return 0;
                        }

                        return -1;
                    }
                });
                break;
            case "JIAGEXIA":
                Collections.sort(plist, new Comparator<HangQingBean.quotationBean>() {
                    BigDecimal bg1, bg2;

                    public int compare(HangQingBean.quotationBean p1, HangQingBean.quotationBean p2) {
                        bg1 = new BigDecimal(p2.getNow_price());
                        bg2 = new BigDecimal(p1.getNow_price());
                        //按照Person的年龄进行升序排列
                        if (bg2.compareTo(bg1) == 1) {
                            return 1;
                        }
                        if (bg2.compareTo(bg1) == 0) {
                            return 0;
                        }

                        return -1;
                    }
                });
                break;
            case "BAIFENSHANG":
                Collections.sort(plist, new Comparator<HangQingBean.quotationBean>() {
                    BigDecimal bg1, bg2;

                    public int compare(HangQingBean.quotationBean p1, HangQingBean.quotationBean p2) {
                        bg1 = new BigDecimal(p1.getChange());
                        bg2 = new BigDecimal(p2.getChange());
                        //按照Person的年龄进行升序排列
                        if (bg2.compareTo(bg1) == 1) {
                            return 1;
                        }
                        if (bg2.compareTo(bg1) == 0) {
                            return 0;
                        }

                        return -1;
                    }
                });
                break;
            case "BAIFENXIA":
                Collections.sort(plist, new Comparator<HangQingBean.quotationBean>() {
                    BigDecimal bg1, bg2;

                    public int compare(HangQingBean.quotationBean p1, HangQingBean.quotationBean p2) {
                        bg1 = new BigDecimal(p2.getChange());
                        bg2 = new BigDecimal(p1.getChange());
                        Log.e("PAIXU", bg1 + "=BAIFENXIA=" + bg2);
                        //按照Person的年龄进行升序排列
                        if (bg2.compareTo(bg1) == 1) {
                            return 1;
                        }
                        if (bg2.compareTo(bg1) == 0) {
                            return 0;
                        }

                        return -1;
                    }
                });
                break;
            case "PAIXUPING":
                break;
            default:
                break;

        }
        Log.e("PAIXU", "SHULIANGSHANG=" + PAIXU);
        adapter = new FragmentTwoAdapter(getActivity(), plist);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (MyApplication.Authori.equals("")) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                dialog.showLoading();
                long curClickTime = System.currentTimeMillis();
                if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
                    // 超过点击间隔后再将lastClickTime重置为当前点击时间
                    lastClickTime = curClickTime;


                    UserBean.LegalIDs = plist.get(i).getLegal_id();
                    UserBean.CurrencyDs = plist.get(i).getCurrency_id();
                    UserBean.LegalNames = plist.get(i).getLegal_name();
                    UserBean.CurrencyNames = plist.get(i).getCurrency_name();

                    KLineManager.getInstance().mSymbolName = UserBean.CurrencyNames + "/" + UserBean.LegalNames;
                    KLineManager.getInstance().mCurrencyId = String.valueOf(UserBean.CurrencyDs);
                    KLineManager.getInstance().mLegalId = String.valueOf(UserBean.LegalIDs);

                    KTititlesBean.Titles = UserBean.CurrencyNames + "/" + UserBean.LegalNames;
                    UserBean.HuoQuShow(getActivity(), UserBean.CurrencyDs, UserBean.LegalIDs);
                    UserBean.FanYongShow(getActivity(), UserBean.CurrencyDs, UserBean.LegalIDs);
                    KShow(UserBean.CurrencyNames + "/" + UserBean.LegalNames);
                    KLineActivity.open(getActivity());
                }

                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        if (MyApplication.Authori.equals("")) {
                            startActivity(new Intent(getActivity(), LoginActivity.class));

                            Toast.makeText(getActivity(), "请登录", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("legalCurrencyShow2323", plist.get(position).getCurrency_id() + "=legalCurrencyShow=" + plist.get(position).getLegal_id());
                            legalCurrencyShow(plist.get(position).getLegal_id(), plist.get(position).getCurrency_id(), plist.get(position).getCurrency_match_id());
                        }
                        return true;
                    }
                });
            }
        });

    }

    //填充Hlist数据 顶部横向标题
    private void setHList(final List<HangQingBean.MessageBean> news) {
        if (Hlist != null) {
            Hlist.clear();
        }
        for (int n = 0; n < news.size(); n++) {
            List<HangQingBean.quotationBean> beanList = news.get(n).getQuotation();

            beanMap1 = new HashMap<>();
            for (int nn = 0; nn < beanList.size(); nn++) {
                beanMap1.put(StringUtil.strToInt(beanList.get(nn).getCurrency_id()), beanList.get(nn));
                Log.e("FFDDFF", n + "=dd=" + beanList.get(nn).getCurrency_name() + "" + beanList.get(nn).getLegal_name());
            }
            Log.e("FFDDFF", n + "=dd=" + beanMap1.values().size());
            titlemap.put(news.get(n).getId(), beanMap1);
            Log.e("liangsPAIXU", news.size() + "=??=" + news.get(n).getLegal_id() + "titlemap=" + titlemap.get(news.get(n).getLegal_id()).values().size());

        }
        Hlist.addAll(news);
        hListAdapter = new HangQingTitleAdapter(getActivity(), Hlist);
        hlistview.setAdapter(hListAdapter);
        hListAdapter.notifyDataSetChanged();
        Log.e("KKKKK", "" + Hlist.get(poi).getQuotation());
        if (AZ.equals("all")) {
            //setList(Hlist.get(poi).getQuotation());

            PAIXU(Hlist.get(poi).getLegal_id());
            hListAdapter.setSelectedPosition(poi);
            hListAdapter.notifyDataSetInvalidated();
        } else {
            if (MyApplication.Authori.equals("")) {
                wu.setVisibility(View.VISIBLE);
                wu.setText("请先登录");
                listView.setVisibility(View.GONE);
                hlistview.setVisibility(View.GONE);
                return;
            }
            HlistTitleZXShow();
        }

        hlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                hListAdapter.setSelectedPosition(position);
                hListAdapter.notifyDataSetInvalidated();


                poi = position;
                Log.e("HlistTitleZXShow", poi + "=HlistTitleZXShow=" + Hlist.get(poi).getLegal_id());
                listView.setVisibility(View.VISIBLE);
                PAIXU(Hlist.get(poi).getLegal_id());


            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (MyApplication.Authori.equals("")) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));

                    Toast.makeText(getActivity(), "请登录", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("legalCurrencyShow2323", plist.get(position).getCurrency_id() + "=legalCurrencyShow=" + plist.get(position).getLegal_id());
                    legalCurrencyShow(plist.get(position).getLegal_id(), plist.get(position).getCurrency_id(), plist.get(position).getCurrency_match_id());
                }
                return true;
            }
        });
    }

    //行情头部横向标题
    private void HlistTitleShow() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(getActivity()).getService();
        Call<HangQingBean> indexdata = mFromServerInterface.getquotation();
        indexdata.enqueue(new Callback<HangQingBean>() {

            @Override
            public void onResponse(Call<HangQingBean> call, Response<HangQingBean> response) {
                refreshLayout.setRefreshing(false);
                Log.e("行情头部横向标题", response.body().toString());
                if (response.body() == null) {
                    return;
                }
                if (response.body().getType().equals("ok")) {
                    setHList(response.body().getMessage());

                }
            }

            @Override
            public void onFailure(Call<HangQingBean> call, Throwable t) {
                refreshLayout.setRefreshing(false);
            }
        });
    }

    //填充数据 list数据
    private void setZXList(final List<HangQingZiXuanBean.MessageBean> news) {

        if (plist != null) {
            plist.clear();
        }
        for (int zx = 0; zx < news.size(); zx++) {
            Log.e("legalCurrencyShow2323", news.get(zx).getCurrencyId() + "=legalCurrencyShow=" + news.get(zx).getLegalId());
            Map<Integer, HangQingBean.quotationBean> beanMap1 = new HashMap<>();
            HangQingBean.quotationBean quotationBean = new HangQingBean.quotationBean();
            beanMap1 = titlemap.get(news.get(zx).getLegalId());
            quotationBean = beanMap1.get(news.get(zx).getCurrencyId());
            plist.add(quotationBean);

            Log.e("liangsPAIXU", news.size() + news.get(zx).getLegalId() + "titlemap=" + titlemap.get(news.get(zx).getLegalId()).values().size());
        }
        for (int m = 0; m < plist.size(); m++) {
            Log.e("legalCurrencyShow2323=", plist.get(m).getLegal_id());
            Log.e("legalCurrencyShow2323=", plist.get(m).getCurrency_id());
        }
        if (plist.size() == 0) {
            wu.setVisibility(View.VISIBLE);
            wu.setText("长按添加删除数据");
        } else {
            wu.setVisibility(View.GONE);
        }
        Log.e("ZXQ", "plist" + plist.size());
        adapter = new FragmentTwoAdapter(getActivity(), plist);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
      /*  listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (MyApplication.Authori.equals("")) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                dialog.showLoading();
                long curClickTime = System.currentTimeMillis();
                if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
                    // 超过点击间隔后再将lastClickTime重置为当前点击时间
                    lastClickTime = curClickTime;

                    UserBean.LegalIDs = plist.get(i).getLegal_id();
                    UserBean.CurrencyDs = plist.get(i).getCurrency_id();
                    UserBean.LegalNames = plist.get(i).getLegal_name();
                    UserBean.CurrencyNames = plist.get(i).getCurrency_name();

                    KLineManager.getInstance().mSymbolName = UserBean.CurrencyNames + "/" + UserBean.LegalNames;
                    KLineManager.getInstance().mCurrencyId = String.valueOf(UserBean.CurrencyDs);
                    KLineManager.getInstance().mLegalId = String.valueOf(UserBean.LegalIDs);

                    KTititlesBean.Titles = UserBean.CurrencyNames + "/" + UserBean.LegalNames;
                    UserBean.HuoQuShow(getActivity(), UserBean.CurrencyDs, UserBean.LegalIDs);
                    UserBean.FanYongShow(getActivity(), UserBean.CurrencyDs, UserBean.LegalIDs);
                    KShow(UserBean.CurrencyNames + "/" + UserBean.LegalNames);

                }

            }
        });*/

    }

    //行情头部横向自选区详情
    private void HlistTitleZXShow() {
        Log.e("HlistTitleZXShow", "1212");
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(getActivity()).getService();
        Call<HangQingZiXuanBean> indexdata = mFromServerInterface.getZxuanQu();
        indexdata.enqueue(new Callback<HangQingZiXuanBean>() {

            @Override
            public void onResponse(Call<HangQingZiXuanBean> call, Response<HangQingZiXuanBean> response) {
                refreshLayout.setRefreshing(false);
                if (response.body() == null) {
                    return;
                }
                Log.e("HlistTitleZXShow", "HlistTitleZXShow" + response.body().getType());
                if (response.body().getType().equals("ok")) {
                    setZXList(response.body().getMessage());

                }
            }

            @Override
            public void onFailure(Call<HangQingZiXuanBean> call, Throwable t) {
                refreshLayout.setRefreshing(false);

            }
        });
    }


    private void legalCurrencyShow(final String LG, final String CY, final String LGY) {
     /*   String LG = null, CY = null;
        if (AZ.equals("all")) {
            LG = Hlist.get(poi).getQuotation().get(nn).getLegal_id();
            CY = Hlist.get(poi).getQuotation().get(nn).getCurrency_id();
        } else {
            LG = hlist1.get(nn).getLegal_id();
            CY = hlist1.get(nn).getCurrency_id();
        }*/
        Log.e("legalCurrencyShow2323", LG + "=legalCurrencyShow=" + CY);
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(getActivity()).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.legalCurrency(LG, CY);
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                refreshLayout.setRefreshing(false);
                if (response.body() == null) {
                    return;
                }
                Log.e("legalCurrencyShow", response.body().getType() + "legalCurrencyShow" + response.body().getMessage());

                di = new CustomDialog(getActivity(), R.style.customDialog, R.layout.dialog_item);
                if (response.body().getType().equals("ok")) {
                    if (AZ.equals("all")) {
                        di.show();
                        dia(LGY, "加入自选");
                    } else {
                        di.show();
                        dia(LGY, "删除自选");
                    }

                } else {
                    di.show();
                    dia(LGY, "加入自选");
                }
            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                refreshLayout.setRefreshing(false);

            }
        });
    }

    public void dia(final String LGY, final String str) {
        LayoutInflater in = LayoutInflater.from(getActivity());
        View view = in.inflate(R.layout.dialog_item, null);
        di.setContentView(view);
        TextView et_dialogContent = (TextView) view.findViewById(R.id.et_dialogContent);
        et_dialogContent.setText("是否" + str);
        TextView queren = (TextView) view.findViewById(R.id.queren);
        TextView quxiao = (TextView) view.findViewById(R.id.quxiao);
        queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (str.equals("删除自选")) {
                    deleteZXShow(LGY);
                } else {
                    AddZXShow(LGY);
                }
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

    private void AddZXShow(String LGY) {
     /*   String LG = null;
        if (AZ.equals("all")) {
            LG = hlist.get(n).getCurrency_match_id();
        } else {
            LG = hlist1.get(n).getCurrency_match_id();
        }*/
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(getActivity()).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.legalCurrencyadd(LGY);
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                refreshLayout.setRefreshing(false);
                if (response.body() == null) {
                    return;
                }
                Log.e("HlistTitleZXShow", "HlistTitleZXShow" + response.body().getType());

                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                HlistTitleShow();
            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                refreshLayout.setRefreshing(false);

            }
        });
    }

    private void deleteZXShow(String LGY) {
       /* String LG = null;
        if (AZ.equals("all")) {
            LG = hlist.get(n).getCurrency_match_id();
        } else {
            LG = hlist1.get(n).getCurrency_match_id();
        }*/
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(getActivity()).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.legalCurrencydelete(LGY);
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                refreshLayout.setRefreshing(false);
                if (response.body() == null) {
                    return;
                }
                Log.e("HlistTitleZXShow", "HlistTitleZXShow" + response.body().getType());
                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                HlistTitleShow();
            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                refreshLayout.setRefreshing(false);

            }
        });
    }
}
