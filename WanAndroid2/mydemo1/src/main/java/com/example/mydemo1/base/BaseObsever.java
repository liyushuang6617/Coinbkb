package com.example.mydemo1.base;


import com.example.mydemo1.http.ApiException;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

public abstract class BaseObsever<T> implements Observer<T> {

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void onSubscribe(Disposable d) {
        compositeDisposable.add(d);
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ApiException) {
            ApiException apiException = (ApiException) e;
            int errorCode = apiException.getErrorCode();
            switch (errorCode) {
                case 1:
                break;
            }
            onFail(apiException.getErrorMsg());
        } else if (e instanceof HttpException) {
            onFail(e.getMessage());
        }
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    @Override
    public void onComplete() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    public abstract void onSuccess(T data);

    public abstract void onFail(String error);
}
