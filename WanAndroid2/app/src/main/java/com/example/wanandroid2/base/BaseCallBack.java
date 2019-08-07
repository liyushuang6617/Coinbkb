package com.example.wanandroid2.base;

public interface BaseCallBack<T> {

    void onSuccess(T t);

    void onFail(String msg);
}
