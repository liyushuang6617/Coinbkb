package com.example.mydemo1.presenter;

import com.example.mydemo1.base.BasePresenter;
import com.example.mydemo1.bean.WxArticleDataTabBean;
import com.example.mydemo1.contract.WxArticleContract;
import com.example.mydemo1.model.WxArticleModel;

import java.util.List;

public class WxArticlePresenter<V extends WxArticleContract.WxArticleView> extends BasePresenter<V>
        implements WxArticleContract.WxArticlePresenter, WxArticleContract.WxArticleModel.CallBack {

    public WxArticleContract.WxArticleModel wxArticleModel = new WxArticleModel();


    @Override
    public void onWxArticlehttp() {
        if (mView != null) {
            wxArticleModel.getWxArticleData(this);
        }
    }

    @Override
    public void onWxArticleSuccess(List<WxArticleDataTabBean> wxArticleDataBean) {
        mView.onWxArticleSuccess(wxArticleDataBean);
    }

    @Override
    public void onWxArticleFail(String msg) {
        mView.onWxArticleFail(msg);
    }

}
