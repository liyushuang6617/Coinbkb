package com.example.wanandroid2.base;

public abstract class BaseMvpActivity
        <P extends BasePresenter,V extends BaseView,M extends BaseModel> extends BaseActivtity{

    protected P myPresenter;

    @Override
    protected void initMvp() {
        super.initMvp();
        myPresenter = initMvpPresenter();

        if(myPresenter!=null){
            myPresenter.addModel(initMvpModel());
            myPresenter.attachView(initMvpView());
        }
    }

    protected abstract M initMvpModel();

    protected abstract V initMvpView();

    protected abstract P initMvpPresenter();
}
