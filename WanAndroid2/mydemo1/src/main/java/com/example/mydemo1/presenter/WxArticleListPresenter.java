package com.example.mydemo1.presenter;

import com.example.mydemo1.base.BasePresenter;
import com.example.mydemo1.bean.WxArticleListBean;
import com.example.mydemo1.contract.WxArticleListContract;
import com.example.mydemo1.model.WxArticleListModel;

public class WxArticleListPresenter<V extends WxArticleListContract.WxArticleListView> extends BasePresenter<V>
        implements WxArticleListContract.WxArticleListPresenter, WxArticleListContract.WxArticleListModel.CallBack {

    public WxArticleListContract.WxArticleListModel wxArticleListModel = new WxArticleListModel();

    @Override
    public void onWxArticleListhttp(int cid) {
        if (mView != null) {
            wxArticleListModel.getWxArticleListData(this, cid);
        }
    }

    @Override
    public void onWxArticleListSuccess(WxArticleListBean wxArticleListBean) {
        mView.onWxArticleListSuccess(wxArticleListBean);
    }

    @Override
    public void onWxArticleListFail(String msg) {
mView.onWxArticleListFail(msg);
    }
}
