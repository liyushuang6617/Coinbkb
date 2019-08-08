package com.example.wanandroid2.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wanandroid2.R;
import com.example.wanandroid2.adapter.RlvArticelItemAdapter;
import com.example.wanandroid2.api.MyService;
import com.example.wanandroid2.base.BaseObsever;
import com.example.wanandroid2.base.BaseResponse;
import com.example.wanandroid2.bean.ArticleItemData;
import com.example.wanandroid2.bean.ArticleListData;
import com.example.wanandroid2.bean.BannerData;
import com.example.wanandroid2.bean.BannerGlideImageLoader;
import com.example.wanandroid2.http.HttpManager;
import com.example.wanandroid2.utils.Constans;
import com.example.wanandroid2.utils.Constants;
import com.example.wanandroid2.utils.MainUtils;
import com.example.wanandroid2.utils.RxUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private View view;
    private RecyclerView mMainRecycleView;
    private SmartRefreshLayout mSmartRefreshLayout;
    private RlvArticelItemAdapter adapter;
    private Banner mBanner;
    private static final String TAG = "MainFragment";
    private ArrayList<ArticleItemData> mArticleList;
    private ArrayList<String> mBannerTitleList;
    private ArrayList<Integer> bannerIdList;
    private ArrayList<String> mBannerUrlList;
    private ArrayList<BannerData> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_main, container, false);
        initView(inflate);
        initData();
        return inflate;
    }

    private void initData()
    {
        HttpManager.getInstance().getApiService(MyService.class).getTopArticles()
                .compose(RxUtils.<BaseResponse<List<ArticleItemData>>>rxScheduleThread())
                .compose(RxUtils.<List<ArticleItemData>>changeResult())
                .subscribe(new Observer<List<ArticleItemData>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<ArticleItemData> articleItemData) {
                        if (articleItemData != null) {
                            mArticleList.addAll(articleItemData);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        HttpManager.getInstance().getApiService(MyService.class).getBannerData()
                .compose(RxUtils.<BaseResponse<List<BannerData>>>rxScheduleThread())
                .compose(RxUtils.<List<BannerData>>changeResult())
                .subscribe(new Observer<List<BannerData>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<BannerData> bannerData) {
                        if (bannerData != null) {
                            list.addAll(bannerData);
                            showBannerData(list);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initView(View inflate) {
        mMainRecycleView = (RecyclerView) inflate.findViewById(R.id.main_recycle_view);
        mSmartRefreshLayout = (SmartRefreshLayout) inflate.findViewById(R.id.smart_refresh_layout);

        initRecyclerView();
        initRefreshLayout();
    }

    private void initRefreshLayout() {

    }

    private void initRecyclerView() {
        mMainRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mArticleList = new ArrayList<>();

        //管理适配器
        adapter = new RlvArticelItemAdapter(R.layout.item_artice_list, mArticleList);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                tartArticleDetailPager(view, position);
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                clickChildEvent(view, position);
            }
        });
        //不影响Item的宽高
        mMainRecycleView.setHasFixedSize(true);

        //添加banner布局
        LinearLayout mHeaderGroup = (LinearLayout) getLayoutInflater().inflate(R.layout.item_artice_banner, null);
        mBanner = mHeaderGroup.findViewById(R.id.head_banner);
        //将原来布局中的banner删除
        mHeaderGroup.removeView(mBanner);
        adapter.setHeaderView(mBanner);
        mMainRecycleView.setAdapter(adapter);

        list = new ArrayList<>();

    }

    private void clickChildEvent(View view, int position) {
        switch (view.getId()) {
            case R.id.tv_article_chapterName:
                //todo chapter click
                break;
            case R.id.iv_article_like:
                collectClickEvent(position);
                break;
            case R.id.tv_article_tag:
                //todo tag click
                break;
            default:
                break;
        }
    }

    private void collectClickEvent(int position) {

    }

    private void tartArticleDetailPager(View view, int position) {
        if(adapter.getData().size() <= 0 ||adapter.getData().size() < position){
            return;
        }
        MainUtils.startArticleDetailActivity(getActivity()
        ,adapter.getData().get(position).getId()
        ,adapter.getData().get(position).getTitle()
        ,adapter.getData().get(position).getLink()
        ,adapter.getData().get(position).isCollect()
        ,true,position,Constants.MAIN_PAGER);
    }

    private void showBannerData(ArrayList<BannerData> list) {
        mBannerTitleList = new ArrayList<>();
        List<String> bannerImageList = new ArrayList<>();
        bannerIdList = new ArrayList<>();
        mBannerUrlList = new ArrayList<>();
        for (BannerData bannerData : list) {
            mBannerTitleList.add(bannerData.getTitle());
            bannerImageList.add(bannerData.getImagePath());
            mBannerUrlList.add(bannerData.getUrl());
            bannerIdList.add(bannerData.getId());
        };

        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        //设置图片加载器
        mBanner.setImageLoader(new BannerGlideImageLoader());
        //设置图片集合
        mBanner.setImages(bannerImageList);
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.Accordion);
        //设置标题集合（当banner样式有显示title时）
        mBanner.setBannerTitles(mBannerTitleList);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(2500);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int i) {
                MainUtils.startArticleDetailActivity(getActivity(), bannerIdList.get(i)
                        , mBannerTitleList.get(i), mBannerUrlList.get(i), false, false
                        , -1, Constants.TAG_DEFAULT);
            }
        });
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
    }
}
