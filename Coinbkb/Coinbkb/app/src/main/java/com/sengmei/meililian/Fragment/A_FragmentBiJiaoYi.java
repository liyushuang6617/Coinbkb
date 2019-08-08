package com.sengmei.meililian.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.util.LogUtils;
import com.sengmei.RetrofitHttps.Adapter.BJiaoYiButtonAdapter;
import com.sengmei.RetrofitHttps.Adapter.BJiaoYiButtonAdapter1;
import com.sengmei.RetrofitHttps.Adapter.BiJiaoYiAdapter;
import com.sengmei.RetrofitHttps.Adapter.BiJiaoYibAdapter;
import com.sengmei.RetrofitHttps.Beans.BJiaoYiBean;
import com.sengmei.RetrofitHttps.Beans.KeYongBean;
import com.sengmei.RetrofitHttps.Beans.ZhuCeBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.kline.KLineManager;
import com.sengmei.kline.R;
import com.sengmei.meililian.Activity.C2CJiaoYiActivity;
import com.sengmei.meililian.Activity.E_WoDeJiaoYi;
import com.sengmei.meililian.Activity.LoginActivity;
import com.sengmei.meililian.Adapter.ThreeListAdapter;
import com.sengmei.meililian.Adapter.ThreeListDownAdapter;
import com.sengmei.meililian.Adapter.ThreeListTopAdapter;
import com.sengmei.meililian.Bean.DataBean;
import com.sengmei.meililian.Bean.ThreeListBean;
import com.sengmei.meililian.Bean.ThreeListDownBean;
import com.sengmei.meililian.Bean.ThreeListTopBean;
import com.sengmei.meililian.MyApplication;
import com.sengmei.meililian.UserBean;
import com.sengmei.meililian.Utils.MenuBackTree;
import com.sengmei.meililian.Utils.MenuLeftFragment;
import com.sengmei.meililian.Utils.MyListView;
import com.sengmei.meililian.Utils.RefreshDialog;
import com.sengmei.meililian.Utils.StringUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class A_FragmentBiJiaoYi extends Fragment implements View.OnClickListener, MenuBackTree {
    private View view;
    private TextView mairu, maichu, denglu, last_price, xianjia, title1, keyong, titlevice1, titlevice, jiaoyie;
    private TextView bt1, bt2, bt3, bt4;
    private EditText jiage, shuliang;
    private String Jiages, Shuliangs, NamesMai, NamesMaiVice, last_prices = "0";
    private int Ids, Idsvice;
    private boolean boo = true;
    private int Idss;
    private Dialog bottomDialog;
    private RefreshDialog dialog;
    private ThreeListTopAdapter topAdapter;
    private List<ThreeListTopBean> toplist = new ArrayList<>();//侧边toplist
    private ThreeListDownAdapter downAdapter;
    private List<ThreeListDownBean> downlist = new ArrayList<>();
    private ListView listview, listview1;
    private MyListView listv;
    private MyListView listv1;
    private ThreeListAdapter adapter;
    private List<ThreeListBean> buttonlist = new ArrayList<>();
    private TextView deles, adds;
    private TextView vvs, mima;
    private String youru, youchu;
    private String LeiXing = "xian";
    private String Paymiam;
    private List<BJiaoYiBean.ObjectBean> Hlist = new ArrayList();
    private List<BJiaoYiBean.ObjectBean> Hlistb = new ArrayList();
    public static List<BJiaoYiBean.ObjectBean> Hlistbt = new ArrayList();//全站
    BJiaoYiButtonAdapter1 biJiaoYiAdapter;
    MenuLeftFragment menuLeftFragment = new MenuLeftFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.a_fragmentbijiaoyi, null);
        denglu = (TextView) view.findViewById(R.id.denglu);
        denglu.setOnClickListener(this);
        initView(view);
        menuLeftFragment.setMenuBackTree(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
     /*   if (DataBean.Types.equals("ok")){
        }else {
          startActivity(new Intent(getActivity(),LoginActivity.class));
        }*/
        ZhuangMAi(DataBean.MM);
        try {
            Socket mSocket = IO.socket(UserBean.Urlio); //正式
            // Socket mSocket = IO.socket("http://47.75.200.255:8080");//测试
            mSocket.connect();
            mSocket.emit("login", "10");
            //mSocket.on("update_online_count", onNewMessage);
            mSocket.on("market_depth", onNewMessage);
            mSocket.on("daymarket", onNewMessage1);
            mSocket.on("market_trade", onNewMessage2);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void initView(View view) {
        bt1 = (TextView) view.findViewById(R.id.bt1);
        bt1.setOnClickListener(this);
        bt2 = (TextView) view.findViewById(R.id.bt2);
        bt2.setOnClickListener(this);
        bt3 = (TextView) view.findViewById(R.id.bt3);
        bt3.setOnClickListener(this);
        bt4 = (TextView) view.findViewById(R.id.bt4);
        bt4.setOnClickListener(this);
        mima = (TextView) view.findViewById(R.id.mima);
        mima.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        vvs = (TextView) view.findViewById(R.id.vvs);
        deles = (TextView) view.findViewById(R.id.deles);
        adds = (TextView) view.findViewById(R.id.adds);
        deles.setOnClickListener(this);
        adds.setOnClickListener(this);
        view.findViewById(R.id.bt_iv).setOnClickListener(this);
        view.findViewById(R.id.shendu).setOnClickListener(this);
        listview = (ListView) view.findViewById(R.id.listview);
        listview1 = (ListView) view.findViewById(R.id.listview1);
        listv = (MyListView) view.findViewById(R.id.listv);
        listv1 = (MyListView) view.findViewById(R.id.listv1);
        last_price = (TextView) view.findViewById(R.id.last_price);
        jiage = (EditText) view.findViewById(R.id.jiage);
        jiage.setOnClickListener(this);
        jiage.addTextChangedListener(textWatcher);
        shuliang = (EditText) view.findViewById(R.id.shuliang);
        shuliang.setOnClickListener(this);
        shuliang.addTextChangedListener(textWatcher);
        jiaoyie = (TextView) view.findViewById(R.id.jiaoyie);
        keyong = (TextView) view.findViewById(R.id.keyong);
        title1 = (TextView) view.findViewById(R.id.title1);
        keyong = (TextView) view.findViewById(R.id.keyong);
        titlevice1 = (TextView) view.findViewById(R.id.titlevice1);
        titlevice = (TextView) view.findViewById(R.id.titlevice);
        xianjia = (TextView) view.findViewById(R.id.xianjia);
        mairu = (TextView) view.findViewById(R.id.mairu);
        mairu.setOnClickListener(this);
        maichu = (TextView) view.findViewById(R.id.maichu);
        maichu.setOnClickListener(this);
        view.findViewById(R.id.weituo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.Authori.equals("")) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getActivity(), E_WoDeJiaoYi.class));
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (null==toplist || toplist.size()==0) {
                    if (0<Hlist.size()){
                        jiage.setText(Hlist.get(position).getPrice());
                    }else {
                        jiage.setText("0");
                    }
                } else {
                    jiage.setText(toplist.get(position).getNums1());

                }
            }
        });

        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (null==downlist || downlist.size()==0) {
                    if (0<Hlistb.size()){
                        jiage.setText(Hlistb.get(position).getPrice());
                    }else {
                        jiage.setText("0");
                    }

                } else {
                    jiage.setText(downlist.get(position).getNums1());
                }
            }
        });
    }

    private void StartColor() {
        bt1.setBackgroundResource(R.drawable.button_bj);
        bt2.setBackgroundResource(R.drawable.button_bj);
        bt3.setBackgroundResource(R.drawable.button_bj);
        bt4.setBackgroundResource(R.drawable.button_bj);
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            Jiages = jiage.getText().toString().trim();
            Shuliangs = shuliang.getText().toString().trim();
            if (Jiages.equals(".")) {
                s.replace(0, 1, "");
            }
            if (Jiages.equals("")) {
                Jiages = "0";
            }
            if (Shuliangs.equals("")) {
                Shuliangs = "0";
            }
            if (Jiages.equals(".")) {
                Jiages = "0.";
            }
            if (Shuliangs.equals(".")) {
                Shuliangs = "0.";
            }

            BigDecimal bg1 = new BigDecimal(0);
            BigDecimal bg2 = new BigDecimal(0);
            if (StringUtil.isBlank(Jiages)) {
                bg1 = new BigDecimal(0);
            } else {
                bg1 = new BigDecimal(Jiages);
            }
            if (StringUtil.isBlank(Jiages)) {
                bg2 = new BigDecimal(0);
            } else {
                bg2 = new BigDecimal(Shuliangs);
            }

            String ss = String.format("%.5f", bg1.multiply(bg2));
            jiaoyie.setText("" + ss);

        }
    };

    @Override
    public void onClick(View v) {
        StartColor();
        switch (v.getId()) {
            case R.id.deles:
                String Str = jiage.getText().toString().trim();
                if (Str.equals("")) {
                    Str = "0";
                }
                double a = Double.parseDouble(Str);
                if (a >= 1) {
                    double n = a - 1;
                    jiage.setText("" + n);
                }
                break;
            case R.id.adds:
                String Stra = jiage.getText().toString().trim();
                if (Stra.equals("")) {
                    Stra = "0";
                }
                double aa = Double.parseDouble(Stra);

                double nn = aa + 1;
                jiage.setText("" + nn);
                break;
            case R.id.bt_iv:
                showDialog();
                break;
            case R.id.shendu:
                showDialog1();
                break;
            case R.id.bt1:
                StartColor();
                bt1.setBackgroundResource(R.color.blue);
                BaiFen(0.25);
                break;
            case R.id.bt2:
                StartColor();
                bt2.setBackgroundResource(R.color.blue);
                BaiFen(0.5);
                break;
            case R.id.bt3:
                StartColor();
                bt3.setBackgroundResource(R.color.blue);
                BaiFen(0.75);
                break;
            case R.id.bt4:
                StartColor();
                bt4.setBackgroundResource(R.color.blue);
                BaiFen(1);
                break;
            case R.id.mairu:
                ZhuangMAi(0);

                break;
            case R.id.maichu:
                ZhuangMAi(1);

                break;
            case R.id.denglu:
                //FanYongListShow(UserBean.URLin);
                Paymiam = mima.getText().toString().trim();
                if (LeiXing.equals("xian")) {
                    Jiages = jiage.getText().toString().trim();
                } else {
                    Jiages = vvs.getText().toString().trim();
                }

                if (!MyApplication.Authori.equals("")) {
                    String jg = Jiages;
                    String sl = shuliang.getText().toString().trim();
                    if (sl.equals("")) {
                        Toast.makeText(getActivity(), "数量不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (jg.equals("")) {
                        Toast.makeText(getActivity(), "价格不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (StringUtil.isBlank(Paymiam)) {
                        Toast.makeText(getActivity(), "交易密码不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    double cc = Double.valueOf(sl);
                    double dd = Double.valueOf(jg);
                    Log.e("AAAA", dd + "boo" + cc);
                    if (dd > 0 & cc > 0) {
                        Log.e("AAAA", dd + "boo" + cc);
                        if (boo == true) {//买入
                            KinShow();
                            //FanYongListShow(UserBean.URLin);
                            Log.e("BBBBBBBsd", "boo22" + boo);
                        } else {//卖出
                            //  FanYongListShow(UserBean.URLout);
                            KoutShow();
                            Log.e("BBBBBBBsd", "boo11" + boo);
                        }
                    } else {
                        Toast.makeText(getActivity(), "数量与价格不能小于0", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            default:
                break;
        }
    }

    private void ZhuangMAi(int n) {
        switch (n) {
            case 0:
                StartColor();
                shuliang.setHint("数量");
                boo = true;
                mairu.setBackgroundResource(R.mipmap.mairu);
                maichu.setBackgroundResource(R.mipmap.mai1);
                mairu.setTextColor(getResources().getColor(R.color.green));
                maichu.setTextColor(getResources().getColor(R.color.color_text_gray));
                denglu.setBackgroundColor(getResources().getColor(R.color.green));
                denglu.setText("买入" + UserBean.CurrencyNames);
                title1.setText(UserBean.CurrencyNames);
                titlevice.setText(UserBean.LegalNames);
                titlevice1.setText(UserBean.LegalNames);
                keyong.setText(UserBean.idstitle);
                vvs.setText(youchu);
                Log.e("SSSSSAAA", "买UserBean.idstitle" + UserBean.idstitle);
                Log.e("SSSSSAAA", "买UserBean.idstitle" + UserBean.idbutown);
                // KeYongMairuShow();
                break;
            case 1:
                StartColor();
                shuliang.setHint("数量");
                boo = false;
                maichu.setBackgroundResource(R.mipmap.maichu);
                mairu.setBackgroundResource(R.mipmap.mai2);
                mairu.setTextColor(getResources().getColor(R.color.color_text_gray));
                maichu.setTextColor(getResources().getColor(R.color.color_text_FireBrick));
                denglu.setBackgroundColor(getResources().getColor(R.color.color_text_FireBrick));
                denglu.setText("卖出" + UserBean.CurrencyNames);
                title1.setText(UserBean.CurrencyNames);
                titlevice.setText(UserBean.LegalNames);
                titlevice1.setText(UserBean.CurrencyNames);
                keyong.setText(UserBean.idbutown);
                vvs.setText(youru);
                Log.e("SSSSSAAA", "卖UserBean.idstitle=" + UserBean.idstitle);
                Log.e("SSSSSAAA", "卖UserBean.idstitle=" + UserBean.idbutown);
                // KeYongMaichuShow();
                break;
            default:
                break;
        }
    }

    private void showDialog() {
        bottomDialog = new Dialog(getActivity(), R.style.BottomDialog);
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_button, null);
        TextView quxiao = (TextView) contentView.findViewById(R.id.quxiao);
        TextView xian = (TextView) contentView.findViewById(R.id.xian);
        TextView shi = (TextView) contentView.findViewById(R.id.shi);
        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
        shi.setOnClickListener(onClickListener);
        xian.setOnClickListener(onClickListener);
        quxiao.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.xian:
                    bottomDialog.cancel();
                    LeiXing = "xian";
                    xianjia.setText("限价");
                    vvs.setVisibility(View.GONE);
                    //  jiage.setText("" + last_prices);
                    break;
                case R.id.shi:
                    bottomDialog.cancel();
                    xianjia.setText("市价");
                    LeiXing = "shi";
                    if (null==downlist || downlist.size()==0) {
                        if (0<Hlistb.size()){
                            youru = Hlistb.get(0).getPrice();
                        }else {
                            youru = "0";
                        }

                    } else {
                        youru = downlist.get(0).getNums1();
                    }
                    String s1 = Hlist.get(4).getPrice();
                    if (s1.equals("--")) {
                        youchu = "0";
                    } else {
                        youchu = s1;
                    }
                    if (null==toplist || toplist.size()==0) {
                        if (0<Hlist.size()){
                            youchu = Hlist.get(0).getPrice();
                        }else {
                            youchu = "0";
                        }
                    } else {
                        youchu = toplist.get(0).getNums1();
                    }
                    vvs.setVisibility(View.VISIBLE);
                    // vvs.setText(last_prices);
                    if (boo == true) {
                        vvs.setText(youchu);
                    } else {
                        vvs.setText(youru);
                    }

                    break;
                case R.id.quxiao:
                    bottomDialog.cancel();
                    break;
                default:
                    break;
            }
        }
    };

    private void showDialog1() {
        final Dialog bottomDialog = new Dialog(getActivity(), R.style.BottomDialog);
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_button1, null);
        TextView quxiao = (TextView) contentView.findViewById(R.id.quxiao);
        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog.cancel();
            }
        });
    }

    private void KinShow() {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("price", Jiages);
        map.put("num", Shuliangs);
        map.put("legal_id", "" + UserBean.LegalIDs);
        map.put("pay_password", "" + Paymiam);
        map.put("currency_id", "" + UserBean.CurrencyDs);
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(getActivity()).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.getKLin(map);
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {

                if (response.body() == null) {
                    return;
                }
                if (response.body().getType().equals("ok")) {
                    DataBean.Types = response.body().getType();
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                KeYongMairuShow1();
                FanYongShow();
            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                Log.e("AAAAA", "@@11=" + t.getMessage().toString());
                //startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
    }

    private void KoutShow() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("price", Jiages);
        map.put("num", Shuliangs);
        map.put("legal_id", "" + UserBean.LegalIDs);
        map.put("pay_password", "" + Paymiam);
        map.put("currency_id", "" + UserBean.CurrencyDs);
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(getActivity()).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.getKLout(map);
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                if (response.body() == null) {
                    return;
                }
                if (response.body().getType().equals("ok")) {
                    DataBean.Types = response.body().getType();
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                KeYongMaichuShow1();
                FanYongShow();
            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                Log.e("AAAAA", "@@11=" + t.getMessage().toString());
            }
        });
    }

    /**
     * 顶部
     */
    private void setHList(final List<BJiaoYiBean.ObjectBean> news) {
        if (Hlist != null) {
            Hlist.clear();
        }
        List<BJiaoYiBean.ObjectBean> news1 = new ArrayList<>();
        int nn = 5 - news.size();
        for (int ii = 0; ii < nn; ii++) {
            BJiaoYiBean.ObjectBean topBean = new BJiaoYiBean.ObjectBean();
            topBean.setPrice("--");
            topBean.setNumber("--");
            news1.add(topBean);
        }
        Hlist.addAll(news1);
        Hlist.addAll(news);
        //Collections.reverse(Hlist);
        BiJiaoYiAdapter biJiaoYiAdapter = new BiJiaoYiAdapter(getActivity(), Hlist);
        listview.setAdapter(biJiaoYiAdapter);
        biJiaoYiAdapter.notifyDataSetChanged();
        Log.e("ASASA", Hlist.size() + "=Q@@@=" + Hlist.get(4).getPrice());
        String s1 = Hlist.get(4).getPrice();
        if (s1.equals("--")) {
            youchu = "0";
        } else {
            youchu = s1;
        }

    }

    /**
     * 低部
     */
    private void setHListb(final List<BJiaoYiBean.ObjectBean> news) {
        if (Hlistb != null) {
            Hlistb.clear();
        }
        Log.e("AAASDADSAD", "news.size()=" + news.size());
        int nn = 5 - news.size();
        for (int ii = 0; ii < nn; ii++) {
            Log.e("AAASDADSAD", nn + "=WW=" + ii + "=news.size()11=" + (5 - news.size()));
            BJiaoYiBean.ObjectBean topBean = new BJiaoYiBean.ObjectBean();
            topBean.setPrice("--");
            topBean.setNumber("--");
            news.add(topBean);
        }
        Log.e("AAASDADSAD", "news.size()=" + news.size());
        Hlistb.addAll(news);
        BiJiaoYibAdapter biJiaoYiAdapter = new BiJiaoYibAdapter(getActivity(), Hlistb);
        listview1.setAdapter(biJiaoYiAdapter);
        biJiaoYiAdapter.notifyDataSetChanged();


    }

    /**
     * 低部list
     */
    private void setHListbt(final List<BJiaoYiBean.ObjectBean> news) {
        if (Hlistbt != null) {
            Hlistbt.clear();
        }
        Log.e("AAASDADSAD", "news.size()=" + news.size());
        Hlistbt.addAll(news);
        /*hListAdapter=new HListAdapter(getActivity(),Hlist);
        hlistview.setAdapter(hListAdapter);*/
        listv1.setVisibility(View.GONE);
        listv.setVisibility(View.VISIBLE);
        biJiaoYiAdapter = new BJiaoYiButtonAdapter1(getActivity(), Hlistbt);
        listv.setAdapter(biJiaoYiAdapter);
    }

    //首次数据
    private void FanYongShow() {
        // Log.e("首次数据", UserBean.LegalIDs+"="+UserBean.CurrencyDs );
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(getActivity()).getService();
        Call<BJiaoYiBean> indexdata = mFromServerInterface.getURL_dels("" + UserBean.LegalIDs, "" + UserBean.CurrencyDs);
        indexdata.enqueue(new Callback<BJiaoYiBean>() {

            @Override
            public void onResponse(Call<BJiaoYiBean> call, Response<BJiaoYiBean> response) {

                if (response.body() == null) {
                    return;
                }
                if (response.body().getType().equals("ok")) {
                    last_prices = response.body().getMessage().getLast_price();
                    last_price.setText("" + response.body().getMessage().getLast_price());
                    setHListbt(response.body().getMessage().getComplete());
                    if (xianjia.getText().equals("市价")) {
                        jiage.setText(last_prices);
                    }
                    setHList(response.body().getMessage().getOut());
                    setHListb(response.body().getMessage().getIn());
                    Log.e("FanYongShow", "@@11=" + response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<BJiaoYiBean> call, Throwable t) {
                Log.e("AAAAA", "@@11=" + t.getMessage());
            }
        });
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
                    // Log.e("首次数据", "推=" + args[0]);
                    TJsonShow(args[0] + "");
                }
            });
        }
    };

    private Emitter.Listener onNewMessage1 = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if (getActivity() == null) {
                return;
            }
            //主线程调用
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Log.e("首次数据", "推=" + args[0]);
                    try {

                        JSONObject objectt = new JSONObject(args[0] + "");
                        last_prices = objectt.getString("now_price");//
                        int legal_id = objectt.getInt("legal_id");
                        int currency_id = objectt.getInt("currency_id");
                        if (StringUtil.isBlank(UserBean.LegalIDs) || StringUtil.isBlank(UserBean.CurrencyDs)) {

                            return;
                        }
                        if (UserBean.LegalIDs.equals("" + legal_id) & UserBean.CurrencyDs.equals("" + currency_id)) {

                            // Log.e("首次数据6711", "推=" + last_prices);
                            //  last_price.setText(last_prices);

                            last_price.setText("" + last_prices);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private void TJsonShow1(String Str) {

    }

    //soket返回
    private void TJsonShow(String Str) {
        // Log.e("首次数据", "推1212=" + Str);
        try {
            JSONObject objectt = new JSONObject(Str);
            //Log.e("首次数据11", "推=" + objectt);


            int legal_id = objectt.getInt("legal_id");

            int currency_id = objectt.getInt("currency_id");
            if (StringUtil.isBlank(UserBean.LegalIDs) || StringUtil.isBlank(UserBean.CurrencyDs)) {

                return;
            }

            if (UserBean.LegalIDs.equals("" + legal_id) & UserBean.CurrencyDs.equals("" + currency_id)) {


                String asks = objectt.getString("asks");
                JSONArray jsonArray = new JSONArray(asks);
                if (toplist != null) {
                    toplist.clear();
                }
                if (jsonArray.length() > 5) {
                    for (int i = 0; i < 5; i++) {
                        JSONArray object = jsonArray.getJSONArray(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        //  Log.e("首次数据222", "推555=" + object);  // 得到 每个对象中的属性值
                        object.getString(0);
                        // Log.e("首次数据2333", "推=" + object.get(0));  // 得到 每个对象中的属性值

                        ThreeListTopBean topBean = new ThreeListTopBean();
                        topBean.setNames((5 - i) + "");
                        topBean.setNums1(object.getString(0));
                        topBean.setNums2(object.getString(1));
                        topBean.setNums3("0");
                        toplist.add(topBean);
                    }
                } else {
                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONArray object = jsonArray.getJSONArray(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                            // Log.e("首次数据222", "推444=" + object);  // 得到 每个对象中的属性值
                            object.getString(0);
                            //Log.e("首次数据2333", "推=" + object.get(0));  // 得到 每个对象中的属性值

                            ThreeListTopBean topBean = new ThreeListTopBean();
                            topBean.setNames((jsonArray.length() - i) + "");
                            topBean.setNums1(object.getString(0));
                            topBean.setNums2(object.getString(1));
                            topBean.setNums3("0");
                            toplist.add(topBean);
                        }
                        for (int ii = 0; ii < 5 - jsonArray.length(); ii++) {
                            ThreeListTopBean topBean = new ThreeListTopBean();
                            topBean.setNames(5 - ii + "");
                            topBean.setNums1("--");
                            topBean.setNums2("--");
                            toplist.add(topBean);

                        }
                    } else {

                        for (int ii = 0; ii < 5 - jsonArray.length(); ii++) {
                            ThreeListTopBean topBean = new ThreeListTopBean();
                            topBean.setNames(5 - ii + "");
                            topBean.setNums1("--");
                            topBean.setNums2("--");
                            toplist.add(topBean);

                        }
                    }
                }
                //  Log.e("首次数据2000", "推=topAdapter" );  // 得到 每个对象中的属性值
                topAdapter = new ThreeListTopAdapter(getActivity(), toplist);
                listview.setAdapter(topAdapter);

                if (downlist.size() > 0) {
                    downlist.clear();
                }
                String bids = objectt.getString("bids");
                JSONArray jsonArrays = new JSONArray(bids);

                if (jsonArrays.length() > 5) {
                    for (int i = 0; i < 5; i++) {
                        JSONArray objec0 = jsonArrays.getJSONArray(i);

                        ThreeListDownBean downBean = new ThreeListDownBean();
                        downBean.setNames((i + 1) + "");
                        downBean.setNums1(objec0.getString(0));
                        downBean.setNums2(objec0.getString(1));
                        downBean.setNums3("0");
                        downlist.add(downBean);
                    }
                } else {

                    if (jsonArray.length() > 0) {

                        for (int i = 0; i < 5; i++) {
                            JSONArray objec0 = jsonArrays.getJSONArray(i);

                            ThreeListDownBean downBean = new ThreeListDownBean();
                            downBean.setNames((i + 1) + "");
                            downBean.setNums1(objec0.getString(0));
                            downBean.setNums2(objec0.getString(1));
                            downBean.setNums3("0");
                            downlist.add(downBean);
                        }
                        for (int ii = 0; ii < 5 - jsonArrays.length(); ii++) {
                            ThreeListDownBean downBean = new ThreeListDownBean();
                            downBean.setNames(ii + jsonArrays.length() + 1 + "");
                            downBean.setNums1("--");
                            downBean.setNums2("--");
                            downlist.add(downBean);
                        }
                    } else {

                        for (int ii = 0; ii < 5 - jsonArrays.length(); ii++) {
                            ThreeListDownBean downBean = new ThreeListDownBean();
                            downBean.setNames(ii + jsonArrays.length() + 1 + "");
                            downBean.setNums1("--");
                            downBean.setNums2("--");
                            downlist.add(downBean);
                        }
                    }
                }
                downAdapter = new ThreeListDownAdapter(getActivity(), downlist);
                listview1.setAdapter(downAdapter);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Ids(int ids, int idsvice) {
        jiage.setText("");
        shuliang.setText("");
        if (UserBean.JiaoYIMM == 1) {

        } else {
            UserBean.LegalIDs = "" + ids;
            UserBean.CurrencyDs = "" + idsvice;
        }
        KLineManager.getInstance().mCurrencyId = String.valueOf(UserBean.CurrencyDs);
        KLineManager.getInstance().mLegalId = String.valueOf(UserBean.LegalIDs);
        if (Hlistbt != null) {
            Hlistbt.clear();
        }
        if (buttonlist != null) {
            buttonlist.clear();
        }
        if (toplist != null) {
            toplist.clear();
        }
        if (downlist != null) {
            downlist.clear();
        }
        //首次数据
        FanYongShow();
        if (boo == true) {//买入
            KeYongMairuShow1();
        } else {
            KeYongMaichuShow1();
        }
    }


    @Override
    public void NamesThree(String Names, String Namesvice) {
        if (UserBean.JiaoYIMM == 1) {

        } else {
            UserBean.LegalNames = "" + Names;
            UserBean.CurrencyNames = "" + Namesvice;
        }
        title1.setText(UserBean.CurrencyNames);
        Log.e("NamesThree", "@@11=" + MyApplication.Authori);
        // if (MyApplication.Authori.equals("")) {
        if (boo == true) {//买入
            Log.e("NamesThree", "@@221=" + UserBean.idstitle);
            denglu.setText("买入" + UserBean.CurrencyNames);
            titlevice.setText(UserBean.LegalNames);
            titlevice1.setText(UserBean.LegalNames);
            keyong.setText(UserBean.idstitle);
            //KeYongMaichuShow();

        } else {//卖出
            Log.e("NamesThree", "@@22122=" + UserBean.idbutown);
            denglu.setText("卖出" + UserBean.CurrencyNames);
            titlevice.setText(UserBean.LegalNames);
            titlevice1.setText(UserBean.CurrencyNames);
            keyong.setText(UserBean.idbutown);
            //KeYongMairuShow();

            //  }
        }
    }


    /**
     * 买入可用
     */
    private void KeYongMairuShow1() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(getActivity()).getService();
        Call<KeYongBean> indexdata = mFromServerInterface.getURL_wallet1("legal", UserBean.LegalIDs + "");
        indexdata.enqueue(new Callback<KeYongBean>() {

            @Override
            public void onResponse(Call<KeYongBean> call, Response<KeYongBean> response) {

                if (response.body() == null) {
                    return;
                }
                if (response.body().getType().equals("ok")) {
                    Log.e("KeYongMaichuShow卖出=", "@@Ids=" + UserBean.LegalIDs + "@@买入可用=" + response.body().getType());
                    UserBean.idstitle = response.body().getMessage().getChange_balance();
                    keyong.setText(UserBean.idstitle);
                }

            }

            @Override
            public void onFailure(Call<KeYongBean> call, Throwable t) {
                Log.e("KeYongMaichuShow买入=", "@@11=" + t.getMessage());
            }
        });
    }

    /**
     * 卖出可用
     */
    private void KeYongMaichuShow1() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(getActivity()).getService();
        Call<KeYongBean> indexdata = mFromServerInterface.getURL_wallet("change", "" + UserBean.CurrencyDs);
        indexdata.enqueue(new Callback<KeYongBean>() {

            @Override
            public void onResponse(Call<KeYongBean> call, Response<KeYongBean> response) {

                if (response.body() == null) {
                    return;
                }
                Log.e("KeYongMaichuShow卖出=", "@@UserBean.CurrencyDs=" + UserBean.CurrencyDs + "@@卖出可用11=" + response.body().getType());
                if (response.body().getType().equals("ok")) {
                    UserBean.idbutown = response.body().getMessage().getChange_balance();
                    Log.e("百分百", "SDD=" + UserBean.idbutown);
                    keyong.setText(UserBean.idbutown);
                }

            }

            @Override
            public void onFailure(Call<KeYongBean> call, Throwable t) {
                Log.e("KeYongMaichuShow卖出=", "@@11=" + t.getMessage());
            }
        });
    }

    private void BaiFen(double n) {
        double shulians = 0;
        shuliang.setHint("数量");
        if (StringUtil.isBlank(Jiages)) {
            Jiages = "0";
        }
        if (LeiXing.equals("xian")) {
            if (boo == true) {
                double keyong = Double.parseDouble(UserBean.idstitle);
                double dajia = Double.parseDouble(Jiages);
                shulians = keyong / dajia;
                if (dajia > 0) {
                    Log.e("百分百", n + "=fdd=" + dajia + "=百分百=" + keyong + "=SDD=" + UserBean.idstitle);
                    shuliang.setText("" + n * shulians);
                }
            } else {
                double keyong = Double.parseDouble(UserBean.idbutown);
                double dajia = Double.parseDouble(Jiages);
                shulians = keyong / dajia;
                if (dajia > 0) {
                    shuliang.setText("" + n * keyong);
                }
            }
        } else {
            if (boo == true) {
                double keyong = Double.parseDouble(UserBean.idbutown);
                double dajia = Double.parseDouble(youchu);
                shulians = keyong / dajia;
                if (dajia > 0) {
                    shuliang.setText("" + n * shulians);
                }
            } else {
                double keyong = Double.parseDouble(UserBean.idstitle);
                double dajia = Double.parseDouble(Jiages);
                shulians = keyong / dajia;
                if (dajia > 0) {
                    shuliang.setText("" + n * keyong);
                }
            }

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("UserBean.poi", UserBean.poi11 + "UserBean.poi=" + UserBean.poi);
    }


    private Emitter.Listener onNewMessage2 = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if (getActivity() == null) {
                return;
            }
            //主线程调用
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {

                        JSONObject objectt = new JSONObject(args[0] + "");
                        int legal_id = objectt.getInt("legal_id");
                        int currency_id = objectt.getInt("currency_id");
                        if (StringUtil.isBlank(UserBean.LegalIDs) || StringUtil.isBlank(UserBean.CurrencyDs)) {

                            return;
                        }
                        if (UserBean.LegalIDs.equals("" + legal_id) & UserBean.CurrencyDs.equals("" + currency_id)) {

                            String Datas = objectt.getString("data");//
                            if (Datas.equals("") | Datas.equals(null)) {
                                return;
                            }
                            JSONArray jsonArray = new JSONArray(Datas);
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject obje = jsonArray.getJSONObject(i);
                                String price = obje.getString("price");
                                double amount = obje.getDouble("amount");
                                String ts = obje.getString("ts");
                                // aaa.setText(price+number+account_number);
                                ThreeListBean buttonBean = new ThreeListBean();
                                String str = StringUtil.date1(ts);
                                buttonBean.setNames(str);
                                buttonBean.setNums1(price);
                                buttonBean.setNums2("" + amount);
                                buttonlist.add(buttonBean);

                            }
                            if (Hlistbt != null) {
                                Hlistbt.clear();
                            }
                            if (biJiaoYiAdapter != null) {
                                biJiaoYiAdapter.notifyDataSetChanged();
                                listv.setVisibility(View.GONE);
                            }
                            if (buttonlist.size() >= 11) {
                                buttonlist.remove(0);
                            }
                            Collections.reverse(buttonlist);
                            listv1.setVisibility(View.VISIBLE);
                            // Log.e("首次数据333", "推=" + args[0]);
                            adapter = new ThreeListAdapter(getActivity(), buttonlist);
                            listv1.setAdapter(adapter);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
}
