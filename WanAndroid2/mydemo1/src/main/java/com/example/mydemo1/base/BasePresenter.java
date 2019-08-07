package com.example.mydemo1.base;

import java.lang.ref.WeakReference;

public class BasePresenter<V> {

    //内存溢出解决方案
    private WeakReference<V> weakReference;

    public V mView;

    public void attach(V view) {
        weakReference = new WeakReference<>(view);
        mView = weakReference.get();
    }

    public void detachView() {
        if (weakReference != null) {
            weakReference.clear();
        }
    }
}
