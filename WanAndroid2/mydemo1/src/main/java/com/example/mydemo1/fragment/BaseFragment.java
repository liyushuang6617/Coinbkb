package com.example.mydemo1.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mydemo1.base.BasePresenter;

public abstract class BaseFragment
        <V, P extends BasePresenter<V>> extends SimpleFragment {

    public P mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mPresenter = createPresenter();
        if(mPresenter!=null){
            mPresenter.attach((V) this);
        }
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    protected abstract P createPresenter();

    //显示进度
    public void showProgressBar() {

    }

    //隐藏进度
    public void hiderogressBar() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPresenter!=null){
            mPresenter.detachView();
        }
    }
}
