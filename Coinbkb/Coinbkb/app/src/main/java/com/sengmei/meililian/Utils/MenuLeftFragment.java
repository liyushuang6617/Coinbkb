package com.sengmei.meililian.Utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.sengmei.RetrofitHttps.Adapter.HangQingTitleAdapter;
import com.sengmei.RetrofitHttps.Beans.HangQingBean;
import com.sengmei.RetrofitHttps.Beans.KeYongBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.kline.R;
import com.sengmei.meililian.Activity.LoginActivity;
import com.sengmei.meililian.Adapter.Fragment1TwoAdapter;
import com.sengmei.meililian.Adapter.FragmentTwoAdapter;
import com.sengmei.meililian.Adapter.MenuListAdapter;
import com.sengmei.meililian.Bean.DataBean;
import com.sengmei.meililian.Bean.MenuBean;
import com.sengmei.meililian.Bean.MenuListBean;
import com.sengmei.meililian.Bean.MenuTitleBean;
import com.sengmei.meililian.MyApplication;
import com.sengmei.meililian.UserBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuLeftFragment extends Fragment implements View.OnClickListener{

    private View view;
    private List<HangQingBean.MessageBean> list1=new ArrayList<>();
    private List<HangQingBean.quotationBean> hlist = new ArrayList<>();
    private int mtit=0;
    private ListView listView;

    private HorizontalListView hlistview;
    public static MenuBack menuBack;
    public static MenuBackTree menuBackTree;
    private HangQingTitleAdapter hListAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.menuleftfragment, container, false);
        init(view);
        HlistTitleShow();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        HlistTitleShow();
    }

    public static void setMenuBack(MenuBack menuBack1) {
        menuBack = menuBack1;
    }

    public static void setMenuBackTree(MenuBackTree menuBackTree1) {
        menuBackTree = menuBackTree1;
    }

    private void init(View view){

        hlistview = (HorizontalListView) view.findViewById(R.id.hlistview);
        listView=view.findViewById(R.id.listview);
    }

    @Override
    public void onClick(View v) {


    }

    private void setHList(final List<HangQingBean.MessageBean> news) {
        if (list1 != null) {
            list1.clear();
        }
        list1.addAll(news);
        hListAdapter = new HangQingTitleAdapter(getActivity(), list1);
        hlistview.setAdapter(hListAdapter);

        MenuTitleBean.name=list1.get(UserBean.poi).getName();
        MenuTitleBean.Ids=list1.get(UserBean.poi).getId();
        KeYongMairuShow(list1.get(UserBean.poi).getQuotation().get(UserBean.poi11).getLegal_id());
        KeYongMaichuShow(list1.get(UserBean.poi).getQuotation().get(UserBean.poi11).getCurrency_id());
        setList(list1.get(UserBean.poi).getQuotation());
        hListAdapter.setSelectedPosition(UserBean.poi);
        hListAdapter.notifyDataSetInvalidated();
        menuBackTree.NamesThree(list1.get(UserBean.poi).getQuotation().get(UserBean.poi11).getLegal_name(),list1.get(UserBean.poi).getQuotation().get(UserBean.poi11).getCurrency_name());
        menuBackTree.Ids(StringUtil.strToInt(list1.get(UserBean.poi).getQuotation().get(UserBean.poi11).getLegal_id()),StringUtil.strToInt(list1.get(UserBean.poi).getQuotation().get(UserBean.poi11).getCurrency_id()));
        menuBack.Names(list1.get(UserBean.poi).getQuotation().get(UserBean.poi11).getCurrency_name()+"/"+list1.get(UserBean.poi).getQuotation().get(UserBean.poi11).getLegal_name());

        hlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setList(list1.get(position).getQuotation());
                UserBean.poi11=0;
                UserBean.poi = position;
                Log.e("UserBean.poi","UserBean.poi11="+UserBean.poi);
                hListAdapter.setSelectedPosition(position);
                hListAdapter.notifyDataSetInvalidated();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("UserBean.poi",UserBean.poi11+"UserBean.poi12="+UserBean.poi);
    }

    //行情头部横向标题
    private void HlistTitleShow() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(getActivity()).getService();
        Call<HangQingBean> indexdata = mFromServerInterface.getquotation();
        indexdata.enqueue(new Callback<HangQingBean>() {

            @Override
            public void onResponse(Call<HangQingBean> call, Response<HangQingBean> response) {
                if (response.body() == null) {
                    return;
                }
                Log.e("KKKKK", "@@1111=" + response.body().getType());
                if (response.body().getType().equals("ok")) {
                    setHList(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<HangQingBean> call, Throwable t) {
            }
        });
    }
    //填充数据 list数据
    private void setList(final List<HangQingBean.quotationBean> news) {
        if (hlist != null) {
            hlist.clear();
        }
        hlist.addAll(news);
        Fragment1TwoAdapter adapter = new Fragment1TwoAdapter(getActivity(), hlist);
        listView.setAdapter(adapter);
        Log.e("UserBean.poi",UserBean.poi11+"UserBean.11poi12="+UserBean.poi);
        MenuTitleBean.namevice=hlist.get(UserBean.poi11).getCurrency_name();
        MenuTitleBean.Ids_namevice=StringUtil.strToInt(hlist.get(UserBean.poi11).getCurrency_id());


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.e("UserBean.poi",UserBean.poi11+"UserBean.11poi12="+UserBean.poi);
                UserBean.poi11=position;

                UserBean.JiaoYIMM=2;
                UserBean.LegalIDs=hlist.get(UserBean.poi11).getLegal_id();
                UserBean.CurrencyDs=hlist.get(UserBean.poi11).getCurrency_id();
                UserBean.LegalNames=hlist.get(UserBean.poi11).getLegal_name();
                UserBean.CurrencyNames=hlist.get(UserBean.poi11).getCurrency_name();

                KTititlesBean.Titles = UserBean.CurrencyNames+ "/" + UserBean.LegalNames;

                KeYongMairuShow(hlist.get(UserBean.poi11).getLegal_id());
                KeYongMaichuShow(hlist.get(UserBean.poi11).getCurrency_id());
                menuBackTree.NamesThree(UserBean.LegalNames,UserBean.CurrencyNames);
                menuBackTree.Ids(StringUtil.strToInt(UserBean.LegalIDs),StringUtil.strToInt(UserBean.CurrencyDs));
                menuBack.Names(UserBean.CurrencyNames+"/"+UserBean.LegalNames);
                menuBack.IsClick(false);
                Log.e("UserBean.poi",UserBean.poi11+"UserBean.11poi12="+UserBean.poi);
            }
        });

    }
    /**
     * 买入可用
     */
    private void KeYongMairuShow(final String id) {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(getActivity()).getService();
        Call<KeYongBean> indexdata = mFromServerInterface.getURL_wallet1("legal",id);
        indexdata.enqueue(new Callback<KeYongBean>() {

            @Override
            public void onResponse(Call<KeYongBean> call, Response<KeYongBean> response) {

                if (response.body() == null) {
                    return;
                }

                if (response.body().getType().equals("ok")) {
                    if (StringUtil.isBlank(response.body().getMessage().getChange_balance())){
                        UserBean.idstitle="0";
                    }else {
                        UserBean.idstitle=response.body().getMessage().getChange_balance();
                    }
                }

            }

            @Override
            public void onFailure(Call<KeYongBean> call, Throwable t) {
                Log.e("KeYongMaichuShow买入=" , "@@11=" + t.getMessage());
                UserBean.idstitle="0";
            }
        });
    }
    /**
     * 卖出可用
     */
    private void KeYongMaichuShow(final String id) {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(getActivity()).getService();
        Call<KeYongBean> indexdata = mFromServerInterface.getURL_wallet("change",id);
        indexdata.enqueue(new Callback<KeYongBean>() {

            @Override
            public void onResponse(Call<KeYongBean> call, Response<KeYongBean> response) {

                if (response.body() == null) {
                    return;
                }

                Log.e("KeYongMaichuShow卖出=", id+"@@卖出11=" + response.body().getType());
                if (response.body().getType().equals("ok")) {
                    Log.e("百分百","SDD12="+UserBean.idbutown);
                    if (StringUtil.isBlank(response.body().getMessage().getChange_balance())){
                        UserBean.idbutown="0";
                    }else {
                        UserBean.idbutown = response.body().getMessage().getChange_balance();
                    }

                }

            }

            @Override
            public void onFailure(Call<KeYongBean> call, Throwable t) {
                Log.e("KeYongMaichuShow卖出=", "@@11=" + t.getMessage());
                UserBean.idbutown="0";
            }
        });
    }
}

