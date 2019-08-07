package com.example.mydemo1.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

public abstract class BaseActivity
        <V, P extends BasePresenter<V>> extends SimpleActivity {

    public P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //在super上面是因为这个逻辑在onCreate()上面
        mPresenter = createPresenter();
        super.onCreate(savedInstanceState);

        //V层
        if (mPresenter != null) {
            mPresenter.attach((V) this);
        }
    }

    //显示进度
    public void showProgressBar() {

    }

    //隐藏进度
    public void hiderogressBar() {

    }

    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //避免内存溢出
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
