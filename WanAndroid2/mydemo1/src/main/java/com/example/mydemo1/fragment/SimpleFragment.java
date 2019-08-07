package com.example.mydemo1.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class SimpleFragment extends Fragment {

    private Unbinder bind;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(createLayout(), null);

        //绑定布局id
        bind = ButterKnife.bind(this, view);

        //注册
        EventBus.getDefault().register(this);

        initViewAndData();
        return view;
    }

    protected abstract void initViewAndData();

    protected abstract int createLayout();

    @Override
    public void onDestroy() {
        super.onDestroy();
        //销毁
        if(bind!=null){
            bind.unbind();
        }
        //解绑
        EventBus.getDefault().unregister(this);
    }
}
