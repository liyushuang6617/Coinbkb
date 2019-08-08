package com.example.mydemo1.presenter;

import com.example.mydemo1.base.BasePresenter;
import com.example.mydemo1.bean.ArticleItemBean;
import com.example.mydemo1.bean.SearchDataBean;
import com.example.mydemo1.contract.SearchContract;
import com.example.mydemo1.model.SearchModel;

import java.util.List;

public class SearchPresenter<V extends SearchContract.SearchView> extends BasePresenter<V>
        implements SearchContract.SearchPresenter, SearchContract.SearchModel.CallBack {

    public SearchContract.SearchModel searchModel = new SearchModel();

    @Override
    public void onSearchhttp(String key) {
        if (mView != null) {
            searchModel.getSearchData(this, key);
        }
    }

    @Override
    public void onSearchSuccess(List<SearchDataBean> searchDataBeans) {
        mView.onSearchSuccess(searchDataBeans);
    }

    @Override
    public void onSearchFail(String msg) {
        mView.onSearchFail(msg);
    }
}
