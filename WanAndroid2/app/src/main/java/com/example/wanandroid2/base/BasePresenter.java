package com.example.wanandroid2.base;

public class BasePresenter<V extends BaseView, M extends BaseModel> {

    protected M myModel;
    protected V myView;

    public void addModel(M m) {
        this.myModel = m;
    }

    public void attachView(V v) {
        this.myView = v;
    }
}
