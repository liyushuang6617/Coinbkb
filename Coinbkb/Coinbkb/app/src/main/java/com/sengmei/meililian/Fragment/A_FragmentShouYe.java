package com.sengmei.meililian.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lidroid.xutils.util.LogUtils;
import com.sengmei.MyService;
import com.sengmei.RetrofitHttps.Adapter.FaBiTitleAdapter;
import com.sengmei.RetrofitHttps.Adapter.FragmentZFAdapter;
import com.sengmei.RetrofitHttps.Beans.FaBiTiTleBean;
import com.sengmei.RetrofitHttps.Beans.FaBiTiTledizhiBean;
import com.sengmei.RetrofitHttps.Beans.HangQingBean;
import com.sengmei.RetrofitHttps.Beans.LunBoBean;
import com.sengmei.RetrofitHttps.Beans.LunBoTopBean;
import com.sengmei.RetrofitHttps.Beans.ZhangFuBean;
import com.sengmei.RetrofitHttps.Beans.ZiChanBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.RetrofitHttps.NetUtils;
import com.sengmei.kline.KLineActivity;
import com.sengmei.kline.KLineManager;
import com.sengmei.kline.R;
import com.sengmei.meililian.Activity.E_AnQuanZhongXin;
import com.sengmei.meililian.Activity.E_TiBiDiZhi;
import com.sengmei.meililian.Activity.E_WodeZiChan;
import com.sengmei.meililian.Activity.GuangGaoActivity;
import com.sengmei.meililian.Activity.JiaoYiFaBi;
import com.sengmei.meililian.Activity.LoginActivity;
import com.sengmei.meililian.Adapter.HListAdapter;
import com.sengmei.meililian.Adapter.WebBannerAdapter;
import com.sengmei.meililian.Bean.DataBean;
import com.sengmei.meililian.Bean.FragmentTwoListBean;
import com.sengmei.meililian.Bean.HListBean;
import com.sengmei.meililian.MainActivity;
import com.sengmei.meililian.MyApplication;
import com.sengmei.meililian.UserBean;
import com.sengmei.meililian.Utils.HorizontalListView;
import com.sengmei.meililian.Utils.HttpUtils;
import com.sengmei.meililian.Utils.KTititlesBean;
import com.sengmei.meililian.Utils.MyListView;
import com.sengmei.meililian.Utils.RefreshDialog;
import com.sengmei.meililian.Utils.StringUtil;
import com.sengmei.meililian.banner.BannerLayout;
import com.sunfusheng.marqueeview.MarqueeView;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class A_FragmentShouYe extends Fragment implements View.OnClickListener {
    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 1500;
    private static long lastClickTime;

    private View view;
    private MyListView listview;
    private HorizontalListView hlistview;
    private HListAdapter hListAdapter;
    private List<HListBean> list=new ArrayList<>();
    private List<HangQingBean.quotationBean> Hlist=new ArrayList<>();

    private TextView login,jiaoyi,jiaoyi1,fabi,fabi1;
    private LinearLayout login_ll,login_l,queeView;

    private MarqueeView marqueeView;
    private List<String> info = new ArrayList<>();
    private Map<String,String> map=new HashMap<>();
    private BannerLayout recyclerBanner;
    public static String Datas;
    final List<LunBoTopBean.Bean> hlist=new ArrayList<>();
    final List<ZhangFuBean.dataBean> zflist=new ArrayList<>();
    private RefreshDialog dialog ;
    public static String CNY;
    private SwipeRefreshLayout refreshLayout;
    private Banner banner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.a_fragmentone, null);
        dialog = new RefreshDialog(getActivity());
        initView(view);
        if (NetUtils.isConnected(getContext())) {
            addView();//横向listview
            TiBiListShow();//涨幅榜
            NewListShow();//公告
            NewListShow1();//轮播
            NewListShow2();//我的资产
        } else {
            StringUtil.ToastShow(getActivity(), "网络未连接");
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        addView();
        TiBiListShow();
        NewListShow();
        NewListShow2();
        marqueeView.startFlipping();
        Log.e("AAAAA11", "@@11=" + MyApplication.Authori);
        if (MyApplication.Authori.equals("")|StringUtil.isBlank(MyApplication.Authori)){
            Log.e("AAAAA11", "@@112=" + MyApplication.Authori);
            login_ll.setVisibility(View.GONE);
            login_l.setVisibility(View.GONE);
        }else {
            Log.e("AAAAA11", "@@113=" + MyApplication.Authori);
            login_ll.setVisibility(View.GONE);
            login_l.setVisibility(View.GONE);
        }
        /*if (DataBean.Types.equals("ok")){
            login_ll.setVisibility(View.GONE);
            login_l.setVisibility(View.VISIBLE);
        }else {
            login_ll.setVisibility(View.VISIBLE);
            login_l.setVisibility(View.GONE);
        }*/
    }

    private void initView(View view) {
        banner = (Banner) view.findViewById(R.id.banner);
         listview = (MyListView) view.findViewById(R.id.listview);
        hlistview = (HorizontalListView) view.findViewById(R.id.hlistview);
        login_ll = (LinearLayout) view.findViewById(R.id.login_ll);
        login_l = (LinearLayout) view.findViewById(R.id.login_l);
        queeView = (LinearLayout) view.findViewById(R.id.queeView);
        marqueeView = (MarqueeView) view.findViewById(R.id.marqueeView);
        marqueeView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                if (StringUtil.isBlank(map.get(info.get(marqueeView.getPosition())))){
                    return;
                }
                dialog.showLoading();
                startActivity(new Intent(getActivity(),GuangGaoActivity.class).putExtra("titles","gonggao")
                        .putExtra("Ids",(String) map.get(info.get(marqueeView.getPosition()))));
            }
        });
        view.findViewById(R.id.fabijiaoyi).setOnClickListener(this);
        login=view.findViewById(R.id.login);
        login.setOnClickListener(this);
        view.findViewById(R.id.zichan).setOnClickListener(this);
        view.findViewById(R.id.jiaoyill).setOnClickListener(this);
        view.findViewById(R.id.fdabill).setOnClickListener(this);
        view.findViewById(R.id.anquan).setOnClickListener(this);
        view.findViewById(R.id.hangqing).setOnClickListener(this);
        view.findViewById(R.id.wdzc).setOnClickListener(this);
         recyclerBanner =  view.findViewById(R.id.recycler);
        jiaoyi = (TextView) view.findViewById(R.id.jiaoyi);
        jiaoyi1 = (TextView) view.findViewById(R.id.jiaoyi1);
        fabi = (TextView) view.findViewById(R.id.fabi);
        fabi1 = (TextView) view.findViewById(R.id.fabi1);
        hlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //startActivity(new Intent(getActivity(),KLineActivity.class));
                dialog.showLoading();
                if (MyApplication.Authori.equals("")){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                long curClickTime = System.currentTimeMillis();
                if((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
                    // 超过点击间隔后再将lastClickTime重置为当前点击时间
                    lastClickTime = curClickTime;

                    UserBean.LegalIDs=Hlist.get(position).getLegal_id();
                    UserBean.CurrencyDs=Hlist.get(position).getCurrency_id();
                    UserBean.LegalNames= Hlist.get(position).getLegal_name();
                    UserBean.CurrencyNames=Hlist.get(position).getCurrency_name();

                    KLineManager.getInstance().mCurrencyId = String.valueOf( UserBean.CurrencyDs);
                    KLineManager.getInstance().mLegalId = String.valueOf(UserBean.LegalIDs);
                    KLineManager.getInstance().mSymbolName = UserBean.CurrencyNames+ "/" + UserBean.LegalNames;
                    KTititlesBean.Titles = UserBean.CurrencyNames+ "/" + UserBean.LegalNames;
                    UserBean.HuoQuShow(getActivity(), UserBean.CurrencyDs,UserBean.LegalIDs);
                    UserBean.FanYongShow(getActivity(),UserBean.CurrencyDs,UserBean.LegalIDs);
                    KShow(UserBean.CurrencyNames + "/" + UserBean.LegalNames);
                    KLineActivity.open(getActivity());
                }
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
                                addView();
                                TiBiListShow();
                                NewListShow();
                                NewListShow1();
                                NewListShow2();
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

    private void KShow(String title) {
        UserBean.titleKid = title;
        UserBean.KLineShow0(getActivity(), title);
        // UserBean.KLineShow(getActivity(), title);
        UserBean.KLineShow5(getActivity(), title);
        UserBean.KLineShow30(getActivity(), title);
        UserBean.KLineShow1h(getActivity(), title);
        UserBean.KLineShow1d(getActivity(), title);
        UserBean.KLineShow1w(getActivity(), title);
        UserBean.KLineShow1m(getActivity(), title);
        //startActivity(new Intent(getContext(), KLineActivity.class));
    }

    /**
     * 横向listview 交易
     */
    private void setHList(final List<HangQingBean.quotationBean> news) {
        if (Hlist != null) {
            Hlist.clear();
        }
        Hlist.addAll(news);
        hListAdapter=new HListAdapter(getActivity(),Hlist);
        hlistview.setAdapter(hListAdapter);
    }
    private void addView() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(getActivity()).getService();
        Call<HangQingBean> indexdata = mFromServerInterface.getquotation();
        indexdata.enqueue(new Callback<HangQingBean>() {

            @Override
            public void onResponse(Call<HangQingBean> call, Response<HangQingBean> response) {
                if (response.body() == null) {

                    return;
                }Log.e("AAAAA", "@@11=" + response.body().getType());
                if (response.body().getType().equals("ok")) {
                    if (response.body().getMessage().size()==0){
                        return;
                    }
                    setHList(response.body().getMessage().get(0).getQuotation());
                    CNY=response.body().getMessage().get(0).getCny_rate();
                }
            }

            @Override
            public void onFailure(Call<HangQingBean> call, Throwable t) {
                Log.e("AAAAA", "@@11=" + t.getMessage());

            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fabijiaoyi:
                if (MyApplication.Authori.equals("")){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getActivity(), JiaoYiFaBi.class));
                break;
            case R.id.login:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.zichan:
                if (MyApplication.Authori.equals("")){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getActivity(), E_WodeZiChan.class));
                break;
            case R.id.jiaoyill:
                if (MyApplication.Authori.equals("")){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getActivity(), E_WodeZiChan.class));
                break;
            case R.id.fdabill:
                if (MyApplication.Authori.equals("")){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getActivity(), E_WodeZiChan.class));
                break;
            case R.id.anquan:
                if (MyApplication.Authori.equals("")){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getActivity(), E_AnQuanZhongXin.class));
                break;
            case R.id.hangqing:
                startActivity(new Intent(getActivity(), MainActivity.class));
                DataBean.MunuS=3;
                break;
            case R.id.wdzc:
                if (MyApplication.Authori.equals("")){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getActivity(), E_WodeZiChan.class));
                break;
                default:
                    break;

        }
    }
/*首页下的工告*/
    private void NewListShow() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(getActivity()).getService();
        Call<LunBoBean> indexdata = mFromServerInterface.getnews("21");
        indexdata.enqueue(new Callback<LunBoBean>() {

            @Override
            public void onResponse(Call<LunBoBean> call, Response<LunBoBean> response) {
                if (response.body() == null) {

                    return;
                }Log.e("AAAAA", "广告id=2=" + response.body().getType());
                if (response.body().getType().equals("ok")) {
                    for (int i = 0; i < response.body().getMessage().getList().size(); i++) {
                        String title = response.body().getMessage().getList().get(i).getTitle();
                        String Ids = response.body().getMessage().getList().get(i).getId();
                        info.add(title);
                        map.put(title,Ids);
                    }
                    if (response.body().getMessage().getList().size()==0){
                        info.add("Coinbkb");
                        map.put("Coinbkb","");
                    }
                    marqueeView.startWithList(info);
                    // 在代码里设置自己的动画
                    marqueeView.startWithList(info, R.anim.anim_bottom_in, R.anim.anim_top_out);

                }
            }

            @Override
            public void onFailure(Call<LunBoBean> call, Throwable t) {
                Log.e("AAAAA", "@@11=" + t.getMessage());


            }
        });
    }
    //填充list数据
    private void setList(final List<LunBoTopBean.Bean> news) {
        if (hlist != null) {
            hlist.clear();
        }
        hlist.addAll(news);
        Log.e("AAAAADSDDS","轮播="+hlist.size());
        List<String> list1 = new ArrayList<>();
        final List<String> list11 = new ArrayList<>();
  for (int i=0;i<hlist.size();i++){
      Log.e("AAAAADSDDS",i+"轮播="+hlist.get(i).getPic());
      list1.add(hlist.get(i).getPic());
      //list11.add(hlist.get(i).getId());
  }
        WebBannerAdapter webBannerAdapter=new WebBannerAdapter(getActivity(),list1);
   /*     webBannerAdapter.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(new Intent(getActivity(),GuangGaoActivity.class).putExtra("titles","gonggao")
                        .putExtra("Ids",""+list11.get(position)));
                // Toast.makeText(getActivity(), "点击了第  " + position+"  项", Toast.LENGTH_SHORT).show();
            }
        });*/
        recyclerBanner.setAdapter(webBannerAdapter);

        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(list1);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    /**
     * 轮播
     */
    private void NewListShow1() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(getActivity()).getService();
        Call<LunBoTopBean> indexdata = mFromServerInterface.getappPic();
        indexdata.enqueue(new Callback<LunBoTopBean>() {

            @Override
            public void onResponse(Call<LunBoTopBean> call, Response<LunBoTopBean> response) {
                if (response.body() == null) {

                    return;
                }Log.e("AAAAA", "轮播=" + response.body().getType());
                if (response.body().getType().equals("ok")) {
                    setList(response.body().getMessage());
                  //  Toast.makeText(getActivity(), response.body().getMessage().getList().get(0).getThumbnail(), Toast.LENGTH_SHORT).show();
                  //  Log.e("AAAADGV","hlist="+response.body().getMessage().getList().size());
                }
            }

            @Override
            public void onFailure(Call<LunBoTopBean> call, Throwable t) {
                Log.e("AAAAA", "@@11=" + t.getMessage());


            }
        });
    }
    /**交易账号*/
    private void NewListShow2() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(getActivity()).getService();
        Call<ZiChanBean> indexdata = mFromServerInterface.getZiChan();
        indexdata.enqueue(new Callback<ZiChanBean>() {

            @Override
            public void onResponse(Call<ZiChanBean> call, Response<ZiChanBean> response) {
                if (response.body() == null) {

                    return;
                }Log.e("AAAAA", "@@11=" + response.body().getType());
                if (response.body().getType().equals("ok")) {
                    /*login_ll.setVisibility(View.GONE);
                    login_l.setVisibility(View.VISIBLE);*/
                    Log.e("AAAAA", "@@11=" + response.body().getType());
                    jiaoyi.setText(response.body().getMessage().getChange_wallet().getTotal());
                    jiaoyi1.setText("≈"+response.body().getMessage().getChange_wallet().getTotalCNY()+"  CNY");
                    fabi.setText(response.body().getMessage().getLegal_wallet().getTotal());
                    fabi1.setText("≈"+response.body().getMessage().getLegal_wallet().getTotalCNY()+" CNY");
                }
            }

            @Override
            public void onFailure(Call<ZiChanBean> call, Throwable t) {
                Log.e("AAAAA", "@@11=" + t.getMessage());
              /*  login_ll.setVisibility(View.VISIBLE);
                login_l.setVisibility(View.GONE);*/


            }
        });
    }

    //涨幅榜
    private void TiBiListShow() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(getActivity()).getService();
        Call<ZhangFuBean> indexdata = mFromServerInterface.getquotation_sort();
        indexdata.enqueue(new Callback<ZhangFuBean>() {

            @Override
            public void onResponse(Call<ZhangFuBean> call, Response<ZhangFuBean> response) {
                if (response.body() == null) {

                    return;
                }
                if (response.body().getType().equals("ok")) {
                    zfsetList(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ZhangFuBean> call, Throwable t) {
                Log.e("AAAAA", "@@11=" + t.getMessage());
            }
        });
    }
    //填充数据 list数据
    private void zfsetList(final List<ZhangFuBean.dataBean> news) {
        if (zflist != null) {
            zflist.clear();
        }
        zflist.addAll(news);
        FragmentZFAdapter fragmentZFAdapter=new FragmentZFAdapter(getActivity(),zflist);
        listview.setAdapter(fragmentZFAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //startActivity(new Intent(getActivity(),KLineActivity.class));
                dialog.showLoading();
                if (MyApplication.Authori.equals("")){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                long curClickTime = System.currentTimeMillis();
                if((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
                    // 超过点击间隔后再将lastClickTime重置为当前点击时间
                    lastClickTime = curClickTime;

                    UserBean.LegalIDs=zflist.get(position).getLegal_id();
                    UserBean.CurrencyDs=zflist.get(position).getCurrency_id();
                    UserBean.LegalNames= zflist.get(position).getLegal_name();
                    UserBean.CurrencyNames=zflist.get(position).getCurrency_name();
                    KTititlesBean.Titles = UserBean.CurrencyNames+ "/" + UserBean.LegalNames;
                    Log.e("涨幅榜","KTititlesBean.Titles"+KTititlesBean.Titles );
                    KLineManager.getInstance().mCurrencyId = String.valueOf( UserBean.CurrencyDs);
                    KLineManager.getInstance().mLegalId = String.valueOf(UserBean.LegalIDs);
                    KLineManager.getInstance().mSymbolName = UserBean.CurrencyNames+ "/" + UserBean.LegalNames;
                    UserBean.HuoQuShow(getActivity(), UserBean.CurrencyDs,UserBean.LegalIDs);
                    UserBean.FanYongShow(getActivity(),UserBean.CurrencyDs,UserBean.LegalIDs);
                    KShow(UserBean.CurrencyNames + "/" + UserBean.LegalNames);
                    KLineActivity.open(getActivity());
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("SASA", "onResume: " );
        dialog= new RefreshDialog(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("SASA", "onPause: " );
        dialog.hideLoading();
    }

    class GlideImageLoader  extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */
            // eg：

            //Glide 加载图片简单用法
            Glide.with(context).load(path).into(imageView);

            //Picasso 加载图片简单用法
            //  Picasso.with(context).load(path).into(imageView);

            //用fresco加载图片简单用法，记得要写下面的createImageView方法
            Uri uri = Uri.parse((String) path);
            imageView.setImageURI(uri);
        }

        //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
    /*@Override
    public ImageView createImageView(Context context) {
        //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
        SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
        return simpleDraweeView;
    }*/
    }
}
