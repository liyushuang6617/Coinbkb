package com.sengmei.meililian;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.sengmei.RetrofitHttps.Beans.BJiaoYiBean;
import com.sengmei.RetrofitHttps.Beans.FaBiTiTleBean;
import com.sengmei.RetrofitHttps.Beans.KLineBean;
import com.sengmei.RetrofitHttps.Beans.KTitleBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.kline.KLineActivity;
import com.sengmei.meililian.Bean.DataBean;
import com.sengmei.meililian.Bean.QuanZanBean;
import com.sengmei.meililian.Utils.KTititlesBean;
import com.sengmei.meililian.Utils.RefreshDialog;
import com.sengmei.meililian.Utils.StringUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserBean {

    public static String titleKid="";
    public static String C2CMM="购买";
    public static String C2CFABU="购买";
    public static String Fabitit="购买";
    public static String Fabititsp="购买";
    public static String idstitle;
    public static String idbutown;
    public static String Address="中国大陆 +86";
    public static String LegalIDs;//da
    public static String CurrencyDs;//xia
    public static String LegalNames;//da
    public static String CurrencyNames;//xia
    public static int poi=0;
    public static int poi11=0;
    public static int JiaoYIMM = 2;
    public static double CNY_RETA;
    public static List<KLineBean.ObjectBean> list0 = new ArrayList<>();
    public static List<KLineBean.ObjectBean> list = new ArrayList<>();
    public static List<KLineBean.ObjectBean> list5 = new ArrayList<>();
    public static List<KLineBean.ObjectBean> list30 = new ArrayList<>();
    public static List<KLineBean.ObjectBean> list1h = new ArrayList<>();
    public static List<KLineBean.ObjectBean> list1d = new ArrayList<>();
    public static List<KLineBean.ObjectBean> list1w = new ArrayList<>();
    public static List<KLineBean.ObjectBean> list1m = new ArrayList<>();
    public static JSONArray jsonArray0 = new JSONArray();
    public static JSONArray jsonArray = new JSONArray();
    public static JSONArray jsonArray5 = new JSONArray();
    public static JSONArray jsonArray30 = new JSONArray();
    public static JSONArray jsonArray1h = new JSONArray();
    public static JSONArray jsonArray1d = new JSONArray();
    public static JSONArray jsonArray1w = new JSONArray();
    public static JSONArray jsonArray1m = new JSONArray();
    public static String Tokens = "";
    public static List<FaBiTiTleBean.ObjectBean> object;
    public static List<QuanZanBean.Databean> Hlistbt = new ArrayList();
    public static String Urlio ="http://www.coinbkb.net";// socket
    public static String Url = "http://www.coinbkb.net/api/";//服务器 http://www.coinbkb.net/
    // public static String Urlio ="https://www.beltandroad.io:2220";//1正式socket
    /****************登录**********************/
    public static String URLlogin = Url + "user/login";
    /****************注册**********************/
    public static String URLsms_send = Url + "sms_send";//发送验证码
    public static String URLsms_mail = Url + "sms_mail";//邮箱发送验证码

    public static String URLcheck = Url + "user/check_mobile";//判断验证码
    public static String URLemail = Url + "user/check_email";//判断邮箱验证码
    public static String URLregister = Url + "user/register";//设置密码

    public static String URLnews = Url + "news/list";//首页新闻                  //2018.11.07
    public static String URLcenter = Url + "user/info";
    public static String URLreal_name = Url + "user/real_name";//身份认证
    public static String URLupload = Url + "upload";//上传图片地址
    public static String URLinvite = Url + "user/invite";//返佣
    public static String URLinvite_list = Url + "user/invite_list";//邀请返佣
    public static String URLsafe_center = Url + "safe/safe_center";//安全中心

    public static String URLlist = Url + "wallet/list";//我的资产
    public static String URLcurrencylist = Url + "wallet/currencylist";//提币地址
    public static String URLget_address = Url + "wallet/get_address";//提币地址
    public static String URLaddaddress = Url + "wallet/addaddress";//添加提币地址
    public static String URLget_info = Url + "wallet/get_info";//提币率
    public static String URLget_out = Url + "wallet/out";//提币率提交
    public static String legal_log = Url + "wallet/legal_log";//账户交易

    public static String URLcash_save = Url + "user/cash_save";//个人中心添加修改
    public static String URLuser_deal = Url + "legal_user_deal";//订单详情 与 筛选
    public static String URLsetaccount = Url + "user/setaccount";//订单筛选
    public static String URLquotation = Url + "currency/quotation";//交易
    public static String URLlever = Url + "currency/lever";//杠杆

    public static String URLcomplete = Url + "transaction_complete";//已完成
    public static String URLins = Url + "transaction_in";//正在买
    public static String URLouts = Url + "transaction_out";//正在卖
    public static String URLdels = Url + "transaction_del";
    public static String URL_dels = Url + "transaction/deal";//首次进入


    public static String URLin = Url + "transaction/in";//买入
    public static String URLout = Url + "transaction/out";//卖出
    public static String URLplatformUSDT = Url + "legal_deal_platform?type=sell&page=1&currency_id=12";//USDT ?type=sell&page=1&currency_id=3
    public static String URLplatformETH = Url + "legal_deal_platform?type=sell&page=1&currency_id=4";//ETH
    public static String URLplatformHT = Url + "legal_deal_platform?type=sell&page=1&currency_id=13";//HT
    public static String URLplatformBTC = Url + "legal_deal_platform?type=sell&page=1&currency_id=3";//BTC
    public static String URLplatformBTC1 = Url + "legal_deal_platform";//ETH 3 ,CNY 19,BTC 2,USDT 4

    public static String GongDan = Url + "feedback/list";//我的工单  //没有数据字段有改变内部字段未知 2018.11.07
    public static String addGongDan = Url + "feedback/add";
    public static String URLinfo = Url + "deal/info";//
    public static String URLdetail = Url + "news/detail";//用户协议与政策隐私    //传字段修改  返回结果（未知错误） 2018.11.07
    public static String URLaddress = Url + "wallet/get_in_address";//充值管理 //传字段修改  返回结果（未知错误） 2018.11.07
    public static String fifteen_minutes = Url + "currency/fifteen_minutes?legal_id=4&currency_id=3";//K线图  @{@"legal_id":@"4",@"currency_id":@"3"}
    public static String UTLprivate = Url + "news/private ";

    public static String today_detail = Url + "currency/today_detail";//行情 ；参数legal_id和currency_id

    /**
     * 全站交易
     */
    public static void setHListbt(Context context,final List<QuanZanBean.Databean> news) {
        if (Hlistbt != null) {
            Hlistbt.clear();
        }
        Log.e("AAASDADSAD", "news.size()=" + news.size());
        Hlistbt.addAll(news);
        /*hListAdapter=new HListAdapter(getActivity(),Hlist);
        hlistview.setAdapter(hListAdapter);*/
    }

    //全站交易
    public static void FanYongShow(final Context context, String currency_id, String legal_id) {

    }

    //获取K线头部信息
    public static void HuoQuShow1(final Context context, String currency_id, String legal_id) {

        DataBean.id1=currency_id;
        DataBean.id2=legal_id;
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(context).getService();
        Call<com.alibaba.fastjson.JSONObject> indexdata = mFromServerInterface.getKdetail1(currency_id, legal_id);
        indexdata.enqueue(new Callback<com.alibaba.fastjson.JSONObject>() {

            @Override
            public void onResponse(Call<com.alibaba.fastjson.JSONObject> call, Response<com.alibaba.fastjson.JSONObject> response) {
                if (response.body() == null) {
                    return;
                }

                Log.e("获取K线头部信息", response.body().toString());
            }

            @Override
            public void onFailure(Call<com.alibaba.fastjson.JSONObject> call, Throwable t) {
                Log.e("HuoQuShow", "@@11=" + t.getMessage());
            }
        });
    }
    public static void HuoQuShow(final Context context, String currency_id, String legal_id) {

        DataBean.id1=currency_id;
        DataBean.id2=legal_id;
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(context).getService();
        Call<KTitleBean> indexdata = mFromServerInterface.getKdetail(currency_id, legal_id);
        indexdata.enqueue(new Callback<KTitleBean>() {

            @Override
            public void onResponse(Call<KTitleBean> call, Response<KTitleBean> response) {
                if (response.body() == null) {
                    return;
                }

                Log.e("获取K线头部信息", response.body().getType());

                if (StringUtil.isBlank(response.body().getMessage().getCny_rate())){
                    CNY_RETA=StringUtil.strToDouble("0");
                }else {
                    CNY_RETA=StringUtil.strToDouble(response.body().getMessage().getCny_rate());
                }
                String Highs = response.body().getMessage().getHigh();
                if (StringUtil.isBlank(Highs)){
                    KTititlesBean.Highs="0.0";
                }else {
                    KTititlesBean.Highs=Highs;
                }
                String Volumes = response.body().getMessage().getVolume();
                if (StringUtil.isBlank(Highs)){
                    KTititlesBean.Volumes="0.0";
                }else {
                    KTititlesBean.Volumes=Volumes;
                }
                String Lows = response.body().getMessage().getLow();
                if (StringUtil.isBlank(Highs)){
                    KTititlesBean.Lows="0.0";
                }else {
                    KTititlesBean.Lows=Lows;
                }
                String Now_priceS = response.body().getMessage().getNow_price();
                if (StringUtil.isBlank(Highs)){
                    KTititlesBean.Now_priceS="0.00";
                }else {
                    KTititlesBean.Now_priceS=Now_priceS;
                }
                String str=response.body().getMessage().getChange();
                String Now_cny_price=response.body().getMessage().getNow_cny_price();
                if (StringUtil.isBlank(str)){
                    str="0";

                    double aA = Double.parseDouble(str);
                    String ss=String.format("%.2f",aA);//保留小数
                    KTititlesBean.Changes =ss + "%";
                }else {
                    double aA = Double.parseDouble(response.body().getMessage().getChange());
                    String ss=String.format("%.2f",aA);//保留小数
                    KTititlesBean.Changes =ss + "%";
                }
                if (StringUtil.isBlank(Now_cny_price)){
                    Now_cny_price="0";
                    KTititlesBean.CNYChanges = "≈"+Now_cny_price+" CNY";
                }else {
                    Now_cny_price =response.body().getMessage().getNow_cny_price();
                    KTititlesBean.CNYChanges =  "≈"+Now_cny_price+" CNY";
                }

                if (response.body().getType().equals("ok")) {
                    Log.e("HuoQuShow", response + "#11#" + response.body().getMessage().getHigh());

                }

            }

            @Override
            public void onFailure(Call<KTitleBean> call, Throwable t) {
                Log.e("HuoQuShow", "@@11=" + t.getMessage());
            }
        });
    }

    //获取K 0分线信息
    public static void KLineShow0(final Context context, String s1) {
        DataBean.titles=s1;
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(context).getService();
        Call<KLineBean> indexdata = mFromServerInterface.getK(s1, "1min");
        indexdata.enqueue(new Callback<KLineBean>() {

            @Override
            public void onResponse(Call<KLineBean> call, Response<KLineBean> response) {
                if (response.body() == null) {
                    return;
                }
                list0 = response.body().getData();

                jsonArray0 = null;
                jsonArray0 = new JSONArray();
                for (int i = 0; i < list0.size(); i++) {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("Close", list0.get(i).getClose());
                        jsonObject.put("Date", StringUtil.date(list0.get(i).getTime()));
                        jsonObject.put("Time", list0.get(i).getTime());
                        jsonObject.put("High", list0.get(i).getHigh());
                        jsonObject.put("Low", list0.get(i).getLow());
                        jsonObject.put("Open", list0.get(i).getOpen());
                        jsonObject.put("Volume", list0.get(i).getVolume());
                        jsonArray0.put(jsonObject);
                        jsonObject = null;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Log.e("jsonArray", "jsonArray=: " + jsonArray);
            }
            @Override
            public void onFailure(Call<KLineBean> call, Throwable t) {
                Log.e("HuoQuShow", "@@11=" + t.getMessage());
            }
        });
    }
    public static void KLineShow01(final Context context, String s1) {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(context).getService();
        Call<KLineBean> indexdata = mFromServerInterface.getK(s1, "1min");
        indexdata.enqueue(new Callback<KLineBean>() {

            @Override
            public void onResponse(Call<KLineBean> call, Response<KLineBean> response) {
                if (response.body() == null) {
                    return;
                }
                list0 = response.body().getData();
                jsonArray0= null;
                jsonArray0 = new JSONArray();
                for (int i = 0; i < list0.size(); i++) {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("Close", list0.get(i).getClose());
                        jsonObject.put("Date", StringUtil.date(list0.get(i).getTime()));
                        jsonObject.put("Time", list0.get(i).getTime());
                        jsonObject.put("High", list0.get(i).getHigh());
                        jsonObject.put("Low", list0.get(i).getLow());
                        jsonObject.put("Open", list0.get(i).getOpen());
                        jsonObject.put("Volume", list0.get(i).getVolume());
                        jsonArray0.put(jsonObject);
                        jsonObject = null;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Log.e("jsonArray", "jsonArray=: " + jsonArray);
            }
            @Override
            public void onFailure(Call<KLineBean> call, Throwable t) {
                Log.e("HuoQuShow", "@@11=" + t.getMessage());
            }
        });
    }

    /*//获取K1分线信息
    public static void KLineShow(final Context context, String s1) {

        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(context).getService();
        Call<KLineBean> indexdata = mFromServerInterface.getK(s1, "1min");
        indexdata.enqueue(new Callback<KLineBean>() {

            @Override
            public void onResponse(Call<KLineBean> call, Response<KLineBean> response) {

                if (response.body() == null) {
                    JSONObject jsonObject = new JSONObject();
                    jsonArray.put(jsonObject);
                    jsonObject = null;
                    return;
                }
                list = response.body().getData();
                jsonArray= null;
                jsonArray = new JSONArray();

                for (int i = 0; i < list.size(); i++) {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("Close", list.get(i).getClose());
                        jsonObject.put("Date", StringUtil.date(list.get(i).getTime()));
                        jsonObject.put("Time", list.get(i).getTime());
                        jsonObject.put("High", list.get(i).getHigh());
                        jsonObject.put("Low", list.get(i).getLow());
                        jsonObject.put("Open", list.get(i).getOpen());
                        jsonObject.put("Volume", list.get(i).getVolume());
                        jsonArray.put(jsonObject);
                        Log.e("jsonArray@#", "jsonArray=: " + StringUtil.date(list.get(i).getTime()));
                        jsonObject = null;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Log.e("jsonArray", "jsonArray=: " + jsonArray);
            }
            @Override
            public void onFailure(Call<KLineBean> call, Throwable t) {
                JSONObject jsonObject = new JSONObject();
                jsonArray.put(jsonObject);
                jsonObject = null;
            }
        });
    }
*/
    //获取K5fen线信息
    public static void KLineShow5(final Context context, String s1) {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(context).getService();
        Call<KLineBean> indexdata = mFromServerInterface.getK(s1, "5min");
        indexdata.enqueue(new Callback<KLineBean>() {

            @Override
            public void onResponse(Call<KLineBean> call, Response<KLineBean> response) {
                if (response.body() == null) {
                    return;
                }
                list5 = response.body().getData();
                jsonArray5 = null;
                jsonArray5 = new JSONArray();

                for (int i = 0; i < list5.size(); i++) {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("Close", list5.get(i).getClose());
                        jsonObject.put("Date", StringUtil.date(list5.get(i).getTime()));
                        jsonObject.put("Time", list5.get(i).getTime());
                        jsonObject.put("High", list5.get(i).getHigh());
                        jsonObject.put("Low", list5.get(i).getLow());
                        jsonObject.put("Open", list5.get(i).getOpen());
                        jsonObject.put("Volume", list5.get(i).getVolume());
                        jsonArray5.put(jsonObject);
                        jsonObject = null;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Log.e("jsonArra5", "jsonArray5=: " + jsonArray5);
            }
            @Override
            public void onFailure(Call<KLineBean> call, Throwable t) {
                Log.e("HuoQuShow", "@@11=" + t.getMessage());
            }
        });
    }
    //获取K 60min线信息
    public static void KLineShow30(final Context context, String s1) {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(context).getService();
        Call<KLineBean> indexdata = mFromServerInterface.getK(s1,"30min");
        indexdata.enqueue(new Callback<KLineBean>() {

            @Override
            public void onResponse(Call<KLineBean> call, Response<KLineBean> response) {
                if (response.body() == null) {
                    return;
                }
                list30 = response.body().getData();
                jsonArray30 = null;
                jsonArray30 = new JSONArray();

                for (int i = 0; i < list30.size(); i++) {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("Close", list30.get(i).getClose());
                        jsonObject.put("Date", StringUtil.date(list30.get(i).getTime()));
                        jsonObject.put("Time", list30.get(i).getTime());
                        jsonObject.put("High", list30.get(i).getHigh());
                        jsonObject.put("Low", list30.get(i).getLow());
                        jsonObject.put("Open", list30.get(i).getOpen());
                        jsonObject.put("Volume", list30.get(i).getVolume());
                        jsonArray30.put(jsonObject);
                        jsonObject = null;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Log.e("jsonArra5", "jsonArray5=: " + jsonArray30);
            }
            @Override
            public void onFailure(Call<KLineBean> call, Throwable t) {
                Log.e("HuoQuShow", "@@11=" + t.getMessage());
            }
        });
    }
    //获取K 1day线信息
    public static void KLineShow1h(final Context context, String s1) {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(context).getService();
        Call<KLineBean> indexdata = mFromServerInterface.getK(s1, "60min");
        indexdata.enqueue(new Callback<KLineBean>() {

            @Override
            public void onResponse(Call<KLineBean> call, Response<KLineBean> response) {
                if (response.body() == null) {
                    return;
                }
                list1h = response.body().getData();
                jsonArray1h = null;
                jsonArray1h = new JSONArray();

                for (int i = 0; i < list1h.size(); i++) {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("Close", list1h.get(i).getClose());
                        jsonObject.put("Date", StringUtil.date(list1h.get(i).getTime()));
                        jsonObject.put("Time", list1h.get(i).getTime());
                        jsonObject.put("High", list1h.get(i).getHigh());
                        jsonObject.put("Low", list1h.get(i).getLow());
                        jsonObject.put("Open", list1h.get(i).getOpen());
                        jsonObject.put("Volume", list1h.get(i).getVolume());
                        jsonArray1h.put(jsonObject);
                        jsonObject = null;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Log.e("jsonArra5", "jsonArray5=: " + jsonArray1h);
            }
            @Override
            public void onFailure(Call<KLineBean> call, Throwable t) {
                Log.e("HuoQuShow", "@@11=" + t.getMessage());
            }
        });
    }
    //获取K 1week线信息
    public static void KLineShow1d(final Context context, String s1) {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(context).getService();
        Call<KLineBean> indexdata = mFromServerInterface.getK(s1, "1day");
        indexdata.enqueue(new Callback<KLineBean>() {

            @Override
            public void onResponse(Call<KLineBean> call, Response<KLineBean> response) {
                if (response.body() == null) {
                    return;
                }
                list1d = response.body().getData();
                jsonArray1d = null;
                jsonArray1d = new JSONArray();

                for (int i = 0; i < list1d.size(); i++) {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("Close", list1d.get(i).getClose());
                        jsonObject.put("Date", StringUtil.date(list1d.get(i).getTime()));
                        jsonObject.put("Time", list1d.get(i).getTime());
                        jsonObject.put("High", list1d.get(i).getHigh());
                        jsonObject.put("Low", list1d.get(i).getLow());
                        jsonObject.put("Open", list1d.get(i).getOpen());
                        jsonObject.put("Volume", list1d.get(i).getVolume());
                        jsonArray1d.put(jsonObject);
                        jsonObject = null;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Log.e("jsonArra5", "jsonArray5=: " + jsonArray1d);
            }
            @Override
            public void onFailure(Call<KLineBean> call, Throwable t) {
                Log.e("HuoQuShow", "@@11=" + t.getMessage());
            }
        });
    }
    //获取K 月线信息
    public static void KLineShow1w(final Context context, String s1) {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(context).getService();
        Call<KLineBean> indexdata = mFromServerInterface.getK(s1, "1week");
        indexdata.enqueue(new Callback<KLineBean>() {

            @Override
            public void onResponse(Call<KLineBean> call, Response<KLineBean> response) {
                if (response.body() == null) {
                    return;
                }
                list1w = response.body().getData();
                jsonArray1w = null;
                jsonArray1w = new JSONArray();

                for (int i = 0; i < list1w.size(); i++) {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("Close", list1w.get(i).getClose());
                        jsonObject.put("Date", StringUtil.date(list1w.get(i).getTime()));
                        jsonObject.put("Time", list1w.get(i).getTime());
                        jsonObject.put("High", list1w.get(i).getHigh());
                        jsonObject.put("Low", list1w.get(i).getLow());
                        jsonObject.put("Open", list1w.get(i).getOpen());
                        jsonObject.put("Volume", list1w.get(i).getVolume());
                        jsonArray1w.put(jsonObject);
                        jsonObject = null;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Log.e("jsonArra5", "jsonArray5=: " + jsonArray1m);
            }
            @Override
            public void onFailure(Call<KLineBean> call, Throwable t) {
                Log.e("HuoQuShow", "@@11=" + t.getMessage());
            }
        });
    }
    //获取K 月线信息
    public static void KLineShow1m(final Context context, String s1) {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(context).getService();
        Call<KLineBean> indexdata = mFromServerInterface.getK(s1, "1mon");
        indexdata.enqueue(new Callback<KLineBean>() {

            @Override
            public void onResponse(Call<KLineBean> call, Response<KLineBean> response) {
                if (response.body() == null) {
                    return;
                }
                list1m = response.body().getData();
                jsonArray1m = null;
                jsonArray1m = new JSONArray();

                for (int i = 0; i < list1m.size(); i++) {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("Close", list1m.get(i).getClose());
                        jsonObject.put("Date", StringUtil.date(list1m.get(i).getTime()));
                        jsonObject.put("Time", list1m.get(i).getTime());
                        jsonObject.put("High", list1m.get(i).getHigh());
                        jsonObject.put("Low", list1m.get(i).getLow());
                        jsonObject.put("Open", list1m.get(i).getOpen());
                        jsonObject.put("Volume",list1m.get(i).getVolume());
                        jsonArray1m.put(jsonObject);
                        jsonObject = null;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Log.e("jsonArra5", "jsonArray5=: " + jsonArray1m);
            }
            @Override
            public void onFailure(Call<KLineBean> call, Throwable t) {
                Log.e("HuoQuShow", "@@11=" + t.getMessage());
            }
        });
    }

    public static void loadKLine1minData(final Context context, String symbol, final OnLoadKLineDataCallback callback) {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(context).getService();
        Call<KLineBean> indexdata = mFromServerInterface.getK(symbol, "1min");
        indexdata.enqueue(new Callback<KLineBean>() {

            @Override
            public void onResponse(Call<KLineBean> call, Response<KLineBean> response) {

                if (response.body() == null) {
                    JSONObject jsonObject = new JSONObject();
                    jsonArray.put(jsonObject);
                    jsonObject = null;
                    return;
                }
                list = response.body().getData();
                jsonArray = null;
                jsonArray = new JSONArray();

                for (int i = 0; i < list.size(); i++) {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("Close", list.get(i).getClose());
                        jsonObject.put("Date", StringUtil.date(list.get(i).getTime()));
                        jsonObject.put("Time", list.get(i).getTime());
                        jsonObject.put("High", list.get(i).getHigh());
                        jsonObject.put("Low", list.get(i).getLow());
                        jsonObject.put("Open", list.get(i).getOpen());
                        jsonObject.put("Volume", list.get(i).getVolume());
                        jsonArray.put(jsonObject);
                        Log.e("jsonArray@#", "jsonArray=: " + StringUtil.date(list.get(i).getTime()));
                        jsonObject = null;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

//                context.startActivity(new Intent(context, KLineActivity.class));
                Log.e("jsonArray", "jsonArray=: " + jsonArray);

                if (callback != null) {
                    callback.onFinish();
                }
            }

            @Override
            public void onFailure(Call<KLineBean> call, Throwable t) {
                JSONObject jsonObject = new JSONObject();
                jsonArray.put(jsonObject);
                jsonObject = null;
                if (callback != null) {
                    callback.onFinish();
                }
            }
        });
    }

    //获取K1分线信息
    public static void KLineShow(Context context, String s1) {
        loadKLine1minData(context, s1, null);
    }

    public interface OnLoadKLineDataCallback {

        void onFinish();

    }
}
